/**
 * 
 */
package com.github.cobolio.internal.cobol.fields;

import java.nio.charset.Charset;

/**
 * @author Andrew
 *
 */
public interface AlphanumericField extends NamedField {
	String getString(byte[] bytes);
	String getString(byte[] bytes, Charset charset);
}
