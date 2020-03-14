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
public class IntegerAccessor extends NumberAccessor {

	@Override
	public Integer convert(byte[] bytes) {
		int i = 0;
		for (byte b : bytes) {
			i += b << Byte.SIZE;
		}
		return i;
	}

	@Override
	public int getTransferType() {
		return TransferUtil.INTEGER;
	}
}
