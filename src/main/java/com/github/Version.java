package com.github;

/**
 * 
 */

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * An annotation used to indicate the version that a trait was last updated in.
 * This is easier to look up than through JavaDoc.
 * @author Andrew
 *
 */
@Documented
@Retention(SOURCE)
@Target({ TYPE, FIELD, METHOD, CONSTRUCTOR, ANNOTATION_TYPE, PACKAGE, LOCAL_VARIABLE})
@Version(major = 0, minor = 1, bugfix = 0)
public @interface Version {
	int major();

	int minor();

	int bugfix();
}
