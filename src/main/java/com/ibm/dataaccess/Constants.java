/**
 * 
 */
package com.ibm.dataaccess;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Andrew
 *
 */
public class Constants {
	@SuppressWarnings("squid:CommentedOutCodeLine")

	// (int)floor((24-1)*(log10(2)/log10(10)));
	public static final int MAX_FLOAT_SCALE = 6;
	@SuppressWarnings("squid:CommentedOutCodeLine")
	// (int)floor((53-1)*(log10(2)/log10(10)));
	public static final int MAX_DOUBLE_SCALE = 15;
	public static final int BASE_TEN = 10;

	// Error messages
	public static final String NOT_SUPPORTED = " is not supported by this library.";

	public static final Charset UTF_8 = StandardCharsets.UTF_8;

	private Constants() {
		throw new IllegalArgumentException();
	}
}
