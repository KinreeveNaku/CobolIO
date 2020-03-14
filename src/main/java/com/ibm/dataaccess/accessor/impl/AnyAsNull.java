/**
 * 
 */
package com.ibm.dataaccess.accessor.impl;

import com.ibm.dataaccess.accessor.ObjectAccessor;
import com.ibm.dataaccess.transform.TransferConstants;

import net.sf.cobol2j.FieldFormat;

/**
 * This class is an Accessor wrapper for instances where the desired return type
 * is void. This class works for all types, regardless of their format.
 * 
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public class AnyAsNull implements ObjectAccessor {
	private static final Void VOID = null;

	public AnyAsNull() {
	}

	/**
	 * @param format
	 */
	public AnyAsNull(FieldFormat format) {
	}

	@Override
	public Void convert(byte[] bytes) {
		return VOID;
	}

	@Override
	public int getTransferType() {
		return TransferConstants.ANY_NULL;
	}

}
