/**
 * 
 */
package com.ibm.dataaccess.transform;

/**
 * @author Andrew
 *
 */
public interface Transform<T> {
	T to(byte[] bytes);
}
