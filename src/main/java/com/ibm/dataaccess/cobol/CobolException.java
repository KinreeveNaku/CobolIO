/**
 * 
 */
package com.ibm.dataaccess.cobol;

/**
 * @author Andrew
 *
 */
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class CobolException extends Exception implements Serializable {
	static final long serialVersionUID = 9135392659399557335L;
	static final String sccsid = "@(#)$RCSfile$ $Revision: 959194 $";
	private byte[] causeStackTrace = null;
	private String causeMessage = null;
	private Throwable causeOfException;
	private static final String causeBy = "Caused by:";

	public CobolException() {
	}

	public CobolException(String var1) {
		super(var1);
	}

	public CobolException(String var1, Throwable var2) {
		super(var1);
		this.causeOfException = var2;
		this.setupCauseStackTrace(var2);
	}

	public CobolException(Throwable var1) {
		this.causeOfException = var1;
		this.setupCauseStackTrace(var1);
	}

	private void setupCauseStackTrace(Throwable var1) {
		ByteArrayOutputStream var2 = new ByteArrayOutputStream();
		PrintWriter var3 = new PrintWriter(var2);
		var1.printStackTrace(var3);
		var3.close();
		this.causeStackTrace = var2.toByteArray();
		this.causeMessage = var1.getLocalizedMessage();
		if (this.causeMessage == null) {
			this.causeMessage = var1.getMessage();
			if (this.causeMessage == null) {
				this.causeMessage = "";
			}
		}

	}

	@Override
	public synchronized Throwable initCause(Throwable var1) {
		this.causeOfException = var1;
		return this;
	}

	@Override
	public synchronized Throwable getCause() {
		return this.causeOfException;
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
		if (this.causeStackTrace != null) {
			System.err.println(causeBy + this.causeMessage);
			System.err.println(new String(this.causeStackTrace, StandardCharsets.UTF_8));
		}

	}

	@Override
	public void printStackTrace(PrintStream var1) {
		super.printStackTrace(var1);
		if (this.causeStackTrace != null) {
			var1.println(causeBy + this.causeMessage);
			var1.println(new String(this.causeStackTrace, StandardCharsets.UTF_8));
		}

	}

	@Override
	public void printStackTrace(PrintWriter var1) {
		super.printStackTrace(var1);
		if (this.causeStackTrace != null) {
			var1.println("Caused by:" + this.causeMessage);
			var1.println(new String(this.causeStackTrace, StandardCharsets.UTF_8));
		}

	}

	@Override
	public String getMessage() {
		if (this.causeOfException != null) {
			return this.causeOfException.getMessage() == null
					? (super.getMessage() + " (cause class:" + this.causeOfException.getClass().getName() + ")")
					: (super.getMessage() + " (cause class:" + this.causeOfException.getClass().getName() + ", cause:"
							+ this.causeOfException.getMessage() + ")");
		} else {
			return super.getMessage();
		}
	}
}
