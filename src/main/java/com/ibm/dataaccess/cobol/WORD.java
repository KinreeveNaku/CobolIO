package com.ibm.dataaccess.cobol;

public class WORD extends Number implements DataType {
	private static final long serialVersionUID = -549345644536645241L;
	private UINT16 value = new UINT16();
	private byte[] bytes;

	@Override
	public byte[] getBytes() throws CobolException {
		this.bytes = this.value.getBytes();
		return this.bytes;
	}

	@Override
	public void synchronizeData() throws CobolException {
		this.value = UINT16.fromBytes(this.bytes);
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
}