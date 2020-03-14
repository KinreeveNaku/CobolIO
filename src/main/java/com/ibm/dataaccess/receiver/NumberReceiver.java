/**
 * 
 */
package com.ibm.dataaccess.receiver;

import com.ibm.dataaccess.accessor.NumberAccessor;
import com.ibm.dataaccess.transform.Transformer;

/**
 * @author Andrew
 *
 */
public class NumberReceiver<T extends NumberAccessor> implements AbstractReceiver<Number> {

	private T accessor;

	public NumberReceiver(T accessor) {
		this.accessor = accessor;
	}

	@Override
	public Transformer<?> transformed() {
		return this::transform;
	}

	@Override
	public Number to(byte[] bytes) {
		return accessor.convert(bytes);
	}

}
