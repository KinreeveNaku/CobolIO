/**
 * 
 */
package com.github.mfds2j.classgen;

import java.util.Collection;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.mfds2j.classgen.registry.Conversion;

/**
 * @author Andrew
 *
 */
public class VirtualData {
	private Map<String, Conversion<?>> conversions = new HashMap<>();
	private Map<Class<?>, Map<String, Conversion<?>>> conversionsByClass = new IdentityHashMap<>();

	public Collection<Conversion<?>> getConversions() {
		return conversions.values();
	}

	/**
	 * Registers the given conversion to be used when reading and writing with this
	 * data model.
	 *
	 * @param conversion a logical type Conversion.
	 */
	public void addLogicalTypeConversion(Conversion<?> conversion) {
		conversions.put(conversion.getLogicalTypeName(), conversion);
		Class<?> type = conversion.getConvertedType();
		if (conversionsByClass.containsKey(type)) {
			conversionsByClass.get(type).put(conversion.getLogicalTypeName(), conversion);
		} else {
			Map<String, Conversion<?>> conversions = new LinkedHashMap<>();
			conversions.put(conversion.getLogicalTypeName(), conversion);
			conversionsByClass.put(type, conversions);
		}
	}

}
