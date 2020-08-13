/**
 * 
 */
package com.github.mfds2j.classgen.registry;

/**
 * @author Andrew
 *
 */
public class LogicalType {

	public static final String LOGICAL_TYPE_PROP = "logicalType";

	public static final String STRING_PROP = "java.lang.String";

	public static final String CLASS_PROP = "java-class";

	public static final String KEY_CLASS_PROP = "java-key-class";

	public static final String ELEMENT_PROP = "";

	private static final String[] INCOMPATIBLE_PROPS = new String[] { STRING_PROP, CLASS_PROP, KEY_CLASS_PROP,
			ELEMENT_PROP };

	private final String name;

	public LogicalType(String logicalTypeName) {
		this.name = logicalTypeName.intern();
	}

	/**
	 * Get the name of this logical type.
	 * <p>
	 * This name is set as the Schema property "logicalType".
	 *
	 * @return the String name of the logical type
	 */
	public String getName() {
		return name;
	}
}
