/**
 * Pinganfu.com Inc. Copyright (c) 2003-2013 All Rights Reserved.
 */
package com.dtask.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

	/** yyyyMMddHHmmss */
	public final static String LONG_FORMAT = "yyyyMMddHHmmss";

	/** yyyy-MM-dd HH:mm:ss */
	public final static String LONG_WEB_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/** yyyyMMddHHmmssSSS */
	public final static String LONG_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

	/** yyyy-MM-dd */
	public final static String WEB_FORMAT = "yyyy-MM-dd";


	public static String getTimestamp()
	{
		SimpleDateFormat df = new SimpleDateFormat(LONG_WEB_FORMAT);//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
	}

}
