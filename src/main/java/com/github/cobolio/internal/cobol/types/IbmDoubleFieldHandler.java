/**
 * 
 */
package com.github.cobolio.internal.cobol.types;

import static com.github.cobolio.internal.cobol.PrimitiveConstants.THREE;
import static com.github.cobolio.internal.cobol.PrimitiveConstants.TWO;

import com.github.cobolio.internal.cobol.ByteUtils;
import com.github.cobolio.types.TypeConversionException;
import com.github.cobolio.types.TypeHandler;

/**
 * <h2><span id="Double-precision_64-bit">Double-precision 64-bit</span></h2>
 * <p>
 * The <a title="Double precision">double-precision</a> floating-point format
 * (called "long" by IBM) is the same as the "short" format except that the
 * fraction field is wider and the double-precision number is stored in a double
 * word (8 bytes):
 * </p>
 * <dl>
 * <dd>
 * <table>
 * 
 * <tbody>
 * <tr style="text-align: center">
 * <td style="width: 20px">1</td>
 * <td style="width: 20px"></td>
 * <td style="width: 50px">7</td>
 * <td style="width: 20px"></td>
 * <td style="width: 20px"></td>
 * <td style="width: 320px">56</td>
 * <td style="width: 20px"></td>
 * <td style="text-align: left"><i>(width in bits)</i></td>
 * </tr>
 * <tr style="text-align: center">
 * <td colspan="1" style="text-align: center; background-color: #FC9">S</td>
 * <td colspan="3" style="text-align: center; background-color: #99F">Exp</td>
 * <td colspan="3" style="text-align: center; background-color: #9F9">Fraction
 * </td>
 * <td colspan="1" style="text-align: center; background-color:
 * #FFF">&nbsp;</td>
 * </tr>
 * <tr style="text-align: center">
 * <td>63</td>
 * <td>62</td>
 * <td>...</td>
 * <td>56</td>
 * <td>55</td>
 * <td>...</td>
 * <td>0</td>
 * <td align="left"><i>(bit index)</i>*</td>
 * </tr>
 * <tr>
 * <td colspan="8"><i>* IBM documentation numbers the bits from left to right,
 * so that the most significant bit is designated as bit number 0.</i></td>
 * </tr>
 * </tbody>
 * </table>
 * </dd>
 * </dl>
 * <p>
 * The exponent for this format covers only about a quarter of the range as the
 * corresponding IEEE binary format.
 * </p>
 * <p>
 * 14 hexadecimal digits of precision is roughly equivalent to 17 decimal
 * digits. A conversion of double precision hexadecimal float to decimal string
 * would require at least 18 significant digits in order to convert back to the
 * same hexadecimal float value.
 * </p>
 * <h2><span id="Extended-precision_128-bit">Extended-precision 128-bit</span>
 * </h2>
 * <p>
 * Called extended-precision by IBM, a
 * <a title="Quadruple precision floating-point format">quadruple-precision</a>
 * floating-point format was added to the System/370 series and was available on
 * some S/360 models (S/360-85, -195, and others by special request or simulated
 * by OS software). The extended-precision fraction field is wider, and the
 * extended-precision number is stored as two double words (16 bytes):
 * </p>
 * <dl>
 * <dd>
 * <table>
 * 
 * <tbody>
 * <tr>
 * <td colspan="8"><b>High-order part</b></td>
 * </tr>
 * <tr style="text-align: center">
 * <td style="width: 20px">1</td>
 * <td style="width: 20px"></td>
 * <td style="width: 50px">7</td>
 * <td style="width: 20px"></td>
 * <td style="width: 20px"></td>
 * <td style="width: 320px">56</td>
 * <td style="width: 20px"></td>
 * <td style="text-align: left"><i>(width in bits)</i></td>
 * </tr>
 * <tr style="text-align: center">
 * <td colspan="1" style="text-align: center; background-color: #FC9">S</td>
 * <td colspan="3" style="text-align: center; background-color: #99F">Exp</td>
 * <td colspan="3" style="text-align: center; background-color: #9F9">Fraction
 * (high-order 14 digits)</td>
 * <td colspan="1" style="text-align: center; background-color:
 * #FFF">&nbsp;</td>
 * </tr>
 * <tr style="text-align: center">
 * <td>127</td>
 * <td>126</td>
 * <td>...</td>
 * <td>120</td>
 * <td>119</td>
 * <td>...</td>
 * <td>64</td>
 * <td align="left"><i>(bit index)</i>*</td>
 * </tr>
 * <tr>
 * <td colspan="8"><b>Low-order part</b></td>
 * </tr>
 * <tr style="text-align: center">
 * <td colspan="4">8</td>
 * <td></td>
 * <td>56</td>
 * <td></td>
 * <td style="text-align: left"><i>(width in bits)</i></td>
 * </tr>
 * <tr style="text-align: center">
 * <td colspan="4" style="text-align: center; background-color:
 * #CCC">Unused</td>
 * <td colspan="3" style="text-align: center; background-color: #9F9">Fraction
 * (low-order 14 digits)</td>
 * <td colspan="1" style="text-align: center; background-color:
 * #FFF">&nbsp;</td>
 * </tr>
 * <tr style="text-align: center">
 * <td>63</td>
 * <td colspan="2">...</td>
 * <td>56</td>
 * <td>55</td>
 * <td>...</td>
 * <td>0</td>
 * <td align="left"><i>(bit index)</i>*</td>
 * </tr>
 * <tr>
 * <td colspan="8"><i>* IBM documentation numbers the bits from left to right,
 * so that the most significant bit is designated as bit number 0.</i></td>
 * </tr>
 * </tbody>
 * </table>
 * </dd>
 * </dl>
 * <p>
 * 28 hexadecimal digits of precision is roughly equivalent to 32 decimal
 * digits. A conversion of extended precision hexadecimal float to decimal
 * string would require at least 35 significant digits in order to convert back
 * to the same hexadecimal float value.
 * </p>
 * 
 * @author Andrew
 *
 */
public class IbmDoubleFieldHandler implements TypeHandler {
	public static final int BYTE_LENGTH = 8;
	/**
	 * Mask for the sign of a 64 bit binary value.
	 */
	public static final long BIT64_SIGN_MASK = 			0x8000000000000000L;
	/**
	 * Mask for all but the sign of a 64 bit binary value. This would be both the
	 * mantissa and the exponent.
	 */
	public static final long NON_BIT64_SIGN_MASK = 0x7FFFFFFFFFFFFFFFL;
	/**
	 * The mask for the mantissa of a 64 bit hexadecimal floating point value.
	 */
	public static final long HFP_MANTISSA_MASK = 		0x00FFFFFFFFFFFFFFL;
	/**
	 * The mask for the mantissa of a 64 bit binary floating point value.
	 */
	public static final long BFP_MANTISSA_MASK = 		0x000FFFFFFFFFFFFFL;
	/**
	 * The implied high order bit of the mantissa of a 64 bit binary floating point
	 * value.
	 */
	public static final long BFP_MANTISSA_IMPLIED_HOB = 0x0010000000000000L;
	/**
	 * The mask for the high order mantissa byte of a binary floating point value.
	 */
	public static final long BFP_MANTISSA_HON = 		0x00F0000000000000L;
	/**
	 * The mask for the exponent portion of a 64 bit hexadecimal floating point
	 * value.
	 */
	public static final long HFP_EXPONENT_MASK = 		0x7F00000000000000L;
	/**
	 * The mask for the exponent portion of a 64 bit binary floating point value.
	 */
	public static final long BFP_EXPONENT_MASK = 		0x7FF0000000000000L;
	public static final long BFP_EXPONENT_MASK_SHIFTED = 0x00000000000007FFL;
	/**
	 * The number of bits in the mantissa of a 64 bit hexadecimal floating point
	 * value.
	 */
	public static final int HFP_MANTISSA_LENGTH = 56;
	/**
	 * The number of bits in the mantissa of a 64 bit binary floating point value.
	 */
	public static final int BFP_MANTISSA_LENGTH = 52;
	/**
	 * The standard exponent bias of a 64 bit hexadecimal floating point value.
	 */
	public static final int HFP_BIAS = 63;
	/**
	 * The highest permissible value that a hexadecimal floating point value can
	 * have.
	 */
	public static final int HFP_MAX_EXPONENT = 127;
	/**
	 * The standard exponent bias of a 64 bit binary floating point value.
	 */
	public static final int BFP_BIAS = 1023;
	/**
	 * The highest permissible value that a binary floating point value can have.
	 */
	public static final int BFP_INFINITY_EXPONENT = 2047;
	/**
	 * A floating point value with this is interpreted as being positive/negative
	 * infinity, depending on the sign. This happens when the value to be stored was
	 * larger than the largest permissible value.
	 */
	public static final int HFP_INFINITY_EXPONENT = 127;

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

	public byte[] encodeDouble(double value) {
		long binFpBits = Double.doubleToLongBits(value);
		long hexFpBits = this.binToHex(binFpBits);
		return ByteUtils.longToBytes(hexFpBits);// TODO Test
	}

	/**
	 * @param hexFpBits
	 * @return
	 */
	private long hexToBin(long hexFpBits) {
		long sign = hexFpBits & BIT64_SIGN_MASK;
		if ((hexFpBits & NON_BIT64_SIGN_MASK) == 0L) {
			return sign;
		} else {
			long mantissa = hexFpBits & HFP_MANTISSA_MASK;
			long hexponent = (hexFpBits & HFP_EXPONENT_MASK) >> HFP_MANTISSA_LENGTH;
			long bexponent = ((hexponent - HFP_BIAS) << TWO) + BFP_BIAS - 1L;
			for (mantissa >>= THREE; (mantissa & BFP_MANTISSA_IMPLIED_HOB) == 0L; mantissa <<= 1) {
				--bexponent;
			}
			if (bexponent <= 0L) {
				if (bexponent < -HFP_MANTISSA_LENGTH) {
					mantissa = 0L;
				} else {
					mantissa >>= (int) (-bexponent + 1L);
				}
				bexponent = 0L;
			} else if (bexponent >= BFP_INFINITY_EXPONENT) {
				mantissa = 0L;
				bexponent = BFP_INFINITY_EXPONENT;
			} else {
				mantissa &= BFP_MANTISSA_MASK;
			}
			return sign | (bexponent << BFP_MANTISSA_LENGTH) | mantissa;
		}
	}

	private long binToHex(long binFpBits) {
		long sign = binFpBits & BIT64_SIGN_MASK;
		/*
		 * long mantissa = binFpBits & BFP_MANTISSA_MASK; long bexponent = binFpBits &
		 * BFP_EXPONENT_MASK; bexponent >>= BFP_MANTISSA_LENGTH; if(bexponent >
		 * HFP_INFINITY_EXPONENT) { bexponent = HFP_INFINITY_EXPONENT; } bexponent <<=
		 * HFP_MANTISSA_LENGTH; return sign | bexponent | mantissa;
		 */
		
		if ((binFpBits & NON_BIT64_SIGN_MASK) == 0L) {
			return sign;
		} else {
			long mantissa = binFpBits & BFP_MANTISSA_MASK;
			long bexponent = (binFpBits & BFP_EXPONENT_MASK) >> BFP_MANTISSA_LENGTH;
			long hexponent = ((bexponent + 1L - BFP_BIAS) >> TWO) + HFP_BIAS;
			// TODO continue writing logic
			for (mantissa >>= THREE; (mantissa & BFP_MANTISSA_IMPLIED_HOB) == 0L; mantissa <<= 1) {
				--hexponent;
			}
			if (hexponent <= 0L) {
				if (hexponent < -BFP_MANTISSA_LENGTH) {
					mantissa = 0L;
				} else {
					mantissa >>= (int) (-hexponent + 1L);
				}
				hexponent = 0L;
			} else if (hexponent >= HFP_INFINITY_EXPONENT) {
				mantissa = 0L;
				hexponent = HFP_INFINITY_EXPONENT;
			} else {
				mantissa &= HFP_MANTISSA_MASK;
			}
			return sign | ((hexponent << HFP_MANTISSA_LENGTH) & HFP_EXPONENT_MASK) | (mantissa & HFP_MANTISSA_MASK);
			// TODO incomplete. Also needs testing after
		}

	}
	
	private void binToHexSlow(long binFpBits) {
		long sign = binFpBits & BIT64_SIGN_MASK;
		/*
		 * if((binFpBits & NON_BIT64_SIGN_MASK) == 0) {//int s = ((bits >> 63) == 0) ? 1
		 * : -1; return sign; } else {
		 */
			long bexponent = binFpBits & BFP_EXPONENT_MASK >> BFP_MANTISSA_LENGTH;//this may be replaceable with int bexponent = (int)((binFpBits >> 52) & 0x7FFL)
			long mantissa = binFpBits & BFP_MANTISSA_MASK;//this may be replaceable with mantissa = (bexponent == 0) ? (binFpBits & 0xFFFFFFFFFFFFFL) << 1 : (binFpBits & 0xFFFFFFFFFFFFFL) | 0x10000000000000L
			long hexponent = bexponent & HFP_EXPONENT_MASK;
			long hmantissa = mantissa & HFP_MANTISSA_MASK;
			double value = sign * (long) (hmantissa * (StrictMath.pow(2.0, (hexponent))));//s·m·2^e-1075
			System.out.println(value);//TODO Complete this method
			
			
		//}
	}

	public boolean equals(Double wrap, double prim) {
		if (wrap == null) {
			return false;
		} else {
			return wrap == prim;
		}
	}

	@Override
	public Object parse(byte[] text) throws TypeConversionException {
		try {
			return this.getDouble(text, this.getOffset());
		} catch (Exception e) {
			throw new TypeConversionException("Data could not be transformed into an Integer instance.", e);
		}
	}

	@Override
	public byte[] format(Object value) {
		return this.encodeDouble((Double) value);// TODO will this require artificial offsetting
	}

	@Override
	public Class<?> getType() {
		return Double.TYPE;
	}
}
