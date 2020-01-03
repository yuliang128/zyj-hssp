package com.hand.hap.core.util;

import java.time.format.DateTimeFormatter;


/**
 * <p>
 *  时间格式
 * </p>
 *
 * @author rui.shi@hand-china.com  2019/03/2019/3/6
 */
public enum TimeFormat {

	/**
	 * 短时间格式
	 */
	SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
	SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
	SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
	SHORT_DATE_PATTERN_NONE("yyyyMMdd"),

	/**
	 * 长时间格式
	 */
	LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),
	LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
	LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
	LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),

	/**
	 * 长时间格式 带毫秒
	 */
	LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
	LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
	LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),
	LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS");

	private transient DateTimeFormatter formatter;

	TimeFormat(String pattern) {
		formatter = DateTimeFormatter.ofPattern(pattern);
	}

	public DateTimeFormatter getFormatter() {
		return formatter;
	}


}
