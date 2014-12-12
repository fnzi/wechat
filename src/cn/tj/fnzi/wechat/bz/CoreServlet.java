package cn.tj.fnzi.wechat.bz;

import java.util.Date;

import cn.tj.fnzi.wechat.fw.message.BaseMsg;
import cn.tj.fnzi.wechat.fw.message.TextMsg;
import cn.tj.fnzi.wechat.fw.message.req.TextReqMsg;
import cn.tj.fnzi.wechat.fw.servlet.WeixinServletSupport;

public class CoreServlet extends WeixinServletSupport {

    @Override
    protected String getToken() {
        return "fnzi";
    }
    
    static {
        addMessageHandles(new Message001Handle());
        addMessageHandles(new Message002Handle());
    }

    protected BaseMsg handleTextMsg(TextReqMsg msg) {

        String req = msg.getContent();
        if (req.equals("hello")) {
            TextMsg tm = new TextMsg("hello");
            tm.setMsgType("text");
            tm.setFromUserName(msg.getToUserName());
            tm.setToUserName(msg.getFromUserName());
            tm.setCreateTime(new Date().getTime());
            return tm;
        }

        return handleDefaultMsg(msg);
    }
}
