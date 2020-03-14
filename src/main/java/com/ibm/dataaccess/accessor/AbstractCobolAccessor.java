/**
 * 
 */
package com.ibm.dataaccess.accessor;

import net.sf.cobol2j.FieldFormat;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractCobolAccessor implements ObjectAccessor {
	private final transient FieldFormat format;

	protected AbstractCobolAccessor(FieldFormat format) {
		this.format = format;
	}

	public abstract ObjectAccessor newInstance(FieldFormat format);

	public int getDisplayLength() {
		return this.format.getSize().intValueExact();
	}

	public final FieldFormat getFormat() {
		return this.format;
	}
}
