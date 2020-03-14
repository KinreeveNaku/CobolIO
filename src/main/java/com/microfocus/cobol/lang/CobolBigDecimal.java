package com.microfocus.cobol.lang;
import com.microfocus.cobol.CobolException;
import java.math.BigDecimal;

public class CobolBigDecimal implements DataType {
	private BigDecimal value = null;
	private Comp3 comp3 = null;
	private byte[] tempBuffer = null;

	public CobolBigDecimal() {
		this.value = BigDecimal.valueOf(0L);
	}

	public CobolBigDecimal(BigDecimal var1) {
		this.value = var1;
	}

	public CobolBigDecimal(String var1) {
		this.value = new BigDecimal(var1);
	}

	@Override
	public byte[] getBytes() throws CobolException {
		this.comp3 = new Comp3(this.value.toString(), 38, 19);
		this.tempBuffer = this.comp3.getBytes();
		return this.tempBuffer;
	}

	public BigDecimal getValue() {
		return this.value;
	}

	public void setValue(BigDecimal var1) {
		this.value = var1;
	}

	@Override
	public void synchronizeData() throws CobolException {
		this.comp3.synchronizeData();
		this.value = new BigDecimal(this.comp3.toString());
	}

	@Override
	public String toString() {
		return this.value.toString();
	}
}