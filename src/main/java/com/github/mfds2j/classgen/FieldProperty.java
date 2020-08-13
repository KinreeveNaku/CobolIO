/**
 * 
 */
package com.github.mfds2j.classgen;

/**
 * @author Andrew
 *
 */
public final class FieldProperty {
	private String fieldName;
	private String fieldType;
	private int fieldIndex;
	private String fieldValue;
	private String fieldGetSetName;
	private boolean isArray;

	public FieldProperty(String fieldName, String fieldType, int fieldIndex, String fieldValue,
			String fieldGetSetName) {
		this.fieldName = fieldName;
		this.fieldIndex = fieldIndex;
		this.fieldType = fieldType;
		this.fieldValue = fieldValue;
		this.fieldGetSetName = fieldGetSetName;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return this.fieldName;
	}

	/**
	 * @return the fieldType
	 */
	public String getFieldType() {
		return this.fieldType;
	}

	/**
	 * @return the fieldIndex
	 */
	public int getFieldIndex() {
		return this.fieldIndex;
	}

	/**
	 * @return the fieldValue
	 */
	public String getFieldValue() {
		return this.fieldValue;
	}

	/**
	 * @return the fieldGetSetName
	 */
	public String getFieldGetSetName() {
		return this.fieldGetSetName;
	}
	public boolean isArray() {
		return this.isArray;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @param fieldType the fieldType to set
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * @param fieldIndex the fieldIndex to set
	 */
	public void setFieldIndex(int fieldIndex) {
		this.fieldIndex = fieldIndex;
	}

	/**
	 * @param fieldValue the fieldValue to set
	 */
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * @param fieldGetSetName the fieldGetSetName to set
	 */
	public void setFieldGetSetName(String fieldGetSetName) {
		this.fieldGetSetName = fieldGetSetName;
	}
	/**
	 * @param Wether or not this field is an array.
	 */
	public void setIsArray(boolean isArray) {
		this.isArray = isArray;
	}
}
