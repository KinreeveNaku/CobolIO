/**
 * 
 */
package com.github.cobolio.internal.util.transform;

import java.math.BigDecimal;

import com.github.Alpha;
import com.github.cobolio.internal.cobol.datatype.ExternalDecimal;
import com.github.cobolio.internal.cobol.datatype.PackedDecimal;
import com.github.cobolio.internal.cobol.datatype.UnicodeDecimal;

/**
 * Transforms Complex Cobol types into Java primitive types.
 * This class shortens import statement lists and also
 * further standardizes implementations of transformations.
 * @author Andrew
 *
 */
@Alpha(major = 0, minor = 0, snapshot = 10)
public class Cobol2JavaTransform {

	public static final TransformFunction<PackedDecimal, Boolean> PackedDecimal_Boolean = p -> (boolean) p.booleanValue();
	public static final TransformFunction<PackedDecimal, Byte> PackedDecimal_Byte = p -> p.byteValue();
	public static final TransformFunction<PackedDecimal, Double> PackedDecimal_Double = p -> p.doubleValue();
	public static final TransformFunction<PackedDecimal, Float> PackedDecimal_Float = p -> p.floatValue();
	public static final TransformFunction<PackedDecimal, Integer> PackedDecimal_Integer = p -> p.intValue();
	public static final TransformFunction<PackedDecimal, Long> PackedDecimal_Long = p -> p.longValue();
	public static final TransformFunction<PackedDecimal, Short> PackedDecimal_Short = p -> p.shortValue();
	public static final TransformFunction<PackedDecimal, BigDecimal> PackedDecimal_BigDecimal = p -> p.bigDecimalValue();//TODO complete PackedDecimal#bigDecimalValue()
	
	public static final TransformFunction<ExternalDecimal, Boolean> ExternalDecimal_Boolean = p -> p.booleanValue();
	public static final TransformFunction<ExternalDecimal, Byte> ExternalDecimal_Byte = p -> p.byteValue();
	public static final TransformFunction<ExternalDecimal, Double> ExternalDecimal_Double = p -> p.doubleValue();
	public static final TransformFunction<ExternalDecimal, Float> ExternalDecimal_Float = p -> p.floatValue();
	public static final TransformFunction<ExternalDecimal, Integer> ExternalDecimal_Integer = p -> p.intValue();
	public static final TransformFunction<ExternalDecimal, Long> ExternalDecimal_Long = p -> p.longValue();
	public static final TransformFunction<ExternalDecimal, Short> ExternalDecimal_Short = p -> p.shortValue();
	public static final TransformFunction<ExternalDecimal, BigDecimal> ExternalDecimal_BigDecimal = p -> p.bigDecimalValue();//TODO complete ExternalDecimal#bigDecimalValue()
	
	public static final TransformFunction<UnicodeDecimal, Boolean> UnicodeDecimal_Boolean = p -> p.booleanValue();
	public static final TransformFunction<UnicodeDecimal, Byte> UnicodeDecimal_Byte = p -> p.byteValue();
	public static final TransformFunction<UnicodeDecimal, Double> UnicodeDecimal_Double = p -> p.doubleValue();
	public static final TransformFunction<UnicodeDecimal, Float> UnicodeDecimal_Float = p -> p.floatValue();
	public static final TransformFunction<UnicodeDecimal, Integer> UnicodeDecimal_Integer = p -> p.intValue();
	public static final TransformFunction<UnicodeDecimal, Long> UnicodeDecimal_Long = p -> p.longValue();
	public static final TransformFunction<UnicodeDecimal, Short> UnicodeDecimal_Short = p -> p.shortValue();
	public static final TransformFunction<UnicodeDecimal, BigDecimal> UnicodeDecimal_BigDecimal = p -> p.bigDecimalValue();//TODO complete UnicodeDecimal#bigDecimalValue()
	
	/**
	 * 
	 */
	private Cobol2JavaTransform() {
		throw new UnsupportedOperationException();
	}
	
	
}
