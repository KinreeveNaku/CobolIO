package com.microfocus.cobol.lang.internal;
public abstract class CobolField extends CobolBytes {
	public static final int T_DISPLAY = 0;
	public static final int T_EDITED_DISPLAY = 1;
	public static final int T_BINARY = 2;
	public static final int T_PORTABLE_BINARY = 3;
	public static final int T_BCD = 4;
	public static final int T_UBCD = 5;
	public static final int T_FLOAT = 6;
	public static final int T_EDITED_FLOAT = 7;
	public static final int T_ALPHANUMERIC = 8;
	public static final int T_EDITED_ALPHANUMERIC = 9;
	public static final int S_UNSIGNED = 1;
	public static final int S_SIGNED_DEFAULT = 2;
	public static final int S_LEADING_INCLUDED = 4;
	public static final int S_LEADING_SEPARATE = 8;
	public static final int S_TRAILING_INCLUDED = 16;
	public static final int S_TRAILING_SEPARATE = 32;
	public static final int F_NONE = 0;
	public static final int F_EBCDIC = 1;
	public static final int F_DBCS = 2;
	public static final int F_IBMCOMP = 4;
	public static final int F_TRUNC = 8;
	public static final int F_BWZ = 16;
	protected CobolDescriptor descriptor;
	protected int occurs;

	public CobolField(CobolRecord var1, int var2, int var3, int var4, CobolDescriptor var5) {
		super(var1, var2, var3);
		this.occurs = var4;
		this.descriptor = var5;
	}

	public CobolField(CobolRecord var1, int var2, int var3, CobolDescriptor var4) {
		this(var1, var2, 0, var3, var4);
	}

	@Override
	public abstract String toString();

	public abstract void setString(String var1) throws CobolRecordException;
}