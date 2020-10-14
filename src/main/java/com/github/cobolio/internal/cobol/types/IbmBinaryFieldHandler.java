/**
 * 
 */
package com.github.cobolio.internal.cobol.types;

import com.github.cobolio.types.TypeHandler;

/**
 * @author Andrew
 *
 */
public interface IbmBinaryFieldHandler extends TypeHandler {

	/**
	 * @param bytes
	 * @return
	 */
	Number get(byte[] bytes);

	/**
	 * @param bytes
	 * @param offset
	 * @return
	 */
	Number get(byte[] bytes, int offset);

}
