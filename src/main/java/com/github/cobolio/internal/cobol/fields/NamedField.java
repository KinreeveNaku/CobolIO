/**
 * 
 */
package com.github.cobolio.internal.cobol.fields;

import com.github.cobolio.internal.xml.cb2xml.EnhancedItem;

/**
 * @author Andrew
 *
 */
public interface NamedField {
	EnhancedItem getProperties();

	/**
	 * Returns a new instance of the same type of field except formatted for the
	 * Item <code>properties</code>. This method is primarily used for method
	 * chaining for cases where <code>occurs</code> clauses are encountered.
	 * 
	 * @param properties
	 * @return
	 */
	NamedField newInstance(EnhancedItem properties);
}
