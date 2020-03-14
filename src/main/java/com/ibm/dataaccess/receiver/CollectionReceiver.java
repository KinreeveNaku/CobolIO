/**
 * 
 */
package com.ibm.dataaccess.receiver;

import com.ibm.dataaccess.accessor.CollectionAccessor;
import com.ibm.dataaccess.transform.Transformer;

/**
 * @author Andrew
 *
 */
public class CollectionReceiver<T extends CollectionAccessor<E>, E> implements AbstractReceiver<T> {
	private T accessor;
	public CollectionReceiver(T accessor) {
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
