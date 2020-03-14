/**
 * 
 */
package com.ibm.dataaccess.receiver;

import com.ibm.dataaccess.accessor.TypeAccessor;
import com.ibm.dataaccess.transform.Transformer;

/**
 * @author Andrew
 *
 */
public class TypeReceiver<T extends TypeAccessor<E>, E> implements AbstractReceiver<T> {
	private T accessor;

	public TypeReceiver(T accessor) {
		this.accessor = accessor;
	}

	@Override
	public Transformer<?> transformed() {
		return this::transform;
	}

	@Override
	public E to(byte[] bytes) {
		return accessor.convert(bytes);
	}

}
