package net.sf.cobol2j;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"recordFormat"})
@XmlRootElement(name = "FileFormat")
public class FileFormat {
	@XmlElement(name = "RecordFormat", required = true)
	protected List<RecordFormat> recordFormat;
	@XmlAttribute(name = "ConversionTable")
	protected String conversionTable;
	@XmlAttribute(name = "distinguishFieldSize")
	protected BigInteger distinguishFieldSize;
	@XmlAttribute(name = "newLineSize")
	protected BigInteger newLineSize;
	@XmlAttribute(name = "dataFileImplementation")
	protected String dataFileImplementation;

	public List<RecordFormat> getRecordFormat() {
		if (this.recordFormat == null) {
			this.recordFormat = new ArrayList<>();
		}

		return this.recordFormat;
	}

	public String getConversionTable() {
		return this.conversionTable;
	}

	public void setConversionTable(String value) {
		this.conversionTable = value;
	}

	public BigInteger getDistinguishFieldSize() {
		return this.distinguishFieldSize;
	}

	public void setDistinguishFieldSize(BigInteger value) {
		this.distinguishFieldSize = value;
	}

	public BigInteger getNewLineSize() {
		return this.newLineSize;
	}

	public void setNewLineSize(BigInteger value) {
		this.newLineSize = value;
	}

	public String getDataFileImplementation() {
		return this.dataFileImplementation;
	}

	public void setDataFileImplementation(String value) {
		this.dataFileImplementation = value;
	}
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\git\RecordReader\RecordParser\dependencies\cobol2j\net\sf\cobol2j\FileFormat.class
	Total time: 9 ms
	
	Decompiled with FernFlower.
*/
