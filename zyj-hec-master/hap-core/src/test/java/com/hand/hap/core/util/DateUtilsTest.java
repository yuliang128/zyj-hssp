package com.hand.hap.core.util;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * <p>
 * DateUtils 测试类
 * </p>
 *
 * @author rui.shi@hand-china.com  2019/03/2019/3/6
 */
public class DateUtilsTest {

	@Test
	public void getCurrentDate() {
		Date currentDate = DateUtils.getCurrentDate();
		System.out.println(currentDate);   // 2019-03-06
	}

	@Test
	public void toSimpleDate() {
		Date DateWithOutTime = DateUtils.toSimpleDate(new Date());
		System.out.println(DateWithOutTime);
	}

	@Test
	public void dateTimeFormat() {
		String  now = DateUtils.dateTimeFormat(new Date(), TimeFormat.LONG_DATE_PATTERN_DOUBLE_SLASH.getFormatter());
		System.out.println(now);

		String  now2 = DateUtils.dateTimeFormat(DateUtils.getCurrentDate(), TimeFormat.LONG_DATE_PATTERN_DOUBLE_SLASH.getFormatter());
		System.out.println(now2);

	}


	@Test
	public void testLocalDate() {
		LocalDate localDate = LocalDate.now();
		LocalDateTime localDateTime= LocalDateTime.now();

		System.out.println(localDate);
		System.out.println(localDateTime);

		Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		System.out.println(date);
	}
}