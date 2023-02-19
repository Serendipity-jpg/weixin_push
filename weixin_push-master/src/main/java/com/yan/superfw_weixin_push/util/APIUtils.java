package com.yan.superfw_weixin_push.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
public class APIUtils {

    //@Value 无法注入static属性
    public static String apiKey;

    @Value("${ling.apiKey}")
    public void setApiKey(String apiKey) {
        APIUtils.apiKey = apiKey;
    }


    /**
     * 调用彩虹屁|舔狗日记|早安心语|晚安心语接口
     * api示例响应：
     * {
     *   "code": 200,
     *   "msg": "success",
     *   "newslist": [
     *     {
     *       "content": "月亮藏进云里，如果你已经睡了，那我要偷偷吻吻你。你比一百只猫猫加起来可爱，我呢，比一百只猫猫加起来好睡，你要不要来试试？"
     *     }
     *   ]
     * }
     * @return
     */
    public static String getContent(String type) {
        String httpUrl = "http://api.tianapi.com/"+type+"/index?key="+apiKey;
        BufferedReader reader = null;
        String result = null;
        StringBuilder sbf = new StringBuilder();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray newslist = jsonObject.getJSONArray("newslist");
        return newslist.getJSONObject(0).getString("content");
    }

//    public static void main(String[] args) {
//        List<String> contentTypeList= Arrays.asList("wanan","caihongpi","tiangou","zaoan");
//        APIUtils.setApiKey("d1cbed3a81a1d4d962f3e027aba1e140");
//        for (int i = 0; i <contentTypeList.size() ; i++) {
//            System.out.println(getContent(contentTypeList.get(i)));
//        }
//    }
}
