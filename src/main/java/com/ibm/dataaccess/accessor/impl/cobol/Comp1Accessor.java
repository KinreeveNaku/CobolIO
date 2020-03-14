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
public class Comp1Accessor extends AbstractCobolAccessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1386296132622001067L;

	Comp1Accessor() {
		super(null);
	}

	/**
	 * @param format
	 */
	public Comp1Accessor(FieldFormat format) {
		super(format);
	}

	@Override
	public Float convert(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTransferType() {
		return TransferUtil.FLOAT;
	}

	@Override
	public Comp1Accessor newInstance(FieldFormat format) {
		return new Comp1Accessor(format);
	}

	@Override
	public int getDisplayLength() {
		return super.getFormat().getSize().intValueExact();
	}
}
