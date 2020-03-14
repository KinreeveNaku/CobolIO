package net.sf.cobol2j;

import java.math.BigInteger;
import java.util.Properties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FieldFormat")
public class FieldFormat {
	@XmlAttribute(name = "Name")
	protected String name;
	@XmlAttribute(name = "Occurs")
	protected BigInteger occurs;
	@XmlAttribute(name = "DependingOn")
	protected String dependingOn;
	@XmlAttribute(name = "Redefined")
	protected Boolean redefined;
	@XmlAttribute(name = "Redefines")
	protected String redefines;
	@XmlAttribute(name = "Size")
	protected BigInteger size;
	@XmlAttribute(name = "Type")
	protected String type;
	@XmlAttribute(name = "Decimal")
	protected BigInteger decimal;
	@XmlAttribute(name = "Signed")
	protected Boolean signed;
	@XmlAttribute(name = "ImpliedDecimal")
	protected Boolean impliedDecimal;
	@XmlAttribute(name = "Value")
	protected String value;
	@XmlAttribute(name = "Picture")
	protected String picture;
	
	protected transient Properties properties;
	
	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public BigInteger getOccurs() {
		return this.occurs;
	}

	public void setOccurs(BigInteger value) {
		this.occurs = value;
	}

	public String getDependingOn() {
		return this.dependingOn;
	}

	public void setDependingOn(String value) {
		this.dependingOn = value;
	}

	public Boolean isRedefined() {
		return this.redefined;
	}

	public void setRedefined(Boolean value) {
		this.redefined = value;
	}

	public String getRedefines() {
		return this.redefines;
	}

	public void setRedefines(String value) {
		this.redefines = value;
	}

	public BigInteger getSize() {
		return this.size;
	}
	
	public Properties getProperties() {
		return this.properties;
	}
	
	public void setSize(BigInteger value) {
		this.size = value;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String value) {
		this.type = value;
	}

	public BigInteger getDecimal() {
		return this.decimal;
	}

	public void setDecimal(BigInteger value) {
		this.decimal = value;
	}

	public Boolean isSigned() {
		return this.signed;
	}

	public void setSigned(Boolean value) {
		this.signed = value;
	}

	public Boolean isImpliedDecimal() {
		return this.impliedDecimal;
	}

	public void setImpliedDecimal(Boolean value) {
		this.impliedDecimal = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String value) {
		this.picture = value;
	}
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\git\RecordReader\RecordParser\dependencies\cobol2j\net\sf\cobol2j\FieldFormat.class
	Total time: 18 ms
	
	Decompiled with FernFlower.
*/