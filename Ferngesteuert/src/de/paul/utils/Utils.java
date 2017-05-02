package de.paul.utils;

public class Utils {
	
	public static String normalize(String str) {
		StringBuilder sb = new StringBuilder(30);
		char[] chars = new char[str.length()];
		str.getChars(0, str.length(), chars, 0);
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (('a' <= c && 'z' >= c) || ('A' <= c && 'Z' >= c) || ('0' <= c && '9' >= c))
				sb.append(c);
		}
		return sb.toString();
	}

	public static String normWithSpace(String str) {
		StringBuilder sb = new StringBuilder(30);
		char[] chars = new char[str.length()];
		str.getChars(0, str.length(), chars, 0);
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (('a' <= c && 'z' >= c) || ('A' <= c && 'Z' >= c) || ('0' <= c && '9' >= c) || ' ' == c)
				sb.append(c);
		}
		return sb.toString();
	}
}
