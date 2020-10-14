/**
 * 
 */
package com.github.cobolio.internal.cobol;

import java.math.BigDecimal;
import java.math.BigInteger;

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
	static BigDecimal slowSignedPackedDecimalToBigDecimal(byte[] byteArray, int offset, int precision, int scale) {
		int length = (precision + LOW_TWO) / LOW_TWO;
		int sign = byteArray[offset + length - 1] & LOW_NIBBLE_MASK;
		char[] temp = new char[length * LOW_TWO];
		temp[0] = (char) (sign != PACKED_NEGATIVE_SIGN && sign != PACKED_OTHER_NEGATIVE_SIGN ? ASCII_ZED : ASCII_MINUS);

		for (int i = 0; i < length - 1; ++i) {
			temp[LOW_TWO * i + 1] = (char) (ASCII_ZED + ((byteArray[i + offset] >>> NIBBLE_LENGTH) & LOW_NIBBLE_MASK));
			temp[LOW_TWO * i + LOW_TWO] = (char) (ASCII_ZED + (byteArray[i + offset] & LOW_NIBBLE_MASK));
		}

		temp[length * LOW_TWO - 1] = (char) (HIGH_THREE + ((byteArray[offset + length - 1] >>> NIBBLE_LENGTH) & LOW_NIBBLE_MASK));
		return new BigDecimal(new BigInteger(new String(temp)), scale);
	}
	
	/*
	 * public static void packedToBigDecimalCustom(BigDecimal bd, byte[] byteArray,
	 * int precision, int scale, int decimalType) { byte signNibble; switch
	 * (decimalType) { case EBCDIC_SIGN_EMBEDDED_TRAILING : signNibble = (byte)
	 * (byteArray[byteArray.length - 1] & LOW_NIBBLE_MASK); break; case
	 * EBCDIC_SIGN_EMBEDDED_LEADING : signNibble = (byte) (byteArray[0] &
	 * HIGH_NIBBLE_MASK); break; case EBCDIC_SIGN_SEPARATE_TRAILING : signNibble =
	 * byteArray[byteArray.length - 1]; break; case EBCDIC_SIGN_SEPARATE_LEADING :
	 * signNibble = byteArray[0]; break; default : throw new
	 * IllegalStateException("DecimalType is not recognized."); } int signext =
	 * getSign(signNibble) == PACKED_POSITIVE_SIGN ? 1 : -1; char[] buffer = new
	 * char[getPackedByteCount(precision)]; //TODO write deserialization logic. bd =
	 * new BigDecimal(buffer,); value *= signext; bd.setScale(scale); }
	 */
	
	static void bigDecimalToSignedPackedDecimal(BigDecimal bd, byte[] byteArray, int offset, int precision,
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

			for (int i = numDigitsLeft - 1; i >= endPosition && offset + index >= 0; --index) {
				byteArray[offset + index] = (byte) (buffer[i] - ASCII_ZED);
				byteArray[offset + index] |= (byte) ((buffer[i - 1] - ASCII_ZED) << NIBBLE_LENGTH);
				i -= 2;
			}
			if (endPosition > 0 && offset + index >= 0) {
				byteArray[offset + index] = (byte) (buffer[0] - ASCII_ZED);
			}

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
				case 0 :
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
				case 0 :
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
