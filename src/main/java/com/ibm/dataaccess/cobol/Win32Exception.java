package com.ibm.dataaccess.cobol;

public class Win32Exception extends CobolRuntimeException {
	private static final long serialVersionUID = -2481251294356503031L;
	private int errorCode;
	private long appReturnCode;

	public Win32Exception() {
	}

	public Win32Exception(String var1) {
		super(var1);
	}

	public Win32Exception(String var1, int var2) {
		super(var1);
		this.errorCode = var2;
	}

	public Win32Exception(String var1, int var2, long var3) {
		super(var1);
		this.errorCode = var2;
		this.appReturnCode = var3;
	}

	@Override
	public int getErrorCode() {
		return this.errorCode;
	}

	public long getAppReturnCode() {
		return this.appReturnCode;
	}
}