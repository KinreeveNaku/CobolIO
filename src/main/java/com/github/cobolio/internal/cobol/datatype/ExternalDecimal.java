/**
 * 
 */
package com.github.cobolio.internal.cobol.datatype;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * A java representation of the external decimal storage format used by IBM in
 * the COBOL language runtime. Each digit in an external decimal is stored in
 * the format {@code 0xF#}. The sign is stored as {@code 0x4E} for '+' and
 * {@code 0x60} for '-'.
 * 
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class ExternalDecimal extends CobolNumber {
	
	/**
	 * 
	 */
	private static final int SIGN_BYTE_CEILING = 217;
	/**
	 * 
	 */
	private static final int SIGN_BYTE_FLOOR = 192;
	/**
	 * 
	 */
	private static final String SCALE_EXCEPTION_STRING = "Scale must be non-negative. The value provided was ";
	/**
	 * 
	 */
	private static final String PRECISION_EXCEPTION_STRING = "Precision must be at least 1. The value provided was ";
	
	
	public static final int EBCDIC_SIGN_SEPARATE_TRAILING = 3;
	public static final int EBCDIC_SIGN_SEPARATE_LEADING = 4;
	public static final int EBCDIC_UNSIGNED = 5;
	
	private static final byte SIGN_SEPARATE_PLUS = 0x4E;
	private static final byte SIGN_SEPARATE_MINUS = 0x60;

	
	private final byte[] zonedBytes;
	private final BigDecimal jValue;
	private final int precision;
	private final int scale;
	private final boolean isFractional;

	/**
	 * 
	 */
	public ExternalDecimal(byte[] zonedBytes, int precision) {
		super(false);
		this.zonedBytes = zonedBytes;
		this.jValue = decode(zonedBytes);
		this.scale = this.jValue.scale();
		this.precision = precision;
		this.isFractional = this.scale != 0;
	}

	/**
	 * 
	 */
	public ExternalDecimal(byte[] zonedBytes, int precision, int scale, boolean isSigned) {
		super(isSigned);
		if(precision < 1) {
			throw new IllegalArgumentException(PRECISION_EXCEPTION_STRING + precision);
		} else if(scale < 0) {
			throw new IllegalArgumentException(SCALE_EXCEPTION_STRING + scale);
		}
		this.zonedBytes = zonedBytes;
		this.jValue = decode(zonedBytes);
		this.scale = scale;
		this.precision = precision;
		this.isFractional = scale != 0;
	}

	public ExternalDecimal(Number jValue, int decimalType) {
		super(false);
		if(precision < 1) {
			throw new IllegalArgumentException(PRECISION_EXCEPTION_STRING + precision);
		} else if(scale < 0) {
			throw new IllegalArgumentException(SCALE_EXCEPTION_STRING + scale);
		}
		this.jValue = jValue instanceof BigDecimal ? (BigDecimal) jValue : new BigDecimal(jValue.toString());
		this.zonedBytes = encode(jValue, decimalType);
		this.scale = this.jValue.scale();
		this.precision = this.jValue.precision();
		this.isFractional = this.scale != 0;
	}

	@Override
	public int intValue() {
		return this.jValue.intValue();
	}

	@Override
	public long longValue() {
		return this.jValue.longValue();
	}

	@Override
	public float floatValue() {
		return this.jValue.floatValue();
	}

	@Override
	public double doubleValue() {
		return this.jValue.doubleValue();
	}

	/**
	 * @return
	 */
	@Override
	public boolean booleanValue() {
		return this.jValue.signum() == 0;
	}

	@Override
	public int getPrecision() {
		return this.precision;
	}

	@Override
	public int getScale() {
		return this.scale;
	}

	@Override
	public boolean isFractional() {
		return this.isFractional;
	}
	
	@Override
	public byte[] rawValue() {
		return this.zonedBytes;
	}
	
	/**
	 * @return
	 */
	@Override
	public BigDecimal bigDecimalValue() {
		return this.jValue;
	}
	
	/**
	 * @param value2
	 * @param decimalType
	 */
	public static final byte[] encode(Number value, int decimalType) {
		byte sign = 0x00;
		String digits = null;
		if(value instanceof CobolNumber) {
			return ((CobolNumber) value).rawValue();//Preliminary assumption
		} else if(value instanceof BigDecimal) {
			 sign = getExternalSign(((BigDecimal) value).signum() < 0);
			digits = ((BigDecimal) value).unscaledValue().toString();
		} else if(value instanceof BigInteger) {
			sign = getExternalSign(((BigInteger)value).signum() < 0);
			digits = value.toString();
		} else {
			sign = getExternalSign(value.doubleValue() < 0.0);
			digits = Double.toString(value.doubleValue());
		}
		if(digits.contains(".")) {//There must be a better way to deal with this.
			digits = digits.substring(0, digits.indexOf('.')) + digits.substring(digits.indexOf('.'), digits.length());
		}
		byte[] bytes = new byte[digits.length()];
		if(sign == SIGN_SEPARATE_MINUS) {
			digits = digits.substring(1);
		}
		int signPos = positionByDecimalType(bytes.length, decimalType);
		//TODO ... need logic for sign position built into the for loop
		for(int i = 0; i < bytes.length; i++) {
			
		}
		bytes[signPos] = sign;
		return bytes;
		
		
	}
	
	public static final BigDecimal decode(byte[] bytes, int decimalType, int scale, int precision) {
		int signPos = positionByDecimalType(bytes.length, decimalType);
		byte[] realDigits = unzone(bytes);
		byte sign = 0x0;
		for(int i = 0; i < bytes.length; i++) {
			if(i == signPos) {
				sign = 
			}
		}
	}

	/**
	 * Strips the high nibble from each byte in the array. Care must be taken when
	 * choosing to use this method as the high nibble of a sign byte will be
	 * flushed.
	 * 
	 * @param bytes
	 * @return
	 */
	public static final byte[] unzone(byte[] bytes) {
		byte[] unzoned = new byte[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			unzoned[i] = (byte) (0x0F & bytes[i]);
		}
		return unzoned;
	}

	/**
	 * Zones the high nibble of each byte in the array. Care must be taken when
	 * choosing to use this method as the high nibble of a sign byte will be
	 * overwritten.
	 * 
	 * @param bytes
	 * @return
	 */
	public static final byte[] zone(byte[] bytes) {
		byte[] zoned = new byte[bytes.length];
		for(int i = 0; i < bytes.length; i++) {
			zoned[i] = (byte) (0xF0 | bytes[i]);
		}
		return zoned;
	}
	
	private static final byte getExternalSign(boolean isNegative) {
		return isNegative ? SIGN_SEPARATE_MINUS : SIGN_SEPARATE_PLUS;
	}
	
	public static final int positionByDecimalType(int bytesLength, int decimalType) {
		switch (decimalType) {
		case EBCDIC_SIGN_SEPARATE_TRAILING:
			return bytesLength - 1;
		case EBCDIC_SIGN_SEPARATE_LEADING:
			return 0;
		case EBCDIC_UNSIGNED :
			return bytesLength;
		default:
			throw new IllegalArgumentException();// TODO Set message value for exception
		}

	}
	
	/**
	 * Finds and returns the index of the first byte that matches a sign byte. This
	 * should not be used if the byte[] contains multiple values.
	 * 
	 * @param bytes
	 * @return The index of the first sign byte, or -1 if none are found.
	 */
	public static final int exhaustiveSignByteSearch(byte[] bytes) {
		for(int i = 0; i < bytes.length; i++) {
			if(bytes[i] >= SIGN_BYTE_FLOOR || bytes[i] <= SIGN_BYTE_CEILING) {
				return bytes[i];
			}
		}
		return -1;
	}
}
