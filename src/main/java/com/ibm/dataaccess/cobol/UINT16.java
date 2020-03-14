package com.ibm.dataaccess.cobol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class UINT16 extends Number implements Comparable<UINT16> {
	/**
	 * 
	 */
	private static final long FULL_SHORT_MASK = 65535L;
	private static final long serialVersionUID = -533733742593477128L;
	private static final int SIZE = 2;
	private int value;

	public UINT16(byte var1) {
		this.value = var1;
	}

	public UINT16(short var1) {
		this.value = var1;
	}

	public UINT16(int var1) {
		this.value = var1 & '￿';
	}

	public UINT16(long var1) {
		this.value = (int) (var1 & FULL_SHORT_MASK);
	}

	public UINT16() {
		this.value = 0;
	}

	@Override
	public double doubleValue() {
		return (double) this.value;
	}

	@Override
	public float floatValue() {
		return (float) this.value;
	}

	@Override
	public short shortValue() {
		return (short) (this.value & '￿');
	}

	@Override
	public int intValue() {
		return this.value & '￿';
	}

	@Override
	public long longValue() {
		return (long) this.value & FULL_SHORT_MASK;
	}

	public byte[] getBytes() {
		ByteBuffer var1 = ByteBuffer.allocate(SIZE);
		var1.order(ByteOrder.LITTLE_ENDIAN);
		var1.position(0);
		var1.putShort(this.shortValue());
		return var1.array();
	}

	public static UINT16 fromBytes(byte[] var0) {
		ByteBuffer var1 = ByteBuffer.allocate(SIZE);
		var1.order(ByteOrder.LITTLE_ENDIAN);
		var1.put(var0);
		var1.position(0);
		return new UINT16(var1.getShort());
	}

	@Override
	public int compareTo(UINT16 var1) {
		int var2 = var1.intValue();
		if (this.value > var2) {
			return 1;
		} else {
			return this.value < var2 ? -1 : 0;
		}
	}

	@Override
	public boolean equals(Object var1) {
		return var1 instanceof UINT16 ? Arrays.equals(this.getBytes(), ((UINT16) var1).getBytes()) : Boolean.FALSE;
	}

	@Override
	public int hashCode() {
		return this.value;
	}

	@Override
	public String toString() {
		return Integer.toString(this.value);
	}
}