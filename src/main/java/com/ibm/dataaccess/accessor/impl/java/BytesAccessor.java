/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.java;

import java.nio.ByteBuffer;

import com.ibm.dataaccess.accessor.ObjectAccessor;
import com.ibm.dataaccess.accessor.TransferUtil;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class BytesAccessor implements ObjectAccessor {

	@Override
	public ByteBuffer convert(byte[] bytes) {
		return ByteBuffer.wrap(bytes);
	}

	@Override
	public int getTransferType() {
		return TransferUtil.BYTEBUFFER;
	}

}
