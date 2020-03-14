/**
 * 
 */
package com.ibm.dataaccess.receiver;

import java.util.function.Function;

import com.ibm.dataaccess.transform.Transform;
import com.ibm.dataaccess.transform.Transformable;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("rawtypes")
public interface AbstractReceiver<T> extends Transformable, Transform {
	default <R> R transform(Function<? super AbstractReceiver<T>, ? extends R> f) {
		return f.apply(this);
	}
}
