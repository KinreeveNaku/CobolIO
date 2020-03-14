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
public class LongAccessor extends NumberAccessor {

	@Override
	public Long convert(byte[] bytes) {
		long l = 0;
		for (byte b : bytes) {
			l += b << 8;
		}
		return l;
	}

	@Override
	public int getTransferType() {
		return TransferUtil.LONG;
	}
}
