/**
 * 
 */
package com.github.cobolio.internal.cobol;

import static com.github.mfds2j.data.cobol.cobol.PrimitiveConstants.*;

/**
 * @author Andrew
 *
 */
public final class ByteUtils {

	/**
	 * @param bytes
	 * @param i
	 * @param byteLength
	 * @return
	 */
	public static byte[] intAsBytes(int i) {
		return new byte[] {(byte) ((i >>> TWENTYFOUR) & 0xFF), (byte) ((i >>> SIXTEEN) & 0xFF), (byte) ((i >>> BYTE_LENGTH) & 0xFF), (byte) ((i >>> 0) & 0xFF)};
	}
	
	public static long bytesAsLong(byte[] bytes) {
		return bytesAsLong(bytes, 0, bytes.length);
	}
	
	public static long bytesAsLong(byte[] bytes, int offset, int byteLength) {
		if(byteLength > BYTE_LENGTH) {
			throw new IllegalArgumentException();
		} else {
			long value = 0;
			int end = offset + byteLength;
			
			for(int i = offset; i < end; i++) {
				value <<= BYTE_LENGTH;
				value |= (long)(bytes[i] & 0xFF);
			}
			return value;
		}
	}
	
	public static int bytesAsInteger(byte[] bytes) {
		return bytesAsInteger(bytes, 0, bytes.length);
	}
	
	/**
	 * @param bytes
	 * @param i
	 * @param byteLength
	 * @return
	 */
	public static int bytesAsInteger(byte[] bytes, int offset, int byteLength) {
		if(byteLength > NIBBLE_LENGTH) {
			throw new IllegalArgumentException();
		} else {
			int value = 0;
			int end = offset + byteLength;
			
			for(int i = offset; i < end; i++) {
				value <<= BYTE_LENGTH;
				value |= bytes[i] & 0xFF;
			}
			return value;
		}
	}
	
	
	public static byte getHighNibble(byte b) {
		return (byte) (b & PrimitiveConstants.HIGH_NIBBLE_MASK);
	}
	
	public static byte getLowNibble(byte b) {
		return (byte) (b & PrimitiveConstants.LOW_NIBBLE_MASK);
	}
	
	private ByteUtils() {
		throw new IllegalArgumentException();
	}
}
