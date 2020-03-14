/**
 * 
 */
package com.ibm.dataaccess.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrew
 *
 */
public class Union {
	@SuppressWarnings("rawtypes")
	private Map<String, Class> types;
	private Union parent;
	private List<?> children;
	
	public Union(Union parent) {
		this.parent = parent;
		this.children = new ArrayList<>();
		this.types = new HashMap<>();
	}
	
	public Union(Union parent, List<?> children) {
		this.parent = parent;
		this.children = children;
		this.types = new HashMap<>();
		for(Object child : children) {
			if(child instanceof Union) {
				this.types.putAll(((Union)child).getTypes());
			} else {
				this.types.put(child.getClass().getName(), child.getClass());
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public Map<String, Class> getTypes() {
		return this.types;
	}

	public Union getParent() {
		return this.parent;
	}

	@SuppressWarnings("rawtypes")
	public List getChildren() {
		return this.children;
	}
	
	
}
