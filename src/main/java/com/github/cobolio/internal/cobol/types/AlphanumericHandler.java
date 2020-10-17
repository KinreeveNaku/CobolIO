/**
 * 
 */
package com.github.cobolio.internal.cobol.types;

import com.github.cobolio.types.TypeConversionException;
import com.github.cobolio.types.TypeHandler;

/**
 * @author Andrew
 *
 */
//TODO Java Task: TODO Complete AlphanumericHandler
public class AlphanumericHandler implements TypeHandler {
	private boolean isDoubleByte = false;
	private int offset;
	@Override
	public Object parse(byte[] text) throws TypeConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] format(Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<?> getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
