/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.cobol;

import com.ibm.dataaccess.ByteArrayUnmarshaller;
import com.ibm.dataaccess.accessor.AbstractCobolAccessor;
import com.ibm.dataaccess.accessor.TransferUtil;

import net.sf.cobol2j.FieldFormat;

/**
 * @author Andrew
 *
 */
public class ZonedDecimalAccessor extends AbstractCobolAccessor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4482902168989389170L;

	/**
	 * 
	 */
	public ZonedDecimalAccessor() {
		super(null);
	}

	public ZonedDecimalAccessor(FieldFormat format) {
		super(format);
	}

	@Override
	public Number convert(byte[] bytes) {
		return ByteArrayUnmarshaller.readInt(bytes, 0, true);
	}

	@Override
	public int getTransferType() {
		return TransferUtil.NUMBER;
	}

	@Override
	public ZonedDecimalAccessor newInstance(FieldFormat format) {
		return this.newInstance(format);
	}

	@Override
	public int getDisplayLength() {
		return super.getFormat().getSize().intValueExact();
	}

}
