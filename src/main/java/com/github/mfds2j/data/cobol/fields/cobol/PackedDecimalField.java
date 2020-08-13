/**
 * 
 */
package com.github.mfds2j.data.cobol.fields.cobol;

import com.github.mfds2j.data.cobol.cb2xml.EnhancedItem;
import com.github.mfds2j.data.cobol.cobol.IbmPackedDecimalField;
import com.github.mfds2j.data.cobol.fields.AbstractField;
import com.github.mfds2j.data.cobol.fields.NamedField;
import com.github.mfds2j.data.cobol.fields.NumericField;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class PackedDecimalField extends AbstractField implements NumericField {
	private final transient IbmPackedDecimalField field;

	/**
	 * @param properties
	 */
	public PackedDecimalField(EnhancedItem properties) {
		super(properties);
		this.field = new IbmPackedDecimalField();
	}

	@Override
	public EnhancedItem getProperties() {
		return super.properties;
	}

	@Override
	public NamedField newInstance(EnhancedItem properties) {
		return new PackedDecimalField(properties);
	}

	@Override
	public byte[] asBytes(byte[] bytes) {
		return bytes;
	}

	/**
	 * This will return true iff the packed decimal value is greater than 0. This
	 * includes the sign nibble.
	 */
	@Override
	public boolean asBoolean(byte[] bytes) {
		boolean above = false;
		for (byte b : bytes) {
			if (b > 0) {
				above = true;
				break;
			}
		}
		return (IbmPackedDecimalField.sign(bytes[bytes.length - 1]) > 0) && above;

	}

	@Override
	public int asInteger(byte[] bytes) {
		return IbmPackedDecimalField.getBigInteger(bytes, 0, super.properties.getDisplayLength()).intValueExact();
	}

	@Override
	public long asLong(byte[] bytes) {
		return IbmPackedDecimalField.getBigInteger(bytes, 0, super.properties.getDisplayLength()).longValueExact();
	}

	@Override
	public float asFloat(byte[] bytes) {
		return field.packedToBigDecimal(bytes, super.properties.getScale(), super.properties.getDisplayLength()).floatValue();
	}

	@Override
	public double asDouble(byte[] bytes) {
		return field.packedToBigDecimal(bytes, super.properties.getScale(), super.properties.getDisplayLength()).doubleValue();
	}

	@Override
	public String getTypeAsString() {
		return super.properties.getUsage();
	}

	/**
	 * 
	 * 
	 * HIGH_THREE in this context is used as the offset position of the first
	 * numeric character, '0', which is index 48.
	 * 
	 * @param bytes
	 * @return
	 */
	@Override
	public String asString(byte[] bytes) {
		return field.packedToString(bytes, super.properties.isSignShown());
	}
}
