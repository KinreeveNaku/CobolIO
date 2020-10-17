/**
 * 
 */
package com.github.cobolio.internal.cobol.datatype;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

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
 * This class is intended to be fully self-contained and serializable.
 * 
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class PackedDecimal extends Number {
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
	private transient Number jValue;
	private int precision;
	private int scale;
	private int decimalType;

	static {
		String opt = System.getProperty("cobolio.perfOpt");
		J_OPT_PERF = opt != null ? Boolean.getBoolean(opt) : false;
	}

	/**
	 * 
	 */
	public PackedDecimal(byte[] packedBytes, int decimalType) {
		this.packedBytes = packedBytes;
		this.decimalType = decimalType;
		initialize();
	}

	public PackedDecimal(Number n, int precision, int scale, int decimalType) {
		this.packedBytes = null;
		this.jValue = n;
		this.jValue = new BigDecimal(String.valueOf(n), MathContext.UNLIMITED);
		this.precision = precision;
		this.scale = scale;
		initialize();
	}

	public PackedDecimal(BigDecimal n, int decimalType) {
		this.packedBytes = null;// TODO
		this.jValue = n;
		this.precision = n.precision();
		this.scale = n.scale();
		this.decimalType = decimalType;
		initialize();
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
	public float floatValue() {
		return jValue.floatValue();
	}

	@Override
	public double doubleValue() {
		return jValue.doubleValue();
	}

	@Override
	public short shortValue() {
		return jValue.shortValue();
	}

	public boolean booleanValue() {
		return intValue() != 0;
	}
	
	public BigDecimal bigDecimalValue() {
		//TODO auto-generated method stub
		return null;
	}
	
	public boolean isFractional() {
		return this.scale != 0;
	}

	public byte[] rawValue() {
		return this.packedBytes;
	}

	private void initialize() {
		// TODO Construct initialization logic : Evaluate the raw value, and interpret
		// it into the various storage forms for performance sake.
	}

	private static final Number infer(byte[] bytes, int precision, int scale) {
		if (J_OPT_PERF) {

		}
	}

	private static final byte[] convert(BigDecimal bd, int decimalType) {
		int precision = bd.precision();
		byte[] packedBytes;
		char[] unscaledAbs = bd.unscaledValue().abs().toString().toCharArray();
		int sign = bd.signum();
		int length = getLength(precision);
		switch(decimalType) {
		case EBCDIC_SIGN_EMBEDDED_TRAILING :
			packedBytes = new byte[length];
			break;
		case EBCDIC_SIGN_EMBEDDED_LEADING :
			break;
		case EBCDIC_SIGN_SEPARATE_TRAILING :
			break;
		case EBCDIC_SIGN_SEPARATE_LEADING :
			break;
		default : throw new IllegalArgumentException("Decimal Type must be 1, 2, 3, or 4");
		}
	}

	public static final int getLength(int precision) {
		return length(precision);
		
	}
	private static final int length(int precision) {
		return (precision / HALF) + 1;
	}
	
	/**
	 * 
	 * @param ho The high order nibble
	 * @param lo The low order nibble
	 * @return Returns the packed format of the two digits.
	 */
	@SuppressWarnings("java:S3034")//Suppressed because low nibble will never be negative when used properly.
	private static final byte pack(int ho, int lo) {
		return (byte) ((ho << NIBBLE_LENGTH) + lo);//(ho * 16) + lo
		
	}
	
	private static final byte[] pack(char[] chars, int precision, int decimalType) {
		byte[] bytes = new byte[getLength(precision)];
		boolean neg = chars[0] == '-';
		int charStart = neg ? 1 : 0;
		int charEnd = chars.length - 1;
		int[] digits = getDigitsFromAsciiChars(chars, charStart, charEnd);
		int sign = (byte) (neg ? 13 : 12);
		switch(decimalType) {
		case EBCDIC_SIGN_EMBEDDED_TRAILING :
			//sign stored as the low order nibble of the last element with either the last digit or by itself
			byte b;
			for (digits[charEnd--] = (byte) ((neg ? 13 : 12) | chars[charEnd--] - 48 << 4); charEnd >= 0; digits[charEnd--] = b) {
				//set byte at last position as = sign nibble ORing chars.last - 48 << 4
			}
				b = 0;
				if (charEnd >= charStart) {
					b = (byte) (chars[charEnd--] - 48);
					if (charEnd >= charStart) {
						b = (byte) (b | chars[charEnd--] - 48 << 4);
					}
				}
			break;
		case EBCDIC_SIGN_EMBEDDED_LEADING :
			//sign stored as the high order nibble of the first element
			
			break;
		case EBCDIC_SIGN_SEPARATE_TRAILING :
			//sign stored as the high order nibble of the last element by itself
			
			break;
		case EBCDIC_SIGN_SEPARATE_LEADING :
			//sign stored as the high order nibble of the first element by itself
			break;
		default :
			throw new IllegalArgumentException("");
		}
		return bytes;
		
	}
	
	private static final int[] getDigitsFromAsciiChars(char[] chars, int start, int end) {
		int[] digits = new int[end - start];
		for(int i = start; i < end; i++) {
			digits[i - start] = getDigitFromAsciiChar(chars[i]);
		}
		return digits;
	}
	
	private int getDigitFromAsciiChar(char c) {
		return c - ASCII_ZED;//48 is ASCII 0.
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
