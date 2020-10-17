/**
 * 
 */
package com.github.cobolio.internal.cobol;

public final class PrimitiveConstants {

	static final long B64_MAX = Long.MAX_VALUE;
	static final long B64_MIN = Long.MIN_VALUE;
	static final long B64_SE = 0L;

	static final long B56_MAX = 0x007FFFFFFFFFFFFFL;
	static final long B56_MIN = 0xFF80000000000000L;
	static final long B56_SE = 0x80FFFFFFFFFFFFFFL;

	static final long B48_MAX = 0x00007FFFFFFFFFFFL;
	static final long B48_MIN = 0xFFFF800000000000L;
	static final long B48_SE = 0xFFFF000000000000L;

	static final long B40_MAX = 0x0000007FFFFFFFFFL;
	static final long B40_MIN = 0xFFFFFF8000000000L;
	static final long B40_SE = 0xFFFFFF0000000000L;

	static final long B32_MAX = 0x000000007FFFFFFFL;
	static final long B32_MIN = 0xFFFFFFFF80000000L;
	static final long B32_SE = 0xFFFFFFFF00000000L;

	static final long B24_MAX = 0x00000000007FFFFFL;
	static final long B24_MIN = 0xFFFFFFFFFF800000L;
	static final long B24_SE = 0xFFFFFFFFFF000000L;

	static final long B16_MAX = 0x0000000000007FFFL;
	static final long B16_MIN = 0xFFFFFFFFFFFF8000L;
	static final long B16_SE = 0xFFFFFFFFFFFF0000L;

	static final long B8_MAX = 0x000000000000007FL;
	static final long B8_MIN = 0xFFFFFFFFFFFFFF80L;
	static final long B8_SE = 0xFFFFFFFFFFFFFF00L;

	static final int B32_MAX_INT = 0x7FFFFFFF;
	static final int B32_MIN_INT = 0x80000000;
	static final int B32_SE_INT = 0;

	static final int B24_MAX_INT = 0x007FFFFF;
	static final int B24_MIN_INT = 0xFF800000;
	static final int B24_SE_INT = 0xFF000000;

	static final int B16_MAX_INT = 0x00007FFF;
	static final int B16_MIN_INT = 0xFFFF8000;
	static final int B16_SE_INT = 0xFFFF0000;

	static final int B8_MAX_INT = 0x0000007F;
	static final int B8_MIN_INT = 0xFFFFFF80;
	static final int B8_SE_INT = 0xFFFFFF00;

	public static final byte BIT_LENGTH = 1;
	public static final byte TWO = 2;
	public static final byte THREE = 3;
	public static final byte NIBBLE_LENGTH = 4;
	public static final byte FIVE = 5;
	public static final byte SIX = 6;
	public static final byte SEVEN = 7;
	public static final byte BYTE_LENGTH = 8;
	public static final byte TEN = 10;

	public static final byte SIXTEEN = 16;
	public static final byte TWENTYFOUR = 24;
	public static final byte THIRTYTWO = 32;
	public static final byte FOURTY = 40;
	public static final byte FOURTYEIGHT = 48;
	public static final byte FIFTYSIX = 56;
	public static final byte SIXTYFOUR = 64;

	// The high is 16 times the low. ((0b...0n00000) == (0b0n0 << 4)) and (4^2 == 16)
	public static final byte LOW_ONE = +0x01;// 16 / 1 = 16
	public static final byte HIGH_ONE = +0x10;
	public static final byte LOW_TWO = +0x02;// 32 / 2 = 16
	public static final byte HIGH_TWO = +0x20;
	public static final byte LOW_THREE = +0x03;// 48 / 3 = 16
	/**
	 * Low two bits of the high nibble
	 */
	public static final byte HIGH_THREE = +0x30;
	public static final byte LOW_FOUR = +0x04;// 64 / 4 = 16
	public static final byte HIGH_FOUR = +0x40;
	public static final byte LOW_FIVE = +0x05;// 80 / 5 = 16
	public static final byte HIGH_FIVE = +0x50;
	public static final byte LOW_SIX = +0x06;// 96 / 6 = 16
	public static final byte HIGH_SIX = +0x60;
	public static final byte LOW_SEVEN = +0x07;// 112 / 7 = 16
	/**
	 * Low three bits of the high nibble
	 */
	public static final byte HIGH_SEVEN = +0x70;
	public static final byte LOW_EIGHT = +0x08;// 128 / 8 = 16
	public static final byte HIGH_EIGHT = (byte) +0x80;
	public static final byte LOW_NINE = +0x09;// 144 / 9 = 16
	public static final byte HIGH_NINE = (byte) +0x90;

	public static final byte PACKED_OTHER_NEGATIVE_SIGN = 11;//-?
	public static final byte PACKED_POSITIVE_SIGN = 12;//+
	public static final byte PACKED_NEGATIVE_SIGN = 13;//-
	public static final int PACKED_SIGN_MASK = 252;
	public static final int PACKED_NEGATIVE_HIGH_SIGN = 253;
	
	public static final byte EXTERNAL_NEGATIVE_SIGN_SEPARATE = -48;
	public static final byte EXTERNAL_NEGATIVE_SIGN_SEPARATE_OTHER = -80;
	
	public static final int EBCDIC_SIGN_EMBEDDED_TRAILING = 1;
	public static final int EBCDIC_SIGN_EMBEDDED_LEADING = 2;
	public static final int EBCDIC_SIGN_SEPARATE_TRAILING = 3;
	public static final int EBCDIC_SIGN_SEPARATE_LEADING = 4;
	
	public static final byte PACKED_ZERO = 0;
	public static final byte PACKED_SIGNED_ZERO = 12;
	public static final byte PACKED_PLUS = 12;
	public static final byte PACKED_MINUS = 13;
	public static final byte PACKED_ALT_PLUS = 15;
	public static final byte PACKED_ALT_PLUS1 = 14;
	public static final byte PACKED_ALT_PLUS2 = 10;
	public static final byte PACKED_ALT_MINUS = 11;
	public static final byte EXTERNAL_SIGN_PLUS = 78;
	public static final byte EXTERNAL_SIGN_MINUS = 96;
	public static final byte EXTERNAL_EMBEDDED_SIGN_PLUS = -64;
	public static final byte EXTERNAL_EMBEDDED_SIGN_MINUS = -48;
	public static final byte EXTERNAL_EMBEDDED_SIGN_PLUS_ALTERNATE_A = -96;
	public static final byte EXTERNAL_EMBEDDED_SIGN_PLUS_ALTERNATE_E = -32;
	public static final byte EXTERNAL_EMBEDDED_SIGN_PLUS_ALTERNATE_F = -16;
	public static final byte EXTERNAL_EMBEDDED_SIGN_MINUS_ALTERNATE_B = -80;
	public static final byte LOW_NIBBLE_MASK = 15;
	public static final byte BYTE_7F = 0x7F;
	public static final byte HIGH_NIBBLE_MASK = -16;
	public static final byte BIT_POS_128 = (byte) 0b10000000;
	public static final byte BIT_POS_64 = 0b01000000;
	public static final byte BIT_POS_32 = 0b00100000;
	public static final byte BIT_POS_16 = 0b00010000;
	public static final byte BIT_POS_8 = 0b00001000;
	public static final byte BIT_POS_4 = 0b00000100;
	public static final byte BIT_POS_2 = 0b00000010;
	public static final byte BIT_POS_1 = 0b00000001;
	
	public static final byte ASCII_PLUS = 43;
	public static final byte ASCII_MINUS = 45;
	public static final byte ASCII_ZED = 48;

	static final long U_MIN = 0L;

	private PrimitiveConstants() {
		throw new IllegalArgumentException();
	}

}
