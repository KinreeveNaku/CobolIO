/**
 * 
 */
package com.ibm.dataaccess.accessor;

import java.util.Collection;

/**
 * @author Andrew
 *
 */
public interface CollectionAccessor<E> extends ObjectAccessor {
	@Override
	Collection<E> convert(byte[] bytes);

	@Override
	int getTransferType();
	
	static <E> CollectionAccessor<E> instantiate() {
		throw new IllegalArgumentException("This method must be overridden.");
	}
}
