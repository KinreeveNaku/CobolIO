/**
 * 
 */
package com.github.mfds2j.data.cobol.fields.cobol;

import com.github.mfds2j.Alpha;
import com.github.mfds2j.data.cobol.cb2xml.EnhancedItem;
import com.github.mfds2j.data.cobol.cobol.IbmFloatField;
import com.github.mfds2j.data.cobol.fields.AbstractField;
import com.github.mfds2j.data.cobol.fields.NamedField;
import com.github.mfds2j.data.cobol.fields.NumericField;

import net.sf.cb2xml.def.Cb2xmlConstants.Usage;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
@Alpha(major = 0, minor = 0, snapshot = 9)
public class HexFloatField extends AbstractField implements NumericField {

	private final transient IbmFloatField field;

	/**
	 * @param properties
	 */
	public HexFloatField(EnhancedItem properties) {
		super(properties);
		this.field = new IbmFloatField(0);
	}

	@Override
	public EnhancedItem getProperties() {
		return super.properties;
	}

	@Override
	public NamedField newInstance(EnhancedItem properties) {
		return new HexFloatField(properties);
	}

	@Override
	public byte[] asBytes(byte[] bytes) {
		return bytes;
	}

	@Override
	public boolean asBoolean(byte[] bytes) {
		throw new UnsupportedOperationException("Float cannot be represented as a boolean variable.");
	}

	@Override
	public int asInteger(byte[] bytes) {
		return (int) field.getFloat(bytes);
	}

	@Override
	public long asLong(byte[] bytes) {
		return (long) field.getFloat(bytes);
	}

	@Override
	public float asFloat(byte[] bytes) {
		return field.getFloat(bytes);
	}

	@Override
	public double asDouble(byte[] bytes) {
		return field.getFloat(bytes);
	}

	@Override
	public String getTypeAsString() {
		return Usage.COMP_1.getName();
	}

	@Override
	public String asString(byte[] bytes) {
		return String.valueOf(asFloat(bytes));
	}

}
