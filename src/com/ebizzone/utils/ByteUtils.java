package com.ebizzone.utils;

public class ByteUtils {
	public static byte[] toBytes(int noOfByte, int value) {
		byte[] data = new byte[noOfByte];
		for (int i = 0; i < noOfByte; i ++) {
			data[noOfByte - i - 1] = (byte)(0xff & (value >> (i * 8)));
		}
		return data;
	}
	
	public static int toInt(byte[] data) {
		int value = 0;
		for (int i = 0; i < data.length; i ++) {
			value += (int)((data[data.length - i - 1] & 0xff) << (8 * i));
		}
		
		return value;
	}
	
	/*public static byte[] toBytes(int noOfByte, int value) {
		byte[] data = new byte[noOfByte];
		for (int i = 0; i < noOfByte; i ++) {
			data[i] = (byte)(0xff & (value >> (i * 8)));
		}
		
		return data;
	}
	
	public static int toInt(byte[] data) {
		int value = 0;
		for (int i = 0; i < data.length; i ++) {
			value += (int)((data[i] & 0xff) << (8 * i));
		}
		
		return value;
	}*/
	
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
