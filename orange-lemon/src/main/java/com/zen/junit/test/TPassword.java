package com.zen.junit.test;

import java.util.Random;

import com.van.halley.util.Md5Tool;

public class TPassword {
	public static void main(String[] args) {
		System.out.println(t1("98cde464734b8634e517bb327f9942ae0915ce2a"));
		//System.out.println(randomString(1));
		
	}

	public static String t1(String md5) {
		for (int i = 1; i < 9; i++) {
			for (int j = 0; j < Math.pow(100,i); j++) {
				String password = randomString(i);
				//System.out.println(password);
				if (Md5Tool.encodeMd5(password).equals(md5)) {
					return password;
				} else if (Md5Tool.encodeMd5(Md5Tool.encodeMd5(password))
						.equals(md5)) {
					return password;
				}
			}
		}
		
		return null;
	}

	private static Random randGen = null;
	private static char[] numbersAndLetters = null;

	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
					+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
			// numbersAndLetters =
			// ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
			// randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		}
		return new String(randBuffer);
	}
}
