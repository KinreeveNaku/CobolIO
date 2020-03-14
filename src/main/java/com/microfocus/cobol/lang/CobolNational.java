package com.microfocus.cobol.lang;

import com.microfocus.cobol.CobolException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CobolNational implements DataType {
	/**
	 * 
	 */
	private static final int UTF16_BYTE_LENGTH = 2;
	/**
	 * 
	 */
	private static final Charset UTF_16 = StandardCharsets.UTF_16;
	private Charset encoding;
	private String filler;
	private byte[] nationalBytes;
	private byte[] fillerBytes;

	public CobolNational(String var1, int var2, String var3, char var4) throws UnsupportedEncodingException {
		this.encoding = UTF_16;
		this.filler = null;
		this.nationalBytes = null;
		this.fillerBytes = null;
		this.createCobolNationalString(var1, var2, var3, var4);
	}

	private void createCobolNationalString(String var1, int var2, String charset, char var4) {
		this.encoding = Charset.forName(charset);
		char[] var5 = new char[] { var4 };
		this.filler = new String(var5);
		int var6 = var2 - var1.length();
		if (!charset.startsWith(UTF_16.displayName())) {
			throw new IllegalArgumentException("Illegal encoding specified");
		} else if (var6 < 0) {
			throw new IllegalArgumentException("String length bigger and request field size");
		} else {
			if (var6 != 0) {
				byte[] var7 = this.getFillerBufferOfSize(var6);
				this.nationalBytes = this.mergeByteArrays(this.getBytesGivenEncoding(var1, this.encoding), var7);
			} else {
				this.nationalBytes = this.getBytesGivenEncoding(var1, this.encoding);
			}

		}
	}

	public CobolNational(String var1, int var2, String var3) throws UnsupportedEncodingException {
		this(var1, var2, var3, ' ');
	}

	public CobolNational(String var1, int var2) {
		this.encoding = UTF_16;
		this.filler = null;
		this.nationalBytes = null;
		this.fillerBytes = null;

		this.createCobolNationalString(var1, var2, UTF_16.displayName(), ' ');

	}

	public CobolNational(StringBuilder var1) {
		this(var1.toString(), var1.capacity());
	}

	public CobolNational(StringBuilder var1, int var2) {
		this(var1.toString(), var2);
	}

	@Override
	public byte[] getBytes() throws CobolException {
		return this.nationalBytes;
	}

	@Override
	public void synchronizeData() throws CobolException {
		// Does nothing internally
	}

	private byte[] getFillerBufferOfSize(int var1) {
		if (this.fillerBytes == null) {
			this.fillerBytes = this.getBytesGivenEncoding(this.filler, this.encoding);
		}

		int var2 = this.fillerBytes.length;
		byte[] var3 = new byte[var2 * var1];

		for (int var4 = 0; var4 < var1; ++var4) {
			System.arraycopy(this.fillerBytes, 0, var3, var4 * var2, var2);
		}

		return var3;
	}

	private byte[] getBytesGivenEncoding(String data, Charset charset) {
		byte[] dataBytes = data.getBytes(charset);
		if (UTF_16.equals(charset)) {
			byte[] var4 = new byte[dataBytes.length - UTF16_BYTE_LENGTH];
			System.arraycopy(dataBytes, UTF16_BYTE_LENGTH, var4, 0, var4.length);
			return var4;
		} else {
			return dataBytes;
		}
	}

	private byte[] mergeByteArrays(byte[] var1, byte[] var2) {
		int var3 = var1.length + var2.length;
		byte[] var4 = new byte[var3];
		System.arraycopy(var1, 0, var4, 0, var1.length);
		System.arraycopy(var2, 0, var4, var1.length, var2.length);
		return var4;
	}

	@Override
	public String toString() {
		if (UTF_16.equals(this.encoding)) {
			byte[] var1 = " ".getBytes(this.encoding);
			byte[] var2 = new byte[this.nationalBytes.length + UTF16_BYTE_LENGTH];
			System.arraycopy(this.nationalBytes, 0, var2, UTF16_BYTE_LENGTH, this.nationalBytes.length);
			var2[0] = var1[0];
			var2[1] = var1[1];
			return new String(var2, this.encoding);
		} else {
			return new String(this.nationalBytes, this.encoding);
		}
	}
}
