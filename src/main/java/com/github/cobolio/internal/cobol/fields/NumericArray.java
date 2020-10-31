/**
 * 
 */
package com.github.cobolio.internal.cobol.fields;

/**
 * @author Andrew
 *
 */
public interface NumericArray {

	int[] asIntegers(byte[] bytes);

	long[] asLongs(byte[] bytes);

	float[] asFloats(byte[] bytes);

	double[] asDoubles(byte[] bytes);
	
	default boolean checkLength(byte[] bytes, byte byteCount) {
		return bytes.length % byteCount == 0;
	}
}
