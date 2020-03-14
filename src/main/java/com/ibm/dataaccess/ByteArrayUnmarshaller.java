package com.ibm.dataaccess;

public final class ByteArrayUnmarshaller {
	public static short readShort(byte[] byteArray, int offset, boolean bigEndian) {
		if (offset + 2 <= byteArray.length && offset >= 0) {
			return bigEndian ? readShort_(byteArray, offset, true) : readShort_(byteArray, offset, false);
		} else {
			throw new ArrayIndexOutOfBoundsException(
					"Array access index out of bounds. readShort is trying to access byteArray[" + offset
							+ "] and byteArray[" + (offset + 1) + "],  but valid indices are from 0 to "
							+ (byteArray.length - 1) + ".");
		}
	}

	private static short readShort_(byte[] byteArray, int offset, boolean bigEndian) {
		return bigEndian
				? (short) ((byteArray[offset] & 255) << 8 | byteArray[offset + 1] & 255)
				: (short) ((byteArray[offset + 1] & 255) << 8 | byteArray[offset] & 255);
	}

	public static short readShort(byte[] byteArray, int offset, boolean bigEndian, int numBytes, boolean signExtend) {
		if (offset + numBytes <= byteArray.length && offset >= 0) {
			if (numBytes >= 0 && numBytes <= 2) {
				if (bigEndian) {
					return signExtend
							? readShort_(byteArray, offset, true, numBytes, true)
							: readShort_(byteArray, offset, true, numBytes, false);
				} else {
					return signExtend
							? readShort_(byteArray, offset, false, numBytes, true)
							: readShort_(byteArray, offset, false, numBytes, false);
				}
			} else {
				throw new IllegalArgumentException("numBytes == " + numBytes);
			}
		} else {
			throw new ArrayIndexOutOfBoundsException(
					"Access offset must be positive or zero and last byte must be in range.");
		}
	}

	private static short readShort_(byte[] byteArray, int offset, boolean bigEndian, int numBytes, boolean signExtend) {
		switch (numBytes) {
			case 0 :
				return 0;
			case 1 :
				if (signExtend) {
					return (short) byteArray[offset];
				}

				return (short) (byteArray[offset] & 255);
			case 2 :
				if (bigEndian) {
					return (short) ((byteArray[offset] & 255) << 8 | byteArray[offset + 1] & 255);
				}

				return (short) ((byteArray[offset + 1] & 255) << 8 | byteArray[offset] & 255);
			default :
				return 0;
		}
	}

	public static int readInt(byte[] byteArray, int offset, boolean bigEndian) {
		if (offset + 4 <= byteArray.length && offset >= 0) {
			return bigEndian ? readInt_(byteArray, offset, true) : readInt_(byteArray, offset, false);
		} else {
			throw new ArrayIndexOutOfBoundsException(
					"Array access index out of bounds. readInt is trying to access byteArray[" + offset
							+ "] to byteArray[" + (offset + 3) + "],  but valid indices are from 0 to "
							+ (byteArray.length - 1) + ".");
		}
	}

	private static int readInt_(byte[] byteArray, int offset, boolean bigEndian) {
		return bigEndian
				? (byteArray[offset] & 255) << 24 | (byteArray[offset + 1] & 255) << 16
						| (byteArray[offset + 2] & 255) << 8 | byteArray[offset + 3] & 255
				: (byteArray[offset + 3] & 255) << 24 | (byteArray[offset + 2] & 255) << 16
						| (byteArray[offset + 1] & 255) << 8 | byteArray[offset] & 255;
	}

	public static int readInt(byte[] byteArray, int offset, boolean bigEndian, int numBytes, boolean signExtend) {
		if (offset + numBytes <= byteArray.length && offset >= 0) {
			if (numBytes >= 0 && numBytes <= 4) {
				if (bigEndian) {
					return signExtend
							? readInt_(byteArray, offset, true, numBytes, true)
							: readInt_(byteArray, offset, true, numBytes, false);
				} else {
					return signExtend
							? readInt_(byteArray, offset, false, numBytes, true)
							: readInt_(byteArray, offset, false, numBytes, false);
				}
			} else {
				throw new IllegalArgumentException("numBytes == " + numBytes);
			}
		} else {
			throw new ArrayIndexOutOfBoundsException(
					"Access offset must be positive or zero and last byte must be in range.");
		}
	}

	private static int readInt_(byte[] byteArray, int offset, boolean bigEndian, int numBytes, boolean signExtend) {
		int answer;
		switch (numBytes) {
			case 0 :
				return 0;
			case 1 :
				if (signExtend) {
					return byteArray[offset];
				}

				return byteArray[offset] & 255;
			case 2 :
				if (bigEndian) {
					answer = (byteArray[offset] & 255) << 8 | byteArray[offset + 1] & 255;
				} else {
					answer = (byteArray[offset + 1] & 255) << 8 | byteArray[offset] & 255;
				}

				if (signExtend) {
					return (short) answer;
				}

				return answer & '￿';
			case 3 :
				if (bigEndian) {
					answer = (byteArray[offset] & 255) << 16 | (byteArray[offset + 1] & 255) << 8
							| byteArray[offset + 2] & 255;
				} else {
					answer = (byteArray[offset + 2] & 255) << 16 | (byteArray[offset + 1] & 255) << 8
							| byteArray[offset] & 255;
				}

				if (signExtend && (answer & 8388608) != 0) {
					answer |= -16777216;
				}

				return answer;
			case 4 :
				if (bigEndian) {
					return (byteArray[offset] & 255) << 24 | (byteArray[offset + 1] & 255) << 16
							| (byteArray[offset + 2] & 255) << 8 | byteArray[offset + 3] & 255;
				}

				return (byteArray[offset + 3] & 255) << 24 | (byteArray[offset + 2] & 255) << 16
						| (byteArray[offset + 1] & 255) << 8 | byteArray[offset] & 255;
			default :
				return 0;
		}
	}

	public static long readLong(byte[] byteArray, int offset, boolean bigEndian) {
		if (offset + 8 <= byteArray.length && offset >= 0) {
			return bigEndian ? readLong_(byteArray, offset, true) : readLong_(byteArray, offset, false);
		} else {
			throw new ArrayIndexOutOfBoundsException(
					"Array access index out of bounds. readLong is trying to access byteArray[" + offset
							+ "] to byteArray[" + (offset + 7) + "],  but valid indices are from 0 to "
							+ (byteArray.length - 1) + ".");
		}
	}

	private static long readLong_(byte[] byteArray, int offset, boolean bigEndian) {
		return bigEndian
				? (long) (byteArray[offset] & 255) << 56 | (long) (byteArray[offset + 1] & 255) << 48
						| (long) (byteArray[offset + 2] & 255) << 40 | (long) (byteArray[offset + 3] & 255) << 32
						| (long) (byteArray[offset + 4] & 255) << 24 | (long) ((byteArray[offset + 5] & 255) << 16)
						| (long) ((byteArray[offset + 6] & 255) << 8) | (long) (byteArray[offset + 7] & 255)
				: (long) (byteArray[offset + 7] & 255) << 56 | (long) (byteArray[offset + 6] & 255) << 48
						| (long) (byteArray[offset + 5] & 255) << 40 | (long) (byteArray[offset + 4] & 255) << 32
						| (long) (byteArray[offset + 3] & 255) << 24 | (long) ((byteArray[offset + 2] & 255) << 16)
						| (long) ((byteArray[offset + 1] & 255) << 8) | (long) (byteArray[offset] & 255);
	}

	public static long readLong(byte[] byteArray, int offset, boolean bigEndian, int numBytes, boolean signExtend) {
		if (offset + numBytes <= byteArray.length && offset >= 0) {
			if (numBytes >= 0 && numBytes <= 8) {
				if (bigEndian) {
					return signExtend
							? readLong_(byteArray, offset, true, numBytes, true)
							: readLong_(byteArray, offset, true, numBytes, false);
				} else {
					return signExtend
							? readLong_(byteArray, offset, false, numBytes, true)
							: readLong_(byteArray, offset, false, numBytes, false);
				}
			} else {
				throw new IllegalArgumentException("numBytes == " + numBytes);
			}
		} else {
			throw new ArrayIndexOutOfBoundsException(
					"Access offset must be positive or zero and last byte must be in range.");
		}
	}

	private static long readLong_(byte[] byteArray, int offset, boolean bigEndian, int numBytes, boolean signExtend) {
		long answer;
		switch (numBytes) {
			case 0 :
				return 0L;
			case 1 :
				if (signExtend) {
					return (long) byteArray[offset];
				}

				return (long) byteArray[offset] & 255L;
			case 2 :
				if (bigEndian) {
					answer = (long) ((byteArray[offset] & 255) << 8 | byteArray[offset + 1] & 255);
				} else {
					answer = (long) ((byteArray[offset + 1] & 255) << 8 | byteArray[offset] & 255);
				}

				if (signExtend) {
					return (long) ((short) ((int) answer));
				}

				return answer & 65535L;
			case 3 :
				if (bigEndian) {
					answer = (long) ((byteArray[offset] & 255) << 16 | (byteArray[offset + 1] & 255) << 8
							| byteArray[offset + 2] & 255);
				} else {
					answer = (long) ((byteArray[offset + 2] & 255) << 16 | (byteArray[offset + 1] & 255) << 8
							| byteArray[offset] & 255);
				}

				if (signExtend) {
					answer = answer << 40 >> 40;
				}

				return answer;
			case 4 :
				if (bigEndian) {
					answer = ((long) byteArray[offset] & 255L) << 24 | (long) ((byteArray[offset + 1] & 255) << 16)
							| (long) ((byteArray[offset + 2] & 255) << 8) | (long) (byteArray[offset + 3] & 255);
				} else {
					answer = ((long) byteArray[offset + 3] & 255L) << 24 | (long) ((byteArray[offset + 2] & 255) << 16)
							| (long) ((byteArray[offset + 1] & 255) << 8) | (long) (byteArray[offset] & 255);
				}

				if (signExtend) {
					answer = answer << 32 >> 32;
				}

				return answer;
			case 5 :
				if (bigEndian) {
					answer = ((long) byteArray[offset] & 255L) << 32 | ((long) byteArray[offset + 1] & 255L) << 24
							| (long) ((byteArray[offset + 2] & 255) << 16) | (long) ((byteArray[offset + 3] & 255) << 8)
							| (long) (byteArray[offset + 4] & 255);
				} else {
					answer = ((long) byteArray[offset + 4] & 255L) << 32 | ((long) byteArray[offset + 3] & 255L) << 24
							| (long) ((byteArray[offset + 2] & 255) << 16) | (long) ((byteArray[offset + 1] & 255) << 8)
							| (long) (byteArray[offset] & 255);
				}

				if (signExtend) {
					answer = answer << 24 >> 24;
				}

				return answer;
			case 6 :
				if (bigEndian) {
					answer = ((long) byteArray[offset] & 255L) << 40 | ((long) byteArray[offset + 1] & 255L) << 32
							| ((long) byteArray[offset + 2] & 255L) << 24 | (long) ((byteArray[offset + 3] & 255) << 16)
							| (long) ((byteArray[offset + 4] & 255) << 8) | (long) (byteArray[offset + 5] & 255);
				} else {
					answer = ((long) byteArray[offset + 5] & 255L) << 40 | ((long) byteArray[offset + 4] & 255L) << 32
							| ((long) byteArray[offset + 3] & 255L) << 24 | (long) ((byteArray[offset + 2] & 255) << 16)
							| (long) ((byteArray[offset + 1] & 255) << 8) | (long) (byteArray[offset] & 255);
				}

				if (signExtend) {
					answer = answer << 16 >> 16;
				}

				return answer;
			case 7 :
				if (bigEndian) {
					answer = ((long) byteArray[offset] & 255L) << 48 | ((long) byteArray[offset + 1] & 255L) << 40
							| ((long) byteArray[offset + 2] & 255L) << 32 | ((long) byteArray[offset + 3] & 255L) << 24
							| (long) ((byteArray[offset + 4] & 255) << 16) | (long) ((byteArray[offset + 5] & 255) << 8)
							| (long) (byteArray[offset + 6] & 255);
				} else {
					answer = ((long) byteArray[offset + 6] & 255L) << 48 | ((long) byteArray[offset + 5] & 255L) << 40
							| ((long) byteArray[offset + 4] & 255L) << 32 | ((long) byteArray[offset + 3] & 255L) << 24
							| (long) ((byteArray[offset + 2] & 255) << 16) | (long) ((byteArray[offset + 1] & 255) << 8)
							| (long) (byteArray[offset] & 255);
				}

				if (signExtend) {
					answer = answer << 8 >> 8;
				}

				return answer;
			case 8 :
				if (bigEndian) {
					return ((long) byteArray[offset] & 255L) << 56 | ((long) byteArray[offset + 1] & 255L) << 48
							| ((long) byteArray[offset + 2] & 255L) << 40 | ((long) byteArray[offset + 3] & 255L) << 32
							| ((long) byteArray[offset + 4] & 255L) << 24 | (long) ((byteArray[offset + 5] & 255) << 16)
							| (long) ((byteArray[offset + 6] & 255) << 8) | (long) (byteArray[offset + 7] & 255);
				}

				return ((long) byteArray[offset + 7] & 255L) << 56 | ((long) byteArray[offset + 6] & 255L) << 48
						| ((long) byteArray[offset + 5] & 255L) << 40 | ((long) byteArray[offset + 4] & 255L) << 32
						| ((long) byteArray[offset + 3] & 255L) << 24 | (long) ((byteArray[offset + 2] & 255) << 16)
						| (long) ((byteArray[offset + 1] & 255) << 8) | (long) (byteArray[offset] & 255);
			default :
				return 0L;
		}
	}

	public static float readFloat(byte[] byteArray, int offset, boolean bigEndian) {
		if (offset + 4 <= byteArray.length && offset >= 0) {
			return bigEndian ? readFloat_(byteArray, offset, true) : readFloat_(byteArray, offset, false);
		} else {
			throw new ArrayIndexOutOfBoundsException(
					"Array access index out of bounds. readFloat is trying to access byteArray[" + offset
							+ "] to byteArray[" + (offset + 3) + "],  but valid indices are from 0 to "
							+ (byteArray.length - 1) + ".");
		}
	}

	private static float readFloat_(byte[] byteArray, int offset, boolean bigEndian) {
		return Float.intBitsToFloat(readInt(byteArray, offset, bigEndian));
	}

	public static double readDouble(byte[] byteArray, int offset, boolean bigEndian) {
		if (offset + 8 <= byteArray.length && offset >= 0) {
			return bigEndian ? readDouble_(byteArray, offset, true) : readDouble_(byteArray, offset, false);
		} else {
			throw new ArrayIndexOutOfBoundsException(
					"Array access index out of bounds. readDouble is trying to access byteArray[" + offset
							+ "] to byteArray[" + (offset + 7) + "],  but valid indices are from 0 to "
							+ (byteArray.length - 1) + ".");
		}
	}

	private static double readDouble_(byte[] byteArray, int offset, boolean bigEndian) {
		return Double.longBitsToDouble(readLong(byteArray, offset, bigEndian));
	}
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\git\RecordReader\RecordParser\dependencies\dataaccess\com\ibm\dataaccess\ByteArrayUnmarshaller.class
	Total time: 165 ms
	
	Decompiled with FernFlower.
*/