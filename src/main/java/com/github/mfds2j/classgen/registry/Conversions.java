/**
 * 
 */
package com.github.mfds2j.classgen.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Andrew
 *
 */
public class Conversions {
	private static final Map<String, Conversion> registry = new ConcurrentHashMap<>();
}
