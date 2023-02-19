package com.yan.superfw_weixin_push;

import com.yan.superfw_weixin_push.controller.Push;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@SpringBootApplication
@EnableScheduling // 开启定时任务
public class SuperfwWeixinPushApplication {

    @Autowired
    private Push push;

    public static void main(String[] args) {
        SpringApplication.run(SuperfwWeixinPushApplication.class, args);

    }


    // 定时 早8点推送  0秒 0分 8时
    @Scheduled(cron = "0 0 8 * * ?")
    public void morningPush(){
        push.morningPush("morning");
        log.info("定时消息发送成功!");
    }

    // 定时 晚10点30分推送  0秒 30分 10时
    @Scheduled(cron = "0 30 22 * * ?")
    public void eveningPush(){
        push.eveningPush("evening");
        log.info("定时消息发送成功!");
    }
}
