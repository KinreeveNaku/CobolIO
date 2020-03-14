package com.ibm.dataaccess.cobol;

public class DWORD extends Number implements DataTypeByValue {
	private static final long serialVersionUID = 2050273755881371615L;
	private UINT32 value;
	private Integer getParamValue;

	public DWORD(int var1) {
		this.value = new UINT32(var1);
	}

	public DWORD(UINT32 var1) {
		this.value = var1;
	}

	@Override
	public int intValue() {
		return this.value.intValue();
	}

	@Override
	public long longValue() {
		return this.value.longValue();
	}

	@Override
	public float floatValue() {
		return this.value.floatValue();
	}

	@Override
	public double doubleValue() {
		return this.value.doubleValue();
	}

	@Override
	public Object getParameter() {
		this.getParamValue = this.intValue();
		return this.getParamValue;
	}

	@Override
	public void synchronizeData() throws CobolException {
		this.value = new UINT32(this.getParamValue);
	}

	@Override
	public String toString() {
		return this.value.toString();
	}
}