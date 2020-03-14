/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.java;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import com.ibm.dataaccess.accessor.CharSequenceAccessor;
import com.ibm.dataaccess.accessor.Stringable;
import com.ibm.dataaccess.accessor.TransferUtil;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class StringAccessor extends CharSequenceAccessor implements Stringable {
	/**
	 * 
	 */
	private static final String STRINGABLE = "%s1 %s2 %s3 %s4 %s5 %s6";

	/**
	 * 
	 */
	public StringAccessor() {
		super(Charset.defaultCharset(), Locale.getDefault());
	}

	public StringAccessor(Charset charset) {
		super(charset, Locale.getDefault());
	}

	public StringAccessor(Charset charset, Locale locale) {
		super(charset, locale);
	}

	@Override
	public String convert(byte[] bytes) {
		return new String(bytes, super.charset);
	}

	@Override
	public int getTransferType() {
		return TransferUtil.STRING;
	}

	@Override
	public String toString() {
		return String.format(locale, STRINGABLE, this.getClass().getSimpleName(), "{", this.getCharsetName(), ", ",
				this.locale, "}");
	}

	@Override
	public String revert(String str, Charset[] inOrderCharsets) {
		if (!inOrderCharsets[inOrderCharsets.length - 1].equals(this.charset)
				|| !inOrderCharsets[inOrderCharsets.length - 1].equals(StandardCharsets.UTF_16BE)) {
			throw new IllegalArgumentException(String.format(
					"Last charset should match this instances designated charset of %s or UTF-16BE but was %s",
					this.charset, inOrderCharsets[inOrderCharsets.length - 1]));
		}
		for (Charset set : inOrderCharsets) {
			str = new String(str.getBytes(set), set);
		}
		return str;
	}
}
