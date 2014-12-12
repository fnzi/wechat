package cn.tj.fnzi.wechat.bz;

import cn.tj.fnzi.wechat.fw.handle.MessageHandle;
import cn.tj.fnzi.wechat.fw.message.Article;
import cn.tj.fnzi.wechat.fw.message.BaseMsg;
import cn.tj.fnzi.wechat.fw.message.NewsMsg;
import cn.tj.fnzi.wechat.fw.message.req.TextReqMsg;

public class Message002Handle implements MessageHandle<TextReqMsg> {

    @Override
    public BaseMsg handle(TextReqMsg msg) {
        String req = msg.getContent();
        if (req.equals("news")) {

            Article a = new Article();
            a.setTitle("test");
            a.setDescription("testetstetstetetetteteteetett");
            a.setPicUrl("http://mmbiz.qpic.cn/mmbiz/zwPCzaGX75Q3x3mia2qH3qRvh2K8UVHsVufjcNcCm8CesDgMZfPg75X9UZBoPyc57gqKe1M1U5WRhsoZ5ko13RA/0");
            a.setUrl("http://www.jiaqianyi.com/cms4jrpt/RobSeven/request?SiteID=124");

            NewsMsg nm = new NewsMsg();
            nm.add(a);
            nm.setCreateTime(System.currentTimeMillis());
            nm.setMaxSize(1);
            nm.setMsgType("news");
            nm.setFromUserName(msg.getToUserName());
            nm.setToUserName(msg.getFromUserName());
            return nm;
        }
        return null;
    }

    @Override
    public boolean beforeHandle(TextReqMsg message) {
        return true;
    }

}
