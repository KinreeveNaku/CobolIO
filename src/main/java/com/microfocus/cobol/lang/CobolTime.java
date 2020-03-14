package com.microfocus.cobol.lang;

import com.microfocus.cobol.CobolException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CobolTime implements DataType {
	private Time sqlTime;
	private byte[] bytes;
	private Charset encoding;

	public CobolTime() {
		this(new Time(System.currentTimeMillis()));
	}

	public CobolTime(Time var1) {
		this.encoding = StandardCharsets.ISO_8859_1;
		this.sqlTime = var1;
		this.bytes = var1.toString().getBytes(this.encoding);

	}

	public CobolTime(Time var1, String var2) throws UnsupportedEncodingException {
		this.encoding = StandardCharsets.ISO_8859_1;
		this.sqlTime = var1;
		this.bytes = var1.toString().getBytes(var2);
	}

	@Override
	public byte[] getBytes() throws CobolException {
		return this.bytes;
	}

	@Override
	public void synchronizeData() throws CobolException {
		String var1 = new String(this.bytes, this.encoding);
		SimpleDateFormat var2 = new SimpleDateFormat("HH:mm:ss");
		long var3 = 0;
		try {
			var3 = var2.parse(var1).getTime();
		} catch (ParseException e) {
			// Will never happen in this case.
			e.printStackTrace();
		}
		this.sqlTime.setTime(var3);
	}

	public String getEncoding() {
		return this.encoding.displayName();
	}

	@Override
	public String toString() {
		return new String(this.bytes, this.encoding);
	}
}