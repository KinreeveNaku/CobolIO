package net.sf.cobol2j;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FieldsList", propOrder = {"fieldFormatOrFieldsGroup"})
@XmlSeeAlso({RecordFormat.class, FieldsGroup.class})
public class FieldsList {
	@XmlElements({@XmlElement(name = "FieldFormat", type = FieldFormat.class),
			@XmlElement(name = "FieldsGroup", type = FieldsGroup.class)})
	protected List<Object> fieldFormatOrFieldsGroup;

	public List<Object> getFieldFormatOrFieldsGroup() {
		if (this.fieldFormatOrFieldsGroup == null) {
			this.fieldFormatOrFieldsGroup = new ArrayList<>();
		}

		return this.fieldFormatOrFieldsGroup;
	}
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\git\RecordReader\RecordParser\dependencies\cobol2j\net\sf\cobol2j\FieldsList.class
	Total time: 8 ms
	
	Decompiled with FernFlower.
*/
