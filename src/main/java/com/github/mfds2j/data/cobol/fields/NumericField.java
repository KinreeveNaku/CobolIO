/**
 * 
 */
package com.github.mfds2j.data.cobol.fields;

/**
 * @author Andrew
 *
 */
public interface NumericField extends NamedField {

	byte[] asBytes(byte[] bytes);

	boolean asBoolean(byte[] bytes);

	int asInteger(byte[] bytes);

	long asLong(byte[] bytes);

	float asFloat(byte[] bytes);

	double asDouble(byte[] bytes);
	
	String asString(byte[] bytes);
}
