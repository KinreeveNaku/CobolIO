/**
 * 
 */
package com.ibm.dataaccess.support;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ibm.dataaccess.Constants;
import com.ibm.dataaccess.DecimalData;

/**
 * @author Andrew
 *
 */
public final class CompSixUtil {
	private CompSixUtil() {
		throw new IllegalArgumentException();
	}
	
	public static BigDecimal unsignedPackedDecimalToBigDecimal(byte[] bytes, int offset, int precision, int scale, boolean checkOverflow) {
		return DecimalData.convertPackedDecimalToBigDecimal(bytes, offset, precision, scale, checkOverflow).abs();
	}
	
	public static BigInteger unsignedPackedDecimalToBigInteger(byte[] bytes, int offset, int precision, boolean checkOverflow) {
		return DecimalData.convertPackedDecimalToBigInteger(bytes, offset, precision, checkOverflow).abs();
	}
	
	public static double unsignedPackedDecimalToDouble(byte[] bytes, int offset, int precision, int scale, boolean checkOverflow) {
		return unsignedPackedDecimalToBigDecimal(bytes, offset, precision, scale, checkOverflow).doubleValue();
	}
	
	public static float unsignedPackedDecimalToFloat(byte[] bytes, int offset, int precision, int scale, boolean checkOverflow) {
		if(scale <= precision && precision <= Constants.MAX_FLOAT_SCALE) {
			return unsignedPackedDecimalToBigDecimal(bytes, offset, precision, scale, checkOverflow).floatValue();
		} else {
			throw new NumberFormatException("");
		}
	}
}
