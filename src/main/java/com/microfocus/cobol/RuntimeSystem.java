package com.microfocus.cobol;

import java.nio.ByteBuffer;

import com.microfocus.cobol.lang.ParameterList;
import com.microfocus.cobol.lang.ReturnCode;
import com.microfocus.cobol.lang.win32.Win32Exception;

public class RuntimeSystem {
	/**
	 * 
	 */
	private static final String JAVACLASS = "javaclass";
	/**
	 * 
	 */
	private static final String _1_4_2 = "1.4.2";
	/**
	 * 
	 */
	private static final String UNIX_WARE = "UnixWare";
	/**
	 * 
	 */
	private static final String THE_SCO_GROUP = "The SCO Group";
	/**
	 * 
	 */
	private static final String LINUX = "Linux";
	/**
	 * 
	 */
	private static final String SUN_OS = "SunOS";
	/**
	 * 
	 */
	private static final String HP_UX = "HP-UX";
	/**
	 * 
	 */
	private static final String HEWLETT_PACKARD = "Hewlett-Packard";
	private static boolean isCobjvmLoaded = false;
	private static boolean hasJavaClassBeenLoaded = false;
	private static int srvCtlDefault = 0;
	public static final int BY_VALUE = 0;
	public static final int BY_REFERENCE = 1;
	public static final int BY_CONTENT = 2;
	public static final int SRV_CTL_NONE = 0;
	public static final int SRV_CTL_NOTHREAD = 1;
	public static final int SRV_CTL_NOSTOPRUN = 2;
	public static final int SRV_CTL_NOCANCEL = 4;
	public static final int SRV_CTL_ERRORHAND = 8;
	public static final int SRV_CTL_ERRORHAND_ALL = 24;
	public static final int SRV_CTL_THREADTIDY = 32;
	private static ThreadLocal<Win32Exception> Win32ExceptionLocal = new ThreadLocal<Win32Exception>();
	public static final short A_NORMAL = 0;
	public static final short A_BOLD = 256;
	public static final short A_UNDER = 512;
	public static final short A_REVERSE = 1024;
	public static final short A_BLINK = 2048;
	public static final short A_DIM = 16384;
	protected static boolean isWindows = false;
	protected static final String osName;
	protected static final long cblClassHandle = 0L;
	protected final long cblInstanceHandle = 0L;

	public static void main(String[] var0) {
		String var1 = getCobolRuntimeSupportLibraryNameSuffix();
		if (isCobjvmLoaded) {
			System.out.println("com.microfocus.cobol.RuntimeSystem is not an executable class");
			System.exit(255);
		}

		if (var1 != null) {
			System.out.println("Please set COBJVM environment variable to " + var1);
			System.exit(255);
		}

		String[] var2 = new String[]{"java.fullversion", "java.version", "java.vendor", "os.name", "os.arch",
				"os.version", "java.vendor.url", "java.vm.vendor"};
		System.out.println(
				"Sorry but your Java environment is not supported\nPlease check for the latest updates\n");
		System.out.println("Java Environment information\n");

		for (int var3 = 0; var3 < var2.length; ++var3) {
			String var4 = System.getProperty(var2[var3]);
			if (var4 != null) {
				System.out.println("\t" + var2[var3] + " = " + var4);
			}
		}

	}

	public static void setSrvCtlDefault(int var0) {
		srvCtlDefault = var0;
	}

	public static int getSrvCtlDefault() {
		return srvCtlDefault;
	}

	public static void cobcall(ReturnCode var0, String var1, Object[] var2, int[] var3, boolean var4)
			throws CobolException {
		cobcall((Object) var0, var1, var2, var3, var4);
	}

	@Deprecated
	public static native void cobcall(Object var0, String var1, Object[] var2, int[] var3, boolean var4)
			throws CobolException;

	public static void cobcall(ReturnCode var0, String var1, Object[] var2, int[] var3, boolean var4, int var5)
			throws Exception {
		cobcall((Object) var0, var1, var2, var3, var4, var5);
	}

	@Deprecated
	public static native void cobcall(Object var0, String var1, Object[] var2, int[] var3, boolean var4, int var5)
			throws CobolException, Exception;

	public static void cobcall(ReturnCode var0, String var1, Object[] var2, int[] var3)
			throws CobolException, Exception {
		cobcall((Object) var0, var1, var2, var3, false);
	}

	@Deprecated
	public static void cobcall(Object var0, String var1, Object[] var2, int[] var3) throws CobolException, Exception {
		cobcall(var0, var1, var2, var3, false);
	}

	public static int cobcall(String var0, ParameterList var1) throws Exception {
		ReturnCode var2 = new ReturnCode();
		cobcall(var2, var0, var1.getArguments(), var1.getUsageDescrption());
		return var2.intValue();
	}

	public static void cobcall(ReturnCode var0, String var1, ParameterList var2) throws Exception {
		cobcall((Object) var0, var1, var2.getArguments(), var2.getUsageDescrption());
	}

	@Deprecated
	public static void cobcall(Object var0, String var1, ParameterList var2) throws CobolException, Exception {
		cobcall(var0, var1, var2.getArguments(), var2.getUsageDescrption());
	}

	public static int cobcall(String var0) throws CobolException, Exception {
		return cobcall_int(var0, (Object[]) null);
	}

	public static void ccall(ReturnCode var0, String var1, Object[] var2, int[] var3) throws CobolException, Exception {
		ccall((Object) var0, var1, var2, var3);
	}

	@Deprecated
	public static native void ccall(Object var0, String var1, Object[] var2, int[] var3)
			throws CobolException, Exception;

	public static void ccall(ReturnCode var0, String var1, Object[] var2, int[] var3, int var4)
			throws CobolException, Exception {
		ccall((Object) var0, var1, var2, var3, var4);
	}

	@Deprecated
	public static native void ccall(Object var0, String var1, Object[] var2, int[] var3, int var4)
			throws CobolException, Exception;

	public static native int ccall(String var0, Object[] var1, int[] var2) throws CobolException, Exception;

	public static Win32Exception getLastWin32Exception() {
		return (Win32Exception) Win32ExceptionLocal.get();
	}

	public static int getLastWin32ErrorCode() {
		Win32Exception var0 = (Win32Exception) Win32ExceptionLocal.get();
		return var0 == null ? 0 : var0.getErrorCode();
	}

	private static void setLastWin32Exception(Win32Exception var0) {
		Win32ExceptionLocal.set(var0);
	}

	public static void stdapi_call(ReturnCode var0, String var1, Object[] var2, int[] var3)
			throws CobolException, Exception {
		stdapi_call((Object) var0, var1, var2, var3);
	}

	@Deprecated
	public static void stdapi_call(Object var0, String var1, Object[] var2, int[] var3)
			throws Exception {
		setLastWin32Exception((Win32Exception) null);
		Exception var4 = internal_stdapi_call(var0, var1, var2, var3, getSrvCtlDefault());
		if (var4 != null) {
			if (var4 instanceof Win32Exception) {
				setLastWin32Exception((Win32Exception) var4);
				throw (Win32Exception) var4;
			} else {
				throw new CobolException(var4);
			}
		}
	}

	public static void stdapi_call(ReturnCode var0, String var1, Object[] var2, int[] var3, int var4, boolean var5)
			throws CobolException, Win32Exception, Exception {
		stdapi_call((Object) var0, var1, var2, var3, var4, var5);
	}

	@Deprecated
	public static void stdapi_call(Object var0, String var1, Object[] var2, int[] var3, int var4, boolean var5)
			throws CobolException, Win32Exception, Exception {
		setLastWin32Exception((Win32Exception) null);
		Exception var6 = internal_stdapi_call(var0, var1, var2, var3, var4);
		if (var6 != null) {
			if (!(var6 instanceof Win32Exception)) {
				throw new CobolException(var6);
			}

			setLastWin32Exception((Win32Exception) var6);
			if (var5) {
				throw (Win32Exception) var6;
			}
		}

	}

	@Deprecated
	private static native Exception internal_stdapi_call(Object var0, String var1, Object[] var2, int[] var3, int var4)
			throws CobolException, Exception;

	private static void cobrcall(ReturnCode var0, String var1, Object[] var2, int[] var3, int var4)
			throws CobolException, Exception {
		cobrcall((Object) var0, var1, var2, var3, var4);
	}

	@Deprecated
	private static native void cobrcall(Object var0, String var1, Object[] var2, int[] var3, int var4)
			throws Exception;

	public static void cobrcall(ReturnCode var0, String var1, ParameterList var2) throws CobolException, Exception {
		if (var2 == null) {
			cobrcall((ReturnCode) var0, var1, (Object[]) null, (int[]) null, getSrvCtlDefault());
		} else {
			cobrcall(var0, var1, var2.getArguments(), var2.getUsageDescrption(), getSrvCtlDefault());
		}

	}

	@Deprecated
	public static void cobrcall(Object var0, String var1, ParameterList var2) throws CobolException, Exception {
		if (var2 == null) {
			cobrcall((Object) var0, var1, (Object[]) null, (int[]) null, getSrvCtlDefault());
		} else {
			cobrcall(var0, var1, var2.getArguments(), var2.getUsageDescrption(), getSrvCtlDefault());
		}

	}

	public static void cobrcall(ReturnCode var0, String var1, ParameterList var2, int var3)
			throws CobolException, Exception {
		if (var2 == null) {
			cobrcall((ReturnCode) var0, var1, (Object[]) null, (int[]) null, var3);
		} else {
			cobrcall(var0, var1, var2.getArguments(), var2.getUsageDescrption(), var3);
		}

	}

	@Deprecated
	public static void cobrcall(Object var0, String var1, ParameterList var2, int var3)
			throws CobolException, Exception {
		if (var2 == null) {
			cobrcall((Object) var0, var1, (Object[]) null, (int[]) null, var3);
		} else {
			cobrcall(var0, var1, var2.getArguments(), var2.getUsageDescrption(), var3);
		}

	}

	public static void cobcall(ReturnCode var0, String var1, ParameterList var2, int var3)
			throws CobolException, Exception {
		cobcall(var0, var1, var2.getArguments(), var2.getUsageDescrption(), false, var3);
	}

	@Deprecated
	public static void cobcall(Object var0, String var1, ParameterList var2, int var3)
			throws CobolException, Exception {
		cobcall(var0, var1, var2.getArguments(), var2.getUsageDescrption(), false, var3);
	}

	public static native void cobcall_Object(Object var0, String var1, Object[] var2) throws Exception;

	public static String cobgetenv(String var0) throws Exception {
		StringBuilder var1 = new StringBuilder(1024);
		Object[] var2 = new Object[]{var0};
		int[] var3 = new int[]{1};
		ccall((Object) var1, "cobgetenv", var2, var3);
		return var1.toString();
	}

	public static native int cobputenv(String var0) throws Exception;

	public static void cobclear() throws CobolException, Exception {
		ccall("cobclear", (Object[]) null, (int[]) null);
	}

	public static void cobaddstr(String var0) throws CobolException, Exception {
		Object[] var1 = new Object[]{var0};
		int[] var2 = new int[]{1};
		if (var0.length() < 100) {
			ccall((ReturnCode) null, "cobaddstrc", var1, var2);
		}

	}

	public static void mFgF806(String var0) throws Exception {
		int var1 = var0.length();
		Object[] var2 = new Object[]{var0, var1};
		int[] var3 = new int[]{1, 0};
		if (var0.length() < 100) {
			ccall((ReturnCode) null, "_mFgF806", var2, var3);
		}

	}

	public static void cobaddstr(int var0, String var1) throws CobolException, Exception {
		ReturnCode var2 = new ReturnCode();
		int[] var3 = new int[]{0};

		for (int var4 = 0; var4 < var1.length(); ++var4) {
			Short var5 = new Short((short) (var1.charAt(var4) | var0));
			Object[] var6 = new Object[]{var5};
			ccall(var2, "cobaddch", var6, var3);
		}

	}

	public static char cobgetch() throws CobolException, Exception {
		ReturnCode var0 = new ReturnCode();
		ccall((ReturnCode) var0, "cobgetch", (Object[]) null, (int[]) null);
		return (char) var0.byteValue();
	}

	public static int cobcols() throws CobolException, Exception {
		ReturnCode var0 = new ReturnCode();
		ccall((ReturnCode) var0, "cobcols", (Object[]) null, (int[]) null);
		return var0.intValue();
	}

	public static int coblines() throws CobolException, Exception {
		ReturnCode var0 = new ReturnCode();
		ccall((ReturnCode) var0, "coblines", (Object[]) null, (int[]) null);
		return var0.intValue();
	}

	public static void cobmove(int var0, int var1) throws CobolException, Exception {
		ReturnCode var2 = new ReturnCode();
		Object[] var3 = new Object[]{new Integer(var0), new Integer(var1)};
		int[] var4 = new int[]{0, 0};
		ccall(var2, "cobmove", var3, var4);
	}

	public static int cobrescanenv() throws CobolException, Exception {
		ReturnCode var0 = new ReturnCode();
		ccall((ReturnCode) var0, "cobrescanenv", (Object[]) null, (int[]) null);
		return var0.intValue();
	}

	public static void cobcancel(String var0) throws CobolException, Exception {
		Object[] var1 = new Object[]{var0};
		int[] var2 = new int[]{1};
		ccall((ReturnCode) null, "cobcancel", var1, var2);
	}

	public static void cobtidy() throws CobolException, Exception {
		ccall((ReturnCode) null, "cobtidy", (Object[]) null, (int[]) null);
	}

	public static int cobthreadtidy() throws CobolException, Exception {
		return ccall("cobthreadtidy", (Object[]) null, (int[]) null);
	}

	public static native int cobcall_int(String var0, Object[] var1) throws CobolException, Exception;

	public static int cobcall(String var0, Object[] var1) throws CobolException, Exception {
		return cobcall_int(var0, var1);
	}

	public static native byte cobcall_byte(String var0, Object[] var1) throws CobolException, Exception;

	public static native boolean cobcall_boolean(String var0, Object[] var1) throws CobolException, Exception;

	public static native short cobcall_short(String var0, Object[] var1) throws CobolException, Exception;

	public static native float cobcall_float(String var0, Object[] var1) throws CobolException, Exception;

	public static Integer cobcall_Integer(String var0, Object[] var1) throws Exception, CobolException {
		ReturnCode var2 = new ReturnCode();
		cobcall_Object(var2, var0, var1);
		return var2.intValue();
	}

	public static Byte cobcall_Byte(String var0, Object[] var1) throws Exception, CobolException {
		ReturnCode var2 = new ReturnCode();
		cobcall_Object(var2, var0, var1);
		return var2.byteValue();
	}

	public static Boolean cobcall_Boolean(String var0, Object[] var1) throws Exception, CobolException {
		ReturnCode var2 = new ReturnCode();
		cobcall_Object(var2, var0, var1);
		return var2.byteValue() == 1 ? Boolean.TRUE : Boolean.FALSE;
	}

	public static Short cobcall_Short(String var0, Object[] var1) throws Exception, CobolException {
		ReturnCode var2 = new ReturnCode();
		cobcall_Object(var2, var0, var1);
		return var2.shortValue();
	}

	public static Float cobcall_Float(String var0, Object[] var1) throws Exception, CobolException {
		ReturnCode var2 = new ReturnCode();
		cobcall_Object(var2, var0, var1);
		return var2.floatValue();
	}

	public static String cobcall_String(String var0, Object[] var1) throws Exception, CobolException {
		StringBuilder var2 = new StringBuilder();
		cobcall_Object(var2, var0, var1);
		return var2.toString();
	}

	protected native void updateParameters(Object[] var1, Object[] var2) throws CobolException, Exception;

	protected static String getCobolRuntimeSupportLibraryName() {
		String var0 = "";
		String var1;
		if (isWindows) {
			var0 = "cbljvm_";
			var1 = getCobolRuntimeSupportLibraryNameSuffix();
			if (var1.length() == 0) {
				var1 = System.getProperty("com.microfocus.cobol.cobjvm");
				if (var1 == null) {
					return "";
				}
			}

			return var0 + var1;
		} else {
			var1 = System.getProperty("sun.arch.data.model");
			if (var1 == null) {
				var1 = System.getProperty("com.ibm.vm.bitmode");
			}

			if (var1 != null && var1.equals("64")) {
				var0 = "cobjvm64";
			} else {
				var0 = "cobjvm";
			}

			return var0;
		}
	}

	private static String getCobolRuntimeSupportLibraryNameSuffix() {
		String var0 = System.getProperty("java.version");
		String var1 = System.getProperty("java.vendor");
		String var2 = System.getProperty("com.microfocus.cobol.librarysuffix", "");
		String var3 = "";
		String[][] var4;
		if (isWindows) {
			var4 = new String[][]{{"IBM", "1.7", "ibm7"}, {"IBM", "1.8", "ibm7"}};
			var3 = "adoptopenjdk8";

			for (int var5 = 0; var5 < var4.length; ++var5) {
				if (var1.startsWith(var4[var5][0]) && var0.startsWith(var4[var5][1])) {
					var3 = var4[var5][2];
				}
			}
		} else {
			var4 = new String[][]{{"AIX", "IBM", "1.2"}, {"AIX", "IBM", "1.3"},
					{HP_UX, HEWLETT_PACKARD, "JavaVM-1.3"}, {HP_UX, HEWLETT_PACKARD, "1.2"},
					{HP_UX, HEWLETT_PACKARD, "1.3"}, {LINUX, "IBM", "1.3"}, {LINUX, "Sun", "1.3"},
					{SUN_OS, "Sun", "1.3"}, {SUN_OS, "Sun", "1.4.0"}, {UNIX_WARE, "SCO, Inc.", "1.2"},
					{UNIX_WARE, "SCO, Inc.", "1.3"}};
			String[][] var7 = new String[][]{{"AIX", "IBM", _1_4_2, "ibm_142"}, {"AIX", "IBM", "1.5", "ibm_150"},
					{"AIX", "IBM", "1.6", "ibm_160"}, {HP_UX, HEWLETT_PACKARD, "1.4", "hp_142"},
					{HP_UX, HEWLETT_PACKARD, "1.5", "hp_150"}, {HP_UX, HEWLETT_PACKARD, "1.6", "hp_160"},
					{LINUX, "IBM", _1_4_2, "ibm_142"}, {LINUX, "IBM", "1.5", "ibm_150"},
					{LINUX, "IBM", "1.6", "ibm_160"}, {LINUX, "Sun", _1_4_2, "sun_142"},
					{LINUX, "Sun", "1.5", "sun_150"}, {LINUX, "Sun", "1.6", "sun_160"},
					{"OSF1", HEWLETT_PACKARD, _1_4_2, "tru_142"}, {SUN_OS, "Sun", _1_4_2, "sun_142"},
					{SUN_OS, "Sun", "1.5", "sun_150"}, {SUN_OS, "Sun", "1.6", "sun_160"},
					{"OpenServer", THE_SCO_GROUP, _1_4_2, "unx_142"},
					{"OpenServer", THE_SCO_GROUP, "1.5.0", "unx_150"},
					{UNIX_WARE, THE_SCO_GROUP, _1_4_2, "unx_142"},
					{UNIX_WARE, THE_SCO_GROUP, "1.5.0", "unx_150"}};

			int var6;
			for (var6 = 0; var6 < var4.length; ++var6) {
				if (osName.startsWith(var4[var6][0]) && var1.startsWith(var4[var6][1])
						&& var0.startsWith(var4[var6][2])) {
					return "";
				}
			}

			for (var6 = 0; var6 < var7.length; ++var6) {
				if (osName.startsWith(var7[var6][0]) && var1.startsWith(var7[var6][1])
						&& var0.startsWith(var7[var6][2])) {
					var3 = var7[var6][3];
				}
			}
		}

		return var3 != null ? (var3 + var2) : var3;
	}

	public static native void cobassocclass(String var0, Class<?> var1);

	public static native void cobassocclass(String var0, String var1);

	public static int cobloadclass(String var0, String var1) {
		if (!hasJavaClassBeenLoaded && cobload((String) null, JAVACLASS) == 0) {
			hasJavaClassBeenLoaded = true;
		}

		return hasJavaClassBeenLoaded ? cobload(var0, var1) : -1;
	}

	public static int cobloadclass(String var0, String var1, Class<?> var2) {
		if (!hasJavaClassBeenLoaded && cobload((String) null, JAVACLASS) == 0) {
			hasJavaClassBeenLoaded = true;
		}

		if (hasJavaClassBeenLoaded) {
			cobassocclass(var1, var2);
			return cobload(var0, var1);
		} else {
			return -1;
		}
	}

	public static int cobloadclass(String var0, String var1, String var2) {
		if (!hasJavaClassBeenLoaded && cobload((String) null, JAVACLASS) == 0) {
			hasJavaClassBeenLoaded = true;
		}

		if (hasJavaClassBeenLoaded) {
			cobassocclass(var1, var2);
			return cobload(var0, var1);
		} else {
			return -1;
		}
	}

	private static native int cobload(String var0, String var1);

	public static int cobload(String var0) {
		return cobload(var0, (String) null);
	}

	public native boolean cobinvoke_boolean(String var1, Object[] var2) throws CobolException, Exception;

	public native byte cobinvoke_byte(String var1, Object[] var2) throws CobolException, Exception;

	public native char cobinvoke_char(String var1, Object[] var2) throws CobolException, Exception;

	public native short cobinvoke_short(String var1, Object[] var2) throws CobolException, Exception;

	public native int cobinvoke_int(String var1, Object[] var2) throws CobolException, Exception;

	public native long cobinvoke_long(String var1, Object[] var2) throws CobolException, Exception;

	public native float cobinvoke_float(String var1, Object[] var2) throws CobolException, Exception;

	public native double cobinvoke_double(String var1, Object[] var2) throws CobolException, Exception;

	public native void cobinvoke_void(String var1, Object[] var2) throws CobolException, Exception;

	public native Object cobinvoke_object(String var1, Object[] var2) throws CobolException, Exception;

	public static native boolean cobinvokestatic_boolean(Class<?> var0, String var1, Object[] var2)
			throws CobolException, Exception;

	public static native byte cobinvokestatic_byte(Class<?> var0, String var1, Object[] var2)
			throws CobolException, Exception;

	public static native char cobinvokestatic_char(Class<?> var0, String var1, Object[] var2)
			throws CobolException, Exception;

	public static native short cobinvokestatic_short(Class<?> var0, String var1, Object[] var2)
			throws CobolException, Exception;

	public static native int cobinvokestatic_int(Class<?> var0, String var1, Object[] var2)
			throws CobolException, Exception;

	public static native long cobinvokestatic_long(Class<?> var0, String var1, Object[] var2)
			throws CobolException, Exception;

	public static native float cobinvokestatic_float(Class<?> var0, String var1, Object[] var2)
			throws CobolException, Exception;

	public static native double cobinvokestatic_double(Class<?> var0, String var1, Object[] var2)
			throws CobolException, Exception;

	public static native void cobinvokestatic_void(Class<?> var0, String var1, Object[] var2)
			throws CobolException, Exception;

	public static native Object cobinvokestatic_object(Class<?> var0, String var1, Object[] var2)
			throws CobolException, Exception;

	public static native void cobfinalize(Object var0) throws Throwable;

	public static native int nio_getaddress32(ByteBuffer var0);

	public static native long nio_getaddress64(ByteBuffer var0);

	public static native void unsafeCopy(int var0, long var1, byte[] var3, int var4);

	public static native String getWin32SystemMessage(int var0);

	static {
		String var0 = System.getProperty("com.microfocus.cobol.getcobjvmenv");
		String var1 = System.getProperty("mfcobol.getcobjvmenv");
		osName = System.getProperty("os.name");
		if (osName.startsWith("Windows")) {
			isWindows = true;
		}

		String var2;
		if (!isWindows) {
			var2 = System.getProperty("com.microfocus.cobol.allowLoadLibrary", "false");
			if ("false".equals(var2) && var0 == null && var1 == null) {
				System.err.println(
						"Micro Focus COBOL Runtime Support for Java is preventing this application from \nexecuting because an internal Java property is not setup.\n\nReason: Application should use \"cobjrun\" to execute this application instead\n        of the default \"java\" trigger\n");
				System.exit(255);
			}
		}

		var2 = getCobolRuntimeSupportLibraryName();
		if (var0 == null && var1 == null && var2.length() == 0) {
			throw new UnsatisfiedLinkError("COBOL Runtime - unsupported JVM");
		} else {
			if (var0 == null && var1 == null) {
				if (isWindows) {
					String var3 = null;

					try {
						var3 = System.getProperty("com.microfocus.cobol.jvmpath");
					} catch (Exception var10) {
						;
					}

					String var4;
					if (var3 == null) {
						var4 = null;

						try {
							var4 = System.getenv("COBDIR");
						} catch (Exception var9) {
							;
						}

						if (var4 != null) {
							int var5 = var4.length();

							boolean var6;
							try {
								var6 = "64".equals(System.getProperty("sun.arch.data.model"));
							} catch (Exception var8) {
								var6 = false;
							}

							if (var5 > 4) {
								String var7 = var4.substring(var5 - 4);
								if ("\\x86".equals(var7) || "\\x64".equals(var7)) {
									var3 = var4.substring(0, var5 - 4) + "\\" + (var6 ? "x64" : "x86") + "\\bin";
								}
							}

							if (var3 == null) {
								int var11 = var4.indexOf(59);
								if (var11 >= 0) {
									var4 = var4.substring(0, var11);
								}

								var3 = var4 + "\\" + (var6 ? "bin64" : "bin");
							}
						}
					}

					if (var3 == null) {
						System.loadLibrary(var2);
					} else {
						var4 = var3 + "\\" + var2 + ".dll";
						System.load(var4);
					}
				} else {
					System.loadLibrary(var2);
				}

				isCobjvmLoaded = true;
				if (cobload(var2, (String) null) != 0) {
					if (isWindows) {
						throw new UnsatisfiedLinkError("COBOL module can not be loaded (" + var2 + ")");
					}

					if (cobload("lib" + var2, (String) null) != 0) {
						throw new UnsatisfiedLinkError("COBOL module can not be loaded (lib" + var2 + ")");
					}
				}
			}

		}
	}
}