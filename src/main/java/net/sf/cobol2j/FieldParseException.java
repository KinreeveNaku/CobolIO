package net.sf.cobol2j;

public class FieldParseException extends Exception {
	private byte[] oryginalData;
	private FieldFormat fieldFormat;

	public FieldParseException(byte[] oD, FieldFormat fF, Throwable cause) {
		this("", oD, fF, cause);
	}

	public FieldParseException(String msg, byte[] oD, FieldFormat fF, Throwable cause) {
		super(msg, cause);
		this.oryginalData = oD;
		this.fieldFormat = fF;
	}

	public byte[] getOryginalData() {
		return this.oryginalData;
	}

	public FieldFormat getFieldFormat() {
		return this.fieldFormat;
	}
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\git\RecordReader\RecordParser\dependencies\cobol2j\net\sf\cobol2j\FieldParseException.class
	Total time: 6 ms
	
	Decompiled with FernFlower.
*/