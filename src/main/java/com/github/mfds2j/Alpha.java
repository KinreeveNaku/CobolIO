package com.github.mfds2j;

/**
 * 
 */

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * An annotation used to indicate that the entity annotated is currently in
 * Alpha stages and is still in early development and as such, experimental.
 * 
 * @author Andrew
 *
 */
@Documented
@Retention(SOURCE)
@Target({ TYPE, FIELD, METHOD, CONSTRUCTOR, ANNOTATION_TYPE, PACKAGE })
public @interface Alpha {
	int major();

	int minor();

	int snapshot();
}
