package com.ibm.dataaccess.cobol;
import com.ibm.dataaccess.cobol.CobolException;

public interface DataTypeByValue {
	Object getParameter() throws CobolException;

	void synchronizeData() throws CobolException;
}