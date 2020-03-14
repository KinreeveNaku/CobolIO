package com.microfocus.cobol.util;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;

class JarInfo {
	public static String getMainClassFromJarfile(String var0) {
		Attributes var1 = null;
		JarFile var2 = null;
		Manifest var3 = null;

		try {
			var2 = new JarFile(var0);
		} catch (Exception var7) {
			System.err.println("Unable to open " + var0 + " (" + var7.getMessage() + ")");
			try {
				var2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		try {
			var3 = var2.getManifest();
		} catch (Exception var6) {
			System.err.println("Unable to open manifest in " + var0 + " (" + var6.getMessage() + ")");
			try {
				var2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		try {
			var1 = var3.getMainAttributes();
		} catch (Exception var5) {
			System.err.println("Unable to find main-class in " + var0 + " (" + var5.getMessage() + ")");
			try {
				var2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		String var4 = var1.getValue(Name.MAIN_CLASS);
		if (var4 == null) {
			System.err.println("Unable to find the attribute main-class in the manifest of " + var0);
			try {
				var2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		} else if (var4.length() == 0) {
			System.err.println("No specified in the manifest " + var0);
			try {
				var2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		} else {
			try {
				var2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return var4;
		}
	}
}