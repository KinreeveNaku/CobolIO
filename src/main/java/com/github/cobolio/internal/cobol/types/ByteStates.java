/**
 * 
 */
package com.github.cobolio.internal.cobol.types;

/**
 * @author Andrew
 *
 */
public enum ByteStates {
	/**
	 * @param max 9223372036854775807
	 * @param min -9223372036854775808
	 * @param signext 
	 */
	B64(Long.MAX_VALUE, Long.MIN_VALUE, 0L),
	/**
	 * @param max 36028797018963967
	 * @param min -36028797018963968
	 * @param signext -9151314442816847873
	 */
	B56(0x007FFFFFFFFFFFFFL, 0xFF80000000000000L, 0x80FFFFFFFFFFFFFFL),//?
	/**
	 * @param max 140737488355327
	 * @param min -140737488355328
	 * @param signext -281474976710656
	 */
	B48(0x00007FFFFFFFFFFFL, 0xFFFF800000000000L, 0xFFFF000000000000L),
	/**
	 * @param max 549755813887
	 * @param min -549755813888
	 * @param signext -1099511627776
	 */
	B40(0x0000007FFFFFFFFFL, 0xFFFFFF8000000000L, 0xFFFFFF0000000000L),
	/**
	 * @param max 2147483647
	 * @param min -2147483648
	 * @param signext -4294967296
	 */
	B32(0x000000007FFFFFFFL, 0xFFFFFFFF80000000L, 0xFFFFFFFF00000000L),
	/**
	 * @param max 8388607
	 * @param min -8388608
	 * @param signext -16777216
	 */
	B24(0x00000000007FFFFFL, 0xFFFFFFFFFF800000L, 0xFFFFFFFFFF000000L),
	/**
	 * @param max 32767
	 * @param min -32768
	 * @param signext
	 */
	B16(0x0000000000007FFFL, 0xFFFFFFFFFFFF8000L, 0xFFFFFFFFFFFF0000L),
	/**
	 * @param max 127
	 * @param min -128
	 * @param signext -256
	 */
	B8(0x000000000000007FL, 0xFFFFFFFFFFFFFF80L, 0xFFFFFFFFFFFFFF00L);

	static final long U_MIN = 0L;
	static final int ONE = 1;
	static final int TWO = 2;
	static final int THREE = 3;
	static final int FOUR = 4;
	static final int FIVE = 5;
	static final int SIX = 6;
	static final int SEVEN = 7;
	static final int EIGHT = 8;
	final long max;
	final long min;
	final long signext;

	/**
	 * @param max
	 * @param min
	 * @param signext
	 */
	ByteStates(long max, long min, long signext) {
		this.max = max;
		this.min = min;
		this.signext = signext;
	}
}
