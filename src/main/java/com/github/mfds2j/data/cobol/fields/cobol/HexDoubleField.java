/**
 * 
 */
package com.github.mfds2j.data.cobol.fields.cobol;

import com.github.mfds2j.data.cobol.cb2xml.EnhancedItem;
import com.github.mfds2j.data.cobol.cobol.IbmDoubleField;
import com.github.mfds2j.data.cobol.fields.AbstractField;
import com.github.mfds2j.data.cobol.fields.NamedField;
import com.github.mfds2j.data.cobol.fields.NumericField;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class HexDoubleField extends AbstractField implements NumericField {
	private final transient IbmDoubleField field;

	/**
	 * @param properties
	 */
	public HexDoubleField(EnhancedItem properties) {
		super(properties);
		this.field = new IbmDoubleField(0);
	}

	@Override
	public EnhancedItem getProperties() {
		return super.properties;
	}

	@Override
	public NamedField newInstance(EnhancedItem properties) {
		return new HexDoubleField(properties);
	}

	@Override
	public byte[] asBytes(byte[] bytes) {
		return bytes;
	}

	@Override
	public boolean asBoolean(byte[] bytes) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int asInteger(byte[] bytes) {
		return (int) field.getDouble(bytes);
	}

	@Override
	public long asLong(byte[] bytes) {
		return (long) field.getDouble(bytes);
	}

	@Override
	public float asFloat(byte[] bytes) {
		return (float) field.getDouble(bytes);
	}

	@Override
	public double asDouble(byte[] bytes) {
		return field.getDouble(bytes);
	}

	@Override
	public String getTypeAsString() {
		return super.properties.getUsage();
	}
	
	@Override
	public String asString(byte[] bytes) {
		return String.valueOf(asDouble(bytes));
	}

}
