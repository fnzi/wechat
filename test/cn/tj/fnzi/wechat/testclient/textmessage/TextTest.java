package cn.tj.fnzi.wechat.testclient.textmessage;

import org.apache.http.client.fluent.Request;
import org.junit.Test;

public class TextTest {

    /**
     * text
     * */
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
    
    /**
     * text   天气预报
     * */
    @Test
    public void test0010() {
        StringBuilder msg = new StringBuilder();

        msg.append("<xml>")
        .append("<ToUserName><![CDATA[gh_8f05782ab77c]]></ToUserName>")
        .append("<FromUserName><![CDATA[gh_8f05782ab77c]]></FromUserName>")
        .append("<CreateTime>1348831860</CreateTime>")
        .append("<MsgType><![CDATA[text]]></MsgType>")
        .append("<Content><![CDATA[天气预报]]></Content>")
        .append("<MsgId>1234567890123456</MsgId>")
        .append("</xml>");

        String url = "http://127.0.0.1/WeChatService/CoreServlet";
        try {
            String ret = Request.Post(url).setHeader("Content-Type", "text/html; charset=UTF-8").bodyByteArray(msg.toString().getBytes("utf-8")).execute().returnContent().asString();
            System.out.println(new String(ret.getBytes("ISO-8859-1"), "utf-8"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
    /**
     * text   翻译
     * */
    @Test
    public void test0011() {
        StringBuilder msg = new StringBuilder();

        msg.append("<xml>")
        .append("<ToUserName><![CDATA[gh_8f05782ab77c]]></ToUserName>")
        .append("<FromUserName><![CDATA[gh_8f05782ab77c]]></FromUserName>")
        .append("<CreateTime>1348831860</CreateTime>")
        .append("<MsgType><![CDATA[text]]></MsgType>")
        .append("<Content><![CDATA[翻译]]></Content>")
        .append("<MsgId>1234567890123456</MsgId>")
        .append("</xml>");

        String url = "http://127.0.0.1/WeChatService/CoreServlet";
        try {
            String ret = Request.Post(url).setHeader("Content-Type", "text/html; charset=UTF-8").bodyByteArray(msg.toString().getBytes("utf-8")).execute().returnContent().asString();
            System.out.println(new String(ret.getBytes("ISO-8859-1"), "utf-8"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
    /**
     * subscribe
     * */
    @Test
    public void test002() {
        StringBuilder msg = new StringBuilder();

        msg.append("<xml>")
        .append("<ToUserName><![CDATA[gh_8f05782ab77c]]></ToUserName>")
        .append("<FromUserName><![CDATA[gh_8f05782ab77c]]></FromUserName>")
        .append("<CreateTime>1348831860</CreateTime>")
        .append("<MsgType><![CDATA[event]]></MsgType>")
        .append("<Event><![CDATA[subscribe]]></Event>")
        .append("</xml>");

        String url = "http://127.0.0.1/WeChatService/CoreServlet";
        try {
            String ret = Request.Post(url).bodyByteArray(msg.toString().getBytes("utf-8")).execute().returnContent().asString();
            System.out.println(ret);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    /**
     * CLICK
     * */
    @Test
    public void test003() {
        StringBuilder msg = new StringBuilder();

        msg.append("<xml>")
        .append("<ToUserName><![CDATA[gh_8f05782ab77c]]></ToUserName>")
        .append("<FromUserName><![CDATA[gh_8f05782ab77c]]></FromUserName>")
        .append("<CreateTime>1348831860</CreateTime>")
        .append("<MsgType><![CDATA[event]]></MsgType>")
        .append("<Event><![CDATA[CLICK]]></Event>")
        .append("<EventKey><![CDATA[EVENTKEY]]></EventKey>")
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
