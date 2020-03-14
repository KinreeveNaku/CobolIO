/**
 * 
 */
package com.ibm.dataaccess.accessor;

/**
 * @author Andrew
 *
 */
public interface TypeAccessor<T> extends ObjectAccessor {

	@Override
	T convert(byte[] bytes);

	TypeAccessor<T> newInstance();
}
