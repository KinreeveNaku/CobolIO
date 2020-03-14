/**
 * 
 */
package com.ibm.dataaccess.accessor;

import com.ibm.dataaccess.types.Union;

/**
 * @author Andrew
 *
 */
public interface UnionAccessor extends TypeAccessor<Union> {
	
	@Override
	Union convert(byte[] bytes);
	
	@Override
	int getTransferType();
	
	@Override
	UnionAccessor newInstance();
}
