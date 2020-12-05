/**
 * 
 */
package com.github.cobolio.internal.cobol.datatype;

import java.math.BigDecimal;

/**
 * An abstract class that is the parent of all Decimal-Type COBOL datatypes.
 * @author Andrew
 *
 */
@SuppressWarnings("serial")
public abstract class CobolNumber extends Number {// Should this be an interface?
	private int scale;
	private int precision;
	private boolean isSigned;

	protected CobolNumber(boolean isSigned) {
		this.isSigned = isSigned;
	}

	/**
	 * Returns the COBOL representation of this value.
	 * 
	 * @return
	 */
	public abstract byte[] rawValue();

	/**
	 * Returns the value of the specified number as a BigDecimal.
	 * 
	 * @return the numeric value represented by this object as a BigDecimal.
	 */
	public abstract BigDecimal bigDecimalValue();

	/**
	 * Returns the value of the specified number as a boolean.
	 * 
	 * @return Returns true if <code>value != 0</code>, or false if
	 *         <code>value == 0</code>.
	 */
	public abstract boolean booleanValue();

	/**
	 * Returns the digit precision of this value.
	 * 
	 * @return The precision of this value.
	 */
	public abstract int getPrecision();

	/**
	 * Returns the scale of this value, or 0 if unscaled.
	 * 
	 * @return The scale of this value.
	 */
	public abstract int getScale();

	/**
	 * Returns whether or not this value has a fractional portion.
	 * 
	 * @return Returns true if this value is fractional, otherwise returns false.
	 */
	public abstract boolean isFractional();

	/**
	 * 
	 * @return
	 */
	public boolean isSigned() {
		return this.isSigned;
	}

	/*
	 * @Deprecated
	 */
	protected final void setJValue(BigDecimal jValue) {
	}

	/*
	 * @Deprecated
	 */
	protected final void setRawBytes(byte[] bytes) {
	}
}
