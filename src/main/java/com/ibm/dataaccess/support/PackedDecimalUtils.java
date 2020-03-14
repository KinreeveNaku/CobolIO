/**
 * 
 */
package com.ibm.dataaccess.support;

import java.math.BigInteger;

/**
 * @author Andrew
 *
 */
public class PackedDecimalUtils {
	private static final byte BYTE_MASK = (byte) 255;
	private static final String MINUS = "-";
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
	private static final int NIBBLE_LENGTH = 4;

	public static final BigInteger comp3ToBigInteger(byte[] var1) {
		int var2 = var1.length;
		StringBuilder var3 = new StringBuilder(1 + var2 * 2);

		int var4;
		for (var4 = 0; var4 < var2; ++var4) {
			String var5 = Integer.toHexString(var1[var4] & BYTE_MASK);
			if (var5.length() == 1) {
				var3.append("0");
			}

			var3.append(var5);
		}

		var4 = var3.length() - 1;
		switch (var3.charAt(var4)) {
		case 'c':
			var3.deleteCharAt(var4);
			return new BigInteger(var3.toString());
		case 'd':
			var3.deleteCharAt(var4);
			return new BigInteger(MINUS + var3.toString());
		case 'e':
		default:
			throw new ArithmeticException("Bad signed byte (" + var3.charAt(var4) + ")");
		case 'f':
			var3.deleteCharAt(var4);
			return new BigInteger(var3.toString());
		}
	}

	public static final byte[] encode(BigInteger biValue, boolean signed) {
		int var1 = biValue.signum();
		String var2 = biValue.abs().toString() + "0";
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
				if (signed) {
					if (var1 == -1) {
						signNibble = MINUS_SIGN_NIBBLE;
					} else {
						signNibble = PLUS_SIGN_NIBBLE;
					}
				} else {
					signNibble = 15;
				}
			}

			if (var4 < 0) {
				var4 = signNibble << NIBBLE_LENGTH;
			} else {
				var6[var7 / 2] = (byte) ((var4 + signNibble) & BYTE_MASK);
				var4 = -1;
			}
		}

		return var6;
	}
}
