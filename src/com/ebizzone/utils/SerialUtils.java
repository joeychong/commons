package com.ebizzone.utils;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Random;

public class SerialUtils {

	private SerialUtils() {

	}
	
	protected static final String BASE_CODEC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	protected static final String[] BASE_CODECS = {
		"ABCDEFGHIJKLMNOPQRSTUVWXYZ123456",
		"CDEFGHIJKLMNOPABSTUVWXYZ12345678",
		"EFGHIJKLMNOPQRCDUVWXYZ1234567890",
		"GHIJKLMNOPQRSTEFWXYZ1234567890AB",
		"IJKLMNOPQRSTUVGHYZ1234567890ABCD",
		"KLMNOPQRSTUVWXIJ1234567890ABCDEF",
		"MNOPQRSTUVWXYZKL34567890ABCDEFGH",
		"OPQRSTUVWXYZ12MN567890ABCDEFGHIJ",
		"QRSTUVWXYZ1234OP7890ABCDEFGHIJKL"
	};
	
	protected static final String[] RANDOM_CODEC = {
		"3DNCMEXSL4ARJFWZ25UYTQVK6O1HBGPI",
		"DYILNSPB1K6X5HWM3AJE2G7FTCZ8V4UO",
		"87D2CQ9LOMNHZFP13IUEKRJ06V4WYXG5",
		"L46ZR71T5IOKHE983PGMJ0QAN2XSYFBW",
		"8UYIBJKMO3D26R7V1PSALHGCT9504QZN",
		"NAV7LE53D6IXP290OSBMJ18WCKRUTFQ4",
		"W4DPT0QC3YRMNBU9GHVOE78X5LSK6AZF",
		"RT9X5IPWHB1DNZEOJQFUGMSC67V0YA28",
		"R0TQ7UIS938DJV1LBPZO4EGHWXYF2CKA"
	};
		
	public static void main(String[] args) {
		SecureRandom random = new SecureRandom();
		random.setSeed(System.currentTimeMillis());
		for (String codec : BASE_CODECS) {
			char[] c = codec.toCharArray();
			for (int i = 0; i < c.length; i++) {
				char x = c[i];
				int r = random.nextInt(c.length);
				c[i] = c[r];
				c[r] = x;
			}
			System.out.println("\"" + new String(c) + "\",");
		}
		
		
		long testLong = 0; 
		String test = null;
		
		for (int i = 0; i < 10; i++) {
			testLong = random.nextLong();
			test = encodeSerialKey(testLong);
			System.out.println(test);
			try {
				if (testLong == decodeSerialKey(test)) {
					System.out.println("PASSED");
				}
			} catch (ParseException e) {
				System.out.println("FAILED");
			}
		}
	}

	private static final int X_FACTOR = RANDOM_CODEC[0].length();
	
	public static String encodeSerialKey(long input) {
		int[] rand = new int[3];
		long ori = input;
		StringBuilder sb = new StringBuilder();
		
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < 3; i ++) {
			rand[i] = random.nextInt(36);
			random.setSeed(System.currentTimeMillis());
			sb.append(BASE_CODEC.charAt(rand[i]));
		}
		
		long seed = (rand[0] * rand[1]) + rand[2];
		int p = 0;
		int data = 0;
		
		Random r = new Random(seed);
		
		for (int i = 3; i < 16; i++) {

			data = (int) ori & 31;
			ori = ori >> 5;

			p = r.nextInt(RANDOM_CODEC.length);
			data = data - r.nextInt(X_FACTOR) + i;
			if (data < 0) {
				data += X_FACTOR;
			} else if (data >= X_FACTOR) {
				data -= X_FACTOR;
			}

			sb.append(RANDOM_CODEC[p].charAt(data));
		}
		
		return sb.toString();
	}
	
	public static long decodeSerialKey(String code) throws ParseException {
		long result = 0;
		
		if (code == null || !code.matches("[" + BASE_CODEC + "]{16}")) {
			throw new ParseException("Invalid code!", 0);
		}
		
		int[] rand = new int[3];
		for (int i = 0; i < 3; i ++) {
			rand[i] = BASE_CODEC.indexOf(code.substring(i, i + 1));
		}
		
		long seed = (rand[0] * rand[1]) + rand[2];
		
		int p = 0;
		long data = 0;
		Random r = new Random(seed);
		for (int i = 3; i < 16; i++) {
			p = r.nextInt(RANDOM_CODEC.length);
			data = RANDOM_CODEC[p].indexOf(code.substring(i, i + 1));
			data = data + r.nextInt(X_FACTOR) - i;
			if (data < 0) {
				data += X_FACTOR;
			} else if (data >= X_FACTOR) {
				data -= X_FACTOR;
			}
			data = (data << (i - 3) * 5);
			result = result | data;
		}
		return result;
	}
	
	
}