package com.microfocus.cobol.lang.win32;
import com.microfocus.cobol.RuntimeSystem;

public class Win32Utils {
	private static final String[] dllNames = new String[]{"shell32", "comdlg32", "comctl32", "advapi32", "gdi32",
			"user32", "kernel32", "msvcrt"};

	public static void PreLoadStandardDLLs() {
		String[] var0 = dllNames;
		int var1 = var0.length;

		for (int var2 = 0; var2 < var1; ++var2) {
			String var3 = var0[var2];
			RuntimeSystem.cobload(var3);
		}

	}

	public static int PointerSize() {
		String var0 = System.getProperty("sun.arch.data.model");
		if (var0 == null) {
			var0 = System.getProperty("com.ibm.vm.bitmode");
		}

		return var0 != null && var0.equals("64") ? 8 : 4;
	}
}