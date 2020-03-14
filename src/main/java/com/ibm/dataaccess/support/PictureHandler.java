/**
 * 
 */
package com.ibm.dataaccess.support;

import net.sf.cb2xml.def.Cb2xmlConstants.Usage;

/**
 * @author Andrew
 *
 */
public class PictureHandler {

	private static final int NUMERIC = 1;
	private static final int NUMERIC_REAL = 2;
	private static final int NUMERIC_DECIMAL = 3;
	private static final int NUMERIC_EDITED = 4;
	private static final int ALPHABETIC = 26;
	private static final int ALPHABETIC_EDITED = 27;
	private static final int ALPHANUMERIC = 36;
	private static final int ALPHANUMERIC_EDITED = 37;
	private static final int NUMERIC_FLOAT = 12;// 32bit 1bit sign 7bit exp 24bit mantissa.
	private static final int NUMERIC_DOUBLE = 22;// 64bit 1bit sign 7bit exp 56bit mantissa.
	private static final int NUMERIC_COMP3 = 32;//n-length
	private static final int NUMERIC_BINARY = 42;//16,32,64bit two's compliment.

	public static final int parsePicture(String picture) {
		return 0;
	}
	
	public static final int parseField(String picture, Usage usage) {
		return 0;
	}
}
