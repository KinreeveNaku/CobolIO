package com.microfocus.cobol;
public class CobolRuntimeException extends CobolException {
	private static final long serialVersionUID = 3491002099525078465L;
	private int errorCode = 0;

	public CobolRuntimeException() {
	}

	public CobolRuntimeException(String var1) {
		super(var1);
	}

	public CobolRuntimeException(String var1, Throwable var2) {
		super(var1, var2);
	}

	public CobolRuntimeException(Throwable var1) {
		super(var1);
	}

	public CobolRuntimeException(String var1, int var2) {
		super(var1);
		this.errorCode = var2;
	}

	public int getErrorCode() {
		return this.errorCode;
	}
}