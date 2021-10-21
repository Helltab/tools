package com.helltab.repo.tool.work.str;

import cn.hutool.core.date.DateUnit;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * Topic 日期函数
 *
 * @author helltab
 * @version 1.0
 * @date 2021/10/20 15:16
 */
public class DateUtil {
    public static LocalDate str2date(String dateString, Format pattern) {
        return LocalDate.parse(dateString, pattern.gen());
    }

    public static String nowDate(Format pattern) {
        return pattern.format(LocalDate.now());
    }

    public static String nowDate() {
        return Format.yyyyMMdd2.format(LocalDate.now());
    }
    public static String nowTime(Format pattern) {
        return pattern.format(LocalTime.now());
    }

    public static String nowTime() {
        return Format.HHmmss.format(LocalTime.now());
    }
    public static String nowDateTime(Format pattern) {
        return pattern.format(LocalDateTime.now());
    }
    public static String nowDateTime() {
        return Format.yyyyMMdd2HHmmss.format(LocalDateTime.now());
    }

    public static LocalTime str2time(String timeString, Format pattern) {
        return LocalTime.parse(timeString, pattern.gen());
    }

    public static LocalDateTime str2dateTime(String dateTimeString, Format pattern) {
        return LocalDateTime.parse(dateTimeString, pattern.gen());
    }


    public static void main(String[] args) {
        System.out.println(str2date("20210506", Format.yyyyMMdd));
        System.out.println(str2date("2021_05_06", Format.yyyyMMdd_));
        System.out.println(str2date("2021 05 06", Format.yyyyMMdd0));
        System.out.println(str2date("2021/05/06", Format.yyyyMMdd1));
        System.out.println(nowTime(Format.HHmmssS));
        System.out.println(nowDateTime(Format.yyyyMMddHHmmssS));
    }

    public enum Format {
        yyyyMMdd("yyyyMMdd"),
        yyyyMMdd_("yyyy_MM_dd"),
        yyyyMMdd0("yyyy MM dd"),
        yyyyMMdd1("yyyy/MM/dd"),
        yyyyMMdd2("yyyy-MM-dd"),
        HHmmss("HH:mm:ss"),
        HHmmssS("HH:mm:ss.SSS"),
        yyyyMMddHHmmss("yyyyMMdd HH:mm:ss"),
        yyyyMMdd0HHmmss("yyyy MM dd HH:mm:ss"),
        yyyyMMdd1HHmmss("yyyy/MM/dd HH:mm:ss"),
        yyyyMMdd2HHmmss("yyyy-MM-dd HH:mm:ss"),
        yyyyMMddHHmmssS("yyyyMMdd HH:mm:ss.SSS"),
        yyyyMMdd0HHmmssS("yyyy MM dd HH:mm:ss.SSS"),
        yyyyMMdd1HHmmssS("yyyy/MM/dd HH:mm:ss.SSS"),
        yyyyMMdd2HHmmssS("yyyy-MM-dd HH:mm:ss.SSS"),
        ;
        String value;

        Format(String value) {
            this.value = value;
        }

        DateTimeFormatter gen() {
            return DateTimeFormatter.ofPattern(value);
        }

        String format(TemporalAccessor accessor) {
            return gen().format(accessor);
        }
    }
}
