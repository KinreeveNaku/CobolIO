/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.java;

import com.ibm.dataaccess.accessor.NumberAccessor;
import com.ibm.dataaccess.accessor.TransferUtil;

/**
 * https://en.wikipedia.org/wiki/IBM_hexadecimal_floating_point
 * 
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class FloatAccessor extends NumberAccessor {

	@Override
	public Float convert(byte[] bytes) {
		int i = 0;
		if (bytes.length != Float.BYTES) {
			throw new NumberFormatException("byte[] is not four bytes in length.");
		} else {
			for (byte b : bytes) {
				i += b << Byte.SIZE;
			}
		}
		return Float.intBitsToFloat(i);
	}

	@Override
	public int getTransferType() {
		return TransferUtil.FLOAT;
	}
}
