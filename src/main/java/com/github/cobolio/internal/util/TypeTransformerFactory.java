/**
 * 
 */
package com.github.cobolio.internal.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.github.cobolio.types.transform.ConfigurableTypeTransformer;
import com.github.cobolio.types.transform.TypeTransformer;

/**
 * Pulled from org.beanio.
 * @author Andrew
 *
 */
public class TypeTransformerFactory {

	private TypeTransformerFactory parent;
	private ClassLoader classLoader;
	private Map<String, TypeTransformer> transformerMap = new HashMap<>();
	private static final String NAME_KEY = "name:";
	private static final String TYPE_KEY = "type:";

	private static final TypeTransformerFactory DEFAULT_FACTORY;

	static {
		DEFAULT_FACTORY = new TypeTransformerFactory(TypeTransformerFactory.class.getClassLoader());
	}

	/**
	 * Constructs a new <tt>TypeTransformerFactory</tt> using the default type
	 * transformer factory for its parent.
	 * 
	 * @param classLoader the {@link ClassLoader} for resolving unrecognized types
	 * @since 2.0
	 */
	public TypeTransformerFactory(ClassLoader classLoader) {
		setParent(getDefault());
		this.classLoader = classLoader;
	}

	/**
	 * Constructs a new <tt>TypeTransformerFactory</tt>.
	 * 
	 * @param classLoader the {@link ClassLoader} for resolving unrecognized types
	 * @param parent      the parent <tt>TypeTransformerFactory</tt>
	 * @since 2.0
	 */
	public TypeTransformerFactory(ClassLoader classLoader, TypeTransformerFactory parent) {
		setParent(parent);
		this.classLoader = classLoader;
	}

	/**
	 * Returns a named type transformer, or <tt>null</tt> if there is no type
	 * transformer configured for the given name in this factory or any of its
	 * ancestors.
	 * 
	 * @param name the name of type transformer was registered under
	 * @return the type transformer, or <tt>null</tt> if there is no configured type
	 *         transformer registered for the name
	 */
	public TypeTransformer getTypeTransformer(String name) {
		return getTypeTransformer(name, null);
	}

	/**
	 * Returns a named type transformer, or <tt>null</tt> if there is no type
	 * transformer configured for the given name in this factory or any of its
	 * ancestors.
	 * 
	 * @param name       the name the type transformer was registered under
	 * @param properties the custom properties for configuring the type transformer
	 * @return the type transformer, or <tt>null</tt> if there is no configured type
	 *         transformer registered for the name
	 * @throws IllegalArgumentException if a custom property value was invalid
	 */
	public TypeTransformer getTypeTransformer(String name, Properties properties) {
		if (name == null) {
			throw new NullPointerException();
		}
		return getHandler(NAME_KEY + name, null, properties);
	}

	/**
	 * Returns the type transformer for the given type, or <tt>null</tt> if there is
	 * no type transformer configured for the type in this factory or any of its
	 * ancestors.
	 * 
	 * @param type the class name or type alias
	 * @return the type transformer, or <tt>null</tt> if there is no configured type
	 *         transformer registered for the type
	 */
	public TypeTransformer getTypeTransformerFor(String type) {
		return getTypeTransformerFor(type, (String) null, (Properties) null);
	}

	/**
	 * Returns the type transformer for the given type and format, or <tt>null</tt>
	 * if there is no type transformer configured for the type in this factory or
	 * any of its ancestors.
	 * 
	 * @param type   the class name or type alias
	 * @param format the stream format, or if null, format specific transformers
	 *               will not be returned
	 * @return the type transformer, or <tt>null</tt> if there is no configured type
	 *         transformer registered for the type
	 * @since 2.0
	 */
	public TypeTransformer getTypeTransformerFor(String type, String format) {
		return getTypeTransformerFor(type, format, (Properties) null);
	}

	/**
	 * Returns the type transformer for the given type, or <tt>null</tt> if there is
	 * no type transformer configured for the type in this factory or any of its
	 * ancestors.
	 * 
	 * @param type       the property type
	 * @param format     the stream format, or if null, format specific transformers
	 *                   will not be returned
	 * @param properties the custom properties for configuring the type transformer
	 * @return the type transformer, or <tt>null</tt> if there is no configured type
	 *         transformer registered for the type
	 * @throws IllegalArgumentException if a custom property value was invalid
	 * @since 2.0
	 */
	public TypeTransformer getTypeTransformerFor(String type, String format, Properties properties) {
		if (type == null) {
			throw new NullPointerException();
		}

		if (TypeUtil.isAliasOnly(type)) {
			return getHandler(TYPE_KEY + type.toLowerCase(), format, properties);
		} else {
			Class<?> clazz = TypeUtil.toType(classLoader, type);
			if (clazz == null) {
				return null;
			}
			return getTypeTransformerFor(clazz, format, properties);
		}
	}

	/**
	 * Returns a type transformer for a class, or <tt>null</tt> if there is no type
	 * transformer configured for the class in this factory or any of its ancestors
	 * 
	 * @param clazz the target class to find a type transformer for
	 * @return the type transformer, or null if the class is not supported
	 */
	public TypeTransformer getTypeTransformerFor(Class<?> clazz) {
		return getTypeTransformerFor(clazz, null, null);
	}

	/**
	 * Returns a type transformer for a class, or <tt>null</tt> if there is no type
	 * transformer configured for the class in this factory or any of its ancestors
	 * 
	 * @param clazz      the target class to find a type transformer for
	 * @param format     the stream format, or if null, format specific transformers
	 *                   will not be returned
	 * @param properties the custom properties for configuring the type transformer
	 * @return the type transformer, or null if the class is not supported
	 * @throws IllegalArgumentException if a custom property value was invalid
	 */
	public TypeTransformer getTypeTransformerFor(Class<?> clazz, String format, Properties properties) {
		if (clazz == null) {
			throw new NullPointerException();
		}
		clazz = TypeUtil.toWrapperClass(clazz);

		return getHandler(TYPE_KEY + clazz.getName(), format, properties);
	}

	private TypeTransformer getHandler(String key, String format, Properties properties) {
		TypeTransformer transformer = null;
		TypeTransformerFactory factory = this;
		while (factory != null) {
			// query for format specific transformer first
			if (format != null) {
				transformer = factory.transformerMap.get(format + "." + key);
				if (transformer != null) {
					return getHandler(transformer, properties);
				}
			}
			transformer = factory.transformerMap.get(key);
			if (transformer != null) {
				return getHandler(transformer, properties);
			}
			factory = factory.parent;
		}
		return null;
	}

	private TypeTransformer getHandler(TypeTransformer transformer, Properties properties) {
		if (properties != null && !properties.isEmpty()) {
			if (transformer instanceof ConfigurableTypeTransformer) {
				transformer = ((ConfigurableTypeTransformer) transformer).newInstance(properties);
			} else {
				String property = properties.keys().nextElement().toString();
				throw new IllegalArgumentException("'" + property + "' setting not supported by type transformer");
			}
		}
		return transformer;
	}

	/**
	 * Registers a type transformer in this factory.
	 * 
	 * @param name        the name to register the type transformer under
	 * @param transformer the type transformer to register
	 */
	public void registerHandler(String name, TypeTransformer transformer) {
		if (name == null) {
			throw new NullPointerException();
		}
		if (transformer == null) {
			throw new NullPointerException();
		}
		transformerMap.put(NAME_KEY + name, transformer);
	}

	/**
	 * Registers a type transformer in this factory by class type for all stream
	 * formats
	 * 
	 * @param type        the fully qualified class name or type alias to register
	 *                    the type transformer for
	 * @param transformer the type transformer to register
	 * @throws IllegalArgumentException if the type name is invalid or if the
	 *                                  transformer type is not assignable from the
	 *                                  type
	 */
	public void registerHandlerFor(String type, TypeTransformer transformer) {
		registerHandlerFor(type, transformer, null);
	}

	/**
	 * Registers a type transformer in this factory by class type for a specific
	 * stream format.
	 * 
	 * @param type        the fully qualified class name or type alias to register
	 *                    the type transformer for
	 * @param transformer the type transformer to register
	 * @param format      the stream format to register the type transformer for, or
	 *                    if null the type transformer may be returned for any
	 *                    format
	 * @throws IllegalArgumentException if the type name is invalid or if the
	 *                                  transformer type is not assignable from the
	 *                                  type
	 */
	public void registerHandlerFor(String type, TypeTransformer transformer, String format) {
		if (type == null) {
			throw new NullPointerException();
		}

		Class<?> clazz = TypeUtil.toType(classLoader, type);
		if (clazz == null) {
			throw new IllegalArgumentException("Invalid type or type alias '" + type + "'");
		}
		if (TypeUtil.isAliasOnly(type)) {
			type = type.toLowerCase();
			registerHandlerFor(format, type, clazz, transformer);
		} else {
			registerHandlerFor(format, clazz.getName(), clazz, transformer);
		}
	}

	/**
	 * Registers a type transformer in this factory for any stream format.
	 * 
	 * @param clazz       the target class to register the type transformer for
	 * @param transformer the type transformer to register
	 * @throws IllegalArgumentException if the transformer type is not assignable
	 *                                  from the registered class type
	 */
	public void registerHandlerFor(Class<?> clazz, TypeTransformer transformer) {
		registerHandlerFor(clazz, transformer, null);
	}

	/**
	 * Registers a type transformer in this factory for a specific stream format.
	 * 
	 * @param clazz       the target class to register the type transformer for
	 * @param transformer the type transformer to register
	 * @param format      the stream format to register the type transformer for, or
	 *                    if null the type transformer may be returned for any
	 *                    format
	 * @throws IllegalArgumentException if the transformer type is not assignable
	 *                                  from the registered class type
	 */
	public void registerHandlerFor(Class<?> clazz, TypeTransformer transformer, String format)
			throws IllegalArgumentException {
		if (clazz == null) {
			throw new NullPointerException();
		}
		clazz = TypeUtil.toWrapperClass(clazz);
		registerHandlerFor(format, clazz.getName(), clazz, transformer);
	}

	private void registerHandlerFor(String format, String type, Class<?> expectedClass, TypeTransformer transformer) {
		if (!TypeUtil.isAssignable(expectedClass, transformer.getType())) {
			throw new IllegalArgumentException("Type transformer type '" + transformer.getType().getName()
					+ "' is not assignable from configured " + "type '" + expectedClass.getName() + "'");
		}
		if (format != null) {
			transformerMap.put(format + "." + TYPE_KEY + type, transformer);
		} else {
			transformerMap.put(TYPE_KEY + type, transformer);
		}
	}

	/**
	 * Returns the default <tt>TypeTransformerFactory</tt>.
	 * 
	 * @return the default <tt>TypeTransformerFactory</tt>
	 */
	public static TypeTransformerFactory getDefault() {
		return DEFAULT_FACTORY;
	}

	/**
	 * Sets the parent <tt>TypeTransformerFactory</tt>.
	 * 
	 * @param parent the parent <tt>TypeTransformerFactory</tt>
	 */
	private void setParent(TypeTransformerFactory parent) {
		this.parent = parent;
	}
}
