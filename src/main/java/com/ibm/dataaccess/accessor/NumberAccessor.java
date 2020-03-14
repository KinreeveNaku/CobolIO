/**
 * 
 */
package com.ibm.dataaccess.accessor;

import net.sf.cobol2j.FieldFormat;

/**
 * @author Andrew
 *
 */
public abstract class NumberAccessor implements JavaAccessor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -661950684458588168L;

	@Override
	public abstract Number convert(byte[] bytes);

	@Override
	public int getTransferType() {
		return TransferUtil.NUMBER;
	}
}
