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
public class BinaryLongFieldHandler implements IbmBinaryFieldHandler {
	private int offset;
	private int storageLength;
	private boolean signed;
	private long signExtension;
	private long maxValue;
	private long minValue;

	public BinaryLongFieldHandler(int offset, int length, boolean signed) {
		this.offset = offset;
		this.storageLength = length;
		this.signed = signed;
		switch (storageLength) {
		case EIGHT:
			init(B64);
			break;
		case SEVEN:
			init(B56);
			break;
		case SIX:
			init(B48);
			break;
		case FIVE:
			init(B40);
			break;
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
			throw new IllegalRangeException(Messages.getString("com.github.cobolio.internal.cobol.types.BinaryLongFieldHandler.IllegalRange"));
		}
	}
	
	@Override
	public Long get(byte[] bytes) {
		return this.getLong(bytes);
	}
	
	@Override
	public Long get(byte[] bytes, int offset) {
		return this.getLong(bytes, offset);
	}
	
	public long getLong(byte[] bytes) {
		return this.getLong(bytes, 0);
	}
	
	public long getLong(byte[] bytes, int offset) {
		int start = this.offset + offset;
		int end = start + this.storageLength - 1;
		long result = 0L;
		
		for(int i = start; i <= end; i++) {
			result <<= EIGHT;
			result += (bytes[i] & 0xFF);
		}
		if(signed) {
			if(bytes[0] < 0) {
				result += signExtension;
			}
		} else if(result < 0L) {//this instance is declared as unsigned, but the value provided isn't. Logic may require BigInteger backing if flaws are found.
			throw new ArithmeticException("An unsigned instance was given a byte[] containing a negative binary encoded value.");
		} else {
			throw new IllegalArgumentException("An unexpected state has occurred.");
		}
		return result;
	}
	
	public void encodeLong(long value, byte[] bytes, int offset) {
		if(value < this.minValue || value > this.maxValue) {
			throw new IllegalArgumentException();
		} else {
			int start = this.offset + offset;
			int end = start + this.storageLength - 1;
			
			for(int i = end; i >= start; i--) {
				bytes[i] = (byte)((int)value);
				value >>= EIGHT;
			}
		}
	}
	
	private void init(ByteStates state) {
		this.maxValue = state.max;
		if (signed) {
			this.signExtension = state.signext;
			this.minValue = state.min;
		} else {
			this.minValue = 0L;
		}
	}
	
	public boolean areEqual(long p, Long w) {
		if(w == null) {
			return false;
		} else {
			return w == p;
		}
	}

	@Override
	public Object parse(byte[] text) throws TypeConversionException {
		return get(text);
	}

	@Override
	public byte[] format(Object value) {
		byte[] b = new byte[this.storageLength];
		encodeLong((Long)value, b, 0);
		return b;
	}

	@Override
	public Class<?> getType() {
		return Long.TYPE;
	}
}
