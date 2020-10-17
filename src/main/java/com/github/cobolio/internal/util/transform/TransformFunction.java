/**
 * 
 */
package com.github.cobolio.internal.util.transform;

/**
 * @author Andrew
 *
 */
public interface TransformFunction<A, B> {
	B transform(A a);
}
