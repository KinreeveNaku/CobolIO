package com.github.mfds2j.data.cobol.cb2xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.github.mfds2j.data.cobol.fields.NamedField;

/**
 * An Item is a representation of a field in a copybook. This class reflects
 * Item class in cb2xml, but with additional fields for performance improvements
 * to this library's use-cases.
 * 
 * @author Andrew
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "condition", "item" })
@XmlRootElement(name = "item")
public final class EnhancedItem {
	protected List<Condition> conditions;
	protected List<EnhancedItem> enhancedItems;
	@XmlAttribute(name = "assumed-digits")
	protected Integer assumedDigits;
	@XmlAttribute(name = "depending-on")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String dependingOn;
	@XmlAttribute(name = "display-length", required = true)
	protected int displayLength;
	@XmlAttribute(name = "editted-numeric")
	protected Boolean edittedNumeric;
	@XmlAttribute(name = "inherited-usage")
	protected Boolean inheritedUsage;
	@XmlAttribute(name = "insert-decimal-point")
	protected Boolean insertDecimalPoint;
	@XmlAttribute(name = "justified")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String justified;
	@XmlAttribute(name = "level", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String level;
	@XmlAttribute(name = "name", required = true)
	@XmlSchemaType(name = "anySimpleType")
	protected String name;
	@XmlAttribute(name = "numeric")
	protected Boolean numeric;
	@XmlAttribute(name = "occurs")
	protected Integer occurs;
	@XmlAttribute(name = "occurs-min")
	protected Integer occursMin;
	@XmlAttribute(name = "picture")
	@XmlSchemaType(name = "anySimpleType")
	protected String picture;
	@XmlAttribute(name = "position", required = true)
	protected int position;
	@XmlAttribute(name = "redefined")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String redefined;
	@XmlAttribute(name = "redefines")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String redefines;
	@XmlAttribute(name = "scale")
	protected Integer scale;
	@XmlAttribute(name = "sign-position")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String signPosition;
	@XmlAttribute(name = "sign-separate")
	protected Boolean signSeparate;
	@XmlAttribute(name = "signed")
	protected Boolean signed;
	@XmlAttribute(name = "signShown")
	protected Boolean signShown;
	@XmlAttribute(name = "storage-length", required = true)
	protected int storageLength;
	@XmlAttribute(name = "sync")
	protected Boolean sync;
	@XmlAttribute(name = "usage")
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlSchemaType(name = "token")
	protected String usage;
	@XmlAttribute(name = "value")
	@XmlSchemaType(name = "anySimpleType")
	protected String value;
	protected String fieldPath;
	private NamedField field;
	protected boolean canConcatenate;
	protected Class<?> concatenationType;
	/**
	 * Available on groups. If enabled, The group will have its contents
	 * concatenated into the destination type. Recommended primarily for
	 * Alphanumeric, but also applicable for sign-separate value groups
	 */
	@XmlAttribute(name = "sameLevelConcatenating")
	protected Boolean sameLevelConcatenating;

	public List<EnhancedItem> getItems() {
		if (this.enhancedItems == null) {
			this.enhancedItems = new ArrayList<>();
		}

		return this.enhancedItems;
	}

	/**
	 * Returns a list of conditions for this field item. Should never be applicable
	 * to copybook.
	 * 
	 * @return
	 */
	public List<Condition> getConditions() {
		if (this.conditions == null) {
			this.conditions = new ArrayList<>();
		}

		return this.conditions;
	}

	public Integer getAssumedDigits() {
		return this.assumedDigits;
	}

	public String getDependingOn() {
		return this.dependingOn;
	}

	/**
	 * Returns the display format length of data backed by this item.
	 * 
	 * @return
	 */
	public int getDisplayLength() {
		return this.displayLength;
	}

	/**
	 * Returns the Handler type that this template should be handled by.
	 * 
	 * @return
	 */
	public NamedField getField() {
		return field;
	}

	/**
	 * Returns the hierarchical path of this item within its parent copybook.
	 * 
	 * @return
	 */
	public String getFieldPath() {
		return this.fieldPath;
	}

	public String getJustified() {
		return this.justified;
	}

	public String getLevel() {// Presumably can be parsed to an integer.
		return this.level;
	}

	public String getName() {
		return this.name;
	}

	public Integer getOccurs() {
		if (this.occurs == null) {// Purely defensive logic
			this.occurs = 1;
		}
		return this.occurs;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getOccursMin() {
		if (this.occursMin == null) {// Purely defensive logic
			this.occursMin = 1;
		}
		return this.occursMin;
	}

	/**
	 * Returns a String representing the display style representation of the data
	 * that this item represents. This field must strictly follow COBOL's Picture
	 * Clause Logic.
	 * 
	 * @return
	 */
	public String getPicture() {
		return this.picture;
	}

	/**
	 * Returns the numeric position of the first character of this field in a
	 * record.
	 * 
	 * @return
	 */
	public int getPosition() {
		return this.position;
	}

	/**
	 * Returns the name of the field that this field's recurring nature is
	 * controlled by.<br />
	 * FIXME Modify logic to update this field to instead store the HIERARCHIAL PATH
	 * of the field, rather than the name.<br />
	 * TODO FUTURE Modify cb2xml to do this using xsl perhaps?
	 * 
	 * @return
	 */
	public String getRedefined() {
		return this.redefined;
	}

	/**
	 * Returns the name of the field that this field redefines.<br />
	 * FIXME Modify logic to update this field to instead store the HIERARCHIAL PATH
	 * of the field, rather than the name.<br />
	 * TODO FUTURE Modify cb2xml to do this using xsl perhaps?
	 * 
	 * @return
	 */
	public String getRedefines() {
		return this.redefines;
	}

	/**
	 * Returns the number of fractional digits that representations of this item
	 * must have.
	 * 
	 * @return
	 */
	public Integer getScale() {
		return this.scale;
	}

	public String getSignPosition() {
		return this.signPosition;
	}

	public int getStorageLength() {
		return this.storageLength;
	}

	public String getUsage() {
		return this.usage;
	}

	public String getValue() {
		return this.value;
	}

	public Boolean isEdittedNumeric() {
		return this.edittedNumeric;
	}

	public Boolean isInheritedUsage() {
		return this.inheritedUsage;
	}

	public Boolean isInsertDecimalPoint() {
		return this.insertDecimalPoint;
	}

	public Boolean isNumeric() {
		return this.numeric;
	}

	public Boolean isSigned() {
		return this.signed;
	}

	public Boolean isSignSeparate() {
		return this.signSeparate;
	}

	public Boolean isSignShown() {
		return this.signShown;
	}

	public Boolean isSync() {
		return this.sync;
	}

	void setAssumedDigits(Integer value) {
		this.assumedDigits = value;
	}

	void setDependingOn(String value) {
		this.dependingOn = value;
	}

	void setDisplayLength(int value) {
		this.displayLength = value;
	}

	void setEdittedNumeric(Boolean value) {
		this.edittedNumeric = value;
	}

	public void setField(NamedField field) {
		this.field = field;
	}

	public void setFieldPath(String fieldPath) {
		this.fieldPath = fieldPath;
	}

	void setInheritedUsage(Boolean value) {
		this.inheritedUsage = value;
	}

	void setInsertDecimalPoint(Boolean value) {
		this.insertDecimalPoint = value;
	}

	void setJustified(String value) {
		this.justified = value;
	}

	void setLevel(String value) {
		this.level = value;
	}

	void setName(String value) {
		this.name = value;
	}

	void setNumeric(Boolean value) {
		this.numeric = value;
	}

	void setOccurs(Integer value) {
		this.occurs = value;
	}

	void setOccursMin(Integer value) {
		this.occursMin = value;
	}

	void setPicture(String value) {
		this.picture = value;
	}

	void setPosition(int value) {
		this.position = value;
	}

	void setRedefined(String value) {
		this.redefined = value;
	}

	void setRedefines(String value) {
		this.redefines = value;
	}

	void setScale(Integer value) {
		this.scale = value;
	}

	void setSigned(Boolean value) {
		this.signed = value;
	}

	void setSignPosition(String value) {
		this.signPosition = value;
	}

	void setSignSeparate(Boolean value) {
		this.signSeparate = value;
	}

	void setSignShown(Boolean signShown) {
		this.signShown = signShown;
	}

	void setStorageLength(int value) {
		this.storageLength = value;
	}

	void setSync(Boolean value) {
		this.sync = value;
	}

	void setUsage(String value) {
		this.usage = value;
	}

	void setValue(String value) {
		this.value = value;
	}

}
