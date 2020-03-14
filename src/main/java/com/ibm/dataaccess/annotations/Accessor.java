/**
 * 
 */
package com.ibm.dataaccess.annotations;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.ibm.dataaccess.accessor.SerializableAccessor;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, FIELD, CONSTRUCTOR})
/**
 * @author Andrew
 *
 */
public @interface Accessor {
	Class<?> incoming();
	Class<?> outgoing();
	Class<? extends SerializableAccessor> accessor();
}
