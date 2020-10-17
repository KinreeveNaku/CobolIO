/**
 * 
 */
package com.github.cobolio.internal.util;

/**
 * <div>
 * 		A general thrown by CobolIO that contains information about an error that was
 * 		thrown by the library which cannot be reconciled internally.
 * </div>
 * <div>
 * 		Use of this exception outside the library should be limited to the
 * 		constructors that do not have the boolean argument <code>isMessageId</code>
 * 		at the end. These are explicitly for fetching internal messages.
 * </div>
 * @author Andrew
 *
 */
public class CobolIOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message The message to be logged. This first gets processed as a
	 *                message key, and if a corresponding key is not found, the
	 *                message itself is used.
	 */
	public CobolIOException(String message) {
		super(message);
	}

	/**
	 * @param message     The message to be logged.
	 * @param isMessageId Whether the message should be treated as a key for
	 *                    retrieving a message from a resource.
	 */
	public CobolIOException(String message, boolean isMessageId) {
		super(isMessageId ? Messages.getString(message) : message);
	}

	/**
	 * @param cause The cause of this exception being thrown.
	 */
	public CobolIOException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message The message to be logged.
	 * @param cause   The cause of this exception being thrown.
	 */
	public CobolIOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message     The message to be logged.
	 * @param cause       The cause of this exception being thrown.
	 * @param isMessageId Whether the message should be treated as a key for
	 *                    retrieving a message from a resource.
	 */
	public CobolIOException(String message, Throwable cause, boolean isMessageId) {
		super(isMessageId ? Messages.getString(message) : message, cause);
	}

	/**
	 * @param message            The message to be logged.
	 * @param cause              The cause of this exception being thrown.
	 * @param enableSuppression
	 * @param writableStackTrace
	 * @param isMessageId        Whether the message should be treated as a key for
	 *                           retrieving a message from a resource.
	 */
	public CobolIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message            The message to be logged.
	 * @param cause              The cause of this exception being thrown.
	 * @param enableSuppression
	 * @param writableStackTrace
	 * @param isMessageId        Whether the message should be treated as a key for
	 *                           retrieving a message from a resource.
	 */
	public CobolIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
			boolean isMessageId) {
		super(isMessageId ? Messages.getString(message) : message, cause, enableSuppression, writableStackTrace);
	}
}
