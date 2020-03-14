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
public class NationalAccessor extends AbstractCobolAccessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4741173476303328817L;

	/**
	 * 
	 */
	NationalAccessor() {
		super(null);
	}

	public NationalAccessor(FieldFormat format) {
		super(format);
	}

	@Override
	public String convert(byte[] bytes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTransferType() {
		return TransferUtil.STRING;
	}

	@Override
	public NationalAccessor newInstance(FieldFormat format) {
		return new NationalAccessor(format);
	}

	@Override
	public int getDisplayLength() {
		return super.getFormat().getSize().intValueExact();
	}
}
