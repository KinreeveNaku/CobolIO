package com.microfocus.cobol.lang.internal;

import com.microfocus.cobol.RuntimeSystem;
import com.microfocus.cobol.lang.DataType;
import com.microfocus.cobol.lang.ParameterList;
import com.microfocus.cobol.lang.ReturnCode;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

@SuppressWarnings("unused")
public class CobolRecord implements DataType {
	private String formatter;
	private byte[] recordBytes;
	private int recordLength;
	private int recordNumber;
	private static final int FORMAT_GETNUMERICDISPLAY = 1;
	private static final int FORMAT_GETNUMERIC = 3;
	private static final int FORMAT_SETNUMERIC = 4;
	private static final int FORMAT_GETNUMERICEDITED = 5;
	private static final int FORMAT_SETNUMERICEDITED = 6;
	private static final int FORMAT_GETCOMP1 = 7;
	private static final int FORMAT_SETCOMP1 = 8;
	private static final int FORMAT_GETCOMP2 = 9;
	private static final int FORMAT_SETCOMP2 = 10;
	private static final int FORMAT_GETALPHANUMERICEDITED = 11;
	private static final int FORMAT_SETALPHANUMERICEDITED = 12;
	private static final int MAX_NUMBER_SIZE = 78;

	public CobolRecord(int var1, String var2, int var3) {
		this.formatter = var2;
		this.recordNumber = var3;
		this.recordBytes = new byte[var1];
		this.recordLength = var1;
	}

	public CobolRecord(int var1) {
		this(var1, (String) null, 0);
	}

	protected void setNumeric(CobolDescriptor var1, BigDecimal var2, int var3) throws CobolRecordException {
		this.setNumber(4, var1, var2, var3);
	}

	protected void setNumericEdited(CobolDescriptor var1, BigDecimal var2, int var3) throws CobolRecordException {
		this.setNumber(6, var1, var2, var3);
	}

	protected void setFloat(CobolDescriptor var1, BigDecimal var2, int var3) throws CobolRecordException {
		this.callFormatter(8, new Float(var2.floatValue()), 4, var1, var3);
	}

	protected void setDouble(CobolDescriptor var1, BigDecimal var2, int var3) throws CobolRecordException {
		this.callFormatter(10, new Double(var2.doubleValue()), 8, var1, var3);
	}

	protected void setAlphanumericEdited(CobolDescriptor var1, String var2, int var3) throws CobolRecordException {
		byte[] var4 = var2.getBytes();
		this.callFormatter(12, var4, var4.length, var1, var3);
	}

	protected BigDecimal getNumeric(CobolDescriptor var1, int var2) throws CobolRecordException {
		return new BigDecimal(this.getNumber(3, var1, var2));
	}

	protected BigDecimal getNumericEdited(CobolDescriptor var1, int var2) throws CobolRecordException {
		return new BigDecimal(this.getNumber(5, var1, var2));
	}

	protected BigDecimal getFloat(CobolDescriptor var1, int var2) throws CobolRecordException {
		Float var3 = new Float(0.0F);
		this.callFormatter(7, var3, 4, var1, var2);
		return new BigDecimal(var3.doubleValue());
	}

	protected BigDecimal getDouble(CobolDescriptor var1, int var2) throws CobolRecordException {
		Double var3 = new Double(0.0D);
		this.callFormatter(9, var3, 8, var1, var2);
		return new BigDecimal(var3);
	}

	protected String getDisplay(CobolDescriptor var1, int var2) throws CobolRecordException {
		return this.getNumber(1, var1, var2);
	}

	private String getNumber(int var1, CobolDescriptor var2, int var3) throws CobolRecordException {
		byte[] var4 = new byte[78];

		String var5;
		try {
			this.callFormatter(var1, var4, var4.length, var2, var3);
			var5 = new String(var4, "UTF-8");
		} catch (UnsupportedEncodingException var7) {
			throw new CobolRecordException("Unsupported encoding UTF-8", var7);
		}

		return var5.trim();
	}

	private void setNumber(int var1, CobolDescriptor var2, BigDecimal var3, int var4) throws CobolRecordException {
		byte[] var5 = new byte[78];
		String var6 = var3.toString();
		System.arraycopy(var6.getBytes(), 0, var5, 0, var6.length());

		for (int var7 = var6.length() + 1; var7 < 78; ++var7) {
			var5[var7] = 32;
		}

		this.callFormatter(var1, var5, var5.length, var2, var4);
	}

	private void callFormatter(int var1, Object var2, int var3, CobolDescriptor var4, int var5)
			throws CobolRecordException {
		Object var6 = null;
		ParameterList var7 = new ParameterList();
		if (this.formatter == null) {
			var7.add(new Integer(var1), 0);
			var7.add(new Integer(var4.getDescriptor().length), 0);
			var7.add(var4.getDescriptor(), 1);
			var7.add(this.recordBytes, 1);
			var7.add(new Integer(var5), 0);
			var7.add(new Integer(var3), 0);
			var7.add(var2, 1);

			try {
				RuntimeSystem.cobrcall((ReturnCode) var6, "CBLRDFMT", var7, 24);
			} catch (Exception var10) {
				throw new CobolRecordException("Runtime cobol call exception");
			}
		} else {
			var7.add(new Integer(var1), 0);
			var7.add(new Integer(var4.getId()), 0);
			var7.add(var2, 1);
			var7.add(this.recordBytes, 1);

			try {
				RuntimeSystem.cobrcall((ReturnCode) var6, this.formatter, var7);
			} catch (Exception var9) {
				throw new CobolRecordException("Runtime cobol call exception");
			}
		}

	}

	protected int getLength() {
		return this.recordLength;
	}

	@Override
	public byte[] getBytes() throws CobolRecordException {
		return this.recordBytes;
	}

	public void setBytes(byte[] var1) throws CobolRecordException {
		if (var1.length != this.recordLength) {
			throw new CobolRecordException("Record size mismatch");
		} else {
			System.arraycopy(var1, 0, this.recordBytes, 0, this.recordLength);
		}
	}

	@Override
	public void synchronizeData() throws CobolRecordException {
		//Does nothing internally
	}
}
