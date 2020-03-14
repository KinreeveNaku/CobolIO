/**
 * 
 */
package com.ibm.dataaccess.receiver;

import com.ibm.dataaccess.accessor.impl.java.StringAccessor;
import com.ibm.dataaccess.transform.Transformer;

/**
 * @author Andrew
 *
 */
public class StringReceiver<T extends StringAccessor> implements AbstractReceiver<String> {
	private T accessor;

	public StringReceiver(T accessor) {
		this.accessor = accessor;
	}

	@Override
	public Transformer<?> transformed() {
		return this::transform;
	}

	@Override
	public String to(byte[] bytes) {
		return accessor.convert(bytes);
	}
}
