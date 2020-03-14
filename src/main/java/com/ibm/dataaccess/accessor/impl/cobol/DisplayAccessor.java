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
public class DisplayAccessor extends AbstractCobolAccessor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2037785285311533681L;

	/**
	 * 
	 */
	protected DisplayAccessor() {
		super(null);
	}

	public DisplayAccessor(FieldFormat format) {
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
	public DisplayAccessor newInstance(FieldFormat format) {
		return new DisplayAccessor(format);
	}

	public static DisplayAccessor instantiate(FieldFormat format) {
		return new DisplayAccessor(format);
	}

	@Override
	public int getDisplayLength() {
		return super.getFormat().getSize().intValueExact();
	}

}
