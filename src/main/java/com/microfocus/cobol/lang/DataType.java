package com.microfocus.cobol.lang;

import com.microfocus.cobol.CobolException;

public interface DataType {
	byte[] getBytes() throws CobolException;

	void synchronizeData() throws CobolException;
}

/*
	DECOMPILATION REPORT

	Decompiled from: C:\Users\Joseph\AppData\Local\Temp\7zEC40B3526\DataType.class
	Total time: 3 ms
	
	Decompiled with FernFlower.
*/