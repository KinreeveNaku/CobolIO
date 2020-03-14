package net.sf.cobol2j;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FieldsGroup")
public class FieldsGroup extends FieldsList {
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
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\git\RecordReader\RecordParser\dependencies\cobol2j\net\sf\cobol2j\FieldsGroup.class
	Total time: 9 ms
	
	Decompiled with FernFlower.
*/