/**
 * 
 */
package com.ibm.dataaccess.receiver;

import com.ibm.dataaccess.accessor.impl.java.BytesAccessor;
import com.ibm.dataaccess.transform.Transformer;

/**
 * @author Andrew
 *
 */
public class BytesReceiver<T extends BytesAccessor> implements AbstractReceiver<T> {

	private T accessor;

	public BytesReceiver(T accessor) {
		this.accessor = accessor;
	}

	@Override
	public Transformer<?> transformed() {
		return this::transform;
	}

	@Override
	public Object to(byte[] bytes) {
		return accessor.convert(bytes);
	}

}
