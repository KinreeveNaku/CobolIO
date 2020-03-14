/**
 * 
 */
package com.ibm.dataaccess;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXB;

import net.sf.cobol2j.FileFormat;
import net.sf.cobol2j.RecordFormat;

/**
 * @author Andrew
 *
 */
public class CobolRecordDefinitionSet {
	private FileFormat format;
	private Map<String, RecordFormat> records;
	public CobolRecordDefinitionSet(File cb2jSchema) {
		this.format = JAXB.unmarshal(cb2jSchema, FileFormat.class);
		this.records = new HashMap<>();
		for(RecordFormat record : format.getRecordFormat()) {
			records.put(record.getCobolRecordName(), record);
		}
	}
	
	public FileFormat getFieldFormat() {
		return this.format;
	}
	
	public RecordFormat getRecordFormat(String name) {
		return this.records.get(name);
	}
}
