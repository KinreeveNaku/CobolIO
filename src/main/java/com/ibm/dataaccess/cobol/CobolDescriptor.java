package com.ibm.dataaccess.cobol;

@SuppressWarnings({ "unused", "squid:S109" })
public class CobolDescriptor {
	/**
	 * 
	 */
	private static final int LOW_NIBBLE_MASK = 15;
	/**
	 * 
	 */
	private static final int MAX_UNSIGNED_BYTE = 256;
	private int id;
	private int type;
	private byte[] descriptor;
	private static final byte DESC_COMPX = 10;
	private static final byte DESC_COMP5X = 11;
	private static final byte DESC_COMP1 = -128;
	private static final byte DESC_COMP2 = -127;
	private static final byte DESC_NUMERICEDITEDBWZ = -112;
	private static final byte DESC_NUMERICEDITEDNOBWZ = -111;
	private static final byte DESC_ALPHABETICEDITED = -110;
	private static final byte DESC_ALPHANUMERICEDITED = -109;
	private static byte[] baseDesc = new byte[] { 18, -112, 6, 2, 14, LOW_NIBBLE_MASK, -128, -108, -104, -109 };

	public CobolDescriptor(int var1, int var2, byte[] var3) {
		this.id = var1;
		this.type = var2;
		this.descriptor = var3;
	}
	
	public static void main(String[] args) {
		CobolDescriptor desc = new CobolDescriptor(6, -128, new byte[] {-128, 0, 0, 0, 0, 0});
		System.out.println(desc.isNumericEdited());
		System.out.println(desc.isComp1());
		System.out.println(desc.isComp2());
		System.out.println(desc.isAlphanumericEdited());
	}
	
	protected int getType() {
		return this.type;
	}

	protected int getId() {
		return this.id;
	}

	protected byte[] getDescriptor() {
		return this.descriptor;
	}

	protected boolean isAlphanumericEdited() {
		return this.descriptor[0] == DESC_ALPHANUMERICEDITED || this.descriptor[0] == DESC_ALPHABETICEDITED;
	}

	protected boolean isNumericEdited() {
		return this.descriptor[0] == DESC_NUMERICEDITEDBWZ || this.descriptor[0] == DESC_NUMERICEDITEDNOBWZ;
	}

	protected boolean isComp1() {
		return this.descriptor[0] == DESC_COMP1;
	}

	protected boolean isComp2() {
		return this.descriptor[0] == DESC_COMP2;
	}

	protected static CobolDescriptor makeAlphanumericDescriptor(int var0, int var1, int var2) {
		byte var4 = baseDesc[8];
		if ((var2 & 1) == 1) {
			var4 = (byte) (var4 + 48);
		} else if ((var2 & 2) == 2) {
			var4 = (byte) (var4 + 32);
		}

		byte[] var6;
		if (var1 > 32767) {
			var6 = new byte[5];
			var6[0] = var4;
			int var5 = (var1 - 1) / 65536;
			var6[1] = (byte) (var5 / MAX_UNSIGNED_BYTE + 128);
			var6[2] = (byte) (var5 % MAX_UNSIGNED_BYTE);
			var5 = (var1 - 1) % 65536;
			var6[3] = (byte) (var5 / MAX_UNSIGNED_BYTE);
			var6[4] = (byte) (var5 % MAX_UNSIGNED_BYTE);
		} else {
			var6 = new byte[] { var4, (byte) ((var1 - 1) / MAX_UNSIGNED_BYTE),
					(byte) ((var1 - 1) % MAX_UNSIGNED_BYTE) };
		}

		return new CobolDescriptor(var0, 8, var6);
	}

	protected static CobolDescriptor makeNumericDescriptor(int var0, int var1, int var2, int var3, int var4, int var5,
			int var6) {
		byte var7 = baseDesc[var2];
		byte[] var8;
		switch (var2) {
		case 0:
			if ((var3 & 1) == 1) {
				var7 = (byte) (var7 + 32);
			}

			switch (var4) {
			case 4:
				--var7;
				break;
			case 8:
				var7 = (byte) (var7 + 7);
				break;
			case 16:
				var7 = (byte) (var7 - 2);
				break;
			case 32:
				var7 = (byte) (var7 + 6);
			}
		case 1:
		default:
			break;
		case 2:
		case 3:
			if (var4 == 1 && var5 == 0) {
				if (var2 == 2) {
					var7 = 11;
				} else {
					var7 = 10;
				}
				break;
			} else if ((var3 & 8) == 8) {
				++var7;
			}
		case 4:
		case 5:
			if (var4 != 1) {
				var7 = (byte) (var7 - 2);
			}
			break;
		case 6:
			if (var1 == 8) {
				var7 = -127;
			}

			var8 = new byte[] { var7 };
			return new CobolDescriptor(var0, var2, var8);
		}

		var8 = new byte[] { var7, 0, 0 };
		if (var7 != 11 && var7 != 10) {
			if ((var2 == 2 || var2 == 3) && (var3 & 4) == 4) {
				var8[1] = (byte) (var5 + 63);
			} else {
				var8[1] = (byte) (var5 - 1);
			}
		} else {
			var8[1] = (byte) (var1 - 129);
		}

		var8[2] = (byte) (64 - var6);
		return new CobolDescriptor(var0, var2, var8);
	}

	protected static CobolDescriptor makeEditedDescriptor(int var0, int var1, int var2, int var3, String var4) {
		byte var5 = baseDesc[var2];
		byte[] var6 = new byte[32];
		byte var7 = 0;
		int var8 = -1;
		int var11 = 0;
		switch (var2) {
		case 1:
		case 7:
			if ((var3 & 16) == 16) {
				++var5;
			}
		case 9:
			if ((var3 & 1) == 1) {
				var5 = (byte) (var5 + 32);
			}
		default:
			char var9 = ' ';
			int var12 = 0;

			int var10001;
			for (; var12 < var4.length(); ++var12) {
				char var10 = var4.charAt(var12);
				if (var10 == var9) {
					++var11;
				} else if (var10 == '(') {
					int var13 = var4.indexOf(41, var12);
					if (var13 > 0) {
						var11 += Integer.parseInt(var4.substring(var12 + 1, var13)) - 1;
						var12 = var13;
					}
				} else {
					if (var8 >= 0) {
						while (var11 >= 16) {
							var10001 = var8++;
							var6[var10001] = (byte) (var6[var10001] + LOW_NIBBLE_MASK);
							var6[var8] = var7;
							var11 -= 16;
						}

						var6[var8] = (byte) (var6[var8] + var11);
					}

					++var8;
					var9 = var10;
					switch (var10) {
					case '$':
					case '£':
						var7 = 80;
						break;
					case '*':
						var7 = -48;
						break;
					case '+':
						var7 = 32;
						break;
					case ',':
						var7 = 112;
						break;
					case '-':
					case 'A':
						var7 = 16;
						break;
					case '.':
					case 'E':
						var7 = -32;
						break;
					case '/':
						var7 = -112;
						break;
					case '0':
						var7 = 48;
						break;
					case '9':
						var7 = 64;
						break;
					case 'B':
						var7 = 0;
						break;
					case 'C':
						var7 = -128;
						++var12;
						break;
					case 'D':
						var7 = -80;
						++var12;
						break;
					case 'P':
						var7 = -64;
						break;
					case 'V':
						var7 = -96;
						break;
					case 'X':
						var7 = -128;
						break;
					case 'Z':
						var7 = 96;
					}

					var11 = 0;
					var6[var8] = var7;
				}
			}

			while (var11 >= 16) {
				var10001 = var8++;
				var6[var10001] = (byte) (var6[var10001] + LOW_NIBBLE_MASK);
				var6[var8] = var7;
				var11 -= 16;
			}

			var6[var8] = (byte) (var6[var8] + var11);
			byte[] var14 = new byte[var8 + 4];
			System.arraycopy(var6, 0, var14, 3, var8 + 1);
			var14[0] = var5;
			var14[1] = (byte) (var8 / MAX_UNSIGNED_BYTE);
			var14[2] = (byte) (var8 % MAX_UNSIGNED_BYTE);
			return new CobolDescriptor(var0, var2, var14);
		}
	}
}
