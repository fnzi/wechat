package cn.tj.fnzi.wechat.testclient.textmessage;

import org.apache.http.client.fluent.Request;
import org.junit.Test;

public class TextTest {

    @Test
    public void test001() {
        StringBuilder msg = new StringBuilder();

        msg.append("<xml>")
        .append("<ToUserName><![CDATA[gh_8f05782ab77c]]></ToUserName>")
        .append("<FromUserName><![CDATA[gh_8f05782ab77c]]></FromUserName>")
        .append("<CreateTime>1348831860</CreateTime>")
        .append("<MsgType><![CDATA[text]]></MsgType>")
        .append("<Content><![CDATA[news]]></Content>")
        .append("<MsgId>1234567890123456</MsgId>")
        .append("</xml>");

        String url = "http://127.0.0.1/WeChatService/CoreServlet";
        try {
            String ret = Request.Post(url).bodyByteArray(msg.toString().getBytes("utf-8")).execute().returnContent().asString();
            System.out.println(ret);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
