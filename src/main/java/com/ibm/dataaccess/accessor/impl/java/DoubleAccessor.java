/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.java;

import com.ibm.dataaccess.accessor.NumberAccessor;
import com.ibm.dataaccess.accessor.TransferUtil;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class DoubleAccessor extends NumberAccessor {

	@Override
	public Double convert(byte[] bytes) {
		long l = 0;
		if (bytes.length != Double.BYTES) {
			throw new NumberFormatException("The length of the byte[] is not eight bytes.");
		} else {
			for (byte b : bytes) {
				l += b << 8;
			}
		}
		return Double.longBitsToDouble(l);
	}

	@Override
	public int getTransferType() {
		return TransferUtil.DOUBLE;
	}

}
