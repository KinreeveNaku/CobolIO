/**
 * 
 */
package com.github.cobolio.types;

/**
 * 
 *
 * A <tt>TypeHandler</tt> is used to convert field text into a Java object and
 * vice versa.
 * <p>
 * Implementations should be thread-safe if multiple threads may concurrently
 * process the same stream type.
 * @author Andrew
 */
public interface TypeHandler {

	/**
	 * This constant can be returned from {@link #format(Object)} for XML formatted
	 * streams to indicate a nillable element should be set to nil even if the
	 * field's minimum occurrences is zero. In all other cases, if NIL is returned,
	 * the formatted value is treated as <tt>null</tt>.
	 */
	String NIL = ("");

	/**
	 * Parses field text into a Java object.
	 * 
	 * @param text the field text to parse, which may be null if the field was not
	 *             passed in the record
	 * @return the parsed Java object
	 * @throws TypeConversionException if the text cannot be parsed
	 */
	Object parse(String text) throws TypeConversionException;

	/**
	 * Formats a Java object into field text.
	 * 
	 * @param value the Java object to format, which may be null
	 * @return the formatted field text, or <tt>null</tt> to indicate the value is
	 *         not present, or {@link #NIL} for XML formatted streams
	 */
	String format(Object value);

	/**
	 * Returns the class type supported by this handler. Primitive types should not
	 * be returned by this method- use the object equivalent instead.
	 * 
	 * @return the class type supported by this handler
	 */
	Class<?> getType();
}
