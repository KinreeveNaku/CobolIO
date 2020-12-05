/**
 * 
 */
package com.github.cobolio.internal.cobol;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import static com.github.cobolio.internal.cobol.PrimitiveConstants.*;

/**
 * A core utility class that contains various methods for external decimal,
 * Unicode decimal, and Computational 1 through 5 logic, and conversions from
 * hexadecimal to binary, binary to hexadecimal,
 * 
 * @author Andrew
 *
 */
public final class CobolTypeLogic {
	/*
	 * TEN_# are just for sake of sanity
	 */
	private static final long TEN_1 = 10;
	private static final long TEN_2 = 100;
	private static final long TEN_3 = 1000;
	private static final long TEN_4 = 10000;
	private static final long TEN_5 = 100000;
	private static final long TEN_6 = 1000000;
	private static final long TEN_7 = 10000000;
	private static final long TEN_8 = 100000000;
	private static final long TEN_9 = 1000000000;
	
	private static final byte[] P2E_LOOKUP;
	private static final byte[] E2U_LOOKUP;
	static {
		P2E_LOOKUP = new byte[512];
		E2U_LOOKUP = new byte[256];
		Arrays.fill(P2E_LOOKUP, (byte) 0);
		Arrays.fill(E2U_LOOKUP, (byte) 0);

		int i;
		int j;
		int low;
		for (i = 0; i < 10; ++i) {
			for (j = 0; j < 10; ++j) {
				low = i * 16 + j;
				P2E_LOOKUP[low * 2] = (byte) (240 | i);
				P2E_LOOKUP[low * 2 + 1] = (byte) (240 | j);
			}
		}

		for (i = 0; i < 10; ++i) {
			j = 240 | i;
			E2U_LOOKUP[j + 1] = (byte) (48 | i);
		}
	}

	/**
	 * 
	 */
	private CobolTypeLogic() {
		throw new IllegalStateException();
	}

	/*
	 * General Logic
	 */
	
	
	/*
	 * Packed Decimal Logic
	 */
	/**
	 * Returns the number of bytes in a packed decimal, given its precision (total
	 * digit length).
	 * 
	 * @param precision The total count of literal digits.
	 * @return The packed decimal length (storage-length) for the given precision.
	 */
	public static int getPackedByteCount(int precision) {
		return precision / 2 + 1;//need clarification from ((x/2)-1), ((x/2)-2), and ((x/2)+2)
	}
	
	public static int getPackedLengthA(int length) {
		return (length * 2) - 1;
	}
	//Need to infer use-case for each function. Why are there different equations? y = (x/2)+1 vs x = 2(y-1) makes some sense because packed is half the unpacked length, give or take one depending on length and sign...
	public static int getPackedLengthB(int precision) {
		return (precision + 2) / 2;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public static byte getSign(int i) {
		return (i != PACKED_NEGATIVE_SIGN && i != PACKED_OTHER_NEGATIVE_SIGN ? PACKED_PLUS : PACKED_NEGATIVE_SIGN);
	}

	/**
	 * <div>Returns the signum function of the sign byte of the specified packed decimal.
	 * (The return value is -1 if the specified value is negative; and 1 if the
	 * specified value is positive.)</div>
	 * <div style="font-weight: 700;">(Not to be confused with signext/sext/sign
	 * extension, which is for expanding the bit count of a number while preserving
	 * sign)</div>
	 * 
	 * @param b The byte to evaluate the sign nibble of.
	 * @return the signum of the packed byte <code>b</code>.
	 */
	public static int packedSignum(byte b) {
		return getSign(b & LOW_NIBBLE_MASK) == PACKED_NEGATIVE_SIGN ? -1 : 1;
	}
	public static BigDecimal slowSignedPackedDecimalToBigDecimal(byte[] byteArray, int offset, int precision, int scale) {
		int length = (precision + TWO) / TWO;
		int sign = byteArray[offset + length - 1] & LOW_NIBBLE_MASK;
		char[] temp = new char[length * TWO];
		temp[0] = (char) (sign != PACKED_NEGATIVE_SIGN && sign != PACKED_OTHER_NEGATIVE_SIGN ? ASCII_ZED : ASCII_MINUS);

		for (int i = 0; i < length - 1; ++i) {
			temp[TWO * i + 1] = (char) (ASCII_ZED + ((byteArray[i + offset] >>> NIBBLE_LENGTH) & (LOW_NIBBLE_MASK & 0xFF)));
			temp[TWO * i + TWO] = (char) (ASCII_ZED + (byteArray[i + offset] & LOW_NIBBLE_MASK));
		}

		temp[length * TWO - 1] = (char) (HIGH_THREE + ((byteArray[offset + length - 1] >>> NIBBLE_LENGTH) & (LOW_NIBBLE_MASK & 0xFF)));
		return new BigDecimal(new BigInteger(new String(temp)), scale);
	}
	
	public static void bigDecimalToSignedPackedDecimal(BigDecimal bd, byte[] byteArray, int offset, int precision,
			boolean checkOverflow) {
		if (checkOverflow && precision < bd.precision()) {
			throw new ArithmeticException("Decimal overflow - precision of result Packed Decimal lesser than BigDecimal precision");
		} else {
			BigInteger value = bd.unscaledValue();
			char[] buffer = value.abs().toString().toCharArray();
			int numDigitsLeft = buffer.length - 1;
			int endPosition = numDigitsLeft % 2;
			int length = (precision + 2) / 2;
			int index = length - 2;
			byteArray[offset + length - 1] = (byte) (buffer[numDigitsLeft] - ASCII_ZED << NIBBLE_LENGTH);
			byteArray[offset + length - 1] = (byte) (byteArray[offset + length - 1] | (value.signum() == -1 ? PACKED_NEGATIVE_SIGN : PACKED_POSITIVE_SIGN));

			for (int i = numDigitsLeft - 1; i >= endPosition && offset + index >= 0; --index, i-=2) {
				byteArray[offset + index] = (byte) (buffer[i] - ASCII_ZED);
				byteArray[offset + index] |= (byte) ((buffer[i - 1] - ASCII_ZED) << NIBBLE_LENGTH);
			}
			if (endPosition > 0 && offset + index >= 0) {
				byteArray[offset + index] = (byte) (buffer[0] - ASCII_ZED);
			}

		}
	}
	
	/**
	 * This method displays a simplified explanation of how COBOL Packed Decimal
	 * value is converted into a Java datatype.
	 * 
	 * The overall process is to extract the sign nibble from the byte array, and
	 * extract each of the digit nibbles, then interpret this data along with the
	 * scale and precision.
	 * 
	 * The first step is to extract the sign. This can be done in one of two ways.
	 * Either have some argument indicate where the sign nibble is and fetch it
	 * directly, or extract it through an exhaustive search of the byte array. Both
	 * have their inherent flaws.
	 * 
	 * The next step is to iteratively break down each of the bytes into nibbles in
	 * the byte array, excluding the sign nibble, and convert them into numeric
	 * symbols. There are countless ways to do this. After this is done, the numeric
	 * symbols need to be sequentially joined to create one complete numeric
	 * literal, with each of the decimal places being represented by its
	 * corresponding nibble.
	 * 
	 * <pre>
	 * Note: Assume precision is 15
	 * 
	 * [C8][32][18][56][23][99][17][40]
	 *  ^^  ^^  ^^  ^^  ^^  ^^  ^^  ^^
	 *  ||  ||  ||  ||  ||  ||  ||  ||
	 *  V----------VVVVVV-------------
	 *  S          Digits
	 * </pre>
	 * 
	 * S is the sign nibble in this instance and Digits are the nibbles representing
	 * each digit of the translated value In COBOL packed decimal logic, C is the
	 * hexadecimal numeric for POSITIVE, so this value is positive. The final number
	 * would be +832185623991740.
	 * 
	 * Depending on the precision, this could change slightly though. If the
	 * precision is 14 for example, then that last nibble 0 should get skipped,
	 * which would mean the translated value is actually +83218562399174 and a
	 * precision of 15 would result in +832185623991740
	 * 
	 * Care must be taken when performing this logic however. Because if the sign
	 * nibble is at the end, then under-developed logic could unintentionally
	 * truncate the sign nibble. e.g.:
	 * 
	 * <pre>
	 * [83][21][85][62][39][91][74][0C]
	 * </pre>
	 * 
	 * leading to 832185623991740 where the precision is 14 and no sign. No error
	 * would be thrown but the result is incorrect.
	 * 
	 * 
	 * <div> Finally if the packed decimal value has a scale greater than 0, then
	 * the decimal point must be correctly positioned to the proper location. So if
	 * the value has a scale of 5 and a precision of 15, then the final value should
	 * be </div>
	 * 
	 * <pre>
	 * [83][21][85][62][39][91][74][0C] == +8321856239.91740
	 * </pre>
	 * 
	 * 
	 * @param bytes The byte[] containing the packed decimal value in its raw form.
	 * @param scale The number of fractional digits
	 * @return Returns a BigDecimal value representing the value stored as a Packed
	 *         Decimal.
	 * @category Confirmed
	 */
	public static final BigDecimal unpack(byte[] bytes, int scale) {//Needs precision argument for boundary support
		//If sign byte high nibble is non-zero, precision must be taken into account
		char sign = 0;
		char[] chars = new char[(bytes.length * 2) + 1];//Rough approximation of precision. String trimming accounts for inaccuracy.
		int i = 1;
		for(byte b : bytes) {
			if((b & 0xF0) > 0xA0) {//isSign, (? > A0) is any hexadecimal letter value for the HO Nibble
				sign = packedSignum((byte) (b >> 4)) == 1 ? '+' : '-';//if sign nibble is 'C', return '+'. If sign nibble is B or D, return '-'. 
				chars[i] = (char) ((b & 0x0F) + 48);//Get LO Nibble and convert to ASCII numeric.
				i++;
			} else if((b & 0x0F) > 0x0A) {//isSign, 0A is any hexadecimal letter value for the LO Nibble
				sign = packedSignum(b) == 1 ? '+' : '-';//if sign nibble is 'C', return '+'. If sign nibble is B or D, return '-'.
				chars[i] = (char) (((b & 0xF0) >> 4) + 48);//Get HO Nibble and convert to ASCII numeric.
				i++;
			} else {//Not a sign byte, perform standard handling.
				chars[i] = (char) (((b & 0xF0) >> 4) + 48);//Get HO Nibble and convert to ASCII numeric.
				i++;
				chars[i] = (char) ((b & 0x0F) + 48);//Get LO Nibble and convert to ASCII numeric.
				i++;
			}
		}
		chars[0] = sign != 0 ? sign : '+';//set start of string to the interpolated sign.
		return new BigDecimal(new BigInteger(new String(chars).trim()), scale);//convert to unscaled BigInteger, and then to scaled BigDecimal.
	}
	
	public static byte[] pack(Number n, int decimalType) {
		String unpackedString;
		if(n instanceof BigDecimal) {
			unpackedString = ((BigDecimal) n).toPlainString();
		} else {
			unpackedString = n.toString();
		}
		if(unpackedString.contains(".")) {
			unpackedString = unpackedString.substring(0, unpackedString.indexOf('.')).concat(unpackedString.substring(unpackedString.indexOf('.') + 1));
		}
		int decant = (unpackedString.contains("+") || unpackedString.contains("-")) ? 1 : 0;
		char[] chars = unpackedString.toCharArray();
		byte[] bytes = new byte[((unpackedString.length() - decant) / TWO) + 1];//The byte array that will be returned
		byte[] nibbles = new byte[unpackedString.length()];//An array containing each individual nibble as its own byte value
		int signPosition = signPositionByType(decimalType, nibbles.length);
		for(int i = 0, j = 0; i < chars.length; i++, j++) {
			if(chars[i] == '+') {
				nibbles[signPosition] = 0x0F;//If the character is +, then set the sign nibble to F
				//below we are decrementing the nibbles array index back one so we don't skip over an index without storing anything 
				j--;//TODO How can this be moved back into the for loop logic
				
			} else if(chars[i] == '-') {
				nibbles[signPosition] = 0x0D;//If the character is -, then set the sign nibble to D
				//below we are decrementing the nibbles array index back one so we don't skip over an index without storing anything
				j--;//TODO How can this be moved back into the for loop logic
			} else if(chars[i] == '.') {//If the character is '.', disregard it 
				//Just skip it
			} else {
				//Assuming contents were checked, this must be a numeric ascii character, so subtracting 48 will give us the corresponding binary representation of this character.
				nibbles[j] = (byte) (chars[i] - 48);
			}
		}
		//FIXED Correct until here...
		int i = 0;
		int j = 0;
		while(i < nibbles.length) {
			if(i % 2 == 0) {//true if i is even
				//Flip-flopping between the high order and low order nibble of each byte in 'bytes'.
				bytes[j] = (byte) ((bytes[j] & 0x0F) |(byte) (nibbles[i] << 4));
			} else {//true if i is odd
				bytes[j] = (byte) ((bytes[j] & 0xF0) | nibbles[i]);
				j++;//This is the low order byte, so increment to the next byte on the next iteration
			}
			i++;//increment to the next nibble position on the next iteration
		}
		return bytes;
	}
	
	/**
	 * Returns the position in an array of nibble values where the sign is expected to be.
	 * @param decimalType The type of decimal storage style.
	 * @param byteArrayLength The length of the array of nibbles
	 * @return Returns the index where the sign nibble is expected to be.
	 */
	private static final int signPositionByType(int decimalType, int byteArrayLength) {
		switch(decimalType) {
		case EBCDIC_SIGN_EMBEDDED_TRAILING:
			return byteArrayLength - 1;
		case EBCDIC_SIGN_EMBEDDED_LEADING:
			return 0;
		case EBCDIC_SIGN_SEPARATE_TRAILING:
			return byteArrayLength - 1;
		case EBCDIC_SIGN_SEPARATE_LEADING:
			return 0;
		default:
			throw new IllegalArgumentException();
				
		}
	}

	/*
	 * External Decimal Logic
	 */

	/*
	 * Unicode Decimal Logic
	 */

	/*
	 * Binary Logic
	 */
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "java:S3776", "java:S3358", "java:S109" })
	public static int numDigits(int value) {
		value = value == Integer.MIN_VALUE ? Integer.MAX_VALUE : Math.abs(value);
		return (value >= TEN_4
				? (value >= TEN_7 ? (value >= TEN_8 ? (value >= TEN_9 ? 10 : 9) : 8)
						: (value >= TEN_5 ? (value >= TEN_6 ? 7 : 6) : 5))
				: (value >= TEN_2 ? (value >= TEN_3 ? 4 : 3) : (value >= TEN_1 ? 2 : 1)));
	}

	/**
	 * Returns the display-length of the long argument. The range will be between 18
	 * and 1.
	 * 
	 * @param value
	 * @return Returns the display-length of the long argument.
	 */
	@SuppressWarnings({ "java:S3776", "java:S3358", "java:S109" })
	public static int numDigits(long value) {
		value = value == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(value);
		return value >= 1000000000L
				? (value >= 100000000000000L ? (value >= 10000000000000000L
						? (value >= 100000000000000000L ? (value >= 1000000000000000000L ? 19 : 18) : 17)
						: (value >= 1000000000000000L ? 16 : 15))
						: (value >= 100000000000L
								? (value >= 1000000000000L ? (value >= 10000000000000L ? 14 : 13) : 12)
								: (value >= 10000000000L ? 11 : 10)))
				: (value >= TEN_4
						? (value >= TEN_7 ? (value >= TEN_8 ? (value >= TEN_9 ? 10 : 9) : 8)
								: (value >= TEN_5 ? (value >= TEN_6 ? 7 : 6) : 5))
						: (value >= TEN_2 ? (value >= TEN_3 ? 4 : 3) : (value >= TEN_1 ? 2 : 1)));
	}

	/*
	 * Hexadecimal Logic
	 */

	
	/**
	 * 
	 * Utility class containing various methods for converting byte arrays into
	 * different numeric datatypes.
	 *
	 */
	public static final class ByteArrayMarshaller {
		/**
		 * 
		 */
		private static final String NUM_BYTES = "numBytes == ";
		/**
		 * 
		 */
		private static final String ACCESS_INDEX_MUST_BE_POSITIVE_OR_ZERO = "Access index must be positive or zero.";
		/**
		 * 
		 */
		private static final String BUT_VALID_INDICES_ARE_FROM_0_TO2 = "] but valid indices are from 0 to ";
		/**
		 * 
		 */
		private static final String TO_BYTE_ARRAY = "] to byteArray[";
		/**
		 * 
		 */
		private static final String BUT_VALID_INDICES_ARE_FROM_0_TO = "],  but valid indices are from 0 to ";
		
		private ByteArrayMarshaller() {
			throw new IllegalAccessError();
		}
		
		public static void writeShort(short value, byte[] byteArray, int offset, boolean bigEndian) {
			if (offset + 2 <= byteArray.length && offset >= 0) {
				writeShortUnchecked(value, byteArray, offset, bigEndian);
			} else {
				throw new ArrayIndexOutOfBoundsException(
						"Array access index out of bounds. writeShort is trying to access byteArray[" + offset
								+ "] and byteArray[" + (offset + 1) + BUT_VALID_INDICES_ARE_FROM_0_TO
								+ (byteArray.length - 1) + ".");
			}
		}

		private static void writeShortUnchecked(short value, byte[] byteArray, int offset, boolean bigEndian) {
			if (bigEndian) {
				byteArray[offset] = (byte) (value >> 8);
				byteArray[offset + 1] = (byte) value;
			} else {
				byteArray[offset + 1] = (byte) (value >> 8);
				byteArray[offset] = (byte) value;
			}

		}

		public static void writeShort(short value, byte[] byteArray, int offset, boolean bigEndian, int numBytes) {
			if (offset + numBytes > byteArray.length && numBytes > 0 && numBytes <= 2) {
				throw new ArrayIndexOutOfBoundsException(
						"Array access index out of bounds. writeShort is trying to access byteArray[" + offset
								+ TO_BYTE_ARRAY + (offset + numBytes - 1) + BUT_VALID_INDICES_ARE_FROM_0_TO2
								+ (byteArray.length - 1) + ".");
			} else if (offset < 0) {
				throw new ArrayIndexOutOfBoundsException(ACCESS_INDEX_MUST_BE_POSITIVE_OR_ZERO);
			} else if (numBytes >= 0 && numBytes <= 2) {
				writeShortUnchecked(value, byteArray, offset, bigEndian, numBytes);
			} else {
				throw new IllegalArgumentException(NUM_BYTES + numBytes);
			}
		}

		private static void writeShortUnchecked(short value, byte[] byteArray, int offset, boolean bigEndian, int numBytes) {
			switch (numBytes) {
			case 0 :
				break;
			case 1 :
				byteArray[offset] = (byte) value;
				break;
			case 2 :
				if (bigEndian) {
					byteArray[offset] = (byte) (value >> 8);
					byteArray[offset + 1] = (byte) value;
				} else {
					byteArray[offset + 1] = (byte) (value >> 8);
					byteArray[offset] = (byte) value;
				}
				break;
			default :
				break;
			}

		}

		public static void writeInt(int value, byte[] byteArray, int offset, boolean bigEndian) {
			if (offset + 4 <= byteArray.length && offset >= 0) {
				writeIntUnchecked(value, byteArray, offset, bigEndian);
			} else {
				throw new ArrayIndexOutOfBoundsException(
						"Array access index out of bounds. writeInt is trying to access byteArray[" + offset
								+ TO_BYTE_ARRAY + (offset + 3) + BUT_VALID_INDICES_ARE_FROM_0_TO
								+ (byteArray.length - 1) + ".");
			}
		}

		private static void writeIntUnchecked(int value, byte[] byteArray, int offset, boolean bigEndian) {
			if (bigEndian) {
				byteArray[offset] = (byte) (value >> 24);
				byteArray[offset + 1] = (byte) (value >> 16);
				byteArray[offset + 2] = (byte) (value >> 8);
				byteArray[offset + 3] = (byte) value;
			} else {
				byteArray[offset + 3] = (byte) (value >> 24);
				byteArray[offset + 2] = (byte) (value >> 16);
				byteArray[offset + 1] = (byte) (value >> 8);
				byteArray[offset] = (byte) value;
			}

		}

		public static void writeInt(int value, byte[] byteArray, int offset, boolean bigEndian, int numBytes) {
			if (offset + numBytes > byteArray.length && numBytes <= 4 && numBytes > 0) {
				throw new ArrayIndexOutOfBoundsException(
						"Array access index out of bounds. writeInt is trying to access byteArray[" + offset
								+ TO_BYTE_ARRAY + (offset + numBytes - 1) + BUT_VALID_INDICES_ARE_FROM_0_TO2
								+ (byteArray.length - 1) + ".");
			} else if (offset < 0) {
				throw new ArrayIndexOutOfBoundsException(ACCESS_INDEX_MUST_BE_POSITIVE_OR_ZERO);
			} else if (numBytes >= 0 && numBytes <= 4) {
				writeIntUnchecked(value, byteArray, offset, bigEndian, numBytes);
			} else {
				throw new IllegalArgumentException(NUM_BYTES + numBytes);
			}
		}

		private static void writeIntUnchecked(int value, byte[] byteArray, int offset, boolean bigEndian, int numBytes) {
			switch (numBytes) {
			case 0 :
				break;
			case 1 :
				byteArray[offset] = (byte) value;
				break;
			case 2 :
				if (bigEndian) {
					byteArray[offset] = (byte) (value >> 8);
					byteArray[offset + 1] = (byte) value;
				} else {
					byteArray[offset + 1] = (byte) (value >> 8);
					byteArray[offset] = (byte) value;
				}
				break;
			case 3 :
				if (bigEndian) {
					byteArray[offset] = (byte) (value >> 16);
					byteArray[offset + 1] = (byte) (value >> 8);
					byteArray[offset + 2] = (byte) value;
				} else {
					byteArray[offset + 2] = (byte) (value >> 16);
					byteArray[offset + 1] = (byte) (value >> 8);
					byteArray[offset] = (byte) value;
				}
				break;
			case 4 :
				if (bigEndian) {
					byteArray[offset] = (byte) (value >> 24);
					byteArray[offset + 1] = (byte) (value >> 16);
					byteArray[offset + 2] = (byte) (value >> 8);
					byteArray[offset + 3] = (byte) value;
				} else {
					byteArray[offset + 3] = (byte) (value >> 24);
					byteArray[offset + 2] = (byte) (value >> 16);
					byteArray[offset + 1] = (byte) (value >> 8);
					byteArray[offset] = (byte) value;
				}
				break;
			default :
				break;
			}

		}

		public static void writeLong(long value, byte[] byteArray, int offset, boolean bigEndian) {
			if (offset + 8 <= byteArray.length && offset >= 0) {
				writeLongUnchecked(value, byteArray, offset, bigEndian);
			} else {
				throw new ArrayIndexOutOfBoundsException(
						"Array access index out of bounds. writeLong is trying to access byteArray[" + offset
								+ TO_BYTE_ARRAY + (offset + 7) + BUT_VALID_INDICES_ARE_FROM_0_TO
								+ (byteArray.length - 1) + ".");
			}
		}

		private static void writeLongUnchecked(long value, byte[] byteArray, int offset, boolean bigEndian) {
			if (bigEndian) {//whether the order of bits should be reversed or not
				byteArray[offset] = (byte) ((int) (value >> 56));
				byteArray[offset + 1] = (byte) ((int) (value >> 48));
				byteArray[offset + 2] = (byte) ((int) (value >> 40));
				byteArray[offset + 3] = (byte) ((int) (value >> 32));
				byteArray[offset + 4] = (byte) ((int) (value >> 24));
				byteArray[offset + 5] = (byte) ((int) (value >> 16));
				byteArray[offset + 6] = (byte) ((int) (value >> 8));
				byteArray[offset + 7] = (byte) ((int) value);
			} else {
				byteArray[offset + 7] = (byte) ((int) (value >> 56));
				byteArray[offset + 6] = (byte) ((int) (value >> 48));
				byteArray[offset + 5] = (byte) ((int) (value >> 40));
				byteArray[offset + 4] = (byte) ((int) (value >> 32));
				byteArray[offset + 3] = (byte) ((int) (value >> 24));
				byteArray[offset + 2] = (byte) ((int) (value >> 16));
				byteArray[offset + 1] = (byte) ((int) (value >> 8));
				byteArray[offset] = (byte) ((int) value);
			}

		}

		public static void writeLong(long value, byte[] byteArray, int offset, boolean bigEndian, int numBytes) {
			if (offset + numBytes > byteArray.length && numBytes > 0 && numBytes <= 8) {
				throw new ArrayIndexOutOfBoundsException(
						"Array access index out of bounds. writeLong is trying to access byteArray[" + offset
								+ TO_BYTE_ARRAY + (offset + numBytes - 1) + BUT_VALID_INDICES_ARE_FROM_0_TO2
								+ (byteArray.length - 1) + ".");
			} else if (offset < 0) {
				throw new ArrayIndexOutOfBoundsException(ACCESS_INDEX_MUST_BE_POSITIVE_OR_ZERO);
			} else if (numBytes >= 0 && numBytes <= 8) {
				writeLongUnchecked(value, byteArray, offset, bigEndian, numBytes);
			} else {
				throw new IllegalArgumentException(NUM_BYTES + numBytes);
			}
		}

		private static void writeLongUnchecked(long value, byte[] byteArray, int offset, boolean bigEndian, int numBytes) {
			switch (numBytes) {
				case 0 :
				default :
					break;
				case 1 :
					byteArray[offset] = (byte) ((int) value);
					break;
				case 2 :
					if (bigEndian) {
						byteArray[offset] = (byte) ((int) (value >> 8));
						byteArray[offset + 1] = (byte) ((int) value);
					} else {
						byteArray[offset + 1] = (byte) ((int) (value >> 8));
						byteArray[offset] = (byte) ((int) value);
					}
					break;
				case 3 :
					if (bigEndian) {
						byteArray[offset] = (byte) ((int) (value >> 16));
						byteArray[offset + 1] = (byte) ((int) (value >> 8));
						byteArray[offset + 2] = (byte) ((int) value);
					} else {
						byteArray[offset + 2] = (byte) ((int) (value >> 16));
						byteArray[offset + 1] = (byte) ((int) (value >> 8));
						byteArray[offset] = (byte) ((int) value);
					}
					break;
				case 4 :
					if (bigEndian) {
						byteArray[offset] = (byte) ((int) (value >> 24));
						byteArray[offset + 1] = (byte) ((int) (value >> 16));
						byteArray[offset + 2] = (byte) ((int) (value >> 8));
						byteArray[offset + 3] = (byte) ((int) value);
					} else {
						byteArray[offset + 3] = (byte) ((int) (value >> 24));
						byteArray[offset + 2] = (byte) ((int) (value >> 16));
						byteArray[offset + 1] = (byte) ((int) (value >> 8));
						byteArray[offset] = (byte) ((int) value);
					}
					break;
				case 5 :
					if (bigEndian) {
						byteArray[offset] = (byte) ((int) (value >> 32));
						byteArray[offset + 1] = (byte) ((int) (value >> 24));
						byteArray[offset + 2] = (byte) ((int) (value >> 16));
						byteArray[offset + 3] = (byte) ((int) (value >> 8));
						byteArray[offset + 4] = (byte) ((int) value);
					} else {
						byteArray[offset + 4] = (byte) ((int) (value >> 32));
						byteArray[offset + 3] = (byte) ((int) (value >> 24));
						byteArray[offset + 2] = (byte) ((int) (value >> 16));
						byteArray[offset + 1] = (byte) ((int) (value >> 8));
						byteArray[offset] = (byte) ((int) value);
					}
					break;
				case 6 :
					if (bigEndian) {
						byteArray[offset] = (byte) ((int) (value >> 40));
						byteArray[offset + 1] = (byte) ((int) (value >> 32));
						byteArray[offset + 2] = (byte) ((int) (value >> 24));
						byteArray[offset + 3] = (byte) ((int) (value >> 16));
						byteArray[offset + 4] = (byte) ((int) (value >> 8));
						byteArray[offset + 5] = (byte) ((int) value);
					} else {
						byteArray[offset + 5] = (byte) ((int) (value >> 40));
						byteArray[offset + 4] = (byte) ((int) (value >> 32));
						byteArray[offset + 3] = (byte) ((int) (value >> 24));
						byteArray[offset + 2] = (byte) ((int) (value >> 16));
						byteArray[offset + 1] = (byte) ((int) (value >> 8));
						byteArray[offset] = (byte) ((int) value);
					}
					break;
				case 7 :
					if (bigEndian) {
						byteArray[offset] = (byte) ((int) (value >> 48));
						byteArray[offset + 1] = (byte) ((int) (value >> 40));
						byteArray[offset + 2] = (byte) ((int) (value >> 32));
						byteArray[offset + 3] = (byte) ((int) (value >> 24));
						byteArray[offset + 4] = (byte) ((int) (value >> 16));
						byteArray[offset + 5] = (byte) ((int) (value >> 8));
						byteArray[offset + 6] = (byte) ((int) value);
					} else {
						byteArray[offset + 6] = (byte) ((int) (value >> 48));
						byteArray[offset + 5] = (byte) ((int) (value >> 40));
						byteArray[offset + 4] = (byte) ((int) (value >> 32));
						byteArray[offset + 3] = (byte) ((int) (value >> 24));
						byteArray[offset + 2] = (byte) ((int) (value >> 16));
						byteArray[offset + 1] = (byte) ((int) (value >> 8));
						byteArray[offset] = (byte) ((int) value);
					}
					break;
				case 8 :
					if (bigEndian) {
						byteArray[offset] = (byte) ((int) (value >> 56));
						byteArray[offset + 1] = (byte) ((int) (value >> 48));
						byteArray[offset + 2] = (byte) ((int) (value >> 40));
						byteArray[offset + 3] = (byte) ((int) (value >> 32));
						byteArray[offset + 4] = (byte) ((int) (value >> 24));
						byteArray[offset + 5] = (byte) ((int) (value >> 16));
						byteArray[offset + 6] = (byte) ((int) (value >> 8));
						byteArray[offset + 7] = (byte) ((int) value);
					} else {
						byteArray[offset + 7] = (byte) ((int) (value >> 56));
						byteArray[offset + 6] = (byte) ((int) (value >> 48));
						byteArray[offset + 5] = (byte) ((int) (value >> 40));
						byteArray[offset + 4] = (byte) ((int) (value >> 32));
						byteArray[offset + 3] = (byte) ((int) (value >> 24));
						byteArray[offset + 2] = (byte) ((int) (value >> 16));
						byteArray[offset + 1] = (byte) ((int) (value >> 8));
						byteArray[offset] = (byte) ((int) value);
					}
			}

		}

		public static void writeFloat(float value, byte[] byteArray, int offset, boolean bigEndian) {
			if (offset + 4 <= byteArray.length && offset >= 0) {
				writeFloatUnchecked(value, byteArray, offset, bigEndian);
			} else {
				throw new ArrayIndexOutOfBoundsException(
						"Array access index out of bounds. writeFloat is trying to access byteArray[" + offset
								+ TO_BYTE_ARRAY + (offset + 3) + BUT_VALID_INDICES_ARE_FROM_0_TO
								+ (byteArray.length - 1) + ".");
			}
		}

		private static void writeFloatUnchecked(float value, byte[] byteArray, int offset, boolean bigEndian) {
			writeInt(Float.floatToIntBits(value), byteArray, offset, bigEndian);
		}

		public static void writeDouble(double value, byte[] byteArray, int offset, boolean bigEndian) {
			if (offset + 8 <= byteArray.length && offset >= 0) {
				writeDoubleUnchecked(value, byteArray, offset, bigEndian);
			} else {
				throw new ArrayIndexOutOfBoundsException(
						"Array access index out of bounds. writeDouble is trying to access byteArray[" + offset
								+ TO_BYTE_ARRAY + (offset + 7) + BUT_VALID_INDICES_ARE_FROM_0_TO
								+ (byteArray.length - 1) + ".");
			}
		}

		private static void writeDoubleUnchecked(double value, byte[] byteArray, int offset, boolean bigEndian) {
			writeLong(Double.doubleToLongBits(value), byteArray, offset, bigEndian);
		}
	}
}
