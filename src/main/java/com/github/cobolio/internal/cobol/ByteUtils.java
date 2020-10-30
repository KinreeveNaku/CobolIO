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
	
	public static byte[] longToBytes(long l) {
	    byte[] result = new byte[8];
	    for (int i = 7; i >= 0; i--) {
	        result[i] = (byte)(l & 0xFF);
	        l >>= 8;
	    }
	    return result;
	    
	}
	
	public static byte[] longToBytes(long l, boolean bigEndian) {
		byte[] result = new byte[8];
		if(bigEndian) {
		    for (int i = 7; i >= 0; i--) {
		        result[i] = (byte)(l & 0xFF);
		        l >>= 8;
		    }
		} else {
			for(int i = 0; i < 8; i++) {
				result[i] = (byte)(l & 0xFF);
				l >>= 8;
			}
		}
		return result;
		/*
	     * 
	     * if (bigEndian) {//whether the order of bits should be reversed or not
				result[0] = (byte) ((int) (l >> 56));
				result[1] = (byte) ((int) (l >> 48));
				result[2] = (byte) ((int) (l >> 40));
				result[3] = (byte) ((int) (l >> 32));
				result[4] = (byte) ((int) (l >> 24));
				result[5] = (byte) ((int) (l >> 16));
				result[6] = (byte) ((int) (l >> 8));
				result[7] = (byte) ((int) l);
			} else {
				result[7] = (byte) ((int) (l >> 56));
				result[6] = (byte) ((int) (l >> 48));
				result[5] = (byte) ((int) (l >> 40));
				result[4] = (byte) ((int) (l >> 32));
				result[3] = (byte) ((int) (l >> 24));
				result[2] = (byte) ((int) (l >> 16));
				result[1] = (byte) ((int) (l >> 8));
				result[0] = (byte) ((int) l);
			}
			return result;
	     */
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
