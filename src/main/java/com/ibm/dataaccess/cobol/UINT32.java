package com.ibm.dataaccess.cobol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class UINT32 extends Number implements Comparable<UINT32> {
	private static final long UNSIGNED_INT_MASK = 4294967295L;
	private static final long serialVersionUID = -7540699174208517101L;
	private static final int SIZE = 4;
	private long value;

	public UINT32(byte var1) {
		this.value = (long) var1;
	}

	public UINT32(short var1) {
		this.value = (long) var1;
	}

	public UINT32(int var1) {
		this.value = (long) var1;
	}

	public UINT32(long var1) {
		this.value = var1 & UNSIGNED_INT_MASK;
	}

	public UINT32() {
		this.value = 0L;
	}

	public byte[] getBytes() {
		ByteBuffer var1 = ByteBuffer.allocate(SIZE);
		var1.order(ByteOrder.LITTLE_ENDIAN);
		var1.position(0);
		var1.putInt(this.intValue());
		return var1.array();
	}

	public static UINT32 fromBytes(byte[] var0) {
		ByteBuffer var1 = ByteBuffer.allocate(SIZE);
		var1.order(ByteOrder.LITTLE_ENDIAN);
		var1.put(var0);
		var1.position(0);
		return new UINT32(var1.getInt());
	}

	@Override
	public boolean equals(Object var1) {
		if (!(var1 instanceof Number)) {
			return false;
		} else {
			return this.value == ((UINT32) var1).value;
		}
	}

	@Override
	public int compareTo(UINT32 var1) {
		long var2 = var1.longValue();
		if (this.value > var2) {
			return 1;
		} else {
			return this.value < var2 ? -1 : 0;
		}
	}

	@Override
	public int intValue() {
		return (int) (this.value & UNSIGNED_INT_MASK);
	}

	@Override
	public long longValue() {
		return this.value & UNSIGNED_INT_MASK;
	}

	@Override
	public float floatValue() {
		return (float) this.value;
	}

	@Override
	public double doubleValue() {
		return (double) this.value;
	}

	@Override
	public String toString() {
		return Long.toString(this.value & UNSIGNED_INT_MASK);
	}

	@Override
	public int hashCode() {
		return (int) (this.value ^ (this.value >>> 32));
	}
}