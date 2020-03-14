/**
 * 
 */
package com.github.mfds2j.data.cobol.fields;

import java.io.InvalidObjectException;
import java.io.Serializable;

import com.github.mfds2j.Alpha;
import com.github.mfds2j.data.cobol.cb2xml.EnhancedItem;

/**
 * @author Andrew
 *
 */
@SuppressWarnings({ "squid:S1166", "squid:S4926" })
public abstract class AbstractField implements NamedField, Serializable {

	/**
	 * Value is set and concrete as this class should not change for now. If class
	 * does change, update the Alpha annotation to the new version.
	 */
	@Alpha(major = 0, minor = 0, snapshot = 9)
	private static final long serialVersionUID = 8488666392654465200L;
	protected final transient EnhancedItem properties;

	/**
	 * 
	 */
	public AbstractField(EnhancedItem properties) {
		this.properties = properties;
	}

	public String getFieldPath() {
		return this.properties == null ? null : this.properties.getFieldPath();
	}

	public String getFieldName() {
		return this.properties == null ? null : this.properties.getName();
	}

	public abstract String getTypeAsString();

	static void validate(Serializable ser) throws InvalidObjectException {
		try {
			if (Serializable.class.getField("serialVersionUID").getLong(ser) != serialVersionUID) {
				throw new InvalidObjectException(
						"SerialVersionUID does not match. This application is not the same version.");
			}
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new InvalidObjectException(
					"SerialVersionUID is not present or cannot be accessed. This serialization cannot be validated.");
		}
	}
}
