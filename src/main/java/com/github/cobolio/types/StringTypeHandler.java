/**
 * 
 */
package com.github.cobolio.types;

/**
 * 
 * 
 * A type handler implementation for the <tt>String</tt> class.
 * 
 * @author Andrew
 */
public class StringTypeHandler implements TypeHandler {

	private boolean trim = false;
	private boolean nullIfEmpty = false;

	/**
	 * Parses a <tt>String</tt> from the given text.
	 * 
	 * @param text the text to parse
	 * @return the parsed <tt>String</tt>
	 */
	@Override
	public String parse(String text) {
		if (text != null) {
			if (trim) {
				text = text.trim();
			}
			if (nullIfEmpty && text.length() == 0) {
				text = null;
			}
		}
		return text;
	}

	/**
	 * Formats the value by calling {@link Object#toString()}.
	 * 
	 * @param value the value to format
	 * @return the formatted value, or <tt>null</tt> if <tt>value</tt> is
	 *         <tt>null</tt>
	 */
	@Override
	public String format(Object value) {
		if (value == null)
			return null;
		return value.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cobolio.types.TypeHandler#getType()
	 */
	@Override
	public Class<?> getType() {
		return String.class;
	}

	/**
	 * Returns <tt>true</tt> if <tt>parse(String)</tt> should trim the text. By
	 * default, <tt>trim</tt> is <tt>false</tt> which allows trimming to be
	 * controlled by the field definition.
	 * 
	 * @return <tt>true</tt> if parsed text is trimmed
	 */
	public boolean isTrim() {
		return trim;
	}

	/**
	 * Set to <tt>true</tt> to trim text when parsing.
	 * 
	 * @param trim <tt>true</tt> if text should be trimmed when parsed
	 */
	public void setTrim(boolean trim) {
		this.trim = trim;
	}

	/**
	 * Returns <tt>true</tt> if empty string values are parsed as <tt>null</tt>.
	 * Defaults to <tt>false</tt>.
	 * 
	 * @return <tt>true</tt> to convert the empty string to <tt>null</tt>
	 */
	public boolean isNullIfEmpty() {
		return nullIfEmpty;
	}

	/**
	 * Set to <tt>true</tt> if the parsed empty strings should be converted to
	 * <tt>null</tt>.
	 * 
	 * @param nullIfEmpty <tt>true</tt> to convert empty string to <tt>null</tt>
	 */
	public void setNullIfEmpty(boolean nullIfEmpty) {
		this.nullIfEmpty = nullIfEmpty;
	}

}
