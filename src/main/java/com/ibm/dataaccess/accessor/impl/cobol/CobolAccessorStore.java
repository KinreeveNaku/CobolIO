/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.cobol;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.ibm.dataaccess.accessor.AbstractCobolAccessor;
import com.ibm.dataaccess.accessor.ObjectAccessor;
import com.ibm.dataaccess.accessor.impl.AnyAsNull;

import net.sf.cobol2j.FieldFormat;

/**
 * @author Andrew
 *
 */
public class CobolAccessorStore {
	private static final int DEFAULT_MAPS_SIZE = 25;

	private final Map<String, ObjectAccessor> cobolStore;
	private static final Map<String, ObjectAccessor> COBOL_DEFAULTS;

	static {
		COBOL_DEFAULTS = new HashMap<>(DEFAULT_MAPS_SIZE);
		COBOL_DEFAULTS.put("B", new Comp4Accessor());
		COBOL_DEFAULTS.put("1", new Comp1Accessor());
		COBOL_DEFAULTS.put("2", new Comp2Accessor());
		COBOL_DEFAULTS.put("3", new Comp3Accessor());
		COBOL_DEFAULTS.put("6", new Comp6Accessor());
		COBOL_DEFAULTS.put("9", new ZonedDecimalAccessor());
		COBOL_DEFAULTS.put("N", new NationalAccessor());
		COBOL_DEFAULTS.put("DBCS", new DbcsAccessor());
		COBOL_DEFAULTS.put("VOID", new AnyAsNull());
	}

	public CobolAccessorStore() {
		Map<String, ObjectAccessor> temp = new HashMap<>(DEFAULT_MAPS_SIZE);
		temp.putAll(COBOL_DEFAULTS);
		this.cobolStore = Collections.unmodifiableMap(temp);
	}
	
	public AbstractCobolAccessor get(String key) {
		return (AbstractCobolAccessor) this.cobolStore.get(key);
	}
	
	public AbstractCobolAccessor get(FieldFormat format) {
		return (AbstractCobolAccessor) this.cobolStore.get(format.getType());
	}
	
	public AbstractCobolAccessor put(String key, AbstractCobolAccessor accessor) {
		return (AbstractCobolAccessor) this.cobolStore.put(key, accessor);
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, ? extends AbstractCobolAccessor> getDefaults() {
		return (Map<String, ? extends AbstractCobolAccessor>) COBOL_DEFAULTS;
	}
}
