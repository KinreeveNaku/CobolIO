/**
 * 
 */
package com.ibm.dataaccess;

import java.io.OutputStream;

import org.dozer.loader.DozerBuilder;

/**
 * @author Andrew
 *
 */
public class RecordWriter {
	private CobolRecordDefinitionSet definitions;
	private DozerBuilder builder;

	/**
	 * 
	 */
	public RecordWriter(OutputStream os, CobolRecordDefinitionSet definitions) {
		this.definitions = definitions;
	}
}
