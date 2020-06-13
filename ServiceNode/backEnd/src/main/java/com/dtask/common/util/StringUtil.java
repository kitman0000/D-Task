/**
 * Pinganfu.com Inc. Copyright (c) 2003-2013 All Rights Reserved.
 */
package com.dtask.common.util;



public class StringUtil {

	@SuppressWarnings("unused")
	private static int[] HEXADECIMAL;

	/** 空字符串 "" */
	public static final String EMPTY_STRING = "";
	/**
	 * 逗号 ,
	 */
	public static final String SEPARATOR_COMMA = ",";

	/**
	 * "" - true<br>
	 * " " - true<br>
	 * null - true<br>
	 * "1" - false<br>
	 *
	 * @param str
	 * @return boolean
	 */
	public static boolean isBlank(final String str) {
		int length;
		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	/**
	 *
	 * @param str
	 * @return boolean
	 */
	public static boolean isNotBlank(final String str) {
		int length;
		if ((str == null) || ((length = str.length()) == 0)) {
			return false;
		}
		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}

}
