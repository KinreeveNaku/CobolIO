/**
 * 
 */
package com.ibm.dataaccess;

import java.io.InputStream;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.dozer.loader.DozerBuilder;

/**
 * @author Andrew
 *
 */
public class RecordReader {
	private CobolRecordDefinitionSet definitions;
	private Mapper dozer = DozerBeanMapperSingletonWrapper.getInstance();
	private DozerBuilder builder;
	
	/**
	 * 
	 */
	public RecordReader(InputStream is, CobolRecordDefinitionSet definitions) {
		this.definitions = definitions;
	}
}
