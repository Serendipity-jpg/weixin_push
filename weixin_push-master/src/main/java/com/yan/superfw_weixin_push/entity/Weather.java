package com.yan.superfw_weixin_push.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//百度API返回数据
public class Weather {
    /**白天风向*/
    private String wd_day;

    /**晚上风向*/
    private String wd_night;

    /**日期，北京时区*/
    private String date;

    /**星期，北京时区*/
    private String week;

    /*最高温度(℃)**/
    private String high;

    /**最低温度(℃)*/
    private String low;

    /**晚上天气现象*/
    private String text_night;

    /**白天天气现象*/
    private String text_day;

    /**晚上风力*/
    private String wc_night;

    /**白天风力*/
    private String wc_day;

    //补充字段
    /**当前天气现象*/
    private String text_now;
    /**当前温度*/
    private String temp;
    /**当前所在城市*/
    private String city;

}
