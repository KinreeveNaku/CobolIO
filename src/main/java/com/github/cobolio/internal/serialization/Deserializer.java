/**
 * 
 */
package com.github.cobolio.internal.serialization;

import java.util.Map;

/**
 * @author Andrew
 *
 */
public interface Deserializer<T> {
	T deserialize(byte[] data);
	T deserialize(byte[] data, Map<String, Object> meta);
}
