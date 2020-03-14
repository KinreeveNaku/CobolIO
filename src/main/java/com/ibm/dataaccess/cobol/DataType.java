package com.ibm.dataaccess.cobol;
public interface DataType {
	byte[] getBytes() throws CobolException;

	void synchronizeData() throws CobolException;
}