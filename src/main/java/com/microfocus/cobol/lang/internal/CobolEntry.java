package com.microfocus.cobol.lang.internal;
import com.microfocus.cobol.RuntimeSystem;
import com.microfocus.cobol.lang.ParameterList;
import com.microfocus.cobol.lang.ReturnCode;

public class CobolEntry {
	private String entryName;

	public CobolEntry(String var1) {
		this.entryName = var1;
	}

	public String getEntryName() {
		return this.entryName;
	}

	public long call(Object[] var1) throws Exception {
		return this.call(var1, RuntimeSystem.getSrvCtlDefault());
	}

	public long call(Object[] var1, int var2) throws Exception {
		ParameterList var3 = new ParameterList();
		ReturnCode var4 = new ReturnCode();
		if (var1 != null) {
			for (int var5 = 0; var5 < var1.length; ++var5) {
				var3.add(var1[var5], 1);
			}
		}

		RuntimeSystem.cobrcall(var4, this.entryName, var3, var2);
		return var4.longValue();
	}
}