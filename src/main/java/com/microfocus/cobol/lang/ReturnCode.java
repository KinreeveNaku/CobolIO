package com.microfocus.cobol.lang;
import com.microfocus.cobol.CobolException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ReturnCode extends Number implements DataType {
	/**
	 * 
	 */
	private static final String BIT_64 = "64";
	private static final long serialVersionUID = 1L;
	private ByteBuffer buffer;
	private ByteOrder order = ByteOrder.nativeOrder();
	private byte[] syncBuffer;
	private static final boolean is64bitVM;

	public ReturnCode() {
		this.buffer = ByteBuffer.allocate(is64bitVM ? 8 : 4);
	}

	@Override
	public int intValue() {
		this.buffer.order(this.order);
		this.buffer.position(0);
		return is64bitVM ? (int) this.buffer.getLong() : this.buffer.getInt();
	}

	@Override
	public long longValue() {
		this.buffer.order(this.order);
		this.buffer.position(0);
		return is64bitVM ? this.buffer.getLong() : (long) this.buffer.getInt();
	}

	@Override
	public float floatValue() {
		return is64bitVM ? (float) this.longValue() : (float) this.intValue();
	}

	@Override
	public double doubleValue() {
		return is64bitVM ? (double) this.longValue() : (double) this.intValue();
	}

	@Override
	public byte[] getBytes() throws CobolException {
		this.syncBuffer = this.buffer.array();
		return this.syncBuffer;
	}

	@Override
	public void synchronizeData() throws CobolException {
	}

	public ByteOrder getOrder() {
		return this.order;
	}

	public void setOrder(ByteOrder var1) {
		this.order = var1;
	}

	static {
		String var0 = System.getProperty("sun.arch.data.model");
		if (var0 == null) {
			var0 = System.getProperty("com.ibm.vm.bitmode");
		}

		if (var0 != null && var0.equals(BIT_64)) {
			is64bitVM = true;
		} else {
			is64bitVM = false;
		}

	}
}