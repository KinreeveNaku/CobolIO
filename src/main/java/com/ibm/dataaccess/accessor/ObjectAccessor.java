/**
 * 
 */
package com.ibm.dataaccess.accessor;

/**
 * @author Andrew
 *
 */
public interface ObjectAccessor extends SerializableAccessor {
	@Override
	Object convert(byte[] source);
}
