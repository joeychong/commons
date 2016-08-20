package com.ebizzone.utils;

import com.ebizzone.junit.ByteUtilsTestCase;

public class ByteUtils {
	public static byte[] toBigEndianBytes(int noOfByte, int value) {
		if (noOfByte <= 0) {
			return null;
		}
		byte[] data = new byte[noOfByte];
		for (int i = 0; i < noOfByte; i ++) {
			data[noOfByte - i - 1] = (byte)(0xff & (value >> (i * 8)));
		}
		return data;
	}
	
	public static int toBigEndianInt(byte[] data) {
		if (data == null) {
			return 0;
		}
		int value = 0;
		for (int i = 0; i < data.length; i ++) {
			value += (int)((data[data.length - i - 1] & 0xff) << (8 * i));
		}
		return value;
	}
	
	public static byte[] toBigEndianBytes(int noOfByte, long value) {
		if (noOfByte <= 0) {
			return null;
		} else if (noOfByte > 8) {
			noOfByte = 8;
		}
		byte[] data = new byte[noOfByte];
		for (int i = 0; i < noOfByte; i ++) {
			data[noOfByte - i - 1] = (byte)(0xff & (value >> (i * 8)));
		}
		return data;
	}
	
	public static long toBigEndianLong(byte[] data) {
		if (data == null) {
			return 0;
		}
		long value = 0;
		for (int i = 0; i < data.length; i ++) {
			value += ((long) data[data.length - i - 1] & (long)0xff) << (8 * i);
		}
		return value;
	}
	
	public static byte[] toSignedBigEndianBytes(int noOfByte, int value) {
		if (noOfByte <= 0) {
			return null;
		}
		byte[] data = new byte[noOfByte];
		for (int i = 0; i < noOfByte; i ++) {
			if (i == noOfByte - 1) {
				data[noOfByte - i - 1] = (byte)(0x7f & (value >> (i * 8)));
			} else {
				data[noOfByte - i - 1] = (byte)(0xff & (value >> (i * 8)));
			}
		}
		if (value < 0) {
			data[noOfByte - 1] |= 0x80;
		}
		// 8 4 2 1
		return data;
	}
	
	public static int toSignedBigEndianInt(byte[] data) {
		if (data == null) {
			return 0;
		}
		int value = 0;
		boolean negative = (data[data.length -1] & 0x80) == 0x80;
		
		for (int i = 0; i < 4; i ++) {
			if (i < data.length) {
				value += (int)((data[data.length - i - 1] & 0xff) << (8 * i));
			} else {
				if (negative) {
					value += (int)(0xff) << (8 * i);
				}
			}
		}
		
		if (negative) {
			value |= Integer.MIN_VALUE;
		}

		return value;
	}
	
	public static byte[] toSignedBigEndianBytes(int noOfByte, long value) {
		byte[] data = new byte[noOfByte];
		for (int i = 0; i < noOfByte; i ++) {
			data[noOfByte - i - 1] = (byte)(0xff & (value >> (i * 8)));
		}
		return data;
	}
	
	public static long toSignedBigEndianLong(byte[] data) {
		if (data == null) {
			return 0;
		}
		long value = 0;
		boolean negative = (data[data.length -1] & 0x80) == 0x80;
		ByteUtilsTestCase t = new ByteUtilsTestCase();
		System.out.println(t.toBinary(data, true));
		for (int i = 0; i < 8; i ++) {
			if (i < data.length) {
				value += (long)((data[data.length - i - 1] & 0xff) << (8 * i));
			} else {
				if (negative) {
					value += (long)(0xff) << (8 * i);
				}
			}
		}
		
		if (negative) {
			value |= Long.MIN_VALUE;
		}
		
		System.out.println(t.toBinary(ByteUtils.toBigEndianBytes(8, value), true));

		return value;
	}
	public static byte[] toLittleEndianBytes(int noOfByte, long value) {
		return null;
	}
	
	public static long toLittleEndianLong(byte[] data) {
		return 0;
	}
	
	public static final String ENCODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890_-";
	
	public static String encode(long value) {
		StringBuilder sb = new StringBuilder();
		// 1 2 4 8 32 64
		
		int data;
		for (int i = 0; i < 64; i+= 6) {
			data = (int) value & 63;
			value = value >> 6;
			sb.append(ENCODE.charAt(data));
		}
		
		return sb.toString();
	}
	
	public static long decode(String value) {
		int l = value.length();
		long v = 0;
		for (int i = 0; i < l; i++) {
			v = v | ENCODE.indexOf(value.charAt(l - i - 1));
			if (i != l - 1) {
				v = v << 6;
			}
		}
		return v;
	}
}
