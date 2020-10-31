/**
 * 
 */
package com.github.cobolio.internal.cobol.fields;

import java.io.InvalidObjectException;
import java.io.Serializable;

import com.github.Version;
import com.github.cobolio.internal.xml.cb2xml.EnhancedItem;

/**
 * @author Andrew
 *
 */
@SuppressWarnings({ "squid:S1166", "squid:S4926" })
public abstract class AbstractField implements NamedField, Serializable {
	/**
	 * 
	 */
	@Version(major = 0, minor = 1, bugfix = 0)
	private static final long serialVersionUID = 7840242756972131519L;
	protected final transient EnhancedItem properties;

	/**
	 * 
	 */
	protected AbstractField(EnhancedItem properties) {
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
