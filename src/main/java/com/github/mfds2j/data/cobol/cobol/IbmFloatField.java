/**
 * 
 */
package com.github.mfds2j.data.cobol.cobol;

import static com.github.mfds2j.data.cobol.cobol.PrimitiveConstants.*;

/**
 * @author Andrew
 *
 */
public class IbmFloatField {
	public static final int BYTE_LENGTH = 4;
	public static final int BIT32_SIGN_MASK = Integer.MIN_VALUE;
	public static final int NON_BIT32_SIGN_MASK = Integer.MAX_VALUE;
	public static final int HFP_MANTISSA_MASK = 0x00FFFFFF;
	public static final int BFP_MANTISSA_MASK = 0x007FFFFF;
	public static final int BFP_MANTISSA_IMPLIED_HOB = 0x00800000;
	public static final int HFP_MANTISSA_HON = 0x00F00000; 
	public static final int HFP_EXPONENT_MASK = 0x7F000000;
	public static final int BFP_EXPONENT_MASK = 0x7F800000;
	public static final int BFP_BIAS = 127;
	public static final int BFP_INFINITY_EXPONENT = 255;
	public static final int HFP_MANTISSA_LENGTH = 24;
	public static final int BFP_MANTISSA_LENGTH = 23;
	private int offset;
	
	public IbmFloatField(int offset) {
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
	
	public float getFloat(byte[] bytes) {
		return this.getFloat(bytes, 0);
	}
	
	public float getFloat(byte[] bytes, int offset) {
		int hexFpBits = ByteUtils.bytesAsInteger(bytes, this.offset + offset, BYTE_LENGTH);
		int binFpBits = this.hexToBin(hexFpBits);
		return Float.intBitsToFloat(binFpBits);
	}
	
	private int hexToBin(int hexFpBits) {
		int sign = hexFpBits & BIT32_SIGN_MASK;
		if((hexFpBits & NON_BIT32_SIGN_MASK) == 0) {
			return sign;
		} else {
			int mantissa = hexFpBits & HFP_MANTISSA_MASK;
			int hexponent = (hexFpBits & HFP_EXPONENT_MASK) >> HFP_MANTISSA_LENGTH;
			
			int exponent;
			for(exponent = ((hexponent - SIXTYFOUR) << TWO) + BFP_BIAS - 1; (mantissa & BFP_MANTISSA_IMPLIED_HOB) == 0; mantissa <<= 1) {
				--exponent;
			}
			if(exponent <= 0) {
				if(exponent < -HFP_MANTISSA_LENGTH) {
					mantissa = 0;
				} else {
					mantissa >>= (-exponent + 1);
				}
				
				exponent = 0;
			} else if(exponent >= BFP_INFINITY_EXPONENT) {
				mantissa = 0;
				exponent = BFP_INFINITY_EXPONENT;
			} else {
				mantissa &= BFP_MANTISSA_MASK;
			}
			return sign | (exponent << BFP_MANTISSA_LENGTH) | mantissa;
		}
	}
}
