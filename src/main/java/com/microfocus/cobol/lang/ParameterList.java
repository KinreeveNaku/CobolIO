package com.microfocus.cobol.lang;

import com.microfocus.cobol.lang.win32.DWORD;
import com.microfocus.cobol.lang.win32.LPDWORD;
import java.lang.reflect.Method;
import java.util.AbstractList;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class ParameterList implements Cloneable {
	private AbstractList arguments = new ArrayList();
	private AbstractList usage = new ArrayList();
	private int by_ref_usage_count = 0;

	public ParameterList add(Object var1) {
		return this.add(var1, 1);
	}

	@SuppressWarnings("unchecked")
	public ParameterList add(Object var1, int var2) {
		if (var2 >= 0 && var2 <= 3) {
			this.arguments.add(var1);
			if (var2 == 1) {
				++this.by_ref_usage_count;
			}

			this.usage.add(new Integer(var2));
			return this;
		} else {
			throw new IllegalArgumentException("Invalid usage given (usage=" + var2 + ")");
		}
	}

	public ParameterList add(int var1) {
		return this.add(var1, 1);
	}

	public ParameterList add(int var1, int var2) {
		return this.add(var1, var2);
	}

	public ParameterList add(long var1) {
		return this.add(var1, 1);
	}

	public ParameterList add(long var1, int var3) {
		return this.add(var1, var3);
	}

	public ParameterList add(short var1) {
		return this.add(var1, 1);
	}

	public ParameterList add(short var1, int var2) {
		return this.add(var1, var2);
	}

	public ParameterList add(byte var1) {
		return this.add(new Byte(var1), 1);
	}

	public ParameterList add(byte var1, int var2) {
		return this.add(var1, var2);
	}

	public ParameterList add(char var1) {
		return this.add(var1, 1);
	}

	public ParameterList add(char var1, int var2) {
		return this.add(var1, var2);
	}

	public ParameterList add(boolean var1) {
		return this.add(var1, 1);
	}

	public ParameterList add(boolean var1, int var2) {
		return this.add(var1, var2);
	}

	public ParameterList add(float var1) {
		return this.add(var1, 1);
	}

	public ParameterList add(float var1, int var2) {
		return this.add(var1, var2);
	}

	public ParameterList add(double var1) {
		return this.add(var1, 1);
	}

	public ParameterList add(double var1, int var3) {
		return this.add(var1, var3);
	}

	public ParameterList add(LPDWORD var1) {
		return this.add(var1, 1);
	}

	public ParameterList add(DWORD var1) {
		return this.add(var1, 0);
	}

	public Object[] getArguments() {
		return this.arguments.toArray();
	}

	public int[] getUsageDescrption() {
		if (this.by_ref_usage_count == this.usage.size()) {
			return null;
		} else {
			int[] var1 = new int[this.usage.size()];

			for (int var2 = 0; var2 < this.usage.size(); ++var2) {
				Integer var3 = (Integer) this.usage.get(var2);
				var1[var2] = var3;
			}

			return var1;
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Object[] var1 = this.getArguments();
		int[] var2 = this.getUsageDescrption();
		ParameterList var3 = new ParameterList();
		int var4 = var1.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			if (var2 == null) {
				var3.add(var1[var5]);
			} else {
				var3.add(var1[var5], var2[var5]);
			}
		}

		return var3;
	}

	@SuppressWarnings("unused")
	private Object cloneObject(Object var1) throws CloneNotSupportedException {
		if (var1 instanceof Boolean) {
			Boolean var14 = (Boolean) var1;
			return new Boolean(var14);
		} else if (var1 instanceof Byte) {
			Byte var13 = (Byte) var1;
			return new Byte(var13);
		} else if (var1 instanceof Character) {
			Character var12 = (Character) var1;
			return new Character(var12);
		} else if (var1 instanceof Double) {
			Double var11 = (Double) var1;
			return new Double(var11);
		} else if (var1 instanceof Float) {
			Float var10 = (Float) var1;
			return new Float(var10);
		} else if (var1 instanceof Integer) {
			Integer var9 = (Integer) var1;
			return new Integer(var9);
		} else if (var1 instanceof Long) {
			Long var8 = (Long) var1;
			return new Long(var8);
		} else if (var1 instanceof Short) {
			Short var7 = (Short) var1;
			return new Short(var7);
		} else if (var1 instanceof String) {
			return new String(var1.toString());
		} else if (var1 instanceof StringBuffer) {
			return new StringBuffer(var1.toString());
		} else if (var1 instanceof Pointer) {
			Pointer var6 = (Pointer) var1;
			return new Pointer(var6.getBytes());
		} else if (var1 instanceof byte[]) {
			byte[] var5 = (byte[]) ((byte[]) var1);
			byte[] var3 = new byte[var5.length];
			System.arraycopy(var5, 0, var3, 0, var5.length);
			return var3;
		} else {
			if (var1 instanceof Cloneable) {
				try {
					Method var2 = var1.getClass().getMethod("clone", (Class[]) null);
					return var2.invoke(var1, (Object[]) null);
				} catch (Exception var4) {
					;
				}
			}

			return new CloneNotSupportedException(var1.getClass().getName() + " is not cloneable");
		}
	}

	public boolean isEmpty() {
		return this.arguments.isEmpty();
	}

	public Object getArgument(int var1) {
		return this.arguments.get(var1);
	}

	public int getUsage(int var1) {
		return (Integer) this.usage.get(var1);
	}
}
