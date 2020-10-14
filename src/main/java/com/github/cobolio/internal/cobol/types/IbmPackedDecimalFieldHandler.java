/**
 * 
 */
package com.github.cobolio.internal.cobol.types;

import static com.github.cobolio.internal.cobol.PrimitiveConstants.*;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.DecimalData;
import com.PackedDecimal;
import com.github.cobolio.internal.util.Messages;
import com.github.cobolio.types.TypeConversionException;
import com.github.cobolio.types.TypeHandler;

/**
 * An implementation of TypeHandler explicitly for handling packed decimal
 * fields. Information such as the field's precision, scale, and offset must be
 * provided during initialization.
 * 
 * @author Andrew
 *
 */
@SuppressWarnings("unused")
public class IbmPackedDecimalFieldHandler implements TypeHandler {
	public static final byte PACKED_ZERO = 0;
	public static final byte PACKED_SIGNED_ZERO = 12;
	public static final byte PACKED_PLUS = 12;
	public static final byte PACKED_MINUS = 13;
	
	private static final byte A = 0x0A;
	private static final byte B = 0x0B;
	private static final byte C = 0x0C;
	private static final byte D = 0x0D;
	private static final byte E = 0x0E;
	private static final byte F = 0x0F;
	
	private static final int PACKED_BYTES_LENGTH = 33;

	private static final byte HIGH_NIBBLE_MASK = (byte) 240;
	private static final byte LOW_NIBBLE_MASK = 15;
	private static final byte POSITIVE_MASK = 127;

	private final int offset;
	private final int precision;
	private final int scale;
	private final boolean showSign;
	private final int byteLength;

	public IbmPackedDecimalFieldHandler(int offset, int precision, int scale) {
		this.offset = offset;
		this.precision = precision;
		this.scale = scale;
		this.showSign = false;
		this.byteLength = PackedDecimal.precisionToByteLength(precision);
	}

	public IbmPackedDecimalFieldHandler(int offset, int precision, int scale, boolean showSign) {
		this.offset = offset;
		this.precision = precision;
		this.scale = scale;
		this.showSign = showSign;
		this.byteLength = PackedDecimal.precisionToByteLength(precision);
	}

	@SuppressWarnings({ "java:S109", "java:S3358", "java:S3776" })
	public static final int numDigits(int value) {
		value = value == Integer.MIN_VALUE ? Integer.MAX_VALUE : Math.abs(value);
		return value >= 10000
				? (value >= 10000000 ? (value >= 100000000 ? (value >= 1000000000 ? 10 : 9) : 8)
						: (value >= 100000 ? (value >= 1000000 ? 7 : 6) : 5))
				: (value >= 100 ? (value >= 1000 ? 4 : 3) : (value >= 10 ? 2 : 1));
	}

	@SuppressWarnings({ "java:S109", "java:S3358", "java:S3776" })
	public static final int numDigits(long value) {
		value = value == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(value);
		return value >= 1000000000L
				? (value >= 100000000000000L ? (value >= 10000000000000000L
						? (value >= 100000000000000000L ? (value >= 1000000000000000000L ? 19 : 18) : 17)
						: (value >= 1000000000000000L ? 16 : 15))
						: (value >= 100000000000L
								? (value >= 1000000000000L ? (value >= 10000000000000L ? 14 : 13) : 12)
								: (value >= 10000000000L ? 11 : 10)))
				: (value >= 10000L
						? (value >= 1000000L ? (value >= 10000000L ? (value >= 100000000L ? 9 : 8) : 7)
								: (value >= 100000L ? 6 : 5))
						: (value >= 100L ? (value >= 1000L ? 4 : 3) : (value >= 10L ? 2 : 1)));
	}

	/**
	 * 
	 * 
	 * HIGH_THREE in this context is used as the offset position of the first
	 * numeric character, '0', which is index 48.
	 * 
	 * @param bytes
	 * @return
	 */
	public String packedToString(byte[] bytes, boolean showSign) {
		return packedToStringBuilder(bytes, showSign).toString();
	}

	public BigDecimal packedToBigDecimal(byte[] bytes, int scale, int precision) {
		StringBuilder builder = packedToStringBuilder(bytes, false);
		builder.insert(precision - scale, '.');
		return new BigDecimal(builder.toString());
	}

	private StringBuilder packedToStringBuilder(byte[] bytes, boolean showSign) {
		StringBuilder builder = new StringBuilder();
		int end = bytes.length - 1;
		if ((bytes[end] & LOW_NIBBLE_MASK) == PACKED_OTHER_NEGATIVE_SIGN
				|| (bytes[end] & LOW_NIBBLE_MASK) == PACKED_NEGATIVE_SIGN) {
			builder.append('-');
		} else if (showSign) {
			builder.append('+');
		} else {
			// Do nothing
		}
		for (int i = 0; i < bytes.length - 1; i++) {
			builder.append((char) HIGH_THREE + ((byte) (bytes[i] >> NIBBLE_LENGTH) & LOW_NIBBLE_MASK));// High Nibble
																										// packed digit.
			builder.append((char) HIGH_THREE + (bytes[i] & LOW_NIBBLE_MASK));// Low Nibble packed digit.
		}
		builder.append(HIGH_THREE + ((byte) (bytes[end] >> NIBBLE_LENGTH) & LOW_NIBBLE_MASK));// Digit that is packed
																								// with the sign.
		return builder;
	}

	public static int getPackedByteCount(int precision) {
		return precision / TWO + 1;
	}

	public static int checkPackedDecimal(byte[] byteArray, int offset, int precision,
			boolean ignoreHighNibbleForEvenPrecision, boolean canOverwriteHighNibbleForEvenPrecision) {
		if (offset + precision / TWO + 1 <= byteArray.length && offset >= 0) {
			return checkPackedDecimalContent(byteArray, offset, precision, ignoreHighNibbleForEvenPrecision,
					canOverwriteHighNibbleForEvenPrecision);
		} else {
			throw new ArrayIndexOutOfBoundsException(
					"Array access index out of bounds. checkPackedDecimal is trying to access byteArray[" + offset
							+ "] to byteArray[" + (offset + precision / TWO) + "] but valid indices are from 0 to "
							+ (byteArray.length - 1) + ".");
		}
	}

	private static int checkPackedDecimalContent(byte[] byteArray, int offset, int precision,
			boolean ignoreHighNibbleForEvenPrecision, boolean canOverwriteHighNibbleForEvenPrecision) {
		if (precision < 1) {
			throw new IllegalArgumentException("Illegal Precision.");
		} else {
			boolean evenPrecision = precision % TWO == 0;
			int signOffset = offset + getPackedByteCount(precision) - 1;
			int returnCode = 0;
			if (canOverwriteHighNibbleForEvenPrecision && evenPrecision) {
				byteArray[offset] = (byte) (byteArray[offset] & LOW_NIBBLE_MASK);
			}
			if (evenPrecision && ignoreHighNibbleForEvenPrecision) {
				if ((byteArray[offset] & LOW_NIBBLE_MASK) > LOW_NINE) {
					returnCode = TWO;
				}
				++offset;
			}
			int i;
			for (i = offset; i < signOffset && byteArray[i] == 0; ++i) {
			}
			while (i < signOffset) {
				if ((byteArray[i] & LOW_NIBBLE_MASK) > LOW_NINE
						|| (byteArray[i] & HIGH_NIBBLE_MASK & 255) > HIGH_NINE) {
					returnCode = TWO;
					break;
				}
				++i;
			}
			if (i == signOffset && (byteArray[signOffset] & HIGH_NIBBLE_MASK & 255) > HIGH_NINE) {
				returnCode = TWO;
			}
			if ((byteArray[signOffset] & LOW_NIBBLE_MASK) < A) {
				++returnCode;
			}
			return returnCode;
		}
	}

	public static int checkPackedDecimal(byte[] byteArray, int offset, int precision,
			boolean ignoreHighNibbleForEvenPrecision) {
		return checkPackedDecimal(byteArray, offset, precision, ignoreHighNibbleForEvenPrecision, false);
	}

	public static int checkPackedDecimal(byte[] byteArray, int offset, int precision) {
		return checkPackedDecimal(byteArray, offset, precision, false, false);
	}

	public static BigInteger getBigInteger(byte[] pd, int offset, int length) {
		int end = offset + length - 1;
		StringBuilder sb = new StringBuilder();
		if ((pd[end] & LOW_NIBBLE_MASK) == PACKED_OTHER_NEGATIVE_SIGN
				|| (pd[end] & LOW_NIBBLE_MASK) == PACKED_NEGATIVE_SIGN) {
			sb.append('-');
		}

		for (int i = offset; i < end; ++i) {
			sb.append((char) (HIGH_THREE + (((byte) (pd[i] >> NIBBLE_LENGTH)) & LOW_NIBBLE_MASK)));
			sb.append((char) (HIGH_THREE + (pd[i] & LOW_NIBBLE_MASK)));
		}

		sb.append((char) (HIGH_THREE + (((byte) (pd[end] >> NIBBLE_LENGTH)) & LOW_NIBBLE_MASK)));
		return new BigInteger(sb.toString());
	}

	public static void zeroTopNibbleIfEven(byte[] bytes, int offset, int prec) {
		if (prec % TWO == 0) {
			bytes[offset] = (byte) (bytes[offset] & LOW_NIBBLE_MASK);
		}

	}

	

	public static int precisionToByteLength(int precision) {
		return (precision + TWO) / TWO;
	}

	public static byte signMask(byte a, byte b) {
		return (byte) (sign(a) * sign(b) > 0 ? 252 : 253);
	}

	public static byte getSign(int i) {
		return (i != D && i != B ? C : D);
	}

	public static int sign(byte b) {
		return getSign(b & LOW_NIBBLE_MASK) == D ? -1 : 1;
	}

	@Override
	public Object parse(byte[] text) throws TypeConversionException {
		if (checkPackedDecimal(text, offset, precision) == TWO) {
			return packedToBigDecimal(text, scale, precision);
		} else {
			throw new TypeConversionException("Conversion failed. Contents do not represent a packed decimal.");
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public byte[] format(Object value) {
		byte[] packed = new byte[byteLength];
		if (value instanceof BigDecimal) {
			DecimalData.convertBigDecimalToPackedDecimal((BigDecimal)value, packed, 0, precision, true);
		} else if (value instanceof BigInteger) { 
			DecimalData.convertBigIntegerToPackedDecimal((BigInteger)value, packed, 0, precision, true);
		} else if (value instanceof String) {
			DecimalData.convertBigDecimalToPackedDecimal(new BigDecimal((String)value), packed, 0, precision, true);
		} else {
			throw new IllegalStateException(Messages.getString("com.github.cobolio.internal.cobol.types.IbmPackedDecimalFieldHandler.IllegalState"));
		}
		return packed;
	}

	@Override
	public Class<?> getType() {
		return this.getClass();
	}

}
