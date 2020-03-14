package com.microfocus.cobol.lang.internal;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

public class CobolGroup extends CobolAlphanumeric {
	/**
	 * 
	 */
	private static final String COBOL_FIELD_NAME = "Cobol field name ";
	/**
	 * 
	 */
	private static final String NOT_MUMERIC = " not mumeric";
	/**
	 * 
	 */
	private static final String NOT_FOUND = " not found";

	public CobolGroup(CobolRecord var1, int var2, int var3, int var4) {
		super(var1, var2, 0, var1.getLength(), var3, var4);
	}

	public CobolGroup(CobolRecord var1, int var2, int var3) {
		this(var1, var2, 0, var3);
	}

	public CobolGroup(CobolRecord var1, int var2, int var3, int var4, int var5, int var6) {
		super(var1, var2, var3, var4, var5, var6);
	}

	public CobolGroup(CobolRecord var1, int var2, int var3, int var4, int var5) {
		this(var1, var2, var3, var4, 0, var5);
	}

	public CobolField getField(String var1) throws CobolRecordException {
		String var2 = this.convertCobolFieldNameToJavaMemberName(var1);
		CobolField var3 = this.getFieldHelper(this, var2);
		if (var3 == null) {
			this.throwFieldNameNotFoundException(var1);
			return null;
		} else {
			return var3;
		}
	}

	public byte[] getBytesValue(String var1) throws CobolRecordException {
		CobolField var2 = this.getField(var1);
		return var2 == null ? null : var2.getBytesValue();
	}

	public String toString(String var1) throws CobolRecordException {
		CobolField var2 = this.getField(var1);
		return var2 == null ? null : var2.toString();
	}

	public void setBytes(String var1, byte[] var2) throws CobolRecordException {
		CobolField var3 = this.getField(var1);
		if (var3 == null) {
			this.throwFieldNameNotFoundException(var1);
		} else {
			var3.setBytes(var2);
		}

	}

	public void setString(String var1, String var2) throws CobolRecordException {
		CobolField var3 = this.getField(var1);
		if (var3 == null) {
			this.throwFieldNameNotFoundException(var1);
		} else {
			var3.setString(var2);
		}

	}

	public byte getByteValue(String var1) throws CobolRecordException {
		CobolField var2 = this.getField(var1);
		if (var2 == null) {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_FOUND);
		} else if (var2 instanceof CobolNumeric) {
			return ((CobolNumeric) var2).getByteValue();
		} else {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_MUMERIC);
		}
	}

	public short getShortValue(String var1) throws CobolRecordException {
		CobolField var2 = this.getField(var1);
		if (var2 == null) {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_FOUND);
		} else if (var2 instanceof CobolNumeric) {
			return ((CobolNumeric) var2).getShortValue();
		} else {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_MUMERIC);
		}
	}

	public int getIntValue(String var1) throws CobolRecordException {
		CobolField var2 = this.getField(var1);
		if (var2 == null) {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_FOUND);
		} else if (var2 instanceof CobolNumeric) {
			return ((CobolNumeric) var2).getIntValue();
		} else {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_MUMERIC);
		}
	}

	public long getLongValue(String var1) throws CobolRecordException {
		CobolField var2 = this.getField(var1);
		if (var2 == null) {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_FOUND);
		} else if (var2 instanceof CobolNumeric) {
			return ((CobolNumeric) var2).getLongValue();
		} else {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_MUMERIC);
		}
	}

	public float getFloatValue(String var1) throws CobolRecordException {
		CobolField var2 = this.getField(var1);
		if (var2 == null) {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_FOUND);
		} else if (var2 instanceof CobolNumeric) {
			return ((CobolNumeric) var2).getFloatValue();
		} else {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_MUMERIC);
		}
	}

	public double getDoubleValue(String var1) throws CobolRecordException {
		CobolField var2 = this.getField(var1);
		if (var2 == null) {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_FOUND);
		} else if (var2 instanceof CobolNumeric) {
			return ((CobolNumeric) var2).getDoubleValue();
		} else {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_MUMERIC);
		}
	}

	public BigDecimal getBigDecimal(String var1) throws CobolRecordException {
		CobolField var2 = this.getField(var1);
		if (var2 == null) {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_FOUND);
		} else if (var2 instanceof CobolNumeric) {
			return ((CobolNumeric) var2).getBigDecimal();
		} else {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_MUMERIC);
		}
	}

	public BigInteger getBigInteger(String var1) throws CobolRecordException {
		CobolField var2 = this.getField(var1);
		if (var2 == null) {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_FOUND);
		} else if (var2 instanceof CobolNumeric) {
			return ((CobolNumeric) var2).getBigInteger();
		} else {
			throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_MUMERIC);
		}
	}

	public void setByte(String var1, byte var2) throws CobolRecordException {
		CobolField var3 = this.getField(var1);
		if (var3 != null && var3 instanceof CobolNumeric) {
			((CobolNumeric) var3).setByte(var2);
		} else {
			this.throwFieldNameNotFoundException(var1);
		}

	}

	public void setShort(String var1, short var2) throws CobolRecordException {
		CobolField var3 = this.getField(var1);
		if (var3 != null && var3 instanceof CobolNumeric) {
			((CobolNumeric) var3).setShort(var2);
		} else {
			this.throwFieldNameNotFoundException(var1);
		}

	}

	public void setInt(String var1, int var2) throws CobolRecordException {
		CobolField var3 = this.getField(var1);
		if (var3 != null && var3 instanceof CobolNumeric) {
			((CobolNumeric) var3).setInt(var2);
		} else {
			this.throwFieldNameNotFoundException(var1);
		}

	}

	public void setLong(String var1, long var2) throws CobolRecordException {
		CobolField var4 = this.getField(var1);
		if (var4 != null && var4 instanceof CobolNumeric) {
			((CobolNumeric) var4).setLong(var2);
		} else {
			this.throwFieldNameNotFoundException(var1);
		}

	}

	public void setFloat(String var1, float var2) throws CobolRecordException {
		CobolField var3 = this.getField(var1);
		if (var3 != null && var3 instanceof CobolNumeric) {
			((CobolNumeric) var3).setFloat(var2);
		} else {
			this.throwFieldNameNotFoundException(var1);
		}

	}

	public void setDouble(String var1, double var2) throws CobolRecordException {
		CobolField var4 = this.getField(var1);
		if (var4 != null && var4 instanceof CobolNumeric) {
			((CobolNumeric) var4).setDouble(var2);
		} else {
			this.throwFieldNameNotFoundException(var1);
		}

	}

	public void setBigDecimal(String var1, BigDecimal var2) throws CobolRecordException {
		CobolField var3 = this.getField(var1);
		if (var3 != null && var3 instanceof CobolNumeric) {
			((CobolNumeric) var3).setBigDecimal(var2);
		} else {
			this.throwFieldNameNotFoundException(var1);
		}

	}

	public void setBigInteger(String var1, BigInteger var2) throws CobolRecordException {
		CobolField var3 = this.getField(var1);
		if (var3 != null && var3 instanceof CobolNumeric) {
			((CobolNumeric) var3).setBigInteger(var2);
		} else {
			this.throwFieldNameNotFoundException(var1);
		}

	}

	public boolean getBoolean(String var1) throws CobolRecordException {
		CobolField var2 = this.getField(var1);
		if (var2 == null) {
			return false;
		} else {
			return var2 instanceof CobolBoolean ? ((CobolBoolean) var2).getBoolean() : false;
		}
	}

	public void setBoolean(String var1, boolean var2) throws CobolRecordException {
		CobolField var3 = this.getField(var1);
		if (var3 != null && var3 instanceof CobolBoolean) {
			((CobolBoolean) var3).setBoolean(var2);
		} else {
			this.throwFieldNameNotFoundException(var1);
		}

	}

	private String convertCobolFieldNameToJavaMemberName(String var1) {
		char[] var2 = var1.toCharArray();
		int var3 = var2.length;
		StringBuffer var5 = new StringBuffer();

		for (int var6 = 0; var6 < var3; ++var6) {
			char var4 = var2[var6];
			if (var4 == '-') {
				var5.append('_');
			} else if (var4 >= 'A' && var4 <= 'Z') {
				var5.append(Character.toLowerCase(var4));
			} else if (var6 == 0 && var4 >= '0' && var4 <= '9') {
				var5.append('_');
				var5.append(var4);
			} else {
				var5.append(var4);
			}
		}

		return var5.toString();
	}

	private CobolField getFieldHelper(Object var1, String var2) throws CobolRecordException {
		Field[] var3 = var1.getClass().getFields();
		int var4 = var3.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			Class<?> var6 = var3[var5].getType();
			if (this.isImplementingCobolFieldInterface(var6)) {
				CobolField var7 = null;

				try {
					var7 = (CobolField) var3[var5].get(var1);
				} catch (Exception var9) {
					this.throwFieldNameNotFoundException(var2);
				}

				if (var3[var5].getName().equals(var2)) {
					return var7;
				}

				CobolField var8 = this.getFieldHelper(var7, var2);
				if (var8 != null) {
					return var8;
				}
			}
		}

		return null;
	}

	private boolean isImplementingCobolFieldInterface(Class<?> var1) {
		while (var1 != null) {
			if (var1 == CobolField.class) {
				return true;
			}

			var1 = var1.getSuperclass();
		}

		return false;
	}

	private void throwFieldNameNotFoundException(String var1) throws CobolRecordException {
		throw new CobolRecordException(COBOL_FIELD_NAME + var1 + NOT_FOUND);
	}
}
