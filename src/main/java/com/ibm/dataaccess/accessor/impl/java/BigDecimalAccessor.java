/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.java;

import java.math.BigDecimal;
import java.util.Arrays;

import com.ibm.dataaccess.accessor.NumberAccessor;
import com.ibm.dataaccess.accessor.TransferUtil;

/**
 * What was the algorithm for determining the standard bitcount for the mantissa and exponent of an n-bit decimal?
 *	128	1	13	112
 *	64	1	11	52
 *	32	1	8	23
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class BigDecimalAccessor extends NumberAccessor {
	@Override
	public BigDecimal convert(byte[] bytes) {
		int[] lengths = new int[bytes.length / Long.BYTES + 1];
		long[] values = new long[bytes.length / Long.BYTES + 1];
		Arrays.fill(values, 0);
		byte rem = (byte) (bytes.length % Long.BYTES);
		for (int iter = 0; iter < bytes.length / Long.BYTES; iter++) {
			byte[] curr = (iter * Long.BYTES + rem) >= bytes.length
					? Arrays.copyOfRange(bytes, iter * Long.BYTES, iter * Long.BYTES + rem)
					: Arrays.copyOfRange(bytes, iter * Long.BYTES, iter * Long.BYTES + Long.BYTES);
			lengths[iter] = curr.length;

			for (int i = 0; i < curr.length; i++) {
				values[iter] += curr[i] << Byte.SIZE;
			}
		}
		//?????????
		
		return BigDecimal.valueOf(0L);//TODO

	}

	@Override
	public int getTransferType() {
		return TransferUtil.BIGDECIMAL;
	}
}
