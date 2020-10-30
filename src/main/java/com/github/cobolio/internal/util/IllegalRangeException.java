/**
 * 
 */
package com.github.cobolio.internal.util;

/**
 * <div>Thrown when a value has a boundary restriction and the provided value is
 * outside those bounds. This is most commonly thrown when definitions are
 * improperly configured for COBOL type handling. </div><div> Note: Assumptions
 * cannot be made for default values in the base classes, so this exception must
 * be thrown instead. </div>
 * 
 * @author Andrew
 *
 */
public class IllegalRangeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param message
	 * @see RuntimeException#RuntimeException(String)
	 */
	public IllegalRangeException(String message) {
		super(message);
	}

}
