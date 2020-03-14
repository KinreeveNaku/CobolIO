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
public class Comp2Accessor extends AbstractCobolAccessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4985242832152810119L;

	Comp2Accessor() {
		super(null);
	}

	/**
	 * @param format
	 */
	public Comp2Accessor(FieldFormat format) {
		super(format);
	}

	@Override
	public Double convert(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTransferType() {
		return TransferUtil.DOUBLE;
	}

	@Override
	public Comp2Accessor newInstance(FieldFormat format) {
		return new Comp2Accessor(format);
	}

	@Override
	public int getDisplayLength() {
		return super.getFormat().getSize().intValueExact();
	}
}
