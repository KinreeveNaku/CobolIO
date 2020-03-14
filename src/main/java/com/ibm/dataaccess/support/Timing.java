/**
 * 
 */
package com.ibm.dataaccess.support;

import java.math.BigInteger;
import java.util.Timer;
import java.util.TimerTask;

import com.ibm.dataaccess.accessor.impl.cobol.Comp1Accessor;
import com.ibm.dataaccess.accessor.impl.cobol.Comp2Accessor;
import com.ibm.dataaccess.accessor.impl.cobol.Comp3Accessor;
import com.ibm.dataaccess.accessor.impl.cobol.Comp4Accessor;
import com.ibm.dataaccess.accessor.impl.cobol.ZonedDecimalAccessor;

import net.sf.cobol2j.FieldFormat;

/**
 * @author Andrew
 *
 */
@SuppressWarnings("unused")
public class Timing {
	Comp1Accessor comp1;
	Comp2Accessor comp2;
	Comp3Accessor comp3;
	Comp4Accessor comp4;
	ZonedDecimalAccessor extDec;
	private static final byte[] packedBytes = new byte[] {(byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x22, (byte) 0xFC};
	
	private static final byte[] floatBytes = new byte[] {(byte) 0b10000000, (byte) 0b10000000, 0b00001111, (byte) 0b11110000};
	private static final byte[] doubleBytes = new byte[] {(byte) 0b10000000, 0b0, 0b1000, 0b0, 0b0, 0b00011111, (byte) 0b11111111, (byte) 0b11111111};
	private static final byte[] binaryBytes = new byte[] {(byte) 0b10001111, 0b01010101, (byte) 0b11111111, 0b00110011};
	private static final byte[] extDecBytes = new byte[] {(byte) 0xF3, (byte) 0xF5, (byte) 0xF8, (byte) 0xF2, (byte) 0xF9, (byte) 0xFC};
	private static final byte[] displayBytes = new byte[] {0x31, 0x37, 0x36, 0x35};
	public Timing() {
		FieldFormat format = new FieldFormat();
		format.setDecimal(BigInteger.valueOf(2));
		format.setImpliedDecimal(true);
		format.setPicture("S9(9)V9(5)");
		format.setName("test");
		format.setSigned(true);
		format.setType("3");
		format.setSize(BigInteger.valueOf(11));
		comp3 = new Comp3Accessor(format);
		System.out.println(comp3.convert(packedBytes));
		System.out.println(comp3.toDouble(packedBytes));
		System.out.println(comp3.toFloat(packedBytes));
	}
	
	public final void timing() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			private int i = 0;
			@Override
			public void run() {
				
				System.out.println(i++);
				
			}
		};
		timer.scheduleAtFixedRate(task, +0L, +5000L);
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Timing timing = new Timing();
	}
}
