/**
 * 
 */
package com.github.cobolio.internal.cobol.datatype;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Objects;

/**
 * <div>A packed decimal is a datatype in the COBOL language specification. In
 * packed BCD (or simply packed decimal[37]), each of the two nibbles of each
 * byte represent a decimal digit.</div>
 * 
 * <div>Packed BCD has been in use since at least the 1960s and is implemented
 * in all IBM mainframe hardware since then. Most implementations are big
 * endian, i.e. with the more significant digit in the upper half of each byte,
 * and with the leftmost byte (residing at the lowest memory address) containing
 * the most significant digits of the packed decimal value. The lower nibble of
 * the rightmost byte is usually used as the sign flag, although some unsigned
 * representations lack a sign flag.</div>
 * 
 * <div>As an example, a 4-byte value consists of 8 nibbles, wherein the upper 7
 * nibbles store the digits of a 7-digit decimal value, and the lowest nibble
 * indicates the sign of the decimal integer value.</div>
 * 
 * This class is intended to be fully self-contained and serializable. It also
 * contains various static helper methods and convenience methods for performing
 * various logical processes.
 * 
 * @author Andrew
 *
 * @apiNote Any invocations of this class' constructors must make note that any
 *          {@code Number} extending classes should have proper handling for
 *          {@link #toString()}
 * @since 0.1.0-Alpha
 */
@SuppressWarnings({"serial", "unused", "java:S1068"})
public class PackedDecimal extends CobolNumber {
	
	/**
	 * 
	 */
	private static final String SCALE_EXCEPTION_STRING = "Scale must be non-negative. The value provided was ";
	/**
	 * 
	 */
	private static final String PRECISION_EXCEPTION_STRING = "Precision must be at least 1. The value provided was ";
	//Overall 0xA, 0xC, 0xE, and 0xF can be interpreted as +
	//Whereas 0xB and 0xD can be interpreted as -
	
	private static final byte TWO = 2;
	private static final byte LOW_NINE = 0x09;
	private static final byte HIGH_THREE = 0x30;
	private static final byte HIGH_NINE = (byte) +0x90;
	private static final byte A = 0x0A;//Plus sign, ?
	private static final byte B = 0x0B;//Minus sign, usually ExternalDecimal?
	private static final byte C = 0x0C;//Plus sign, usually ExternalDecimal
	private static final byte D = 0x0D;//Minus sign, usually PackedDecimal but also ExternalDecimal
	private static final byte E = 0x0E;//Plus sign, ?
	private static final byte F = 0x0F;//Plus sign, usually PackedDecimal
	
	private static final byte PACKED_OTHER_NEGATIVE_SIGN = B;
	private static final byte PACKED_NEGATIVE_SIGN = D;
	
	private static final byte LOW_NIBBLE_MASK = 15;
	private static final byte HIGH_NIBBLE_MASK = -16;
	
	private static final int EBCDIC_SIGN_EMBEDDED_TRAILING = 1;
	private static final int EBCDIC_SIGN_EMBEDDED_LEADING = 2;
	private static final int EBCDIC_SIGN_SEPARATE_TRAILING = 3;
	private static final int EBCDIC_SIGN_SEPARATE_LEADING = 4;
	private static final int HALF = 2;
	private static final int NIBBLE_LENGTH = 4;
	private static final int ASCII_ZED = 48;
	private static final boolean J_OPT_PERF;
	private static final int ZERO_CHAR = '0';
	
	private final byte[] packedBytes;
	private final transient BigDecimal jValue;
	private final int precision;
	private final int scale;
	private final int decimalType;

	static {
		String opt = System.getProperty("cobolio.perfOpt");
		J_OPT_PERF = opt != null ? Boolean.getBoolean(opt) : Boolean.FALSE;
	}

	/**
	 * 
	 */
	public PackedDecimal(byte[] packedBytes, int decimalType, boolean isSigned) {
		super(isSigned);
		this.packedBytes = packedBytes;
		this.jValue = unpack(packedBytes, decimalType);
		this.scale = this.jValue.scale();
		this.precision = this.jValue.precision();
		this.decimalType = decimalType;
	}

	public PackedDecimal(Number n, int precision, int scale, int decimalType, boolean isSigned) {
		super(isSigned);
		if(precision < 1) {
			throw new IllegalArgumentException(PRECISION_EXCEPTION_STRING + precision);
		} else if(scale < 0) {
			throw new IllegalArgumentException(SCALE_EXCEPTION_STRING + scale);
		}
		this.packedBytes = pack(n, decimalType);
		this.jValue = new BigDecimal(String.valueOf(n), MathContext.UNLIMITED);
		this.precision = precision;
		this.scale = scale;
		this.decimalType = decimalType;
	}

	public PackedDecimal(BigDecimal n, int decimalType, boolean isSigned) {
		super(isSigned);
		this.jValue = n;
		this.packedBytes = pack(n, decimalType);
		this.precision = n.precision();
		this.scale = n.scale();
		this.decimalType = decimalType;
	}

	@Override
	public int intValue() {
		return jValue.intValue();
	}

	@Override
	public long longValue() {
		return jValue.longValue();
	}

	@Override
	public final float floatValue() {
		return jValue.floatValue();
	}

	@Override
	public final double doubleValue() {
		return jValue.doubleValue();
	}

	@Override
	public final short shortValue() {
		return jValue.shortValue();
	}

	@Override
	public boolean booleanValue() {
		return intValue() != 0;
	}
	
	@Override
	public final int getPrecision() {
		return this.precision;
	}

	@Override
	public final int getScale() {
		return this.scale;
	}

	@Override
	public boolean isFractional() {
		return this.scale != 0;
	}
	
	@Override
	public BigDecimal bigDecimalValue() {
		return this.jValue;
	}
	
	@Override
	public byte[] rawValue() {
		return this.packedBytes;
	}

	private static final Number eval(byte[] bytes, int precision, int scale) {
		if (J_OPT_PERF) {

		}
		return null;//TODO
	}
	
	/**
	 * 
	 * @param bd
	 * @param decimalType
	 * @return
	 */
	public static final byte[] pack(Number n, int decimalType) {
		return packExplained(n, decimalType);//TODO Ultimately the Explained methods should not be used, but work for now.
	}
	
	public static final BigDecimal unpack(byte[] bytes, int decimalType) {
		return unpackExplained(bytes, decimalType);
	}

	public static final int getLength(int precision) {
		return length(precision);
		
	}
	private static final int length(int precision) {
		return (precision / HALF) + 1;
	}
	
	public static int sign(byte b) {
		return getSign(b & LOW_NIBBLE_MASK) == D ? -1 : 1;
	}
	private static byte getSign(int i) {
		return (i != D && i != B ? C : D);
	}
	
	private static final int[] getDigitsFromAsciiChars(char[] chars, int start, int end) {
		int[] digits = new int[end - start];
		for(int i = start; i < end; i++) {
			digits[i - start] = getDigitFromAsciiChar(chars[i]);
		}
		return digits;
	}
	
	private static int getDigitFromAsciiChar(char c) {
		return c - ASCII_ZED;//48 is ASCII 0.
	}
	
	
	@SuppressWarnings({ "java:S109", "java:S3358", "java:S3776" })
	public static final int numDigits(int value) {
		value = value == Integer.MIN_VALUE ? Integer.MAX_VALUE : Math.abs(value);
		return value >= 10000
				? (value >= 10000000 ? (value >= 100000000 ? (value >= 1000000000 ? 10 : 9) : 8)
						: (value >= 100000 ? (value >= 1000000 ? 7 : 6) : 5))
				: (value >= 100 ? (value >= 1000 ? 4 : 3) : (value >= 10 ? 2 : 1));
	}

	@SuppressWarnings({ "java:S109", "java:S3358", "java:S3776" })
	public static final int numDigits(long value) {
		value = value == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(value);
		return value >= 1000000000L
				? (value >= 100000000000000L ? (value >= 10000000000000000L
						? (value >= 100000000000000000L ? (value >= 1000000000000000000L ? 19 : 18) : 17)
						: (value >= 1000000000000000L ? 16 : 15))
						: (value >= 100000000000L
								? (value >= 1000000000000L ? (value >= 10000000000000L ? 14 : 13) : 12)
								: (value >= 10000000000L ? 11 : 10)))
				: (value >= 10000L
						? (value >= 1000000L ? (value >= 10000000L ? (value >= 100000000L ? 9 : 8) : 7)
								: (value >= 100000L ? 6 : 5))
						: (value >= 100L ? (value >= 1000L ? 4 : 3) : (value >= 10L ? 2 : 1)));
	}
	
	/**
	 * 
	 * 
	 * HIGH_THREE in this context is used as the offset position of the first
	 * numeric character, '0', which is index 48.
	 * 
	 * @param bytes
	 * @return
	 */
	public static final String packedToString(byte[] bytes, boolean showSign) {
		return packedToStringBuilder(bytes, showSign).toString();
	}

	public static final BigDecimal packedToBigDecimal(byte[] bytes, int scale, int precision) {
		StringBuilder builder = packedToStringBuilder(bytes, false);
		builder.insert(precision - scale, '.');
		return new BigDecimal(builder.toString());
	}
	
	public static final StringBuilder packedToStringBuilder(byte[] bytes, boolean showSign) {
		StringBuilder builder = new StringBuilder();
		int end = bytes.length - 1;
		if ((bytes[end] & LOW_NIBBLE_MASK) == PACKED_OTHER_NEGATIVE_SIGN
				|| (bytes[end] & LOW_NIBBLE_MASK) == PACKED_NEGATIVE_SIGN) {
			builder.append('-');
		} else if (showSign) {
			builder.append('+');
		} else {
			// Do nothing
		}
		for (int i = 0; i < bytes.length - 1; i++) {
			builder.append((char) HIGH_THREE + ((byte) (bytes[i] >> NIBBLE_LENGTH) & LOW_NIBBLE_MASK));// High Nibble
																										// packed digit.
			builder.append((char) HIGH_THREE + (bytes[i] & LOW_NIBBLE_MASK));// Low Nibble packed digit.
		}
		builder.append(HIGH_THREE + ((byte) (bytes[end] >> NIBBLE_LENGTH) & LOW_NIBBLE_MASK));// Digit that is packed
																								// with the sign.
		return builder;
	}

	public static int getPackedByteCount(int precision) {
		return precision / TWO + 1;
	}

	public static final int checkPackedDecimal(byte[] byteArray, int offset, int precision,
			boolean ignoreHighNibbleForEvenPrecision, boolean canOverwriteHighNibbleForEvenPrecision) {
		if (offset + precision / TWO + 1 <= byteArray.length && offset >= 0) {
			return checkPackedDecimalContent(byteArray, offset, precision, ignoreHighNibbleForEvenPrecision,
					canOverwriteHighNibbleForEvenPrecision);
		} else {
			throw new ArrayIndexOutOfBoundsException(
					"Array access index out of bounds. checkPackedDecimal is trying to access byteArray[" + offset
							+ "] to byteArray[" + (offset + precision / TWO) + "] but valid indices are from 0 to "
							+ (byteArray.length - 1) + ".");
		}
	}

	public static final int checkPackedDecimalContent(byte[] byteArray, int offset, int precision,
			boolean ignoreHighNibbleForEvenPrecision, boolean canOverwriteHighNibbleForEvenPrecision) {
		if (precision < 1) {
			throw new IllegalArgumentException("Illegal Precision.");
		} else {
			boolean evenPrecision = precision % TWO == 0;//Could use ((precision & 0x1) == 0)
			int signOffset = offset + getPackedByteCount(precision) - 1;
			int returnCode = 0;
			if (canOverwriteHighNibbleForEvenPrecision && evenPrecision) {//Sanity checking
				byteArray[offset] = (byte) (byteArray[offset] & LOW_NIBBLE_MASK);
			}
			if (evenPrecision && ignoreHighNibbleForEvenPrecision) {
				if ((byteArray[offset] & LOW_NIBBLE_MASK) > LOW_NINE) {//If first byte contains a sign nibble
					returnCode = TWO;
				}
				++offset;
			}
			int i;
			for (i = offset; i < signOffset && byteArray[i] == 0; ++i) {//skipping internal 0x00 bytes
			}
			while (i < signOffset) {//while before sign byte position
				if ((byteArray[i] & LOW_NIBBLE_MASK) > LOW_NINE
						|| (byteArray[i] & HIGH_NIBBLE_MASK & 255) > HIGH_NINE) {//If either nibble is 10 or higher
					returnCode = TWO;
					break;
				}
				++i;
			}
			//If non-sign nibble is 10 or higher
			if (i == signOffset && (byteArray[signOffset] & HIGH_NIBBLE_MASK & 255) > HIGH_NINE) {
				returnCode = TWO;
			}
			//If expected sign nibble is not a sign nibble
			if ((byteArray[signOffset] & LOW_NIBBLE_MASK) < A) {
				++returnCode;
			}
			return returnCode;
		}
	}

	public static final int checkPackedDecimal(byte[] byteArray, int offset, int precision,
			boolean ignoreHighNibbleForEvenPrecision) {
		return checkPackedDecimal(byteArray, offset, precision, ignoreHighNibbleForEvenPrecision, false);
	}

	public static final int checkPackedDecimal(byte[] byteArray, int offset, int precision) {
		return checkPackedDecimal(byteArray, offset, precision, false, false);
	}

	public static final BigInteger getBigInteger(byte[] pd, int offset, int length) {
		int end = offset + length - 1;
		StringBuilder sb = new StringBuilder();
		if ((pd[end] & LOW_NIBBLE_MASK) == PACKED_OTHER_NEGATIVE_SIGN
				|| (pd[end] & LOW_NIBBLE_MASK) == PACKED_NEGATIVE_SIGN) {
			sb.append('-');
		}

		for (int i = offset; i < end; ++i) {
			sb.append((char) (HIGH_THREE + (((byte) (pd[i] >> NIBBLE_LENGTH)) & LOW_NIBBLE_MASK)));
			sb.append((char) (HIGH_THREE + (pd[i] & LOW_NIBBLE_MASK)));
		}

		sb.append((char) (HIGH_THREE + (((byte) (pd[end] >> NIBBLE_LENGTH)) & LOW_NIBBLE_MASK)));
		return new BigInteger(sb.toString());
	}

	public static void zeroTopNibbleIfEven(byte[] bytes, int offset, int prec) {
		if (prec % TWO == 0) {
			bytes[offset] = (byte) (bytes[offset] & LOW_NIBBLE_MASK);
		}

	}
	
	// @Alpha(major = 0, minor = 0, snapshot = 10)
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
	static final BigDecimal unpackExplained(byte[] bytes, int scale) {//Needs precision argument for boundary support
		char sign = 0;
		char[] chars = new char[(bytes.length * 2) + 1];//Rough approximation of precision. String trimming accounts for inaccuracy.
		int i = 1;
		for(byte b : bytes) {
			if((b & 0xF0) > 0xA0) {//isSign, A0 is any hexadecimal letter value for the HO Nibble
				sign = sign((byte) (b >> 4)) == 1 ? '+' : '-';//if sign nibble is 'C', return '+'. If sign nibble is B or D, return '-'. 
				chars[i] = (char) ((b & 0x0F) + 48);//Get LO Nibble and convert to ASCII numeric.
				i++;
			} else if((b & 0x0F) > 0x0A) {//isSign, 0A is any hexadecimal letter value for the LO Nibble
				sign = sign(b) == 1 ? '+' : '-';//if sign nibble is 'C', return '+'. If sign nibble is B or D, return '-'.
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
	
	static byte[] packExplained(Number n, int decimalType) {
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
		byte[] bytes = new byte[getLength(unpackedString.length() - decant)];//The byte array that will be returned
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
	/**
	 * Returns the value of this PackedDecimal in String format.
	 * This method simply calls the #toString() method of the BigDecimal class.
	 * @return Returns a String representation of this numeric value.
	 */
	@Override
	public String toString() {
		return this.jValue.toPlainString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || o instanceof PackedDecimal) {
			return false;
		} else {
			return Arrays.equals(this.rawValue(), ((PackedDecimal)o).rawValue());
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.jValue, this.packedBytes, this.precision, this.scale);
	}
	/*
	 * embedded
	 * odd : 13 / 2 = 6, 6 + 1 = 7
	 * even: 14 / 2 = 7, 7 + 1 = 8
	 * (n / 2) + 1
	 * separate
	 * odd : 13 / 2 = 6, 6 + 1 = 7
	 * even: 14 / 2 = 7, 7 + 1 = 8
	 * (n / 2) + 1
	 */

	
}
