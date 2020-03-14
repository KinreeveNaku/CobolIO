package net.sf.cobol2j;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
	public RecordFormat createRecordFormat() {
		return new RecordFormat();
	}

	public FileFormat createFileFormat() {
		return new FileFormat();
	}

	public FieldsList createFieldsList() {
		return new FieldsList();
	}

	public FieldFormat createFieldFormat() {
		return new FieldFormat();
	}

	public FieldsGroup createFieldsGroup() {
		return new FieldsGroup();
	}
	
	public CompilerProperties createCompilerProperties() {
		return new CompilerProperties();
	}
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\git\RecordReader\RecordParser\dependencies\cobol2j\net\sf\cobol2j\ObjectFactory.class
	Total time: 6 ms
	
	Decompiled with FernFlower.
*/