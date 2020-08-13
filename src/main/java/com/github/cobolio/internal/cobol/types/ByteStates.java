/**
 * 
 */
package com.github.cobolio.internal.cobol.types;

/**
 * @author Andrew
 *
 */
public enum ByteStates {
	B64(Long.MAX_VALUE, Long.MIN_VALUE, 0L),
	B56(0x007FFFFFFFFFFFFFL, 0xFF80000000000000L, 0x80FFFFFFFFFFFFFFL),
	B48(0x00007FFFFFFFFFFFL, 0xFFFF800000000000L, 0xFFFF000000000000L),
	B40(0x0000007FFFFFFFFFL, 0xFFFFFF8000000000L, 0xFFFFFF0000000000L),
	B32(0x000000007FFFFFFFL, 0xFFFFFFFF80000000L, 0xFFFFFFFF00000000L),
	B24(0x00000000007FFFFFL, 0xFFFFFFFFFF800000L, 0xFFFFFFFFFF000000L),
	B16(0x0000000000007FFFL, 0xFFFFFFFFFFFF8000L, 0xFFFFFFFFFFFF0000L),
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
	 * 
	 */
	ByteStates(long max, long min, long signext) {
		this.max = max;
		this.min = min;
		this.signext = signext;
	}
}
