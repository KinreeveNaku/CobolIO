/**
 * 
 */
package com.ibm.dataaccess.receiver;

import com.ibm.dataaccess.accessor.impl.java.BooleanAccessor;
import com.ibm.dataaccess.transform.Transformer;

/**
 * @author Andrew
 *
 */
public class BooleanReceiver<T extends BooleanAccessor> implements AbstractReceiver<T> {

	private T accessor;
	
	public BooleanReceiver(T accessor) {
		this.accessor = accessor;
	}
	
	@Override
	public Transformer<?> transformed() {
		return this::transform;
	}

	@Override
	public Boolean to(byte[] bytes) {
		return accessor.convert(bytes);
	}

}
