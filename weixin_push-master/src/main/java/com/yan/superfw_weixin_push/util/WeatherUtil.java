package com.yan.superfw_weixin_push.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yan.superfw_weixin_push.entity.Weather;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WeatherUtil {

    //行政区域代码
    public static String districtId;
    //百度地图开发平台AK
    public static String accessKey;

    @Value("${ling.districtId}")
    public void setDistrict_id(String district_id) {
        WeatherUtil.districtId = district_id;
    }

    @Value("${ling.accessKey}")
    public void setAk(String ak) {
        WeatherUtil.accessKey = ak;
    }


    /**
     * 百度国内天气查询接口：https://api.map.baidu.com/weather/v1/?district_id=222405&data_type=all&ak=你的ak
     * 请求类型为all的响应示例参考链接：https://lbsyun.baidu.com/index.php?title=webapi/weather
     *
     * @return
     */
    public static Weather getWeather(){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> map = new HashMap<>();
        map.put("district_id",districtId);
        map.put("data_type","all");
        map.put("ak",accessKey);
        String res = restTemplate.getForObject(
                "https://api.map.baidu.com/weather/v1/?district_id={district_id}&data_type={data_type}&ak={ak}",
                String.class,
                map);
        JSONObject json = JSONObject.parseObject(res);
        JSON location=json.getJSONObject("result").getJSONObject("location");   //位置数据
        JSONArray forecasts = json.getJSONObject("result").getJSONArray("forecasts");   //预报数据（7天）
        List<Weather> weathers = forecasts.toJavaList(Weather.class);
        JSONObject now = json.getJSONObject("result").getJSONObject("now"); //未来24h实况数据，当天
        Weather weather = weathers.get(0);
        weather.setText_now(now.getString("text")); //当前天气现象
        weather.setTemp(now.getString("temp")); //当前温度

        HashMap hashMap=JSON.parseObject(location.toString(),HashMap.class);
        Object city=hashMap.get("city");
        weather.setCity((String) city);
        return weather;
    }

}
