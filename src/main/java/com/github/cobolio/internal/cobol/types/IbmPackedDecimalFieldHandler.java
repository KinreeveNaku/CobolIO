/**
 * 
 */
package com.github.cobolio.internal.cobol.types;

import static com.github.cobolio.internal.cobol.PrimitiveConstants.*;

import java.math.BigDecimal;

import com.github.cobolio.internal.cobol.datatype.PackedDecimal;
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
		this.byteLength = PackedDecimal.getLength(precision);
	}

	public IbmPackedDecimalFieldHandler(int offset, int precision, int scale, boolean showSign) {
		this.offset = offset;
		this.precision = precision;
		this.scale = scale;
		this.showSign = showSign;
		this.byteLength = PackedDecimal.getLength(precision);
	}

	public static int precisionToByteLength(int precision) {
		return (precision / TWO) + 1;
	}

	public static byte signMask(byte a, byte b) {
		return (byte) (sign(a) * sign(b) > 0 ? 252 : 253);// FB or FD
	}

	/**
	 * 
	 * @param i The hexadecimal sign value.
	 * @return Returns 0x0F if positive, otherwise returns 0x0D.
	 * @implNote Always resolve any potential negative sign to the value F. Other
	 *           negative signs have no impact outside of the COBOL compiler so it
	 *           is unneeded complexity to account for it.
	 */
	public static byte getSign(int i) {
		return (i != D && i != B ? F : D);// D and B are the negative sign hex values on the COBOL side for packed
											// decimal.
	}

	public static int sign(byte b) {
		return getSign(b & LOW_NIBBLE_MASK) == D ? -1 : 1;
	}

	@Override
	public Object parse(byte[] bytes) throws TypeConversionException {
		if (PackedDecimal.checkPackedDecimal(bytes, offset, precision) == TWO) {
			return PackedDecimal.unpack(bytes, scale);
		} else {
			throw new TypeConversionException("Conversion failed. Contents do not represent a packed decimal.");
		}
	}

	@Override
	public byte[] format(Object value) {
		byte[] packed = new byte[byteLength];
		if (value instanceof Number) {
			return PackedDecimal.pack((Number) value, 1);
		} else if (value instanceof String) {
			PackedDecimal.pack(new BigDecimal((String) value), 1);
		} else {
			throw new IllegalStateException(Messages
					.getString("com.github.cobolio.internal.cobol.types.IbmPackedDecimalFieldHandler.IllegalState"));
		}
		return packed;
	}

	@Override
	public Class<?> getType() {
		return PackedDecimal.class;
	}

}
