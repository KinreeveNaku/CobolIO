package net.sf.cobol2j;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecordFormat")
public class RecordFormat extends FieldsList {
	@XmlAttribute(name = "distinguishFieldValue")
	protected String distinguishFieldValue;
	@XmlAttribute(name = "cobolRecordName")
	protected String cobolRecordName;
	@XmlAttribute(name = "compilerProperties")
	protected CompilerProperties compilerProperties;

	public String getDistinguishFieldValue() {
		return this.distinguishFieldValue;
	}

	public void setDistinguishFieldValue(String value) {
		this.distinguishFieldValue = value;
	}

	public String getCobolRecordName() {
		return this.cobolRecordName;
	}

	public void setCobolRecordName(String value) {
		this.cobolRecordName = value;
	}
	
	public CompilerProperties getCompilerProperties() {
		return this.compilerProperties;
	}
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\git\RecordReader\RecordParser\dependencies\cobol2j\net\sf\cobol2j\RecordFormat.class
	Total time: 5 ms
	
	Decompiled with FernFlower.
*/