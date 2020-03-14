package com.ibm.dataaccess.support;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SqlTypes {
	static boolean updateTime(Time var0, Time var1) throws ParseException {
		SimpleDateFormat var2 = new SimpleDateFormat("HH:mm:ss");
		long var3 = var2.parse(var0.toString()).getTime();
		var1.setTime(var3);
		return true;
	}

	static boolean updateTime(String var0, Time var1) throws ParseException {
		SimpleDateFormat var2 = new SimpleDateFormat("HH:mm:ss");
		long var3 = var2.parse(var0).getTime();
		var1.setTime(var3);
		return true;
	}

	static boolean updateDate(String var0, Date var1) {
		try {
			SimpleDateFormat var2 = new SimpleDateFormat("yyyy-MM-dd");
			long var3 = var2.parse(var0).getTime();
			var1.setTime(var3);
			return true;
		} catch (ParseException var5) {
			return false;
		}
	}

	static boolean updateDate(Date var0, Date var1) {
		try {
			SimpleDateFormat var2 = new SimpleDateFormat("yyyy-MM-dd");
			long var3 = var2.parse(var0.toString()).getTime();
			var1.setTime(var3);
			return true;
		} catch (ParseException var5) {
			return false;
		}
	}
}