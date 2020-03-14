/**
 * 
 */
package com.ibm.dataaccess.receiver;

import com.ibm.dataaccess.accessor.CharSequenceAccessor;
import com.ibm.dataaccess.transform.Transformer;

/**
 * @author Andrew
 *
 */
public class CharSequenceReceiver<T extends CharSequenceAccessor> implements AbstractReceiver<CharSequence> {

	private T accessor;

	public CharSequenceReceiver(T accessor) {
		this.accessor = accessor;
	}

	@Override
	public Transformer<?> transformed() {
		return this::transform;
	}

	@Override
	public CharSequence to(byte[] bytes) {
		return accessor.convert(bytes);
	}

}
