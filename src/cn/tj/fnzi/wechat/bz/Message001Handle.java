package cn.tj.fnzi.wechat.bz;

import java.util.Date;

import cn.tj.fnzi.wechat.fw.handle.MessageHandle;
import cn.tj.fnzi.wechat.fw.message.BaseMsg;
import cn.tj.fnzi.wechat.fw.message.TextMsg;
import cn.tj.fnzi.wechat.fw.message.req.TextReqMsg;

public class Message001Handle implements MessageHandle<TextReqMsg> {

    @Override
    public BaseMsg handle(TextReqMsg msg) {
        String req = msg.getContent();
        if (req.equals("fnzi")) {
            TextMsg tm = new TextMsg("hello fnzi");
            tm.setMsgType("text");
            tm.setFromUserName(msg.getToUserName());
            tm.setToUserName(msg.getFromUserName());
            tm.setCreateTime(new Date().getTime());
            return tm;
        }
        return null;
    }

    @Override
    public boolean beforeHandle(TextReqMsg message) {
        return true;
    }

}
