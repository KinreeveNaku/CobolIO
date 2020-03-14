/**
 * 
 */
package com.ibm.dataaccess.accessor.impl;

import java.util.HashMap;
import java.util.Map;

import com.ibm.dataaccess.accessor.AbstractCobolAccessor;
import com.ibm.dataaccess.accessor.ObjectAccessor;
import com.ibm.dataaccess.accessor.impl.cobol.CobolAccessorStore;
import com.ibm.dataaccess.accessor.impl.java.JavaAccessorStore;

import net.sf.cobol2j.CompilerProperties;
import net.sf.cobol2j.CompilerProperties.NSymbol;
import net.sf.cobol2j.FieldFormat;

/**
 * @author Andrew
 *
 */
public class AccessorStore {
	/**
	 * 
	 */
	private static final int DEFAULTS_MAP_SIZE = 25;

	private final CobolAccessorStore cobolStore;
	private final JavaAccessorStore javaStore;
	private static final CobolAccessorStore COBOL_DEFAULTS;
	private static final JavaAccessorStore JAVA_DEFAULTS;
	private final Map<String, ObjectAccessor> primaryStore;
	private CompilerProperties storeProperties;
	static {
		COBOL_DEFAULTS = new CobolAccessorStore();
		JAVA_DEFAULTS = new JavaAccessorStore();
	}

	public AccessorStore() {
		this.cobolStore = new CobolAccessorStore();
		this.javaStore = new JavaAccessorStore();
		this.primaryStore = new HashMap<>();
		this.primaryStore.putAll(javaStore.getDefaults());
		this.primaryStore.putAll(cobolStore.getDefaults());
		this.storeProperties = new CompilerProperties();
	}

	public AccessorStore(CompilerProperties properties) {
		this.cobolStore = new CobolAccessorStore();
		this.javaStore = new JavaAccessorStore();
		this.primaryStore = new HashMap<>();
		this.primaryStore.putAll(javaStore.getDefaults());
		this.primaryStore.putAll(cobolStore.getDefaults());
		if (properties.getNSymbol() == NSymbol.NATIONAL || properties.getNSymbol() == NSymbol.NAT) {
			this.cobolStore.put("DBCS", COBOL_DEFAULTS.get("N"));
			this.primaryStore.put("DBCS", COBOL_DEFAULTS.get("N"));
		} else {
			this.cobolStore.put("DBCS", COBOL_DEFAULTS.get("DBCS"));
		}
		this.storeProperties = properties;
	}

	/**
	 * @param element
	 * @return
	 */
	public AbstractCobolAccessor getAccessorForFieldFormat(FieldFormat element) {
		return cobolStore.get(element.getType());
	}

	public ObjectAccessor getAccessor(String key) {
		return primaryStore.get(key);
	}
}
