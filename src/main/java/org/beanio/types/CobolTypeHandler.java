/**
 * 
 */
package org.beanio.types;

import java.util.Properties;

import net.sf.cobol2j.FieldFormat;

/**
 * @author Andrew
 *
 */
public abstract class CobolTypeHandler implements ConfigurableTypeHandler {
	@Override
	public final TypeHandler newInstance(Properties properties) throws IllegalArgumentException {
		FieldFormat format = (FieldFormat) properties.get("field.format");
		TypeHandler handler = (TypeHandler) properties.get("field.handler");
		return null;

	}

}
