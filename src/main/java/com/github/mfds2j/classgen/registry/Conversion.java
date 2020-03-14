/**
 * 
 */
package com.github.mfds2j.classgen.registry;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;

import com.github.mfds2j.classgen.IVirtual;

/**
 * Conversion between generic and logical type instances.
 * <p>
 * Instances of this class are added to GenericData to convert a logical type to
 * a particular representation.
 * <p>
 * Implementations must provide: * {@link #getConvertedType()}: get the Java
 * class used for the logical type * {@link #getLogicalTypeName()}: get the
 * logical type this implements
 * <p>
 * Subclasses must also override all of the conversion methods for Avro's base
 * types that are valid for the logical type, or else risk causing
 * {@code UnsupportedOperationException} at runtime.
 * <p>
 * Optionally, use {@link #getRecommendedSchema()} to provide a Schema that will
 * be used when a Schema is generated for the class returned by
 * {@code getConvertedType}.
 *
 * @param <T> a Java type that generic data is converted to
 * 
 * @author Andrew
 */
public abstract class Conversion<T> {

	/**
	 * Return the Java class representing the logical type.
	 *
	 * @return a Java class returned by from methods and accepted by to methods
	 */
	public abstract Class<T> getConvertedType();

	/**
	 * Return the logical type this class converts.
	 *
	 * @return a String logical type name
	 */
	public abstract String getLogicalTypeName();

	/**
	 * Certain logical types may require adjusting the code within the "setter"
	 * methods to make sure the data that is set is properly formatted. This method
	 * allows teh Conversion to generate custom setter code if required.
	 * 
	 * @param varName
	 * @param valParamName
	 * @return a String for the body of the setter method
	 */
	public String adjustAndSetValue(String varName, String valParamName) {
		return varName + " = " + valParamName + ";";
	}

	public T fromBoolean(Boolean value, LogicalType type) {
		throw new UnsupportedOperationException("fromBoolean is not supported for " + type.getName());
	}

	public T fromInt(Integer value, LogicalType type) {
		throw new UnsupportedOperationException("fromInt is not supported for " + type.getName());
	}

	public T fromLong(Long value, LogicalType type) {
		throw new UnsupportedOperationException("fromLong is not supported for " + type.getName());
	}

	public T fromFloat(Float value, LogicalType type) {
		throw new UnsupportedOperationException("fromFloat is not supported for " + type.getName());
	}

	public T fromDouble(Double value, LogicalType type) {
		throw new UnsupportedOperationException("fromDouble is not supported for " + type.getName());
	}

	public T fromCharSequence(CharSequence value, LogicalType type) {
		throw new UnsupportedOperationException("fromCharSequence is not supported for " + type.getName());
	}

	public T fromBytes(ByteBuffer value, LogicalType type) {
		throw new UnsupportedOperationException("fromBytes is not supported for " + type.getName());
	}

	public T fromArray(Collection<?> value, LogicalType type) {
		throw new UnsupportedOperationException("fromArray is not supported for " + type.getName());
	}

	public T fromMap(Map<?, ?> value, LogicalType type) {
		throw new UnsupportedOperationException("fromMap is not supported for " + type.getName());
	}

	public T fromRecord(IVirtual value, LogicalType type) {
		throw new UnsupportedOperationException("fromRecord is not supported for " + type.getName());
	}

	public Boolean toBoolean(T value, byte[] bytes, LogicalType type) {
		throw new UnsupportedOperationException("toBoolean is not supported for " + type.getName());
	}

	public Integer toInt(T value, byte[] bytes, LogicalType type) {
		throw new UnsupportedOperationException("toInt is not supported for " + type.getName());
	}

	public Long toLong(T value, byte[] bytes, LogicalType type) {
		throw new UnsupportedOperationException("toLong is not supported for " + type.getName());
	}

	public Float toFloat(T value, byte[] bytes, LogicalType type) {
		throw new UnsupportedOperationException("toFloat is not supported for " + type.getName());
	}

	public Double toDouble(T value, byte[] bytes, LogicalType type) {
		throw new UnsupportedOperationException("toDouble is not supported for " + type.getName());
	}

	public CharSequence toCharSequence(T value, byte[] bytes, LogicalType type) {
		throw new UnsupportedOperationException("toCharSequence is not supported for " + type.getName());
	}

	public ByteBuffer toBytes(T value, byte[] bytes, LogicalType type) {
		throw new UnsupportedOperationException("toBytes is not supported for " + type.getName());
	}

	public Collection<?> toArray(T value, byte[] bytes, LogicalType type) {
		throw new UnsupportedOperationException("toArray is not supported for " + type.getName());
	}

	public Map toMap(T value, byte[] bytes, LogicalType type) {
		throw new UnsupportedOperationException("toMap is not supported for " + type.getName());
	}

}
