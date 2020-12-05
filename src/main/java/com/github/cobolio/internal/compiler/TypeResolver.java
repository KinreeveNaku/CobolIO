/**
 * 
 */
package com.github.cobolio.internal.compiler;

import java.util.Map;

import com.github.cobolio.internal.xml.cb2xml.EnhancedCopybook;
import com.github.cobolio.internal.xml.cb2xml.EnhancedItem;

/**
 * @author Andrew
 *
 */
public interface TypeResolver {
	
	Class<?> resolve(EnhancedItem item);
	
	Map<String, Class<?>> resolve(EnhancedCopybook copybook);

}
