/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.java;

import com.ibm.dataaccess.accessor.JavaAccessor;
import com.ibm.dataaccess.accessor.TransferUtil;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class BooleanAccessor implements JavaAccessor {

	@Override
	public Boolean convert(byte[] bytes) {
		if (bytes.length < 2) {
			return (bytes.length < 1 || bytes[0] == (byte) 'f');
		} else if (bytes.length == 4) {
			return true;
		} else if (bytes.length == 5) {
			return false;
		} else {
			throw new ArithmeticException();
		}
	}

	@Override
	public int getTransferType() {
		return TransferUtil.BOOLEAN;
	}
}
