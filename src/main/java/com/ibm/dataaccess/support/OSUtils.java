/**
 * 
 */
package com.ibm.dataaccess.support;

/**
 * @author Andrew
 *
 */
public class OSUtils {
	public static final int getPointerSize() {
			String var0 = System.getProperty("sun.arch.data.model");
			if (var0 == null) {
				var0 = System.getProperty("com.ibm.vm.bitmode");
			}
	
			return var0 != null && var0.equals("64") ? 8 : 4;
		}
}

