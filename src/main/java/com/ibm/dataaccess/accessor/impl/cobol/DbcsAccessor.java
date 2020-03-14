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
public class DbcsAccessor extends AbstractCobolAccessor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6005431306128362703L;

	/**
	 * 
	 */
	DbcsAccessor() {
		super(null);
	}

	/**
	 * @param format
	 */
	public DbcsAccessor(FieldFormat format) {
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
	public DbcsAccessor newInstance(FieldFormat format) {
		return new DbcsAccessor(format);
	}

	@Override
	public int getDisplayLength() {
		return super.getFormat().getSize().intValueExact();
	}
}
