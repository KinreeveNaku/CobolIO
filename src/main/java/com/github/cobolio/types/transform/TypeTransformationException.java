/**
 * 
 */
package com.github.cobolio.types.transform;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public final class TypeTransformationException extends Exception {

	/**
	 * Constructs a new <tt>TypeTransformationException</tt>.
	 * 
	 * @param message the error message
	 * @param cause   the root cause
	 */
	public TypeTransformationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new <tt>TypeTransformationException</tt>.
	 * 
	 * @param message the error message
	 */
	public TypeTransformationException(String message) {
		super(message);
	}

	/**
	 * Constructs a new <tt>TypeTransformationException</tt>.
	 * 
	 * @param cause the root cause
	 */
	public TypeTransformationException(Throwable cause) {
		super(cause);
	}
}
