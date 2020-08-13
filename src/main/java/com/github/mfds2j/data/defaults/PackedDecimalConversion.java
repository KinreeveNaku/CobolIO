/**
 * 
 */
package com.github.mfds2j.data.defaults;

import com.github.mfds2j.classgen.registry.Conversion;
import com.github.mfds2j.data.cobol.fields.cobol.PackedDecimalField;

/**
 * @author Andrew
 *
 */
public class PackedDecimalConversion extends Conversion<PackedDecimalField> {

	@Override
	public Class<PackedDecimalField> getConvertedType() {
		return PackedDecimalField.class;
	}

	@Override
	public String getLogicalTypeName() {
		// TODO Auto-generated method stub
		return null;
	}

}
