package com.ebizzone.junit;

import com.ebizzone.utils.ByteUtils;

import junit.framework.TestCase;

public class ByteUtilsTestCase extends TestCase {

	@Override
	protected void setUp() throws Exception {
		//System.out.println("setup");
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		//System.out.println("shutdown");
		super.tearDown();
	}
	
	public String toBinary(byte data) {
		String binary = "00000000";
		binary = binary + Integer.toBinaryString(0xFF & data);
		return binary.substring(binary.length() - 8);
	}
	
	public String toBinary(byte[] data, boolean bigEndian) {
		StringBuilder sb = new StringBuilder();
		for (byte b : data) {
			if (bigEndian) {
				sb.append(toBinary(b));
			} else {
				sb.insert(0, toBinary(b));
			}
		}
		
		return sb.toString();
	}
	
	// 1 2 4 8 16 32 64 128
	public void testToBigEndian1Byte() {
		byte[] data = null;
		byte zero = 0;
		byte max1Byte = (byte) 127;
		byte min1Byte = (byte) -128;
		
		data = ByteUtils.toBigEndianBytes(-1, 0);
		assertNull(data);
		
		data = ByteUtils.toBigEndianBytes(0, 0);
		assertNull(data);
		
		
		data = ByteUtils.toBigEndianBytes(1, 0);
		assertNotNull(data);
		assertEquals(1, data.length);
		assertEquals(zero, data[0]);
		assertEquals("00000000", toBinary(data[0]));
		assertEquals(0, ByteUtils.toBigEndianInt(data));
		
		data = ByteUtils.toBigEndianBytes(1, 1);
		assertEquals(1, data.length);
		assertEquals(1, data[0]);
		assertEquals("00000001", toBinary(data[0]));
		assertEquals(1, ByteUtils.toBigEndianInt(data));
		
		data = ByteUtils.toBigEndianBytes(1, -1);
		assertEquals(1, data.length);
		assertEquals(-1, data[0]);
		assertEquals("11111111", toBinary(data[0]));
		assertEquals(255, ByteUtils.toBigEndianInt(data));
		
		data = ByteUtils.toBigEndianBytes(1, 127);
		assertEquals(1, data.length);
		assertEquals(max1Byte, data[0]);
		assertEquals("01111111", toBinary(data[0]));
		assertEquals(127, ByteUtils.toBigEndianInt(data));
		
		data = ByteUtils.toBigEndianBytes(1, -128);
		assertEquals(1, data.length);
		assertEquals(min1Byte, data[0]);
		assertEquals("10000000", toBinary(data[0]));
		assertEquals(128, ByteUtils.toBigEndianInt(data));
		
		//1000 0000
		data = ByteUtils.toBigEndianBytes(1, 128);
		assertEquals(1, data.length);
		assertEquals(min1Byte, data[0]);
		assertEquals("10000000", toBinary(data[0]));
		assertEquals(128, ByteUtils.toBigEndianInt(data));
		
		data = ByteUtils.toBigEndianBytes(1, 255);
		assertEquals(1, data.length);
		assertEquals(-1, data[0]);
		assertEquals("11111111", toBinary(data[0]));
		assertEquals(255, ByteUtils.toBigEndianInt(data));
		
		//1 0111 1111
		data = ByteUtils.toBigEndianBytes(1, -129);
		assertEquals(1, data.length);
		assertEquals(max1Byte, data[0]);
		assertEquals("01111111", toBinary(data[0]));
		assertEquals(127, ByteUtils.toBigEndianInt(data));
	}
	
	// test unsigned integer
	public void testBigEndianInt() {
		byte[] data;
		int value, noOfByte;
		
		// one byte unsigned integer only can hold value from 0 - 255
		noOfByte = 1;
		
		// test minimum value
		value = 0;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianInt(data));
		
		// test value right to minimum value
		value = 1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianInt(data));
		
		// test value left to minimum value
		value = -1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertFalse(value == ByteUtils.toBigEndianInt(data));
		
		// test max value
		value = 255;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianInt(data));
		
		// test exceed max value
		value = 256;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertFalse(value == ByteUtils.toBigEndianInt(data));
		
		// one byte unsign int only can hold value from 0 - 65535
		noOfByte = 2;
		
		// test minimum value
		value = 0;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianInt(data));
		
		// test value right to minimum value
		value = 1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianInt(data));
		
		// test value left to minimum value
		value = -1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertFalse(value == ByteUtils.toBigEndianInt(data));
		
		// test max value
		value = 65535;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianInt(data));
		
		// test exceed max value
		value = 65536;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertFalse(value == ByteUtils.toBigEndianInt(data));
		
		// one byte unsigned integer only can hold value from 0 - Int.MAX
		noOfByte = 4;
		
		// test minimum value
		value = 0;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianInt(data));
		
		// test value right to minimum value
		value = 1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianInt(data));
		
		// test value left to minimum value
		value = -1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		// because -1 = 1111 1111 1111 1111 1111 1111 1111 1111
		assertTrue(value == ByteUtils.toBigEndianInt(data));
		
		// test max value that integer can store
		value = 2147483647;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianInt(data));
	}
	
	// test unsigned integer
	public void testBigEndianLong() {
		byte[] data;
		long value;
		int noOfByte;
		
		// one byte unsigned long only can hold value from 0 - 255
		noOfByte = 1;
		
		// test minimum value
		value = 0;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianLong(data));
		
		// test value right to minimum value
		value = 1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianLong(data));
		
		// test value left to minimum value
		value = -1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertFalse(value == ByteUtils.toBigEndianLong(data));
		
		// test max value
		value = 255;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianLong(data));
		
		// test exceed max value
		value = 256;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertFalse(value == ByteUtils.toBigEndianLong(data));
		
		// 2 bytes unsign int only can hold value from 0 - 65535
		noOfByte = 2;
		
		// test minimum value
		value = 0;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianLong(data));
		
		// test value right to minimum value
		value = 1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianLong(data));
		
		// test value left to minimum value
		value = -1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertFalse(value == ByteUtils.toBigEndianLong(data));
		
		// test max value
		value = 65535;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianLong(data));
		
		// test exceed max value
		value = 65536;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertFalse(value == ByteUtils.toBigEndianLong(data));
		
		// 4 bytes unsigned integer only can hold value from 0 - 4294967295
		noOfByte = 4;
		
		// test minimum value
		value = 0;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianLong(data));
		
		// test value right to minimum value
		value = 1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianLong(data));
		
		// test value left to minimum value
		value = -1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertFalse(value == ByteUtils.toBigEndianLong(data));
		
		// test max value
		value = 4294967295L;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianLong(data));
		
		// test exceed max value
		value = 4294967296L;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertFalse(value == ByteUtils.toBigEndianLong(data));
		
		// 4 bytes unsigned integer only can hold value from 0 - Long.MAX
		noOfByte = 8;
		
		// test minimum value
		value = 0;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianLong(data));
		
		// test value right to minimum value
		value = 1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianLong(data));
		
		// test value left to minimum value
		value = -1;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertTrue(value == ByteUtils.toBigEndianLong(data));
		
		// test max value that integer can store
		value = Long.MAX_VALUE;
		data = ByteUtils.toBigEndianBytes(noOfByte, value);
		assertEquals(noOfByte, data.length);
		assertEquals(value, ByteUtils.toBigEndianLong(data));
	}
	
	// 1 2 4 8 16 32 64 128
	public void testToSignedBigEndian1Byte() {
		byte[] data = null;
		byte zero = 0;
		byte max1Byte = (byte) 127;
		byte min1Byte = (byte) -128;
		
		data = ByteUtils.toSignedBigEndianBytes(-1, 0);
		assertNull(data);
		
		data = ByteUtils.toSignedBigEndianBytes(0, 0);
		assertNull(data);
		
		
		data = ByteUtils.toSignedBigEndianBytes(1, 0);
		assertNotNull(data);
		assertEquals(1, data.length);
		assertEquals(zero, data[0]);
		assertEquals("00000000", toBinary(data[0]));
		assertEquals(0, ByteUtils.toSignedBigEndianInt(data));
		
		data = ByteUtils.toSignedBigEndianBytes(1, 1);
		assertEquals(1, data.length);
		assertEquals(1, data[0]);
		assertEquals("00000001", toBinary(data[0]));
		assertEquals(1, ByteUtils.toSignedBigEndianInt(data));
		
		data = ByteUtils.toSignedBigEndianBytes(1, -1);
		assertEquals(1, data.length);
		assertEquals(-1, data[0]);
		assertEquals("11111111", toBinary(data[0]));
		assertEquals(-1, ByteUtils.toSignedBigEndianInt(data));
		
		data = ByteUtils.toSignedBigEndianBytes(1, 127);
		assertEquals(1, data.length);
		assertEquals(max1Byte, data[0]);
		assertEquals("01111111", toBinary(data[0]));
		assertEquals(127, ByteUtils.toSignedBigEndianInt(data));
		
		data = ByteUtils.toSignedBigEndianBytes(1, -128);
		assertEquals(1, data.length);
		assertEquals(min1Byte, data[0]);
		assertEquals("10000000", toBinary(data[0]));
		assertEquals(-128, ByteUtils.toSignedBigEndianInt(data));
		
		//1000 0000
		data = ByteUtils.toSignedBigEndianBytes(1, 128);
		assertEquals(1, data.length);
		assertEquals(0, data[0]);
		assertEquals("00000000", toBinary(data[0]));
		assertEquals(0, ByteUtils.toSignedBigEndianInt(data));
		
		data = ByteUtils.toSignedBigEndianBytes(1, 255);
		assertEquals(1, data.length);
		assertEquals(127, data[0]);
		assertEquals("01111111", toBinary(data[0]));
		assertEquals(127, ByteUtils.toSignedBigEndianInt(data));
		
		//1 0111 1111
		data = ByteUtils.toSignedBigEndianBytes(1, -129);
		assertEquals(1, data.length);
		assertEquals(-1, data[0]);
		assertEquals("11111111", toBinary(data[0]));
		assertEquals(-1, ByteUtils.toSignedBigEndianInt(data));
	}
}
