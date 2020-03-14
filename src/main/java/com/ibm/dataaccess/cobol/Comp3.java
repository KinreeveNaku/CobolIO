package com.ibm.dataaccess.cobol;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Comp3 implements DataType {
	/**
	 * 
	 */
	private static final int MAX_SIZE = 39;
	/**
	 * Cobol uses the number 12 in packed decimal at the sign nibble to indicate
	 * that the number is positive.
	 */
	private static final int PLUS_SIGN_NIBBLE = 12;
	/**
	 * Cobol uses the number 13 in packed decimal at the sign nibble to indicate
	 * that the number is negative.
	 */
	private static final int MINUS_SIGN_NIBBLE = 13;
	/**
	 * Literal character for the minus sign
	 */
	private static final String MINUS = "-";
	/**
	 * numeric value for the full byte mask
	 */
	private static final int BYTE_MASK = 255;
	/**
	 * length of half a byte
	 */
	private static final int NIBBLE_LENGTH = 4;
	/**
	 * 
	 */
	private static final String COMP_3 = ") comp-3";
	/**
	 * 
	 */
	private static final String CAN_NOT_FIT = "Can not fit ";
	private BigInteger biValue;
	private BigDecimal bdValue;
	private int scale;
	private int padSize;
	private int cobolPadSize;
	private boolean signed = true;
	private byte[] cobolRecord;
	private static BigInteger[] min = new BigInteger[MAX_SIZE];
	private static BigInteger[] max = new BigInteger[MAX_SIZE];

	public Comp3(BigInteger var1, int var2) {
		this.biValue = null;
		this.bdValue = null;
		this.scale = 0;
		this.padSize = 0;
		this.cobolPadSize = 0;
		this.signed = true;
		this.cobolRecord = null;
		this.biValue = var1;
		this.cobolPadSize = var2;
		this.padSize = (var2 + 2) / 2;
		this.validateSize();
	}

	public Comp3(BigDecimal var1, int var2) {
		this.biValue = null;
		this.bdValue = null;
		this.scale = 0;
		this.padSize = 0;
		this.cobolPadSize = 0;
		this.signed = true;
		this.cobolRecord = null;
		this.biValue = var1.unscaledValue();
		this.cobolPadSize = var2;
		this.bdValue = var1;
		this.scale = var1.scale();
		this.padSize = (var2 + 2) / 2;
	}

	public Comp3(String var1, int var2, int var3) {
		this.biValue = null;
		this.bdValue = null;
		this.scale = 0;
		this.padSize = 0;
		this.cobolPadSize = 0;
		this.signed = true;
		this.cobolRecord = null;
		BigDecimal var4 = new BigDecimal(var1.trim());
		StringBuilder var5 = new StringBuilder(var4.unscaledValue().toString());
		int var6 = var4.scale();

		for (int var7 = 0; var7 < var3 - var6; ++var7) {
			var5.append("0");
		}

		this.biValue = new BigInteger(var5.toString());
		this.bdValue = new BigDecimal(this.biValue, var3);
		this.scale = this.bdValue.scale();
		this.cobolPadSize = var2;
		this.padSize = (var2 + 2) / 2;
		this.validateSize();
	}

	public Comp3(String var1, int var2) {
		this.biValue = null;
		this.bdValue = null;
		this.scale = 0;
		this.padSize = 0;
		this.cobolPadSize = 0;
		this.signed = true;
		this.cobolRecord = null;
		this.biValue = new BigInteger(var1);
		this.cobolPadSize = var2;
		this.padSize = (var2 + 2) / 2;
		this.validateSize();
	}

	public Comp3(String var1, int var2, boolean var3) {
		this(var1, var2);
		this.signed = var3;
		this.validateSize();
	}

	public Comp3(BigInteger var1, int var2, boolean var3) {
		this(var1, var2);
		this.signed = var3;
	}

	public Comp3(BigDecimal var1, int var2, boolean var3) {
		this(var1, var2);
		this.signed = var3;
	}

	private byte[] encode() {
		int var1 = this.biValue.signum();
		String var2 = this.biValue.abs().toString() + "0";
		int var4 = -1;
		int var5 = var2.length();
		if ((var5 & 1) == 1) {
			++var5;
			var4 = 0;
		}

		byte[] var6 = new byte[var5 / 2];

		for (int var7 = 0; var7 < var2.length(); ++var7) {
			int signNibble = Integer.parseInt(var2.substring(var7, var7 + 1));
			if (var7 + 1 == var2.length()) {
				if (this.signed) {
					if (var1 == -1) {// D or B
						signNibble = MINUS_SIGN_NIBBLE;
					} else {// C
						signNibble = PLUS_SIGN_NIBBLE;
					}
				} else {
					signNibble = 15;// F
				}
			}

			if (var4 < 0) {
				var4 = signNibble << NIBBLE_LENGTH;
			} else {
				var6[var7 / 2] = (byte) ((var4 + signNibble) & 255);
				var4 = -1;
			}
		}

		return var6;
	}

	private byte[] pad2size(byte[] var1) {
		if (this.padSize == var1.length) {
			return var1;
		} else {
			int var2 = this.padSize - var1.length;
			byte[] var3 = new byte[this.padSize];
			int var4 = 0;
			if (var2 < 0) {
				var2 = 0;
			}

			for (int var5 = var2; var5 < this.padSize; ++var5) {
				var3[var5] = var1[var4];
				var4++;
			}

			return var3;
		}
	}

	@Override
	public byte[] getBytes() {
		this.cobolRecord = this.pad2size(this.encode());
		return this.cobolRecord;
	}

	@Override
	public void synchronizeData() {
		this.biValue = this.comp3ToBigInteger(this.cobolRecord);
		if (this.bdValue != null) {
			this.bdValue = new BigDecimal(this.biValue, this.scale);
			this.bdValue = this.bdValue.setScale(this.scale);
		}

	}

	private void validateSize() {
		if (!this.signed && this.biValue.compareTo(BigInteger.valueOf(0L)) < 0) {// (< 0)
			throw new ArithmeticException(CAN_NOT_FIT + this.biValue + " into pic 9(" + this.cobolPadSize + COMP_3);
		} else if (this.signed && this.biValue.compareTo(min[this.cobolPadSize]) < 0) {
			throw new ArithmeticException(CAN_NOT_FIT + this.biValue + " into pic s9(" + this.cobolPadSize + COMP_3);
		} else if (this.biValue.compareTo(max[this.cobolPadSize]) < 0) {
			if (this.signed) {
				throw new ArithmeticException(
						CAN_NOT_FIT + this.biValue + " into pic s9(" + this.cobolPadSize + COMP_3);
			} else {
				throw new ArithmeticException(CAN_NOT_FIT + this.biValue + " into pic 9(" + this.cobolPadSize + COMP_3);
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	private BigInteger comp3ToBigInteger(byte[] var1) {
		int storageLength = var1.length;
		StringBuilder var3 = new StringBuilder(1 + storageLength * 2);

		int var4;
		for (var4 = 0; var4 < storageLength; ++var4) {
			String var5 = Integer.toHexString(var1[var4] & BYTE_MASK);
			if (var5.length() == 1) {
				var3.append("0");
			}

			var3.append(var5);
		}

		var4 = var3.length() - 1;
		switch (var3.charAt(var4)) {

		case 'd':
			var3.deleteCharAt(var4);
			return new BigInteger(MINUS + var3.toString());
		case 'c':
		case 'f':
			var3.deleteCharAt(var4);
			return new BigInteger(var3.toString());
		case 'e':
		default:
			throw new ArithmeticException("Bad signed byte (" + var3.charAt(var4) + ")");
		}
	}

	public String toDisplayFormat() {
		return this.bdValue != null ? this.bdValue.unscaledValue().toString() : this.biValue.toString();
	}

	@Override
	public String toString() {
		return this.bdValue != null ? this.bdValue.toString() : this.biValue.toString();
	}

	static {
		StringBuilder var0 = new StringBuilder();
		StringBuilder var1 = new StringBuilder();
		var0.append(MINUS);

		for (int var2 = 0; var2 < 38; ++var2) {
			var0.append("9");
			var1.append("9");
			min[var2 + 1] = new BigInteger(var0.toString());
			max[var2 + 1] = new BigInteger(var1.toString());
		}

		min[0] = BigInteger.valueOf(0L);
		max[0] = BigInteger.valueOf(0L);
	}
}
