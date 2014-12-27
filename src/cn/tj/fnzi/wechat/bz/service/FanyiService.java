package cn.tj.fnzi.wechat.bz.service;

import java.util.Date;

import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;

import cn.tj.fnzi.wechat.fw.handle.WeChatHandler;
import cn.tj.fnzi.wechat.fw.message.req.TextReqMsg;
import cn.tj.fnzi.wechat.fw.message.resp.BaseMsg;
import cn.tj.fnzi.wechat.fw.message.resp.TextMsg;
import cn.tj.fnzi.wechat.fw.util.JSONUtil;

import com.alibaba.fastjson.JSONObject;

@Service("fanyiService")
public class FanyiService implements WeChatHandler<TextReqMsg, BaseMsg> {

    private static String requrl = "http://fanyi.youdao.com/openapi.do?keyfrom=fnziteam&key=187533100&type=data&doctype=json&version=1.1&q=good";

    @Override
    public BaseMsg handle(TextReqMsg param) {
        try {
            String ret = Request.Get(requrl).execute().returnContent().asString();
            System.out.println(ret);
            JSONObject json = JSONUtil.getJSONFromString(ret);
            String basicJson = json.getString("basic");

            JSONObject explainsJson = JSONUtil.getJSONFromString(basicJson);
            String explains = explainsJson.getString("explains");

            explains = explains.replaceAll("\\[\"", "").replaceAll("\"]", "").replaceAll("\",\"", "\n");
            
            System.out.println(explains);

            TextMsg tm = new TextMsg(explains);
            tm.setMsgType("text");
            tm.setCreateTime(new Date().getTime());
            return tm;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}