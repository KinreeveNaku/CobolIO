/**
 * 
 */
package com.ibm.dataaccess.accessor;

import java.nio.charset.Charset;
import java.util.Locale;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class CharSequenceAccessor implements JavaAccessor {
	/**
	 * @Note In following the {@link com.ibm.dataaccess.accessor.Stringable Stringable}
	 *       contract, charset is declared final to coincide with StringAccessor and
	 *       general data integrity constraints.
	 */
	protected final transient Charset charset;
	protected final String charsetName;
	protected final transient Locale locale;

	/**
	 * 
	 */
	public CharSequenceAccessor() {
		this.charset = Charset.defaultCharset();
		this.locale = Locale.getDefault();
		this.charsetName = this.charset.displayName(locale);
	}

	public CharSequenceAccessor(Charset charset, Locale locale) {
		this.charset = charset;
		this.locale = locale;
		this.charsetName = charset.displayName(locale);

	}

	@Override
	public CharSequence convert(byte[] source) {
		return new String(source, charset);
	}

	@Override
	public int getTransferType() {
		return TransferUtil.CHARSEQUENCE;
	}

	public Charset getCharset() {
		return this.charset;
	}

	public String getCharsetName() {
		return charsetName;
	}

	public Locale getLocale() {
		return locale;
	}
}
