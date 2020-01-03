package com.hand.hap.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * <p>
 * 日期工具类： 日期：不带时分秒的日期 时间：带时分秒的日期
 * </p>
 *
 * @author rui.shi@hand-china.com 2019/03/2019/3/6
 */
public class DateUtils {

    public static final String DATE_SCOPE_0_7 = "000-007";
    public static final String DATE_SCOPE_0_30 = "000-030";
    public static final String DATE_SCOPE_0_90 = "000-090";
    public static final String DATE_SCOPE_0_180 = "000-180";
    public static final String DATE_SCOPE_0_365 = "000-365";

    /**
     * 获取默认时间格式: yyyy-MM-dd HH:mm:ss
     */
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER =
                    TimeFormat.LONG_DATE_PATTERN_LINE.getFormatter();


    /**
     * 获取当前日期：
     *
     * @return 格式为 yyyy-MM-dd 的日期
     */
    public static Date getCurrentDate() {
        return toSimpleDate(new Date());
    }

    /**
     * 获取日期范围的下限
     *
     * @param dateScope
     * @author mouse 2019-03-13 18:00
     * @return java.util.Date
     */
    public static Date getDateScopeFrom(String dateScope) {
        switch (dateScope) {
            case DATE_SCOPE_0_7:
                return addDay(getCurrentDate(), -7);
            case DATE_SCOPE_0_30:
                return addDay(getCurrentDate(), -30);
            case DATE_SCOPE_0_90:
                return addDay(getCurrentDate(), -90);
            case DATE_SCOPE_0_180:
                return addDay(getCurrentDate(), -180);
            case DATE_SCOPE_0_365:
                return addDay(getCurrentDate(), -365);
            default:
                return null;
        }
    }


    /**
     * 获取日期范围的上限
     *
     * @param dateScope
     * @author mouse 2019-03-13 18:00
     * @return java.util.Date
     */
    public static Date getDateScopeTo(String dateScope) {
        switch (dateScope) {
            case DATE_SCOPE_0_7:
            case DATE_SCOPE_0_30:
            case DATE_SCOPE_0_90:
            case DATE_SCOPE_0_180:
            case DATE_SCOPE_0_365:
                addDay(getCurrentDate(), 1);
            default:
                return null;
        }
    }

    /**
     * 时间转日期
     *
     * @param dateTime 时间
     * @return 日期
     */
    public static Date toSimpleDate(Date dateTime) {
        return java.sql.Date.valueOf(asLocalDate(dateTime));
    }

    /**
     * 时间、日期格式化
     * 
     * @param dateTime 日期、时间
     * @param dateTimeFormatter 目标格式
     * @return 日期或时间的字符串形式
     */
    public static String dateTimeFormat(Date dateTime, DateTimeFormatter dateTimeFormatter) {

        return asLocalDateTime(dateTime).format(dateTimeFormatter);
    }



    private static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 日期加减
     *
     * @param date 需要处理的日期
     * @param addDay 加的天数
     * @return 加过之后的天数
     */
    public static Date addDay(Date date, int addDay) {
        if (date == null) {
            return null;
        }
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.DAY_OF_YEAR, addDay);
        Date resultDate = calendarToDate(calendar);
        return resultDate;
    }


    /**
     * 日期加1
     *
     * @param date 需要处理的日期
     * @return 加过之后的天数
     */
    public static Date datePlusOne(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        return c.getTime();
    }


    /**
     * 把一个字符串转换为指定的日期格式,默认是yyyy-MM-dd
     *
     * @param strDate
     * @param formater
     * @author mouse 2019-03-13 18:18
     * @return java.util.Date
     */
    public static Date str2Date(String strDate, String formater) {
        String localFormater = null;
        if (strDate == null) {
            return null;
        }
        if (formater == null) {
            localFormater = "yyyy-MM-dd";
        }
        SimpleDateFormat df = new SimpleDateFormat(localFormater);
        Date date = new Date();
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            if (formater != null) {
                pe.getStackTrace();
            } else {
                localFormater = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat dft = new SimpleDateFormat(localFormater);

                try {
                    date = dft.parse(strDate);
                } catch (ParseException pe2) {
                    pe2.getStackTrace();
                }
            }
        }
        return date;
    }


    /**
     * 把一个日期转换为指定的格式,默认是yyyy-MM-dd
     *
     * @param formater
     * @author mouse 2019-03-13 18:18
     * @return java.util.Date
     */
    public static String date2Str(Date date, String formater) {
        if (date == null) {
            return null;
        }
        if (formater == null) {
            formater = "yyyy-MM-dd";
        }
        SimpleDateFormat df = new SimpleDateFormat(formater);
        SimpleDateFormat sdf = new SimpleDateFormat(formater);

        String str = null;
        str = sdf.format(date);
        return str;
    }

    /**
     * 月份加减
     *
     * @param date 需要处理的日期
     * @param addMonth 加的月数
     * @return 加过之后的日期
     */
    public static Date addMonth(Date date, int addMonth) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.MONTH, addMonth);
        Date resultDate = calendarToDate(calendar);
        return resultDate;
    }

    /**
     * 年份加减
     *
     * @param date 需要处理的日期
     * @param addYear 加的年数
     * @return 加过之后的日期
     */
    public static Date addYear(Date date, int addYear) {
        Calendar calendar = dateToCalendar(date);
        calendar.add(Calendar.YEAR, addYear);
        Date resultDate = calendarToDate(calendar);
        return resultDate;
    }


    /**
     * Date 转化Calendar
     *
     * @param date
     * @return
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Calendar 转化Date
     *
     * @param calendar
     * @return
     */
    public static Date calendarToDate(Calendar calendar) {
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 获取当月的第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthFirstDay(Date date) {
        Calendar calendar = dateToCalendar(date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-01");
        String format = sdf.format(date);
        Date date2 = str2Date(format, "yyyy-MM-dd");
        return date2;
    }

    /**
     * 获取当月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthLastDay(Date date) {
        Calendar calendar = dateToCalendar(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        java.text.Format formatter = new SimpleDateFormat("yyyy-MM-" + maxDay);
        String format = formatter.format(calendar.getTime());
        Date date2 = str2Date(format, "yyyy-MM-dd");
        return date2;
    }

    /**
     * 获取上n个月或下n个月相同的日期(注意,如果该日期是本月最后一天,那么获取的上个月也是最后一天,举例,本月是3月31号,上个月就是2月28号)
     * 比如参数填写-1,则返回的是上个月相同的日期,参数填写1,返回的是下个月相同的日期,以此类推
     *
     * @param date
     * @return
     */
    public static Date getMonthSameDay(Date date, int n) {
        Date resultDate = null;
        Date monthLastDay = getMonthLastDay(date);
        String s1 = date2Str(monthLastDay, "yyyy-MM-dd");
        String s2 = date2Str(date, "yyyy-MM-dd");
        // 如果日期为该月最后一天,则之前月也去最后一天
        if (s1.equals(s2)) {
            // 获取该月第一天
            Date monthFirstDay = getMonthFirstDay(date);
            // 先对月份进行加减
            monthFirstDay = addMonth(monthFirstDay, n);
            // 上个月的最后一天
            Date beforLastDate = getMonthLastDay(monthFirstDay);
            resultDate = beforLastDate;
        } else {
            resultDate = addMonth(date, n);
        }
        return resultDate;
    }

    /**
     * 获取指定日期时间戳
     *
     * @param date
     * @author guiyuting 2019-05-09 15:20
     * @return
     */
    public static Long getTimeStamp(Date date) {
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        Calendar c = Calendar.getInstance();
        c.setTimeZone(tz);
        c.setTime(date);
        return c.getTimeInMillis();
    }
}
