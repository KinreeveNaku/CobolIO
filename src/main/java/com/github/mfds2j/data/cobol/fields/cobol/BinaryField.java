/**
 * 
 */
package com.github.mfds2j.data.cobol.fields.cobol;

import com.github.mfds2j.Alpha;
import com.github.mfds2j.data.cobol.cb2xml.EnhancedItem;
import com.github.mfds2j.data.cobol.cobol.BinaryIntegerField;
import com.github.mfds2j.data.cobol.cobol.BinaryLongField;
import com.github.mfds2j.data.cobol.cobol.IbmBinaryField;
import com.github.mfds2j.data.cobol.cobol.PrimitiveConstants;
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
public class BinaryField extends AbstractField implements NumericField {
	private final transient IbmBinaryField field;

	/**
	 * @param properties
	 */
	public BinaryField(EnhancedItem properties) {
		super(properties);
		if (properties.getStorageLength() > 0) {
			this.field = new BinaryIntegerField(0, properties.getDisplayLength(), properties.isSigned());
		} else if (properties.getStorageLength() > PrimitiveConstants.LOW_FOUR
				&& properties.getStorageLength() <= PrimitiveConstants.LOW_EIGHT) {
			this.field = new BinaryLongField(0, properties.getDisplayLength(), properties.isSigned());
		} else {
			throw new ArithmeticException("Computational-4 fields must be between 1 and 8 bytes in length.");
		}

	}

	@Override
	public EnhancedItem getProperties() {
		return super.properties;
	}

	@Override
	public NamedField newInstance(EnhancedItem properties) {
		return new BinaryField(properties);
	}

	@Override
	public byte[] asBytes(byte[] bytes) {
		return bytes;
	}

	@Override
	public boolean asBoolean(byte[] bytes) {
		Number n = field.get(bytes);
		return n.longValue() > 0;
	}

	@Override
	public int asInteger(byte[] bytes) {
		return field.get(bytes).intValue();
	}

	@Override
	public long asLong(byte[] bytes) {
		return field.get(bytes).longValue();
	}

	@Override
	public float asFloat(byte[] bytes) {
		return field.get(bytes).floatValue();
	}

	@Override
	public double asDouble(byte[] bytes) {
		return field.get(bytes).doubleValue();
	}

	@Override
	public String getTypeAsString() {
		return Usage.COMP_4.getName();
	}

	/**
	 * TODO Needs redesign. The intent of the library is for statically streamlining
	 * the flow of parsing logic. Performing any reevaluation each call contradicts
	 * this.
	 */
	@Override
	public String asString(byte[] bytes) {
		if (bytes == null || bytes.length > PrimitiveConstants.LOW_EIGHT || bytes.length < 1) {
			throw new IllegalArgumentException("Invalid length byte[] for COMPUTATIONAL-4.");
		} else if (bytes.length < PrimitiveConstants.LOW_FIVE && bytes.length > 0) {
			return String.valueOf(asInteger(bytes));
		} else {
			return String.valueOf(asLong(bytes));
		}
	}

}
