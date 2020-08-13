/**
 * 
 */
package com.github.cobolio.internal.cobol.types;

/**
 * @author Andrew
 *
 */
public interface IbmBinaryFieldHandler {

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
