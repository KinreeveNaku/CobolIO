/**
 * 
 */
package com.github.cobolio.types;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.UnsupportedCharsetException;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Logger;

/**
 * 
 * 
 * A type handler implementation for the <tt>String</tt> class.
 * 
 * @author Andrew
 */
public class StringTypeHandler implements TypeHandler {
	private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(StringTypeHandler.class);
	private static final byte[] EMPTY = new byte[0];
	Charset charset;
	CharsetDecoder decoder;
	CharsetEncoder encoder;
	private boolean trim = false;
	private boolean nullIfEmpty = false;

	/**
	 * The charset is assumed to be the current default system charset.
	 */
	public StringTypeHandler() {
		this.charset = Charset.defaultCharset();
		this.encoder = this.charset.newEncoder();
		this.decoder = this.charset.newDecoder();
		if(!this.charset.canEncode()) {
			throw new UnsupportedCharsetException(this.charset.name());
		}
	}

	/**
	 * 
	 * @param charset The charset to interpret the bytes with.
	 */
	public StringTypeHandler(Charset charset) {
		this.charset = charset == null ? Charset.defaultCharset() : charset;
		this.encoder = this.charset.newEncoder();
		this.decoder = this.charset.newDecoder();
		if(!this.charset.canEncode()) {
			throw new UnsupportedCharsetException(this.charset.name());
		}
	}

	/**
	 * The charset is assumed to be the current default system charset.
	 * 
	 * @param trim
	 * @param nullIfEmpty Whether to treat empty strings as null. True to treat
	 *                    empty as null, and false to treat them as empty strings.
	 */
	public StringTypeHandler(boolean trim, boolean nullIfEmpty) {
		this.charset = Charset.defaultCharset();
		this.trim = trim;
		this.nullIfEmpty = nullIfEmpty;
		encoder = this.charset.newEncoder();
		decoder = this.charset.newDecoder();
		if(!this.charset.canEncode()) {
			throw new UnsupportedCharsetException(this.charset.name());
		}
	}

	/**
	 * 
	 * @param charset     The charset to interpret the bytes with.
	 * @param trim        Whether whitespace should be trimmed from the extremities
	 *                    of the byte arrays.
	 * @param nullIfEmpty Whether to treat empty strings as null. True to treat
	 *                    empty as null, and false to treat them as empty strings.
	 */
	public StringTypeHandler(Charset charset, boolean trim, boolean nullIfEmpty) {
		this.charset = charset == null ? Charset.defaultCharset() : charset;
		this.trim = trim;
		this.nullIfEmpty = nullIfEmpty;
		this.encoder = this.charset.newEncoder();
		this.decoder = this.charset.newDecoder();
		if(!this.charset.canEncode()) {
			throw new UnsupportedCharsetException(this.charset.name());
		}
	}

	/**
	 * Parses a <tt>String</tt> from the given text.
	 * 
	 * @param text the text to parse
	 * @return the parsed <tt>String</tt>
	 */
	@Override
	public String parse(byte[] text) {
		if (text != null) {
			if (trim) {
				text = trim(text);
				
			}
			if (nullIfEmpty && text.length == 0) {
				return null;
			}
			
		}
		return new String(text, this.charset);
	}

	/**
	 * Formats the value by calling {@link Object#toString()}.
	 * 
	 * @param value the value to format
	 * @return the formatted value, or <tt>null</tt> if <tt>value</tt> is
	 *         <tt>null</tt>
	 */
	@Override
	public byte[] format(Object value) {
		if (value == null) {
			return EMPTY.clone();
		} else {
			try {
				return encoder.encode(CharBuffer.wrap(value.toString())).array();
			} catch (CharacterCodingException e) {
				LOGGER.warn("Encoding could not be performed.", e);
				return EMPTY.clone();
			}
		}
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

	public byte[] trim(byte[] text) {
		return new String(this.charset.decode(ByteBuffer.wrap(text)).array()).trim().getBytes(this.charset);
	}

}
