package com.microfocus.cobol.lang.internal;
public abstract class CobolBytes {
	protected int offset;
	protected int length;
	protected CobolRecord record;

	public CobolBytes(CobolRecord var1, int var2, int var3) {
		this.record = var1;
		this.offset = var2;
		this.length = var3;
	}

	public byte[] getBytesValue() throws CobolRecordException {
		byte[] var1 = new byte[this.length];
		System.arraycopy(this.record.getBytes(), this.offset, var1, 0, this.length);
		return var1;
	}

	public void setBytes(byte[] var1) throws CobolRecordException {
		int var2 = 0;

		for (byte[] var3 = this.record.getBytes(); var2 < this.length; ++var2) {
			if (var2 < var1.length) {
				var3[this.offset + var2] = var1[var2];
			} else {
				var3[this.offset + var2] = 32;
			}
		}

	}

	protected CobolRecord getRecord() {
		return this.record;
	}
}