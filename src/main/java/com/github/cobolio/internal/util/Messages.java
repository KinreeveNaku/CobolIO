/**
 * 
 */
package com.github.cobolio.internal.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A loader class for messages used in the library.
 * 
 * @author Andrew
 *
 */
public final class Messages {
	private static final String BUNDLE_NAME = "com.github.cobolio.internal.util.messages"; //$NON-NLS-1$
	public static final String BUILD_IDENTIFIER = "20200822-2149";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
		throw new IllegalStateException();
	}

	@SuppressWarnings({ "java:S1166" }) // Suppressing MissingResourceException sonar because this cannot occur when
										// built correctly.
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}
}
