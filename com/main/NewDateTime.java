package main;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class NewDateTime {

    public static void main(String[] args){
        //note 创建日期 LocalDate
        LocalDate date = LocalDate.of(2019, 6, 26);
        date = LocalDate.parse("2019-04-28");
         date=LocalDate.now();
        System.out.println(date.getYear()+";"+date.getMonthValue()+";"+date.getDayOfMonth());
        System.out.println(date.isLeapYear());

        //note 使用TemporalField读取LocalDate的值。ChronoFiled枚举了TemporalField的实现类。
        System.out.println(date.get(ChronoField.YEAR));

        // 一天中的时间通过LocalTime类表示
        LocalTime time = LocalTime.of(12, 33, 11);
        time = LocalTime.parse("12:33:11");
        time = LocalTime.now().withNano(0);

        //合并的日期时间类: LocalDateTime
        LocalDateTime dateTime = LocalDateTime.of(2019, 6, 26, 0, 0, 0);
        dateTime.of(date, time);
        date.atTime(time);
        time.atDate(date);
        dateTime.toLocalDate();
        dateTime.toLocalTime();
        dateTime=LocalDateTime.now();

        // 机器时间 毫秒 纳秒
        Instant instant=Instant.now();
        System.out.println(instant.getNano()+";"+System.currentTimeMillis());

        //时间范围区间的表：Duration 和 Period duration主要用于衡量秒和纳秒之间的时间长短，

        Duration duration = Duration.between( LocalDateTime.parse("2019-04-23T12:00:00"),LocalDateTime.now());
        System.out.println(duration.get(ChronoUnit.SECONDS));

        Period period = Period.between(LocalDate.parse("2019-04-23"), LocalDate.now());
        System.out.println(period.getDays());

        //日期，时间的操控和赋值。


        // TemporalAdjuster 创建一些特殊的日期时间 可以自定义TemporalAdjuster的实现
        LocalDate date12 = date.with(TemporalAdjusters.lastDayOfMonth());

        //打印格式化日期的实现
        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        //可以通过DateTimeFormatter来自定义格式
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendText(ChronoField.YEAR)
                .appendLiteral("年").appendText(ChronoField.MONTH_OF_YEAR).appendLiteral("月").toFormatter();
        System.out.println(dateTime.format(formatter));

    }
}
