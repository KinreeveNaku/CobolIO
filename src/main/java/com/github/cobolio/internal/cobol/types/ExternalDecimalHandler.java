/**
 * 
 */
package com.github.cobolio.internal.cobol.types;

import static com.github.cobolio.internal.cobol.PrimitiveConstants.*;

import com.github.cobolio.internal.cobol.datatype.ExternalDecimal;
import com.github.cobolio.types.TypeConversionException;
import com.github.cobolio.types.TypeHandler;

/**
 * <b>Note</b>: <br/>
 * <span style="indent: 20px;">External Decimal precision is the same as or 1
 * less than actual precision depending on decimalType.</span>
 * 
 * @author Andrew
 * 
 * @since 0.1.0-Alpha
 */
public class ExternalDecimalHandler implements TypeHandler {

	private int decimalType;
	private int precision;
	private int offset;

	/**
	 * 
	 */
	public ExternalDecimalHandler(int decimalType, int precision, int offset) {
		this.decimalType = decimalType;
		this.precision = precision;
		this.offset = offset;
	}

	/**
	 * Interrogates a byte[] to see if the value starting at the specified offset
	 * <code>externalOffset</code> with precision <code>precision</code> is negative
	 * or positive.
	 * 
	 * @param externalDecimal the byte[] to be interrogated.
	 * @param externalOffset  The offset of the value within the byte[].
	 * @param precision       The number of literal characters in the external
	 *                        decimal.
	 * @param decimalType     The type of decimal storage style. 1 for trailing
	 *                        embedded sign, 2 for leading embedded sign, 3 for
	 *                        trailing separate sign, and 4 for leading separate
	 *                        sign.
	 * @return
	 */
	static boolean isExternalDecimalSignNegative(byte[] externalDecimal, int externalOffset, int precision,
			int decimalType) {
		byte signByte;
		switch (decimalType) {
		case EBCDIC_SIGN_EMBEDDED_TRAILING:
			signByte = (byte) (externalDecimal[externalOffset + precision - 1] & HIGH_NIBBLE_MASK);// fetch the high
																									// order nibble of
																									// the last byte of
																									// the value stored
			if (signByte == EXTERNAL_EMBEDDED_SIGN_MINUS || signByte == EXTERNAL_EMBEDDED_SIGN_MINUS_ALTERNATE_B) {
				return true;
			}
			break;
		case EBCDIC_SIGN_EMBEDDED_LEADING:
			signByte = (byte) (externalDecimal[externalOffset] & HIGH_NIBBLE_MASK);// fetch high order nibble from first
																					// byte of value as specified by the
																					// offset.
			if (signByte == EXTERNAL_EMBEDDED_SIGN_MINUS || signByte == EXTERNAL_EMBEDDED_SIGN_MINUS_ALTERNATE_B) {
				return true;
			}
			break;
		case EBCDIC_SIGN_SEPARATE_TRAILING:
			signByte = externalDecimal[externalOffset + precision];// fetch the full byte of the last index of the value
																	// stored.
			if (signByte == EXTERNAL_SIGN_MINUS) {
				return true;
			}
			break;
		case EBCDIC_SIGN_SEPARATE_LEADING:
			signByte = externalDecimal[externalOffset];// fetch the first byte of the value stored.
			if (signByte == EXTERNAL_SIGN_MINUS) {
				return true;
			}
			break;
		default:
			throw new IllegalArgumentException("Invalid decimal sign type.");
		}
		return false;// if case does not return, then the value is positive.
	}

	@Override
	public Object parse(byte[] text) throws TypeConversionException {
		try {

		} catch (Exception e) {
			throw new TypeConversionException("Conversion failed. The contents could not be converted to " + getType().getCanonicalName(), e);
		}
	}

	@Override
	public byte[] format(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getType() {
		return ExternalDecimal.class;
	}

}
