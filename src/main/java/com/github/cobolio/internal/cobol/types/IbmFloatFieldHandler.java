/**
 * 
 */
package com.github.cobolio.internal.cobol.types;

import static com.github.mfds2j.data.cobol.cobol.PrimitiveConstants.*;

import com.github.cobolio.internal.cobol.ByteUtils;
import com.github.cobolio.types.TypeConversionException;
import com.github.cobolio.types.TypeHandler;

/**
 * <p>
 * A <a title="Single precision">single-precision</a> binary floating-point
 * number (called "short" by IBM) is stored in a 32-bit word:
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
 * <td style="width: 210px">24</td>
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
 * <td>31</td>
 * <td>30</td>
 * <td>...</td>
 * <td>24</td>
 * <td>23</td>
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
 * In this format the initial bit is not suppressed, and the radix point is set
 * to the left of the significand (fraction in IBM documentation and the
 * figures) in increments of 4 bits.
 * </p>
 * <p>
 * Since the base is 16, the exponent in this form is about twice as large as
 * the equivalent in IEEE 754, in order to have similar exponent range in
 * binary, 9 exponent bits would be required.
 * </p>
 * <h3><span class="mw-headline" id="Example">Example</span><span class=
 * "mw-editsection"></h3>
 * <p>
 * Consider encoding the value −118.625 as an IBM single-precision
 * floating-point value.
 * </p>
 * <p>
 * The value is negative, so the sign bit is 1.
 * </p>
 * <p>
 * The value 118.625<sub>10</sub> in binary is 1110110.101<sub>2</sub>. This
 * value is normalized by moving the radix point left four bits (one hexadecimal
 * digit) at a time until the leftmost digit is zero, yielding
 * 0.01110110101<sub>2</sub>. The remaining rightmost digits are padded with
 * zeros, yielding a 24-bit fraction of
 * .0111&nbsp;0110&nbsp;1010&nbsp;0000&nbsp;0000&nbsp;0000<sub>2</sub>.
 * </p>
 * <p>
 * The normalized value moved the radix point two digits to the left, yielding a
 * multiplier and exponent of 16<sup>+2</sup>. A bias of +64 is added to the
 * exponent (+2), yielding +66, which is 100&nbsp;0010<sub>2</sub>.
 * </p>
 * <p>
 * Combining the sign, exponent plus bias, and normalized fraction produces this
 * encoding:
 * </p>
 * <dl>
 * <dd>
 * <table>
 * 
 * <tbody>
 * <tr style="text-align: center">
 * <td style="width: 20px; text-align: center; background-color: #FC9">S</td>
 * <td style="width: 90px; text-align: center; background-color: #99F">Exp</td>
 * <td style="width: 250px; text-align: center; background-color: #9F9">Fraction
 * </td>
 * <td style="text-align: center; background-color: #FFF">&nbsp;</td>
 * </tr>
 * <tr style="text-align: center">
 * <td style="text-align: center; background-color: #FEC"><tt>1</tt></td>
 * <td style="text-align: center; background-color: #CCF"><tt>100
 * 							0010</tt></td>
 * <td style="text-align: center; background-color: #CFC"><tt>0111
 * 							0110 1010 0000 0000 0000</tt></td>
 * <td style="text-align: center; background-color: #FFF">&nbsp;</td>
 * </tr>
 * </tbody>
 * </table>
 * </dd>
 * </dl>
 * <p>
 * In other words, the number represented is −0.76A000<sub>16</sub> × 16<sup>66
 * − 64</sup> = −0.4633789… × 16<sup>+2</sup> = −118.625
 * </p>
 * <h3><span class="mw-headline" id="Largest_representable_number">Largest
 * representable number</span></h3>
 * <dl>
 * <dd>
 * <table>
 * 
 * <tbody>
 * <tr style="text-align: center">
 * <td style="width: 20px; text-align: center; background-color: #FC9">S</td>
 * <td style="width: 90px; text-align: center; background-color: #99F">Exp</td>
 * <td style="width: 250px; text-align: center; background-color: #9F9">Fraction
 * </td>
 * <td style="text-align: center; background-color: #FFF">&nbsp;</td>
 * </tr>
 * <tr style="text-align: center">
 * <td style="text-align: center; background-color: #FEC"><tt>0</tt></td>
 * <td style="text-align: center; background-color: #CCF"><tt>111
 * 							1111</tt></td>
 * <td style="text-align: center; background-color: #CFC"><tt>1111
 * 							1111 1111 1111 1111 1111</tt></td>
 * <td style="text-align: center; background-color: #FFF">&nbsp;</td>
 * </tr>
 * </tbody>
 * </table>
 * </dd>
 * </dl>
 * <p>
 * The number represented is +0.FFFFFF<sub>16</sub> × 16<sup>127 − 64</sup> = (1
 * − 16<sup>−6</sup>) × 16<sup>63</sup> ≈ +7.2370051 × 10<sup>75</sup>
 * </p>
 * <h3>
 * <span class="mw-headline" id="Smallest_positive_normalized_number">Smallest
 * positive normalized number</span></h3>
 * <dl>
 * <dd>
 * <table>
 * 
 * <tbody>
 * <tr style="text-align: center">
 * <td style="width: 20px; text-align: center; background-color: #FC9">S</td>
 * <td style="width: 90px; text-align: center; background-color: #99F">Exp</td>
 * <td style="width: 250px; text-align: center; background-color: #9F9">Fraction
 * </td>
 * <td style="text-align: center; background-color: #FFF">&nbsp;</td>
 * </tr>
 * <tr style="text-align: center">
 * <td style="text-align: center; background-color: #FEC"><tt>0</tt></td>
 * <td style="text-align: center; background-color: #CCF"><tt>000
 * 							0000</tt></td>
 * <td style="text-align: center; background-color: #CFC"><tt>0001
 * 							0000 0000 0000 0000 0000</tt></td>
 * <td style="text-align: center; background-color: #FFF">&nbsp;</td>
 * </tr>
 * </tbody>
 * </table>
 * </dd>
 * </dl>
 * <p>
 * The number represented is +0.1<sub>16</sub> × 16<sup>0 − 64</sup> =
 * 16<sup>−1</sup> × 16<sup>−64</sup> ≈ +5.397605 × 10<sup>−79</sup>.
 * </p>
 * <h3><span class="mw-headline" id="Zero">Zero</span></h3>
 * <dl>
 * <dd>
 * <table>
 * 
 * <tbody>
 * <tr style="text-align: center">
 * <td style="width: 20px; text-align: center; background-color: #FC9">S</td>
 * <td style="width: 90px; text-align: center; background-color: #99F">Exp</td>
 * <td style="width: 250px; text-align: center; background-color: #9F9">Fraction
 * </td>
 * <td style="text-align: center; background-color: #FFF">&nbsp;</td>
 * </tr>
 * <tr style="text-align: center">
 * <td style="text-align: center; background-color: #FEC"><tt>0</tt></td>
 * <td style="text-align: center; background-color: #CCF"><tt>000
 * 							0000</tt></td>
 * <td style="text-align: center; background-color: #CFC"><tt>0000
 * 							0000 0000 0000 0000 0000</tt></td>
 * <td style="text-align: center; background-color: #FFF">&nbsp;</td>
 * </tr>
 * </tbody>
 * </table>
 * </dd>
 * </dl>
 * <p>
 * Zero (0.0) is represented in normalized form as all zero bits, which is
 * arithmetically the value +0.0<sub>16</sub> × 16<sup>0 − 64</sup> = +0 ×
 * 16<sup>−64</sup> ≈ +0.000000 × 10<sup>−79</sup> = 0. Given a fraction of
 * all-bits zero, any combination of positive or negative sign bit and a
 * non-zero biased exponent will yield a value arithmetically equal to zero.
 * However, the normalized form generated for zero by CPU hardware is all-bits
 * zero. This is true for all three floating-point precision formats.
 * </p>
 * <h3><span class="mw-headline" id="Precision_issues">Precision issues</span>
 * </h3>
 * <p>
 * Since the base is 16, there can be up to three leading zero bits in the
 * binary significand. That means when the number is converted into binary,
 * there can be as few as 21 bits of precision. Because of the "wobbling
 * precision" effect, this can cause some calculations to be very inaccurate.
 * </p>
 * <p>
 * A good example of the inaccuracy is representation of decimal value 0.1. It
 * has no exact binary or hexadecimal representation. In hexadecimal format, it
 * is represented as 0.19999999...<sub>16</sub> or 0.0001 1001 1001 1001 1001
 * 1001 1001...<sub>2</sub>, that is:
 * </p>
 * <dl>
 * <dd>
 * <table>
 * 
 * <tbody>
 * <tr style="text-align: center">
 * <td style="width: 20px; text-align: center; background-color: #FC9">S</td>
 * <td style="width: 90px; text-align: center; background-color: #99F">Exp</td>
 * <td style="width: 250px; text-align: center; background-color: #9F9">Fraction
 * </td>
 * <td style="text-align: center; background-color: #FFF">&nbsp;</td>
 * </tr>
 * <tr style="text-align: center">
 * <td style="text-align: center; background-color: #FEC"><tt>0</tt></td>
 * <td style="text-align: center; background-color: #CCF"><tt>100
 * 							0000</tt></td>
 * <td style="text-align: center; background-color: #CFC"><tt>0001
 * 							1001 1001 1001 1001 1010</tt></td>
 * <td style="text-align: center; background-color: #FFF">&nbsp;</td>
 * </tr>
 * </tbody>
 * </table>
 * </dd>
 * </dl>
 * <p>
 * This has only 21 bits, whereas the binary version has 24 bits of precision.
 * </p>
 * <p>
 * Six hexadecimal digits of precision is roughly equivalent to six decimal
 * digits (i.e. (6 − 1) log<sub>10</sub>(16) ≈ 6.02). A conversion of single
 * precision hexadecimal float to decimal string would require at least 9
 * significant digits (i.e. 6 log<sub>10</sub>(16) + 1 ≈ 8.22) in order to
 * convert back to the same hexadecimal float value.
 * </p>
 * 
 * @author Andrew
 *
 */
public class IbmFloatFieldHandler implements TypeHandler {
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

	public IbmFloatFieldHandler(int offset) {
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

	public byte[] encodeFloat(Float value) {

	}

	private int hexToBin(int hexFpBits) {
		int sign = hexFpBits & BIT32_SIGN_MASK;
		if ((hexFpBits & NON_BIT32_SIGN_MASK) == 0) {
			return sign;//if the sign is 1, then the value is the max negative value. Otherwise its 0.
		} else {
			int mantissa = hexFpBits & HFP_MANTISSA_MASK;
			int hexponent = (hexFpBits & HFP_EXPONENT_MASK) >> HFP_MANTISSA_LENGTH;

			int exponent;
			for (exponent = ((hexponent - SIXTYFOUR) << TWO) + BFP_BIAS - 1; (mantissa & BFP_MANTISSA_IMPLIED_HOB) == 0; mantissa <<= 1) {
				--exponent;
			}
			if (exponent <= 0) {
				if (exponent < -HFP_MANTISSA_LENGTH) {
					mantissa = 0;
				} else {
					mantissa >>= (-exponent + 1);
				}

				exponent = 0;
			} else if (exponent >= BFP_INFINITY_EXPONENT) {
				mantissa = 0;
				exponent = BFP_INFINITY_EXPONENT;
			} else {
				mantissa &= BFP_MANTISSA_MASK;
			}
			return sign | (exponent << BFP_MANTISSA_LENGTH) | mantissa;
		}
	}
	
	private int binToHex(int binFpBits) {
		int sign = binFpBits & BIT32_SIGN_MASK;
		if() {
			
		} else {
			int mantissa = ;
			int hexponent = ;
		}
		return sign | (exponent << HFP_MANTISSA_LENGTH) | mantissa;
	}

	@Override
	public Object parse(byte[] text) throws TypeConversionException {
		return this.getFloat(text, this.getOffset());
	}

	@Override
	public byte[] format(Object value) {
		this.encodeFloat((float) value, b, this.offset);// TODO Does this need artificial offsetting
	}

	@Override
	public Class<?> getType() {
		return Float.TYPE;
	}
}
