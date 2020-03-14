/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.java;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.ibm.dataaccess.accessor.JavaAccessor;
import com.ibm.dataaccess.accessor.ObjectAccessor;
import com.ibm.dataaccess.accessor.impl.AnyAsNull;

/**
 * @author Andrew
 *
 */
public class JavaAccessorStore {
	/**
	 * 
	 */
	private static final int DEFAULT_MAPS_SIZE = 25;
	private final Map<String, ObjectAccessor> javaStore;
	private static final Map<String, ObjectAccessor> JAVA_DEFAULTS;

	static {
		JAVA_DEFAULTS = new HashMap<>(DEFAULT_MAPS_SIZE);
		JAVA_DEFAULTS.put("BIGD", new BigDecimalAccessor());
		JAVA_DEFAULTS.put("BIGI", new BigIntegerAccessor());
		JAVA_DEFAULTS.put("LONG", new LongAccessor());
		JAVA_DEFAULTS.put("INT", new IntegerAccessor());
		JAVA_DEFAULTS.put("STR", new StringAccessor());
		JAVA_DEFAULTS.put("SPFP", new FloatAccessor());
		JAVA_DEFAULTS.put("DPFP", new DoubleAccessor());
		JAVA_DEFAULTS.put("BYTS", new BytesAccessor());
		JAVA_DEFAULTS.put("BOOL", new BooleanAccessor());
		JAVA_DEFAULTS.put("VOID", new AnyAsNull());
	}

	public JavaAccessorStore() {
		Map<String, ObjectAccessor> temp = new HashMap<>(DEFAULT_MAPS_SIZE);
		temp.putAll(JAVA_DEFAULTS);
		this.javaStore = Collections.unmodifiableMap(temp);
	}

	public JavaAccessor putAccessor(String id, JavaAccessor accessor) {
		return (JavaAccessor) this.javaStore.put(id, accessor);
	}

	/**
	 * @param element
	 * @return
	 */
	public JavaAccessor get(String key) {
		return (JavaAccessor) javaStore.get(key);
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, ? extends JavaAccessor> getDefaults() {
		return (Map<String, ? extends JavaAccessor>) JAVA_DEFAULTS;
	}
}
