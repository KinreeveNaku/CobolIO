/**
 * 
 */
package com.ibm.dataaccess.accessor.impl.cobol;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.ibm.dataaccess.DecimalData;
import com.ibm.dataaccess.accessor.AbstractCobolAccessor;
import com.ibm.dataaccess.accessor.TransferUtil;
import com.ibm.dataaccess.annotations.Converter;

import net.sf.cobol2j.FieldFormat;

/**
 * @author Andrew
 *
 */
public class Comp3Accessor extends AbstractCobolAccessor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8528684342770539136L;

	/**
	 * 
	 */
	Comp3Accessor() {
		super(null);
	}

	/**
	 * @param format
	 */
	public Comp3Accessor(FieldFormat format) {
		super(format);
	}

	@Override
	public BigDecimal convert(byte[] bytes) {
		return DecimalData.convertPackedDecimalToBigDecimal(bytes, 0, super.getFormat().getSize().intValueExact(),
				super.getFormat().getDecimal().intValueExact(), true);
	}

	@Override
	public int getTransferType() {
		return TransferUtil.BIGDECIMAL_DESCENDING;
	}

	@Override
	public Comp3Accessor newInstance(FieldFormat format) {
		return new Comp3Accessor(format);
	}

	@Override
	public int getDisplayLength() {
		return super.getFormat().getSize().intValueExact();
	}

	@Converter
	public Double toDouble(byte[] bytes) {
		return convert(bytes).setScale(super.getFormat().getDecimal().intValue(), RoundingMode.HALF_EVEN).doubleValue();
	}

	@Converter
	public Float toFloat(byte[] bytes) {
		return convert(bytes).setScale(super.getFormat().getDecimal().intValue(), RoundingMode.HALF_EVEN).floatValue();
	}

	@Converter
	public BigDecimal toBigDecimal(byte[] bytes) {
		return convert(bytes).setScale(super.getFormat().getDecimal().intValue(), RoundingMode.HALF_EVEN);
	}

	@Converter
	public String toString(byte[] bytes) {
		return toBigDecimal(bytes).toPlainString();
	}

}
