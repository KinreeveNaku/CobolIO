package com.microfocus.cobol.lang;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class Pointer implements DataType, Cloneable {
	/**
	 * 
	 */
	private static final String ISO8859_1 = "ISO8859_1";
	/**
	 * 
	 */
	private static final String UTF_16 = "UTF-16";
	static final String sccsid = "@(#)$RCSfile$ $Revision: 959194 $";
	byte[] bytes;
	private String FILLER = " ";
	private byte[] FILLERbytes;
	private String encoding;

	public Pointer(byte[] var1) {
		this.FILLERbytes = this.FILLER.getBytes();
		this.encoding = ISO8859_1;
		this.bytes = var1;
	}

	public Pointer(StringBuilder var1) {
		this.FILLERbytes = this.FILLER.getBytes();
		this.encoding = ISO8859_1;

		try {
			this.setStringBuilderWithEncoding(var1, this.encoding);
		} catch (Exception var3) {
			;
		}

	}

	public Pointer(StringBuilder var1, String var2) throws UnsupportedEncodingException {
		this.FILLERbytes = this.FILLER.getBytes();
		this.encoding = ISO8859_1;
		this.setStringBuilderWithEncoding(var1, var2);
	}

	void setStringBuilderWithEncoding(StringBuilder var1, String var2) throws UnsupportedEncodingException {
		this.setEncoding(var2);
		int var4 = var1.capacity() * this.FILLERbytes.length;
		byte[] var3 = this.getBytesGivenEncoding(var1.toString(), this.encoding);
		this.bytes = new byte[var4];
		int var7;
		if (var3.length < var4) {
			for (var7 = 0; var7 < var3.length; ++var7) {
				this.bytes[var7] = var3[var7];
			}

			while (true) {
				while (var7 < var4) {
					if (this.FILLERbytes.length == 1) {
						this.bytes[var7++] = this.FILLERbytes[0];
					} else {
						for (int var6 = 0; var6 < this.FILLERbytes.length; ++var6) {
							this.bytes[var7++] = this.FILLERbytes[var6];
						}
					}
				}

				return;
			}
		} else {
			for (var7 = 0; var7 < var3.length; ++var7) {
				this.bytes[var7] = var3[var7];
			}

		}
	}

	public Pointer(String var1) {
		this.FILLERbytes = this.FILLER.getBytes();
		this.encoding = ISO8859_1;
		this.bytes = var1.getBytes();
	}

	public Pointer(String var1, String var2) throws UnsupportedEncodingException {
		this.FILLERbytes = this.FILLER.getBytes();
		this.encoding = ISO8859_1;
		this.setEncoding(var2);
		this.bytes = this.getBytesGivenEncoding(var1, var2);
	}

	public Pointer(String var1, int var2) {
		this.FILLERbytes = this.FILLER.getBytes();
		this.encoding = ISO8859_1;

		try {
			this.setStringWithCapacityAndEncoding(var1, var2, this.getEncoding());
		} catch (UnsupportedEncodingException var4) {
			;
		}

	}

	public Pointer(String var1, int var2, String var3) throws UnsupportedEncodingException {
		this.FILLERbytes = this.FILLER.getBytes();
		this.encoding = ISO8859_1;
		this.setStringWithCapacityAndEncoding(var1, var2, var3);
	}

	public Pointer(String var1, int var2, String var3, String var4) throws UnsupportedEncodingException {
		this.FILLERbytes = this.FILLER.getBytes();
		this.encoding = ISO8859_1;
		this.FILLER = var4;
		this.setStringWithCapacityAndEncoding(var1, var2, var3);
	}

	private void setStringWithCapacityAndEncoding(String var1, int var2, String var3)
			throws UnsupportedEncodingException {
		this.setEncoding(var3);
		int var5 = var1.length();
		byte[] var4 = this.getBytesGivenEncoding(var1, this.encoding);
		this.bytes = new byte[var2 * this.FILLERbytes.length];
		int var8;
		if (var5 < var2) {
			for (var8 = 0; var8 < var4.length; ++var8) {
				this.bytes[var8] = var4[var8];
			}

			while (true) {
				while (var8 < var2 * this.FILLERbytes.length) {
					if (this.FILLERbytes.length == 1) {
						this.bytes[var8++] = this.FILLERbytes[0];
					} else {
						for (int var7 = 0; var7 < this.FILLERbytes.length; ++var7) {
							this.bytes[var8++] = this.FILLERbytes[var7];
						}
					}
				}

				return;
			}
		} else {
			for (var8 = 0; var8 < this.bytes.length; ++var8) {
				this.bytes[var8] = var4[var8];
			}

		}
	}

	@Override
	public void synchronizeData() {
		// Does nothing internally
	}

	@Override
	public byte[] getBytes() {
		return this.bytes;
	}

	@Override
	public String toString() {
		try {
			if (this.encoding.compareToIgnoreCase(UTF_16) == 0) {
				byte[] var1 = " ".getBytes(StandardCharsets.UTF_16);
				byte[] var2 = new byte[this.bytes.length + 2];
				System.arraycopy(this.bytes, 0, var2, 2, this.bytes.length);
				var2[0] = var1[0];
				var2[1] = var1[1];
				return new String(var2, this.encoding);
			} else {
				return new String(this.bytes, this.encoding);
			}
		} catch (UnsupportedEncodingException var3) {
			return new String(this.bytes);
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Pointer var1 = new Pointer(this.bytes);

		try {
			var1.setEncoding(this.encoding);
		} catch (UnsupportedEncodingException var3) {
			;
		}

		return var1;
	}

	public String getEncoding() {
		return this.encoding;
	}

	public void setEncoding(String var1) throws UnsupportedEncodingException {
		this.encoding = var1;
		if (this.encoding.compareToIgnoreCase(UTF_16) == 0) {
			byte[] var2 = (new String(this.FILLER)).getBytes(this.encoding);
			this.FILLERbytes = new byte[var2.length - 2];
			System.arraycopy(var2, 2, this.FILLERbytes, 0, this.FILLERbytes.length);
		} else {
			this.FILLERbytes = (new String(this.FILLER)).getBytes(this.encoding);
		}

	}

	private byte[] getBytesGivenEncoding(String var1, String var2) {
		byte[] var6;
		try {
			var6 = var1.getBytes(var2);
			if (var2.compareToIgnoreCase(UTF_16) == 0) {
				byte[] var4 = new byte[var6.length - 2];
				System.arraycopy(var6, 2, var4, 0, var4.length);
				return var4;
			}
		} catch (UnsupportedEncodingException var5) {
			var6 = var1.getBytes();
		}

		return var6;
	}
}
