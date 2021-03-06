/**
 * 
 */
package com.github.cobolio.types;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class TypeConversionException extends Exception {

	/**
	 * Constructs a new <tt>TypeConversionException</tt>.
	 * 
	 * @param message the error message
	 * @param cause   the root cause
	 */
	public TypeConversionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new <tt>TypeConversionException</tt>.
	 * 
	 * @param message the error message
	 */
	public TypeConversionException(String message) {
		super(message);
	}

	/**
	 * Constructs a new <tt>TypeConversionException</tt>.
	 * 
	 * @param cause the root cause
	 */
	public TypeConversionException(Throwable cause) {
		super(cause);
	}
}
