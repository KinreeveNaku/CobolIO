/**
 * 
 */
package com.github.mfds2j.data.cobol;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Andrew
 *
 */
public class BinaryStream extends BufferedInputStream {
	private long bytesRead = 0L;

	/**
	 * @param in
	 */
	public BinaryStream(InputStream in) {
		super(in);
	}

	@Override
	public int read(byte[] bytes) throws IOException {
		int len = bytes.length;
		int readLength = read(bytes);
		this.bytesRead += (long)readLength;
		if (readLength == len || readLength == 0) {
			return readLength;
		} else {
			throw new IOException(String.format(
					"The bytes read did not equal the requested length. The number of bytes expected was %s while only %s were read.",
					len, readLength));
		}

	}

	public long getBytesRead() {
		return bytesRead;
	}

}
