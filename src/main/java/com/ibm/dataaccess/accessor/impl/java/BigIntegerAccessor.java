/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.java;

import java.math.BigInteger;

import com.ibm.dataaccess.accessor.NumberAccessor;
import com.ibm.dataaccess.accessor.TransferUtil;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class BigIntegerAccessor extends NumberAccessor {

	@Override
	public BigInteger convert(byte[] bytes) {
		return new BigInteger(bytes);
	}

	@Override
	public int getTransferType() {
		return TransferUtil.BIGINTEGER;
	}
}
