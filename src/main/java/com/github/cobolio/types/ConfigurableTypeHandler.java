package com.github.cobolio.types;

import java.util.Properties;

/**
 * Interface for type handlers that support field specific customization. When a
 * type handler is registered that implements this interface, the
 * <tt>TypeHandlerFactory</tt> invokes <tt>newInstance(Properties)</tt> if any
 * type handler field properties were set.
 * 
 * @author Kevin Seim
 * @since 1.0
 */
public interface ConfigurableTypeHandler extends TypeHandler {

	/** The field format pattern */
	String FORMAT_SETTING = "format";

	/**
	 * Creates a customized instance of this type handler.
	 * 
	 * @param properties the properties for customizing the instance
	 * @return the new <tt>TypeHandler</tt>
	 * @throws IllegalArgumentException if a property value is invalid
	 */
	TypeHandler newInstance(Properties properties);

}
