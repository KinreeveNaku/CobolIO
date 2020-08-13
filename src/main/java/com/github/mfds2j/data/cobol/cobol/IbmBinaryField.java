/**
 * 
 */
package com.github.mfds2j.data.cobol.cobol;

/**
 * @author Andrew
 *
 */
public interface IbmBinaryField {

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
