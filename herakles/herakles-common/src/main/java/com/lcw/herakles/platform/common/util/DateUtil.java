package com.lcw.herakles.platform.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {


    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss:SSS";

    public static final String DEFAULT_MAX_DATE_VALUE = "9999-12-31";
    public static final String DEFAULT_MIN_DATE_VALUE = "1990-01-01";

    public static final String START_DATE_TIME = " 00:00:00:000";
    public static final String END_DATE_TIME = " 23:59:59:999";

    public static void main(String[] args) {
        // String dateStr = "2015-03-02";
        // Date date = DateUtils.getDate(dateStr, YYYYMMDD);
        // Date endDate = DateUtils.add(date, Calendar.YEAR, 1);
        // endDate = DateUtils.add(endDate, Calendar.DATE, -1);
        // System.out.println(DateUtils.formatDate(endDate, YYYYMMDD));

        // Date preDate = DateUtils.getDate("2015-09-10", YYYYMMDD);
        // Date nextDate = DateUtils.getDate("2015-09-10", YYYYMMDD);
        // System.out.println(DateUtils.betweenDays(preDate, nextDate));

        // String dateStr = "2015-07-11";
        // String dateStr1 = "2015-06-12";
        // System.out.println(DateUtils.getLastDateOfMonth(dateStr));
        // System.out.println(dateStr1.compareTo(dateStr));
        @SuppressWarnings("unused")
        List<Date> dates = findDates(add(new Date(), Calendar.DAY_OF_YEAR,-100),new Date());
        System.out.println(DateUtil.formatDate(new Date(), YYYYMMDDHHMMSSSSS));
    }

    public static Date getDate(String dateStr, String format) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        try {
            return dateformat.parse(dateStr);
        } catch (ParseException e) {
            // 出生年月
            if (dateStr.matches("\\d{4}-\\d{2}")) {
                dateStr = dateStr + "-01";
                return getDate(dateStr, format);
            }
            log.error("error in date format {}", e);
        }
        return null;
    }

    public static String formatDate(Date date, String format) {
        if (date == null)
            return null;
        SimpleDateFormat dateformat = new SimpleDateFormat(format);
        return dateformat.format(date);
    }

    public static Date maxDate(Date a, Date b) {
        return a.after(b) ? a : b;
    }

    public static Date addHours(Date date, int hour) {
        return add(date, Calendar.HOUR_OF_DAY, hour);
    }

    public static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    public static Date getStartDate(Date date) throws ParseException {
        String dateStr = formatDate(date, YYYYMMDD);
        dateStr = dateStr + START_DATE_TIME;
        return getDate(dateStr, YYYYMMDDHHMMSSSSS);
    }

    public static Date getEndDate(Date date) throws ParseException {
        String dateStr = formatDate(date, YYYYMMDD);
        dateStr = dateStr + END_DATE_TIME;
        return getDate(dateStr, YYYYMMDDHHMMSSSSS);
    }

    public static Date getStartDate(String dateStr) {
        Date date = getDate(dateStr, YYYYMMDD);
        dateStr = formatDate(date, YYYYMMDD);
        dateStr = dateStr + START_DATE_TIME;
        return getDate(dateStr, YYYYMMDDHHMMSSSSS);
    }

    public static Date getEndDate(String dateStr) {
        Date date = getDate(dateStr, YYYYMMDD);
        dateStr = formatDate(date, YYYYMMDD);
        dateStr = dateStr + END_DATE_TIME;
        return getDate(dateStr, YYYYMMDDHHMMSSSSS);
    }

    public static int betweenDays(Date startDate, Date endDate) {
        Calendar beginCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        beginCalendar.setTime(startDate);
        endCalendar.setTime(endDate);

        if (beginCalendar.get(Calendar.YEAR) == endCalendar.get(Calendar.YEAR)) {
            return endCalendar.get(Calendar.DAY_OF_YEAR) - beginCalendar.get(Calendar.DAY_OF_YEAR);
        } else {
            if (beginCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()) {
                int days = beginCalendar.getActualMaximum(Calendar.DAY_OF_YEAR)
                        - beginCalendar.get(Calendar.DAY_OF_YEAR)
                        + endCalendar.get(Calendar.DAY_OF_YEAR);
                for (int i = beginCalendar.get(Calendar.YEAR) + 1; i < endCalendar
                        .get(Calendar.YEAR); i++) {
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.YEAR, i);
                    days += c.getActualMaximum(Calendar.DAY_OF_YEAR);
                }
                return days;
            } else {
                int days = endCalendar.getActualMaximum(Calendar.DAY_OF_YEAR)
                        - endCalendar.get(Calendar.DAY_OF_YEAR)
                        + beginCalendar.get(Calendar.DAY_OF_YEAR);
                for (int i = endCalendar.get(Calendar.YEAR) + 1; i < beginCalendar
                        .get(Calendar.YEAR); i++) {
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.YEAR, i);
                    days += c.getActualMaximum(Calendar.DAY_OF_YEAR);
                }
                return days;
            }
        }
    }

    public static Date getNearestHourDate(long time) {
        long second = time / 1000;
        Date date = new Date(second / 3600 * 3600 * 1000);
        if (second % 3600 >= 1800) {
            date = addHours(date, 1);
        }
        return date;
    }

    /**
     * 当前日期月份的第一天
     * 
     * @param dateStr yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String getFirstDateOfMonth(String dateStr) {
        Date date = getDate(dateStr, YYYYMMDD);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return formatDate(calendar.getTime(), YYYYMMDD);
    }

    /**
     * 当前日期月份的最后一天
     * 
     * @param dateStr yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String getLastDateOfMonth(String dateStr) {
        Date date = getDate(dateStr, YYYYMMDD);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        calendar.roll(Calendar.DATE, -1);
        return formatDate(calendar.getTime(), YYYYMMDD);
    }

    /**
     * 返回当前日期季度的第一天
     * 
     * @param dateStr yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String getFirstDateOfQuarter(String dateStr) {
        Date date = getDate(dateStr, YYYYMMDD);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int factor = 0;
        int month = calendar.get(Calendar.MONTH);
        if (month == Calendar.JANUARY || month == Calendar.APRIL || month == Calendar.JULY
                || month == Calendar.OCTOBER) {
            factor = 2;
        } else if (month == Calendar.FEBRUARY || month == Calendar.MAY || month == Calendar.AUGUST
                || month == Calendar.NOVEMBER) {
            factor = 1;
        } else {
            factor = 0;
        }
        calendar.add(Calendar.MONTH, factor);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        date = calendar.getTime();
        return formatDate(date, YYYYMMDD);
    }

    /**
     * 返回当前日期年份的第一天
     * 
     * @param dateStr yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String getFirstDateOfYear(String dateStr) {
        Date date = getDate(dateStr, YYYYMMDD);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
        return formatDate(calendar.getTime(), YYYYMMDD);
    }

    /**
     * 获取时间段内所有日期
     * 
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List<Date> lDate = Lists.newArrayList();
        // lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            lDate.add(calBegin.getTime());
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
        }
        return lDate;
    }

}
