/**
 * 
 */
package com.github.cobolio.internal.serialization;

import java.util.Map;

/**
 * @author Andrew
 *
 */
public class ByteArrayDeserializer implements Deserializer<byte[]> {

	@Override
	public byte[] deserialize(byte[] data) {
		return data;
	}
	
	@Override
	public byte[] deserialize(byte[] data, Map<String, Object> meta) {
		return data;
	}

	

}
