package com.ibm.dataaccess.cobol;
import com.ibm.dataaccess.cobol.CobolException;
import java.math.BigInteger;

public class CobolBigInteger implements DataType {
	private BigInteger value = null;
	private Comp3 comp3 = null;
	private byte[] tempBuffer = null;

	public CobolBigInteger() {
		this.value = BigInteger.valueOf(0L);
	}

	public CobolBigInteger(BigInteger var1) {
		this.value = var1;
	}

	public CobolBigInteger(String var1) {
		this.value = new BigInteger(var1);
	}

	@Override
	public byte[] getBytes() throws CobolException {
		this.comp3 = new Comp3(this.value.toString(), 38, 0);
		this.tempBuffer = this.comp3.getBytes();
		return this.tempBuffer;
	}

	public BigInteger getValue() {
		return this.value;
	}

	public void setValue(BigInteger var1) {
		this.value = var1;
	}

	@Override
	public void synchronizeData() throws CobolException {
		this.comp3.synchronizeData();
		this.value = new BigInteger(this.comp3.toString());
	}

	@Override
	public String toString() {
		return this.value.toString();
	}
}