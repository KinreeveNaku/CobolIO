/**
	*	
	*/
package com.ibm.dataaccess;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("unused")
public class DataAccessor {
	/**
	 * 
	 */
	private static final String SQUID_S109 = "squid:S109";
	private static final String SQUID_S3358 = "squid:S3358";
	private static final String SQUID_S3776 = "squid:S3776";
	// https://www.exploringbinary.com/number-of-digits-required-for-round-trip-conversions/
	private static final String NOT_SUPPORTED = " is not supported by this library.";

	private DataAccessor() {
		throw new IllegalArgumentException();
	}

	public static double format(int scale, int precision, double value) {
		double d = roundToDecimalPlaces(value, scale);
		if ((numDigits((long) d) + scale) > precision) {
			throw new NumberFormatException(
					"The precision must be greater than or equal to the total of the scale and the length of the whole number portion of the value.");
		} else {
			return d;
		}
	}

	public static float format(int scale, int precision, float value) {
		float f = roundToDecimalPlaces(value, scale);
		if ((numDigits((int) f) + scale) > precision) {
			throw new NumberFormatException(
					"The precision must be greater than or equal to the total of the scale and the length of the whole number portion of the value.");
		} else {
			return f;
		}
	}

	public static double roundToDecimalPlaces(double value, int scale) {
		scale = scale > Constants.MAX_DOUBLE_SCALE ? Constants.MAX_DOUBLE_SCALE : scale;
		double shift = Math.pow(Constants.BASE_TEN, scale);
		return Math.round(value * shift) / shift;
	}

	public static float roundToDecimalPlaces(float value, int scale) {
		scale = scale > Constants.MAX_FLOAT_SCALE ? Constants.MAX_FLOAT_SCALE : scale;
		return (float) roundToDecimalPlaces((double) value, scale);
	}

	/*
	 * NO_SONAR
	 */
	@SuppressWarnings({ SQUID_S3358, SQUID_S109, SQUID_S3776 })
	public static int numDigits(int value) {
		value = value == Integer.MIN_VALUE ? Integer.MAX_VALUE : Math.abs(value);
		return value >= 10000
				? (value >= 10000000 ? (value >= 100000000 ? (value >= 1000000000 ? 10 : 9) : 8)
						: (value >= 100000 ? (value >= 1000000 ? 7 : 6) : 5))
				: (value >= 100 ? (value >= 1000 ? 4 : 3) : (value >= 10 ? 2 : 1));
	}

	/*
	 * NO_SONAR
	 */
	@SuppressWarnings({ SQUID_S3358, SQUID_S109, SQUID_S3776 })
	public static int numDigits(long value) {
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
}
