/**
 * 
 */
package com.ibm.dataaccess.support;

import net.sf.cobol2j.FieldFormat;

/**
 * @author Andrew
 *
 */
public class TypeDecisionEngine {
	private static final int BOOL = 1;
	private static final int[] REAL_NUMBER = { 1, 8, 16, 32, 64, 128 };
	private static final int[] FLOAT_NUMBER = { 161, 193, 257 };
	private static final int[] ALPHANUMERIC = { 321, 385, 513 };

	public TypeDecisionEngine() {

	}

	private Class<?> getSuggested(FieldFormat format) {
		int rating = 0;
		if(format.getPicture().matches(".*[S]{0,1}[9].*")) {
			rating += format.getSize().subtract(format.getDecimal()).intValueExact();
		}
		rating += format.getDecimal().intValueExact();
		return null;
	}
}
