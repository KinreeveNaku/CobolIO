package com.microfocus.cobol.lang.internal;
import java.nio.charset.StandardCharsets;

import com.microfocus.cobol.CobolException;

public class CobolAlphanumeric extends CobolField {
	public CobolAlphanumeric(CobolRecord var1, int var2, int var3, int var4, int var5, int var6) {
		super(var1, var3, var4, var5, CobolDescriptor.makeAlphanumericDescriptor(var2, var4, var6));
	}

	public CobolAlphanumeric(CobolRecord var1, int var2, int var3, int var4, int var5) {
		this(var1, var2, var3, var4, var5, 0);
	}

	public CobolAlphanumeric(CobolRecord var1, int var2, int var3, int var4, int var5, int var6, String var7) {
		super(var1, var3, var4, var5, CobolDescriptor.makeEditedDescriptor(var2, 9, var6, var7));
	}

	public CobolAlphanumeric(CobolRecord var1, int var2, int var3, int var4, int var5, String var6) {
		this(var1, var2, var3, var4, var5, 0);
	}

	@Override
	public void setString(String var1) throws CobolRecordException {
		if (this.descriptor.isAlphanumericEdited()) {
			this.record.setAlphanumericEdited(this.descriptor, var1, this.offset);
		} else {
			try {
				this.setBytes(var1.getBytes());
			} catch (CobolException var3) {
				throw new CobolRecordException();
			}
		}

	}

	@Override
	public String toString() {
		String var1;
		try {
			var1 = new String(this.getBytesValue(), StandardCharsets.UTF_8);
		} catch (CobolRecordException var3) {
			var1 = null;
		}

		return var1;
	}
}
