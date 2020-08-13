/**
 * 
 */
package com.github.cobolio.internal.cobol.types;
import static com.github.mfds2j.data.cobol.cobol.PrimitiveConstants.*;

import com.github.cobolio.internal.cobol.ByteUtils;
/**
 * @author Andrew
 *
 */
public class IbmDoubleFieldHandler {
	public static final int BYTE_LENGTH = 8;
	public static final long BIT64_SIGN_MASK = 0x8000000000000000L;
	public static final long NON_BIT64_SIGN_MASK = 0x7FFFFFFFFFFFFFFFL;
	public static final long HFP_MANTISSA_MASK = 0x00FFFFFFFFFFFFFFL;
	public static final long BFP_MANTISSA_MASK = 0x000FFFFFFFFFFFFFL;
	public static final long BFP_MANTISSA_IMPLIED_HOB = 0x0010000000000000L;
	public static final long BFP_MANTISSA_HON = 0x00F0000000000000L;
	public static final long HFP_EXPONENT_MASK = 0x7F00000000000000L;
	public static final long BFP_EXPONENT_MASK = 0x7FF0000000000000L;
	
	public static final int HFP_MANTISSA_LENGTH = 56;
	public static final int BFP_MANTISSA_LENGTH = 52;
	
	public static final int HFP_BIAS = 64;
	public static final int HFP_MAX_EXPONENT = 127;
	public static final int BFP_BIAS = 1023;
	public static final int BFP_INFINITY_EXPONENT = 2047;
	
	private int offset;
	
	public IbmDoubleFieldHandler(int offset) {
		this.offset = offset;
	}
	
	public int getByteLength() {
		return BYTE_LENGTH;
	}
	
	public int getOffset() {
		return this.offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public double getDouble(byte[] bytes) {
		return this.getDouble(bytes, 0);
	}
	
	public double getDouble(byte[] bytes, int offset) {
		long hexFpBits = ByteUtils.bytesAsLong(bytes, this.offset + offset, BYTE_LENGTH);
		long binFpBits = this.hexToBin(hexFpBits);
		return Double.longBitsToDouble(binFpBits);
	}

	/**
	 * @param hexFpBits
	 * @return
	 */
	private long hexToBin(long hexFpBits) {
		long sign = hexFpBits & BIT64_SIGN_MASK;
		if((hexFpBits & NON_BIT64_SIGN_MASK) == 0L) {
			return sign;
		} else {
			long mantissa = hexFpBits & HFP_MANTISSA_MASK;
			long hexponent = (hexFpBits & HFP_EXPONENT_MASK) >> HFP_MANTISSA_LENGTH;
			long bexponent = ((hexponent - HFP_BIAS) << TWO) + BFP_BIAS - 1L;
			for(mantissa >>= THREE; (mantissa & BFP_MANTISSA_IMPLIED_HOB) == 0L; mantissa <<= 1) {
				--bexponent;
			}
			if(bexponent <= 0L) {
				if(bexponent < -HFP_MANTISSA_LENGTH) {
					mantissa = 0L;
				} else {
					mantissa >>= (int)(-bexponent + 1L);
				}
				
				bexponent = 0L;
			} else if(bexponent >= BFP_INFINITY_EXPONENT) {
				mantissa = 0L;
				bexponent = BFP_INFINITY_EXPONENT;
			} else {
				mantissa &= BFP_MANTISSA_MASK;
			}
			return sign | (bexponent << BFP_MANTISSA_LENGTH) | mantissa;
		}
	}
	
	public boolean equals(Double wrap, double prim) {
		if(wrap == null) {
			return false;
		} else {
			return wrap == prim;
		}
	}
}
