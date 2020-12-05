/**
 * 
 */
package com.github.cobolio.internal.compiler;

import java.util.Collection;
import java.util.Map;

import com.github.cobolio.internal.xml.cb2xml.EnhancedCopybook;
import com.github.cobolio.internal.xml.cb2xml.EnhancedItem;

/**
 * @author Andrew
 *
 */
public class DefaultTypeResolver implements TypeResolver {
	/**
	 * 
	 */
	public DefaultTypeResolver() {
		int i = 0;
		int j = ~i;
	}

	@Override
	public Class<?> resolve(EnhancedItem item) {
		if(item.getItems().isEmpty()) {
			return resolveSimple(item);
		} else {
			return resolveComplex(item);
		}
	}

	@Override
	public Map<String, Class<?>> resolve(EnhancedCopybook copybook) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Class<?> resolveComplex(EnhancedItem item) {
		Class<?> type = null;
		//TODO ResolveType
		if(item.getOccurs() > 0) {
			type = resolveCollection(item);
		}//...
		return type;
	}
	
	private Class<?> resolveSimple(EnhancedItem item) {
		Class<?> type = null;
		//TODO Resolve type
		if(item.isNumeric().booleanValue()) {
			return resolveNumber(item);
		}
		return type;
	}
	
	private Class<? extends Number> resolveNumber(EnhancedItem item) {
		Class<Number> type = null;
		if(item.getDisplayLength() < 9) {
			return Integer.class;
		}
		if(item.getScale() == 0) {
			//is byte, short, integer, long, biginteger
		}
		
		return type;
	}
	
	private Class<? extends Collection<?>> resolveCollection(EnhancedItem item) {
		Class<Collection<?>> type = null;
		//TODO Resolve type
		
		return type;
	}
}
