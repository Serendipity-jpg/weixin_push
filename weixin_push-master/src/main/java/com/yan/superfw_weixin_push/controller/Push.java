package com.yan.superfw_weixin_push.controller;

import com.yan.superfw_weixin_push.entity.Weather;
import com.yan.superfw_weixin_push.util.APIUtils;
import com.yan.superfw_weixin_push.util.DateUtils;
import com.yan.superfw_weixin_push.util.WeatherUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("weixin")
public class Push {

    @Value("${ling.appId}")
    private String appId;
    @Value("${ling.appSecret}")
    private String appSecret;
    @Value("${ling.users}")
    private List<String> users;
    @Value("${ling.templateIds}")
    private List<String> templateIds;
    @Value("${ling.appointedDay}")
    private String appointedDay;


    private List<String> contentTypeList=Arrays.asList("caihongpi","tiangou","zaoan","wanan");

    @GetMapping("/morningPush")
    public void morningPush(String type){
        String templateId=templateIds.get(type.equals("morning")? 0:1);
        String note = "记得按时吃饭哦❤";
        for (String user:users){
            push(templateId,user,note);
        }
    }

    @GetMapping("/eveningPush")
    public void eveningPush(String type){
        String templateId=templateIds.get(type.equals("evening")? 1:0);
        String note = "不要熬夜看小说哦❤";
        for (String user:users){
            push(templateId,user,note);
        }
    }


    public void push(String templateId,String user,String note){

        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appId);
        wxStorage.setSecret(appSecret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        // 推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(user)
                .templateId(templateId)
                .build();
        // 配置推送消息
        Weather weather = WeatherUtil.getWeather();
        templateMessage.addData(new WxMpTemplateData("date",weather.getDate() + "  "+ weather.getWeek(),"#00BFFF"));
        templateMessage.addData(new WxMpTemplateData("text_now",weather.getText_now(),"#00FFFF"));
        templateMessage.addData(new WxMpTemplateData("low",weather.getLow() + "","#FFA500"));
        templateMessage.addData(new WxMpTemplateData("temp",weather.getTemp() + "","#EE212D"));
        templateMessage.addData(new WxMpTemplateData("high",weather.getHigh()+ "","#FF6347" ));
        templateMessage.addData(new WxMpTemplateData("city",weather.getCity()+"","#FFA500"));

        templateMessage.addData(new WxMpTemplateData("rainbow", APIUtils.getContent(contentTypeList.get(0)),"#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("flatterer",APIUtils.getContent(contentTypeList.get(1))+"","#FFA500"));
        templateMessage.addData(new WxMpTemplateData("morning",APIUtils.getContent(contentTypeList.get(2)),"#FF1493"));
        templateMessage.addData(new WxMpTemplateData("evening",APIUtils.getContent(contentTypeList.get(3)),"#FFA500"));

        templateMessage.addData(new WxMpTemplateData("toDayOfCommemoration", DateUtils.calDayOfCommemoration(new Date()).toString(),"#FF1493"));
        templateMessage.addData(new WxMpTemplateData("appointedDay",this.appointedDay,"#FFA500"));
        templateMessage.addData(new WxMpTemplateData("toAppointedDay",DateUtils.calAppointedDay(new Date()).toString(),"#FFA500"));


        templateMessage.addData(new WxMpTemplateData("note",note,"#000000"));

        try {
            log.info("templateMessage.toJson()="+templateMessage.toJson());
            log.info("wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage)="+wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
        } catch (Exception e) {
            log.info("推送失败：" + e.getMessage());
        }
        log.info("Push推送成功!");
    }



}
