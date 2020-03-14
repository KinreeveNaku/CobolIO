/**
 * 
 */
package com.github.mfds2j.data.defaults;

import java.nio.ByteBuffer;

import com.github.mfds2j.classgen.registry.Conversion;
import com.github.mfds2j.classgen.registry.LogicalType;
import com.github.mfds2j.data.cobol.fields.cobol.HexDoubleField;

import net.sf.cb2xml.def.Cb2xmlConstants;

/**
 * @author Andrew
 *
 */
public class HexDoubleConversion extends Conversion<HexDoubleField> {

	@Override
	public Class<HexDoubleField> getConvertedType() {
		return HexDoubleField.class;
	}

	@Override
	public String getLogicalTypeName() {
		return Cb2xmlConstants.COMP_2;
	}

	@Override
	public ByteBuffer toBytes(HexDoubleField value, byte[] bytes, LogicalType type) {
		return ByteBuffer.wrap(value.asBytes(bytes));
	}

	@Override
	public Integer toInt(HexDoubleField value, byte[] bytes, LogicalType type) {
		return value.asInteger(bytes);
	}

	@Override
	public Long toLong(HexDoubleField value, byte[] bytes, LogicalType type) {
		return value.asLong(bytes);
	}

	@Override
	public Double toDouble(HexDoubleField value, byte[] bytes, LogicalType type) {
		return value.asDouble(bytes);
	}

	@Override
	public Float toFloat(HexDoubleField value, byte[] bytes, LogicalType type) {
		return value.asFloat(bytes);
	}

}
