package com.yan.superfw_weixin_push.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Component
public class DateUtils {

    public static String dayOfCommemoration;

    public static String appointedDay;

    @Value("${ling.dayOfCommemoration}")
    public void setDayOfCommemoration(String dayOfCommemoration) {
        DateUtils.dayOfCommemoration = dayOfCommemoration;
    }

    @Value("${ling.appointedDay}")
    public void setAppointedDay(String appointedDay) {
        DateUtils.appointedDay = appointedDay;
    }

    //计算相识天数
    public static Integer calDayOfCommemoration(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return calDays(DateUtils.dayOfCommemoration,simpleDateFormat.format(date));
    }

    // 计算见面剩余天数
    public static Integer calAppointedDay(Date date) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return calDays(simpleDateFormat.format(date),DateUtils.appointedDay);
    }

    //计算相差天数
    public static Integer calDays(String start,String end) {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Integer day = 0;
        try {
            long time = simpleDateFormat.parse(end).getTime() - simpleDateFormat.parse(start).getTime();
            day =(int) (time / 24/60/60/1000L);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }



}
