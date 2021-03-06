/**
 * 
 */
package com.github.cobolio.internal.cobol.types;

import static com.github.cobolio.internal.cobol.types.ByteStates.*;

import com.github.cobolio.internal.util.IllegalRangeException;
import com.github.cobolio.internal.util.Messages;
import com.github.cobolio.types.TypeConversionException;

/**
 * @author Andrew
 *
 */
public class BinaryIntegerFieldHandler implements IbmBinaryFieldHandler {
	private int offset;
	private int storageLength;
	private boolean signed;
	private int signExtension;
	private int maxValue;
	private int minValue;

	public BinaryIntegerFieldHandler(int offset) {
		this(offset, FOUR, true);
	}

	/**
	 * @param offset
	 * @param length
	 */
	public BinaryIntegerFieldHandler(int offset, int length) {
		this(offset, length, true);
	}

	/**
	 * @param offset
	 * @param length
	 * @param signed
	 */
	public BinaryIntegerFieldHandler(int offset, int length, boolean signed) {

		this.offset = offset;
		this.storageLength = length;
		this.signed = signed;
		switch (length) {
		case FOUR:
			init(B32);
			break;
		case THREE:
			init(B24);
			break;
		case TWO:
			init(B16);
			break;
		case ONE:
			init(B8);
			break;
		default:
			throw new IllegalRangeException(Messages.getString("com.github.cobolio.internal.cobol.types.BinaryIntegerFieldHandler.IllegalRange"));
		}
	}

	/**
	 * @param b32
	 */
	private void init(ByteStates state) {
		this.maxValue = (int) state.max;
		this.minValue = (int) state.min;
		this.signExtension = (int) state.signext;

	}

	public int getOffset() {
		return offset;
	}

	public int getStorageLength() {
		return storageLength;
	}

	public boolean isSigned() {
		return signed;
	}
	
	@Override
	public Integer get(byte[] bytes) {
		return this.getInteger(bytes);
	}
	
	@Override
	public Integer get(byte[] bytes, int offset) {
		return this.getInteger(bytes, offset);
	}

	public int getInteger(byte[] bytes) {
		return getInteger(bytes, 0);
	}

	/**
	 * @param bytes
	 * @param i
	 * @return
	 */
	private int getInteger(byte[] bytes, int offset) {
		int start = this.offset + offset;
		int end = start + this.storageLength - 1;
		int result = 0;
		for (int i = start; i <= end; i++) {
			result <<= EIGHT;
			result += bytes[i];
		}
		if (signed) {
			if (bytes[0] < 0) {
				result += signExtension;
			}
		} else if (result < 0L) {// should be signed but isn't
			throw new ArithmeticException(
					"An unsigned instance was given a byte[] containing a negative binary encoded value.");
		} else {
			throw new IllegalArgumentException("An unexpected state has occurred.");
		}
		return result;
	}
	
	public void encodeInteger(int value, byte[] bytes) {
		encodeInteger(value, bytes, 0);
	}
	
	public void encodeInteger(int value, byte[] bytes, int offset) {
		if(value < this.minValue || value > this.maxValue) {
			throw new IllegalArgumentException();
		} else {
			int start = this.offset + offset;
			int end = start + this.storageLength - 1;
			
			for(int i = end; i >= start; i--) {
				bytes[i] = (byte)(value);
				value >>= EIGHT;
			}
		}
	}

	@Override
	public Object parse(byte[] text) throws TypeConversionException {
		return get(text);
	}

	@Override
	public byte[] format(Object value) {
		byte[] b = new byte[this.storageLength];
		this.encodeInteger((Integer)value, b, this.offset);
		return b;
	}

	@Override
	public Class<?> getType() {
		return Integer.TYPE;
	}

}
