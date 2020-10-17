/**
 * 
 */
package com.github.cobolio.types.transform;

/**
 * @author Andrew
 *
 */
public interface TypeTransformer {
	void transform(Object o);
	
	Class<?> getType();
	
	Class<?> getReturnType();
}
