/**
 * 
 */
package com.ibm.dataaccess.accessor;

import java.io.Serializable;

/**
 * @author Andrew
 * 
 */
public interface SerializableAccessor extends Accessor<Object>, Serializable {

	int getTransferType();

	@Override
	Object convert(byte[] source);
}
