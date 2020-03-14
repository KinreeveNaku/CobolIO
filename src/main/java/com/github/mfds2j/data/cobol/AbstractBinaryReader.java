/**
 * 
 */
package com.github.mfds2j.data.cobol;

import java.io.IOException;

import com.github.mfds2j.data.cobol.cb2xml.EnhancedCopybook;

/**
 * @author Andrew
 *
 */
public abstract class AbstractBinaryReader {
	protected BinaryStream stream;

	/**
	 * This constructor is primarily meant to be used for instances of fixed or
	 * blocked fixed length record files (RECFM:F|FB)
	 * 
	 * @param books
	 * @param stream
	 */
	protected AbstractBinaryReader(BinaryStream stream) {
		this.stream = stream;
	}

	public abstract String readRecord() throws IOException;

	protected abstract EnhancedCopybook get(Integer length); 
}
