/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.cobol;

import java.math.BigDecimal;

import com.ibm.dataaccess.DecimalData;
import com.ibm.dataaccess.accessor.AbstractCobolAccessor;

import net.sf.cobol2j.FieldFormat;

/**
 * @author Andrew
 *
 */
public class Comp6Accessor extends AbstractCobolAccessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 684160286457266265L;

	/**
	 * 
	 */
	Comp6Accessor() {
		super(null);
	}

	/**
	 * @param format
	 */
	public Comp6Accessor(FieldFormat format) {
		super(format);
	}

	@Override
	public BigDecimal convert(byte[] bytes) {
		return DecimalData.convertPackedDecimalToBigDecimal(bytes, 0, super.getFormat().getSize().intValueExact(),
				super.getFormat().getDecimal().intValueExact(), true);
	}

	@Override
	public int getTransferType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Comp6Accessor newInstance(FieldFormat format) {
		return new Comp6Accessor(format);
	}

	@Override
	public int getDisplayLength() {
		return super.getFormat().getSize().intValueExact();
	}
}
