/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.cobol;

import com.ibm.dataaccess.accessor.AbstractCobolAccessor;
import com.ibm.dataaccess.accessor.TransferUtil;

import net.sf.cobol2j.FieldFormat;

/**
 * @author Andrew
 *
 */
public class Comp4Accessor extends AbstractCobolAccessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5177530356484740442L;

	/**
	 * 
	 */
	Comp4Accessor() {
		super(null);
	}

	/**
	 * @param format
	 */
	public Comp4Accessor(FieldFormat format) {
		super(format);
	}

	@Override
	public Number convert(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTransferType() {
		return TransferUtil.NUMBER;
	}

	@Override
	public Comp4Accessor newInstance(FieldFormat format) {
		return new Comp4Accessor(format);
	}

	@Override
	public int getDisplayLength() {
		return super.getFormat().getSize().intValueExact();
	}

}
