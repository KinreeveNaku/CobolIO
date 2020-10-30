/**
 * 
 */
package com.github.cobolio.internal.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.github.cobolio.types.ConfigurableTypeHandler;
import com.github.cobolio.types.TypeHandler;
import com.github.Alpha;

/**
 * @author Andrew
 *
 */
@Alpha(major = 0, minor = 0, snapshot = 9)
public class TypeHandlerFactory {

	private TypeHandlerFactory parent;
	private ClassLoader classLoader;
	private Map<String, TypeHandler> handlerMap = new HashMap<>();
	private static final String NAME_KEY = "name:";
	private static final String TYPE_KEY = "type:";

	/* The default type handler factory */
    private static final TypeHandlerFactory DEFAULT_FACTORY;
	
    static {
    	DEFAULT_FACTORY = new TypeHandlerFactory(TypeHandlerFactory.class.getClassLoader());
    }
	
	
	/**
	 * Constructs a new <tt>TypeHandlerFactory</tt> using the default type handler
	 * factory for its parent and the same {@link ClassLoader} that loaded this
	 * class.
	 */
	public TypeHandlerFactory() {
		this(TypeHandlerFactory.class.getClassLoader());
	}

	/**
	 * Constructs a new <tt>TypeHandlerFactory</tt> using the default type handler
	 * factory for its parent.
	 * 
	 * @param classLoader the {@link ClassLoader} for resolving unrecognized types
	 * @since 2.0
	 */
	public TypeHandlerFactory(ClassLoader classLoader) {
		setParent(getDefault());
		this.classLoader = classLoader;
	}

	/**
	 * Constructs a new <tt>TypeHandlerFactory</tt>.
	 * 
	 * @param classLoader the {@link ClassLoader} for resolving unrecognized types
	 * @param parent      the parent <tt>TypeHandlerFactory</tt>
	 * @since 2.0
	 */
	public TypeHandlerFactory(ClassLoader classLoader, TypeHandlerFactory parent) {
		setParent(parent);
		this.classLoader = classLoader;
	}

	/**
	 * Returns a named type handler, or <tt>null</tt> if there is no type handler
	 * configured for the given name in this factory or any of its ancestors.
	 * 
	 * @param name the name of type handler was registered under
	 * @return the type handler, or <tt>null</tt> if there is no configured type
	 *         handler registered for the name
	 */
	public TypeHandler getTypeHandler(String name) {
		return getTypeHandler(name, null);
	}

	/**
	 * Returns a named type handler, or <tt>null</tt> if there is no type handler
	 * configured for the given name in this factory or any of its ancestors.
	 * 
	 * @param name       the name the type handler was registered under
	 * @param properties the custom properties for configuring the type handler
	 * @return the type handler, or <tt>null</tt> if there is no configured type
	 *         handler registered for the name
	 * @throws IllegalArgumentException if a custom property value was invalid
	 */
	public TypeHandler getTypeHandler(String name, Properties properties) {
		if (name == null) {
			throw new NullPointerException();
		}
		return getHandler(NAME_KEY + name, null, properties);
	}

	/**
	 * Returns the type handler for the given type, or <tt>null</tt> if there is no
	 * type handler configured for the type in this factory or any of its ancestors.
	 * 
	 * @param type the class name or type alias
	 * @return the type handler, or <tt>null</tt> if there is no configured type
	 *         handler registered for the type
	 */
	public TypeHandler getTypeHandlerFor(String type) {
		return getTypeHandlerFor(type, (String) null, (Properties) null);
	}

	/**
	 * Returns the type handler for the given type and format, or <tt>null</tt> if
	 * there is no type handler configured for the type in this factory or any of
	 * its ancestors.
	 * 
	 * @param type   the class name or type alias
	 * @param format the stream format, or if null, format specific handlers will
	 *               not be returned
	 * @return the type handler, or <tt>null</tt> if there is no configured type
	 *         handler registered for the type
	 * @since 2.0
	 */
	public TypeHandler getTypeHandlerFor(String type, String format) {
		return getTypeHandlerFor(type, format, (Properties) null);
	}

	/**
	 * Returns the type handler for the given type, or <tt>null</tt> if there is no
	 * type handler configured for the type in this factory or any of its ancestors.
	 * 
	 * @param type       the property type
	 * @param format     the stream format, or if null, format specific handlers
	 *                   will not be returned
	 * @param properties the custom properties for configuring the type handler
	 * @return the type handler, or <tt>null</tt> if there is no configured type
	 *         handler registered for the type
	 * @throws IllegalArgumentException if a custom property value was invalid
	 * @since 2.0
	 */
	public TypeHandler getTypeHandlerFor(String type, String format, Properties properties) {
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
			return getTypeHandlerFor(clazz, format, properties);
		}
	}

	/**
	 * Returns a type handler for a class, or <tt>null</tt> if there is no type
	 * handler configured for the class in this factory or any of its ancestors
	 * 
	 * @param clazz the target class to find a type handler for
	 * @return the type handler, or null if the class is not supported
	 */
	public TypeHandler getTypeHandlerFor(Class<?> clazz) {
		return getTypeHandlerFor(clazz, null, null);
	}

	/**
	 * Returns a type handler for a class, or <tt>null</tt> if there is no type
	 * handler configured for the class in this factory or any of its ancestors
	 * 
	 * @param clazz      the target class to find a type handler for
	 * @param format     the stream format, or if null, format specific handlers
	 *                   will not be returned
	 * @param properties the custom properties for configuring the type handler
	 * @return the type handler, or null if the class is not supported
	 * @throws IllegalArgumentException if a custom property value was invalid
	 * @since 2.0
	 */
	@SuppressWarnings({ })
	public TypeHandler getTypeHandlerFor(Class<?> clazz, String format, Properties properties) {
		if (clazz == null) {
			throw new NullPointerException();
		}
		clazz = TypeUtil.toWrapperClass(clazz);

		return getHandler(TYPE_KEY + clazz.getName(), format, properties);
	}

	private TypeHandler getHandler(String key, String format, Properties properties) {
		TypeHandler handler = null;
		TypeHandlerFactory factory = this;
		while (factory != null) {
			// query for format specific handler first
			if (format != null) {
				handler = factory.handlerMap.get(format + "." + key);
				if (handler != null) {
					return getHandler(handler, properties);
				}
			}
			handler = factory.handlerMap.get(key);
			if (handler != null) {
				return getHandler(handler, properties);
			}
			factory = factory.parent;
		}
		return null;
	}

	private TypeHandler getHandler(TypeHandler handler, Properties properties) {
		if (properties != null && !properties.isEmpty()) {
			if (handler instanceof ConfigurableTypeHandler) {
				handler = ((ConfigurableTypeHandler) handler).newInstance(properties);
			} else {
				String property = properties.keys().nextElement().toString();
				throw new IllegalArgumentException("'" + property + "' setting not supported by type handler");
			}
		}
		return handler;
	}

	/**
	 * Registers a type handler in this factory.
	 * 
	 * @param name    the name to register the type handler under
	 * @param handler the type handler to register
	 */
	public void registerHandler(String name, TypeHandler handler) {
		if (name == null) {
			throw new NullPointerException();
		}
		if (handler == null) {
			throw new NullPointerException();
		}
		handlerMap.put(NAME_KEY + name, handler);
	}

	/**
	 * Registers a type handler in this factory by class type for all stream formats
	 * 
	 * @param type    the fully qualified class name or type alias to register the
	 *                type handler for
	 * @param handler the type handler to register
	 * @throws IllegalArgumentException if the type name is invalid or if the
	 *                                  handler type is not assignable from the type
	 */
	public void registerHandlerFor(String type, TypeHandler handler) {
		registerHandlerFor(type, handler, null);
	}

	/**
	 * Registers a type handler in this factory by class type for a specific stream
	 * format.
	 * 
	 * @param type    the fully qualified class name or type alias to register the
	 *                type handler for
	 * @param handler the type handler to register
	 * @param format  the stream format to register the type handler for, or if null
	 *                the type handler may be returned for any format
	 * @throws IllegalArgumentException if the type name is invalid or if the
	 *                                  handler type is not assignable from the type
	 * @since 2.0
	 */
	public void registerHandlerFor(String type, TypeHandler handler, String format) {
		if (type == null) {
			throw new NullPointerException();
		}

		Class<?> clazz = TypeUtil.toType(classLoader, type);
		if (clazz == null) {
			throw new IllegalArgumentException("Invalid type or type alias '" + type + "'");
		}
		if (TypeUtil.isAliasOnly(type)) {
			type = type.toLowerCase();
			registerHandlerFor(format, type, clazz, handler);
		} else {
			registerHandlerFor(format, clazz.getName(), clazz, handler);
		}
	}

	/**
	 * Registers a type handler in this factory for any stream format.
	 * 
	 * @param clazz   the target class to register the type handler for
	 * @param handler the type handler to register
	 * @throws IllegalArgumentException if the handler type is not assignable from
	 *                                  the registered class type
	 */
	public void registerHandlerFor(Class<?> clazz, TypeHandler handler) {
		registerHandlerFor(clazz, handler, null);
	}

	/**
	 * Registers a type handler in this factory for a specific stream format.
	 * 
	 * @param clazz   the target class to register the type handler for
	 * @param handler the type handler to register
	 * @param format  the stream format to register the type handler for, or if null
	 *                the type handler may be returned for any format
	 * @throws IllegalArgumentException if the handler type is not assignable from
	 *                                  the registered class type
	 */
	public void registerHandlerFor(Class<?> clazz, TypeHandler handler, String format) {
		if (clazz == null) {
			throw new NullPointerException();
		}
		clazz = TypeUtil.toWrapperClass(clazz);
		registerHandlerFor(format, clazz.getName(), clazz, handler);
	}

	private void registerHandlerFor(String format, String type, Class<?> expectedClass, TypeHandler handler) {
		if (!TypeUtil.isAssignable(expectedClass, handler.getType())) {
			throw new IllegalArgumentException("Type handler type '" + handler.getType().getName()
					+ "' is not assignable from configured " + "type '" + expectedClass.getName() + "'");
		}
		if (format != null) {
			handlerMap.put(format + "." + TYPE_KEY + type, handler);
		} else {
			handlerMap.put(TYPE_KEY + type, handler);
		}
	}

	/**
	 * Returns the default <tt>TypeHandlerFactory</tt>.
	 * 
	 * @return the default <tt>TypeHandlerFactory</tt>
	 */
	public static TypeHandlerFactory getDefault() {
		return DEFAULT_FACTORY;
	}

	/**
	 * Sets the parent <tt>TypeHandlerFactory</tt>.
	 * 
	 * @param parent the parent <tt>TypeHandlerFactory</tt>
	 */
	private void setParent(TypeHandlerFactory parent) {
		this.parent = parent;
	}
}
