/**
 * 
 */
package com.github.mfds2j.classgen;

/**
 * @author Andrew
 *
 */
public interface IVirtual {
	Class<?> getTypeOf(int i);
	Object get(int i);
	Class<?> getLogicalType();
}
