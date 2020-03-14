package com.microfocus.cobol.lang.internal;
public class CobolBoolean extends CobolField {
	public CobolBoolean(CobolRecord var1, int var2, int var3, CobolDescriptor var4) {
		super(var1, var2, var3, var4);
	}

	public boolean getBoolean() throws CobolRecordException {
		return true;
	}

	public void setBoolean(boolean var1) throws CobolRecordException {
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public void setString(String var1) throws CobolRecordException {
	}
}