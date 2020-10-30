/**
 * 
 */
package com.github.cobolio.internal.cobol.datatype;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Unicode Decimal, also referred to by the name National Decimal, is a DBCS
 * based storage method for storing numeric values. This class is in essence, an
 * extension of the ExternalDecimal class, except that it reads the data as
 * double-byte encoded rather than single-byte encoded.
 * 
 * Each numeric digit is stored in a byte pair, the first containing 0x00, and
 * the second
 * 
 * @author Andrew
 *
 */
@SuppressWarnings({"serial", "unused", "java:S1068"})
public class UnicodeDecimal extends CobolNumber {
	/**
	 * 
	 */
	private static final int UNICODE_DECIMAL_POSITIVE_SIGN = 0x2B;
	/**
	 * 
	 */
	private static final int UNICODE_DECIMAL_NEGATIVE_SIGN = 0x2D;
	/**
	 * 
	 */
	private static final String SCALE_EXCEPTION_STRING = "Scale must be non-negative. The value provided was ";
	/**
	 * 
	 */
	private static final String PRECISION_EXCEPTION_STRING = "Precision must be at least 1. The value provided was ";
	
	public static final int UNICODE_UNSIGNED = 5;
	public static final int UNICODE_SIGN_SEPARATE_LEADING = 6;
	public static final int UNICODE_SIGN_SEPARATE_TRAILING = 7;
	

	private static final int ASCII_ZED = 48;
	private static final boolean J_OPT_PERF;
	
	private final byte[] nationalBytes;
	private final BigDecimal jValue;
	private final int precision;
	private final int scale;
	private final int decimalType;
	private final boolean isSigned;
	
	static {
		String opt = System.getProperty("cobolio.perfOpt");
		J_OPT_PERF = opt != null ? Boolean.getBoolean(opt) : Boolean.FALSE;
	}
	
	/**
	 * This constructor should only be used if the value {@code n} has the desired
	 * scale and precision. As such, this constructor is not ideal for floating
	 * point datatypes. It is provided merely as a convenience.
	 */
	public UnicodeDecimal(Number n, int decimalType, boolean isSigned) {
		super(isSigned);
		this.decimalType = decimalType;
		if(n instanceof BigDecimal) {
			this.jValue = (BigDecimal) n;
		} else {
			this.jValue = new BigDecimal(n.toString());
		}
		this.precision = this.jValue.precision();
		this.scale = this.jValue.scale();
		this.isSigned = isSigned;
		this.nationalBytes = encode(this.jValue, this.decimalType, this.precision);
	}
	
	public UnicodeDecimal(Number n, int decimalType, int scale, int precision, boolean isSigned) {
		super(isSigned);
		if(precision < 1) {
			throw new IllegalArgumentException(PRECISION_EXCEPTION_STRING + precision);
		} else if(scale < 0) {
			throw new IllegalArgumentException(SCALE_EXCEPTION_STRING + scale);
		}
		this.decimalType = decimalType;
		if(n instanceof BigDecimal) {
			this.jValue = (BigDecimal) n;
		} else {
			this.jValue = new BigDecimal(n.toString());
		}
		this.precision = precision;
		this.scale = scale;
		this.isSigned = isSigned;
		this.nationalBytes = encode(this.jValue, this.decimalType, this.precision);
	}

	public UnicodeDecimal(byte[] bytes, int decimalType, int scale, int precision, boolean isSigned) {
		super(isSigned);
		if(precision < 1) {
			throw new IllegalArgumentException(PRECISION_EXCEPTION_STRING + precision);
		} else if(scale < 0) {
			throw new IllegalArgumentException(SCALE_EXCEPTION_STRING + scale);
		}
		this.decimalType = decimalType;//This can probably be inferred.
		this.nationalBytes = bytes;
		this.precision = precision;
		this.scale = scale;
		this.isSigned = isSigned;
		this.jValue = decode(bytes, scale).setScale(scale, RoundingMode.HALF_EVEN);
	}
	
	public UnicodeDecimal(byte[] bytes, int decimalType, int scale, int precision, boolean isSigned, RoundingMode roundingMode) {
		super(isSigned);
		if(precision < 1) {
			throw new IllegalArgumentException(PRECISION_EXCEPTION_STRING + precision);
		} else if(scale < 0) {
			throw new IllegalArgumentException(SCALE_EXCEPTION_STRING + scale);
		}
		this.decimalType = decimalType;//This can probably be inferred.
		this.nationalBytes = bytes;
		this.precision = precision;
		this.scale = scale;
		this.isSigned = isSigned;
		this.jValue = decode(bytes, scale).setScale(scale, roundingMode);
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
		return this.jValue.signum() != 0;
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
		return this.scale != 0;
	}
	
	@Override
	public boolean isSigned() {
		return this.isSigned;
	}

	@Override
	public byte[] rawValue() {
		return this.rawValue();
	}

	/**
	 * @return
	 */
	@Override
	public BigDecimal bigDecimalValue() {
		return this.jValue;
	}
	
	public static byte[] encode(Number n, int decimalType, int precision) {
		byte[] bytes = new byte[(precision * 2) + 2];//unicode buffer byte for each byte in addition to the sign byte
		String numStr = null;
		if(n instanceof BigDecimal) {
			numStr = ((BigDecimal) n).unscaledValue().toString();
		} else {
			numStr = n.toString();
		}
		byte sign = 0x0;
		if(numStr.contains("+") || numStr.contains("-")) {
			sign = (byte) (numStr.charAt(0) == '-' ? UNICODE_DECIMAL_NEGATIVE_SIGN : UNICODE_DECIMAL_POSITIVE_SIGN);
			numStr = numStr.substring(1, numStr.length());//strip the sign
		}
		char[] chars = numStr.toCharArray();
		int i = 0;
		switch(decimalType) {
			case UNICODE_SIGN_SEPARATE_LEADING :
				i = 2;
				bytes[0] = 0x00;
				bytes[1] = sign;
				break;
			case UNICODE_SIGN_SEPARATE_TRAILING :
				bytes[bytes.length - 2] = 0x00;
				bytes[bytes.length - 1] = sign;
				break;
			case UNICODE_UNSIGNED :
				break;
			default :
				throw new IllegalArgumentException();
		}
		for(int charPos = 0; i < bytes.length; i+=2, charPos++) {
			bytes[i] = 0x00;
			bytes[i + 1] = (byte) chars[charPos - ASCII_ZED];
		} //needs testing
		
		return bytes;
	}
	
	public static final BigDecimal decode(byte[] bytes, int scale) {
		byte[] stripped = new byte[bytes.length / 2];
		char[] chars = new char[stripped.length];
		char sign = '+';
		for(int i = 0, destIndex = 0; i < bytes.length; i++) {
			if(((i & 0x00000001) == 1)) {
				//The only important portion is the low order nibble of every second byte
				if((bytes[i] & 0x0F) == 0x0D) {
					sign = '-';
				} else if((bytes[i] & 0x0F) == 0x0B) {
					sign = '+';
				} else {
					chars[destIndex] = (char) (((byte)(bytes[i] & 0x0F)) + 48);
				}
			}
		}
		return new BigDecimal(new BigInteger((sign + new String(chars)).trim()), scale);
	}
	
	public static final boolean validateNationalDecimal(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			// (is even index and is 0) or (is odd index and high order nibble is not 0011
			// or 0010)
			if ((((i & 0x00000001) == 0) && bytes[i] != 0x00)
					|| (((i & 0x00000001) == 1) && (((bytes[i] & 0xF0) != 0x30) && ((bytes[i] & 0xF0) != 0x20)))) {
				return false;
			}
		}
		return true;
	}
	
	private static final int positionFromDecimalType(int decimalType, int arrayLength) {//TODO Finish if needed
		switch(decimalType) {
		case UNICODE_UNSIGNED:
			// Returning the length itself will always get skipped in iterators. This is
			// safe since finding a sign on an
			// unsigned value is impossible.
			return arrayLength;
		case UNICODE_SIGN_SEPARATE_LEADING :
			return 1;
		case UNICODE_SIGN_SEPARATE_TRAILING :
			return arrayLength - 1;//last
			default :
				throw new IllegalArgumentException();//TODO Write message tag
		}
	}

	

	

}
