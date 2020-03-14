package com.ibm.dataaccess.cobol;

public class LPDWORD extends Number implements DataType {
	private static final long serialVersionUID = -7894351720090050610L;
	private UINT32 value;
	private byte[] bytes;

	public LPDWORD(int var1) {
		this.value = new UINT32(var1);
	}

	public LPDWORD(UINT32 var1) {
		this.value = var1;
	}

	@Override
	public byte[] getBytes() throws CobolException {
		this.bytes = this.value.getBytes();
		return this.bytes;
	}

	@Override
	public void synchronizeData() throws CobolException {
		this.value = UINT32.fromBytes(this.bytes);
		this.bytes = null;
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
	public String toString() {
		return this.value.toString();
	}

	public Object getParameter() {
		return this.intValue();
	}
}