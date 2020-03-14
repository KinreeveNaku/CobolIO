/**
 * 
 */
package com.ibm.dataaccess.support;

import java.util.Arrays;

/**
 * @author Andrew
 *
 */
public class FPUtils {
	private static final int[] BIT32_BIT_MASKS = { 1073741824, 536870912, 268435456, 134217728, 67108864, 33554432,
			16777216, 8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, 32768, 16384, 8192, 4096, 2048,
			1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1 };
	private static final long[] BIT64_BIT_MASKS = { 4611686018427387904L, 2305843009213693952L, 1152921504606846976L,
			576460752303423488L, 288230376151711744L, 144115188075855872L, 72057594037927936L, 36028797018963968L,
			18014398509481984L, 9007199254740992L, 4503599627370496L, 2251799813685248L, 1125899906842624L,
			562949953421312L, 281474976710656L, 140737488355328L, 70368744177664L, 35184372088832L, 17592186044416L,
			8796093022208L, 4398046511104L, 2199023255552L, 1099511627776L, 549755813888L, 274877906944L, 137438953472L,
			68719476736L, 34359738368L, 17179869184L, 8589934592L, 4294967296L, 2147483648L, 1073741824L, 536870912L,
			268435456L, 134217728L, 67108864L, 33554432L, 16777216L, 8388608L, 4194304L, 2097152L, 1048576L, 524288L,
			262144L, 131072L, 65536L, 32768L, 16384L, 8192L, 4096L, 2048L, 1024L, 512L, 256L, 128L, 64L, 32L, 16L, 8L,
			4L, 2L, 1L };
	public static final int BIT32_SIGN_MASK = 0x80000000;
	public static final int IEEE32_EXP_MASK = 0x7F800000;
	public static final int IEEE32_EXP_LEN = 8;
	public static final int IEEE32_MAN_MASK = 0x007FFFFF;
	public static final int IEEE32_MAN_LEN = 23;
	public static final int IEEE32_BIAS = 128;

	public static final int HFP32_EXP_MASK = 0x7F000000;
	public static final int HFP32_EXP_LEN = 7;
	public static final int HFP32_MAN_MASK = 0x00FFFFFF;
	public static final int HFP32_MAN_LEN = 24;

	public static final long BIT64_SIGN_MASK = 0x8000000000000000L;

	public static final long IEEE64_EXP_MASK = 0x7FF0000000000000L;
	public static final int IEEE64_EXP_LEN = 11;
	public static final long IEEE64_MAN_MASK = 0x000FFFFFFFFFFFFFL;
	public static final int IEEE64_MAN_LEN = 52;
	public static final int IEEE64_BIAS = 512;

	public static final long HFP64_EXP_MASK = 0x7F00000000000000L;
	public static final int HFP64_EXP_LEN = 7;
	public static final long HFP64_MAN_MASK = 0x00FFFFFFFFFFFFFFL;
	public static final int HFP64_MAN_LEN = 56;
	public static final int HFP_BIAS = 64;

	private static final int FIFTH_BIT = 16;
	private static final int FOURTH_BIT = 8;
	private static final int THIRD_BIT = 4;

	public static final long getStandardExponentBias(int bits) {
		return (long) StrictMath.pow(2, (bits - 1));
	}

	public static final int getCustom32BitExponentMask(int expLength, int offset) {
		if (expLength + offset < 32 || (expLength > 0 && offset > 0)) {
			return sum(Arrays.copyOfRange(BIT32_BIT_MASKS, offset, offset + expLength));
		} else {
			throw new ArithmeticException(
					"The exponent length and offset, must together, be equal to or within the range of 1 and 31.");
		}
	}

	public static final int getCustom32BitExponentMask(int expLength) {
		if (expLength < 30 || expLength > 0) {
			return sum(Arrays.copyOfRange(BIT32_BIT_MASKS, 1, 1 + expLength));
		} else {
			throw new ArithmeticException("The exponent length must be equal to or within the range of 1 and 30.");
		}

	}

	public static final long getCustom64BitExponentMask(int expLength) {
		if (expLength > 61 || expLength < 1) {
			throw new ArithmeticException("The exponent length must be equal to or within the range of 1 and 62.");
		}
		return sum(Arrays.copyOfRange(BIT64_BIT_MASKS, 1, expLength));
	}

	public static final int sum(int[] ints) {
		int r = 0;
		for (int i : ints) {
			r += i;
		}
		return r;
	}

	public static final int gauss(int i) {
		return i > 0 ? gauss(i - 1) : 0;
	}

	public static final long sum(long[] longs) {
		long r = 0L;
		for (long l : longs) {
			r += l;
		}
		return r;
	}

	public static final int bytesToInt(byte[] bytes) {
		if (bytes.length <= THIRD_BIT) {
			int r = 0;
			for (byte b : bytes) {
				r = (r << FOURTH_BIT) + b;
			}
			return r;
		} else {
			throw new ArithmeticException("Integer must be less than or equal to 4 bytes.");
		}
	}
	
	public static final long bytesToLong(byte[] bytes) {
		if(bytes.length <= FOURTH_BIT) {
			long r = 0;
			for(byte b : bytes) {
				r = (r << FOURTH_BIT) + b;
			}
			return r;
		} else {
			throw new ArithmeticException("Long musty be less than or equal to 8 bytes.");
		}
	}

	public static final int getHfp32Exponent(int i) {
		return i & HFP32_EXP_MASK;
	}

	public static final long getHfp64Exponent(long l) {
		return l & HFP64_EXP_MASK;
	}

	public static final int getSign(int i) {
		return (i & BIT32_SIGN_MASK) == 0 ? 1 : -1;
	}

	public static final int getSign(long l) {
		return (l & BIT64_SIGN_MASK) == 0 ? 1 : -1;
	}

	public static final int getHfp32Mantissa(int i) {
		return i & HFP32_MAN_MASK;
	}

	public static final long getHfp64Mantissa(long l) {
		return l & HFP64_MAN_MASK;
	}

	public static final float hfpToFloat(byte[] bytes) {
		if (bytes.length == THIRD_BIT) {
			int i = bytesToInt(bytes);
			return (float) ((getSign(i) * getHfp32Mantissa(i))
					* StrictMath.pow(FIFTH_BIT, (getHfp32Exponent(i) - HFP_BIAS)));
		} else {
			throw new ArithmeticException("Float must be 4 bytes");
		}
	}
	
	public static final double hfpToDouble(byte[] bytes) {
		if(bytes.length == FOURTH_BIT) {
			long l = bytesToLong(bytes);
			return ((getSign(l) * getHfp64Mantissa(l)) * StrictMath.pow(FIFTH_BIT, (getHfp64Exponent(l) - HFP_BIAS)));
		} else {
			throw new ArithmeticException("Double must be 8 bytes.");
		}
	}
}
