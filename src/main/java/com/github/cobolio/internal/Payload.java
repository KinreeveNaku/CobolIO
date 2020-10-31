/**
 * 
 */
package com.github.cobolio.internal;

import java.io.Serializable;
import java.util.Map;

import com.github.cobolio.CobolSchema;
import com.github.cobolio.internal.util.TypeTransformerFactory;

/**
 * @author Andrew
 *
 */
public abstract class Payload implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte[] record;
	private CobolSchema schema;
	private transient TypeTransformerFactory factory;
	
	public abstract Object get(int fieldIndex);
	
	public abstract void set(int position, Object field, Map<String, Object> properties);
	
	protected byte[] getBytes() {
		return this.record;
	}
	
	public CobolSchema getSchema() {
		return this.schema;
	}
}
