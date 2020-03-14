package com.microfocus.cobol.lang;
import com.microfocus.cobol.CobolException;

public interface DataTypeByValue {
	Object getParameter() throws CobolException;

	void synchronizeData() throws CobolException;
}