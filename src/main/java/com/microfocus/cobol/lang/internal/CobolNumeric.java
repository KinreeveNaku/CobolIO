package com.microfocus.cobol.lang.internal;
import com.microfocus.cobol.CobolException;
import java.math.BigDecimal;
import java.math.BigInteger;
@SuppressWarnings({ "squid:S109", "squid:S00107", "squid:S1166" })
public class CobolNumeric extends CobolField {
	public CobolNumeric(CobolRecord record, int var2, int var3, int var4, int var5, int var6, int var7, int var8,
			int var9, int var10) {
		super(record, var3, var4, var5, CobolDescriptor.makeNumericDescriptor(var2, var4, var6, var7, var8, var9, var10));
	}

	public CobolNumeric(CobolRecord record, int var2, int var3, int var4, int var6, int var7, int var8, int var9,
			int var10) {
		this(record, var2, var3, var4, 0, var6, var7, var8, var9, var10);
	}

	public CobolNumeric(CobolRecord var1, int var2, int var3, int var4, int var5, int var6, String var7) {
		super(var1, var3, var4, var5, CobolDescriptor.makeEditedDescriptor(var2, 1, var6, var7));
	}

	public CobolNumeric(CobolRecord var1, int var2, int var3, int var4, int var5, String var6) {
		this(var1, var2, var3, var4, 0, var5, var6);
	}

	public BigDecimal getBigDecimal() throws CobolRecordException {
		if (this.descriptor.isComp1()) {
			return this.record.getFloat(this.descriptor, this.offset);
		} else if (this.descriptor.isComp2()) {
			return this.record.getDouble(this.descriptor, this.offset);
		} else {
			return this.descriptor.isNumericEdited()
					? this.record.getNumericEdited(this.descriptor, this.offset)
					: this.record.getNumeric(this.descriptor, this.offset);
		}
	}

	public BigInteger getBigInteger() throws CobolRecordException {
		return this.getBigDecimal().toBigInteger();
	}

	public byte getByteValue() throws CobolRecordException {
		return this.getBigDecimal().byteValue();
	}

	public short getShortValue() throws CobolRecordException {
		return this.getBigDecimal().shortValue();
	}

	public int getIntValue() throws CobolRecordException {
		return this.getBigDecimal().intValue();
	}

	public long getLongValue() throws CobolRecordException {
		return this.getBigDecimal().longValue();
	}

	public float getFloatValue() throws CobolRecordException {
		return this.getBigDecimal().floatValue();
	}

	public double getDoubleValue() throws CobolRecordException {
		return this.getBigDecimal().doubleValue();
	}

	public void setBigDecimal(BigDecimal var1) throws CobolRecordException {
		if (this.descriptor.isComp1()) {
			this.record.setFloat(this.descriptor, var1, this.offset);
		} else if (this.descriptor.isComp2()) {
			this.record.setDouble(this.descriptor, var1, this.offset);
		} else if (this.descriptor.isNumericEdited()) {
			this.record.setNumericEdited(this.descriptor, var1, this.offset);
		} else {
			this.record.setNumeric(this.descriptor, var1, this.offset);
		}

	}

	public void setBigInteger(BigInteger var1) throws CobolRecordException {
		this.setBigDecimal(new BigDecimal(var1));
	}

	public void setByte(byte var1) throws CobolRecordException {
		this.setBigDecimal(BigDecimal.valueOf((long) var1));
	}

	public void setShort(short var1) throws CobolRecordException {
		this.setBigDecimal(BigDecimal.valueOf((long) var1));
	}

	public void setInt(int var1) throws CobolRecordException {
		this.setBigDecimal(BigDecimal.valueOf((long) var1));
	}

	public void setLong(long var1) throws CobolRecordException {
		this.setBigDecimal(BigDecimal.valueOf(var1));
	}

	public void setFloat(float var1) throws CobolRecordException {
		this.setBigDecimal(new BigDecimal(Float.toString(var1)));
	}

	public void setDouble(double var1) throws CobolRecordException {
		this.setBigDecimal(new BigDecimal(Double.toString(var1)));
	}

	@Override
	public void setString(String var1) throws CobolRecordException {
		this.setBigDecimal(new BigDecimal(var1));
	}

	@Override
	public String toString() {
		try {
			if (!this.descriptor.isComp1() && !this.descriptor.isComp2()) {
				return this.descriptor.isNumericEdited() ? (new String(this.getBytesValue())).trim()
						: this.record.getDisplay(this.descriptor, this.offset);
			} else {
				return this.getBigDecimal().toString();
			}
		} catch (CobolException var2) {
			return null;
		}
	}
}
