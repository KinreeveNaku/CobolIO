/**
 * 
 */
package com.github.cobolio.internal.compiler;


import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import com.github.cobolio.CobolSchema.Type;
import static com.github.cobolio.CobolSchema.Type.*;
/**
 * @author Andrew
 *
 */
public class TypesDeserializer {
	
	public static Object deserialize(String content, Type type) {
		switch(type) {
		case BYTES : return deserializeBytes(content);
		case ARRAY : return deserializeArray(content);
		default: return deserializeObject(content);
		}
	}
	
	/**
	 * @param content
	 * @return
	 */
	private static Object deserializeArray(String content) {
		// TODO Auto-generated method stub
		return null;
	}

	private String deserializeString(String content) {
		return content;
	}
	
	private int deserializeInt(String content) {
		return Integer.valueOf(content);
	}
	
	private long deserializeLong(String content) {
		return Long.valueOf(content);
	}
	
	private float deserializeFloat(String content) {
		return Float.valueOf(content);
	}
	
	private double deserializeDouble(String content) {
		return Double.valueOf(content);
	}
	
	private static byte[] deserializeBytes(String content) {
		return null;//TODO write logic
	}
	
	private boolean deserializeBoolean(String content) {
		return Boolean.valueOf(content);
	}
	
	private static Object deserializeObject(String content) {
		ByteArrayInputStream stream = new ByteArrayInputStream(content.getBytes(UTF_8));
		ObjectInputStream reader = null;
		try {
			 reader = new ObjectInputStream(stream);
			 return reader.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException(e);
		} catch (ClassNotFoundException e) {
			System.err.println("Object deserialization failed for unknown type specification");
			throw new IllegalArgumentException(e);
		}
		
		
	}
}
