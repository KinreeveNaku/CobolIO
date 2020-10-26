/**
 * 
 */
package com.github.cobolio.internal.cobol.datatype;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andrew
 *
 */
public class PackedDecimalTest {
	private final byte[] bytesCase1 = new byte[] {(byte) 0x89, 0x49, 0x28, 0x32, 0x72, 0x16, 0x42, (byte) 0x84, 0x10, 0x4D};
	private final BigDecimal bdCase1 = new BigDecimal("-894928327216.4284104");
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void unpackTest() {
		
		BigDecimal bd = PackedDecimal.unpack(bytesCase1, 7);
		if(!bd.equals(bdCase1)) {
			fail("Unpacking logic failed");
		}
	}
	
	@Test
	public final void packTest() {
		byte[] returnBytes = PackedDecimal.pack(bdCase1, 1);
		if(!Arrays.equals(bytesCase1, returnBytes)) {
			fail("Packing logic failed");
		}
	}

}
