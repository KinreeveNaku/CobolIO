/**
 * 
 */
package com.github.cobolio.internal.serialization;

import java.util.Map;

import com.github.cobolio.internal.util.ImmutableByteArray;

/**
 * @author Andrew
 *
 */
public class BytesDeserializer implements Deserializer<ImmutableByteArray> {
	@Override
	public ImmutableByteArray deserialize(byte[] data) {
		if (data == null)
			return null;

		return new ImmutableByteArray(data);
	}

	@Override
	public ImmutableByteArray deserialize(byte[] data, Map<String, Object> meta) {
		return new ImmutableByteArray(data);
	}
}
