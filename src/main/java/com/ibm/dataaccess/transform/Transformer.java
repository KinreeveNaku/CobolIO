/**
 * 
 */
package com.ibm.dataaccess.transform;

import java.util.function.Function;

/**
 * @author Andrew
 *
 */
@FunctionalInterface
public interface Transformer<T> {
	<R> R by(Function<? super T, ? extends R> f);
}
