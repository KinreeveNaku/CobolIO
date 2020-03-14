/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.cobol;

import java.math.BigDecimal;

import com.ibm.dataaccess.accessor.AbstractCobolAccessor;
import com.ibm.dataaccess.accessor.TransferUtil;

import net.sf.cobol2j.FieldFormat;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class Comp5Accessor extends AbstractCobolAccessor {

	/**
	 * 
	 */
	Comp5Accessor() {
		super(null);
	}

	/**
	 * @param format
	 */
	public Comp5Accessor(FieldFormat format) {
		super(format);
	}

	@Override
	public BigDecimal convert(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTransferType() {
		return TransferUtil.NUMBER;
	}

	@Override
	public Comp5Accessor newInstance(FieldFormat format) {
		return new Comp5Accessor(format);
	}

	@Override
	public int getDisplayLength() {
		return super.getFormat().getSize().intValueExact();
	}
}
