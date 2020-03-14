package com.microfocus.cobol.lang.internal;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public class CobolOccurs {
	private int index1;
	private int index2;
	private int index3;
	private int index4;
	private int index5;
	private int index6;
	private int index7;
	private int index8;
	private int index9;
	private int index10;
	private int index11;
	private int index12;
	private int index13;
	private int index14;
	private int index15;
	private int index16;
	private int dimensions;
	
	/**
	 * Guess this works?
	 * @param is
	 */
	public CobolOccurs(int... is) {
		if (is != null && is.length < 17 && is.length > 0) {
			this.dimensions = is.length;
			for (int i = 1; i < is.length || i < 17; i++) {
				for (Field f : this.getClass().getFields()) {
					if (f.getName().equals("index" + i)) {
						try {
							f.set(this, is[i]);
						} catch (Exception e) {
							//Shouldn't happen
							e = null;
						}
					}
				}
			}
		}
	}

	public CobolOccurs(int var1) {
		this.init(1, var1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public CobolOccurs(int var1, int var2) {
		this.init(2, var1, var2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public CobolOccurs(int var1, int var2, int var3) {
		this.init(3, var1, var2, var3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public CobolOccurs(int var1, int var2, int var3, int var4) {
		this.init(4, var1, var2, var3, var4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public CobolOccurs(int var1, int var2, int var3, int var4, int var5) {
		this.init(5, var1, var2, var3, var4, var5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public CobolOccurs(int var1, int var2, int var3, int var4, int var5, int var6) {
		this.init(6, var1, var2, var3, var4, var5, var6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public CobolOccurs(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
		this.init(7, var1, var2, var3, var4, var5, var6, var7, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public CobolOccurs(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
		this.init(8, var1, var2, var3, var4, var5, var6, var7, var8, 0, 0, 0, 0, 0, 0, 0, 0);
	}

	public CobolOccurs(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
		this.init(9, var1, var2, var3, var4, var5, var6, var7, var8, var9, 0, 0, 0, 0, 0, 0, 0);
	}

	public CobolOccurs(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9,
			int var10) {
		this.init(10, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, 0, 0, 0, 0, 0, 0);
	}

	public CobolOccurs(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9,
			int var10, int var11) {
		this.init(11, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, 0, 0, 0, 0, 0);
	}

	public CobolOccurs(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9,
			int var10, int var11, int var12) {
		this.init(12, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, 0, 0, 0, 0);
	}

	public CobolOccurs(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9,
			int var10, int var11, int var12, int var13) {
		this.init(13, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, 0, 0, 0);
	}

	public CobolOccurs(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9,
			int var10, int var11, int var12, int var13, int var14) {
		this.init(14, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, 0, 0);
	}

	public CobolOccurs(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9,
			int var10, int var11, int var12, int var13, int var14, int var15) {
		this.init(15, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15,
				0);
	}

	public CobolOccurs(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9,
			int var10, int var11, int var12, int var13, int var14, int var15, int var16) {
		this.init(16, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15,
				var16);
	}

	private void init(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9,
			int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17) {
		this.dimensions = var1;
		this.index1 = var2;
		this.index2 = var3;
		this.index3 = var4;
		this.index4 = var5;
		this.index5 = var6;
		this.index6 = var7;
		this.index7 = var8;
		this.index8 = var9;
		this.index9 = var10;
		this.index10 = var11;
		this.index11 = var12;
		this.index12 = var13;
		this.index13 = var14;
		this.index14 = var15;
		this.index15 = var16;
		this.index16 = var17;
	}
}
