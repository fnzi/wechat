package cn.tj.fnzi.wechat.bz.service;

import java.util.Date;

import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import cn.tj.fnzi.wechat.fw.handle.WeChatHandler;
import cn.tj.fnzi.wechat.fw.message.req.TextReqMsg;
import cn.tj.fnzi.wechat.fw.message.resp.BaseMsg;
import cn.tj.fnzi.wechat.fw.message.resp.TextMsg;
import cn.tj.fnzi.wechat.fw.util.JSONUtil;

@Service("weatherService")
public class WeatherService implements WeChatHandler<TextReqMsg, BaseMsg> {

    private static String requrl = "http://www.weather.com.cn/data/cityinfo/101030100.html";

    @Override
    public BaseMsg handle(TextReqMsg param) {
        try {
            String ret = Request.Get(requrl).execute().returnContent().asString();
            System.out.println(ret);
            JSONObject weatherJson = JSONUtil.getJSONFromString(ret);

            JSONObject weatherInfo = JSONUtil.getJSONFromString(weatherJson.getString("weatherinfo"));

            String city = weatherInfo.getString("city");
            String low = weatherInfo.getString("temp1");
            String high = weatherInfo.getString("temp2");
            String weather = weatherInfo.getString("weather");

            String content = "今日  " + city + " 天气\n天气：" + weather + "\n最低气温：" + low + "\n最高气温：" + high;
            System.out.println(content);
            TextMsg tm = new TextMsg(content);
            tm.setMsgType("text");
            tm.setCreateTime(new Date().getTime());
            return tm;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
