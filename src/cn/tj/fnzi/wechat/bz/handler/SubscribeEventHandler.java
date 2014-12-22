package cn.tj.fnzi.wechat.bz.handler;

import java.util.Date;

import org.springframework.stereotype.Service;

import cn.tj.fnzi.wechat.fw.handle.WeChatHandler;
import cn.tj.fnzi.wechat.fw.message.req.BaseEvent;
import cn.tj.fnzi.wechat.fw.message.resp.BaseMsg;
import cn.tj.fnzi.wechat.fw.message.resp.TextMsg;

@Service("subscribeEventHandler")
public class SubscribeEventHandler implements WeChatHandler<BaseEvent, BaseMsg> {

    @Override
    public BaseMsg handle(BaseEvent param) {
        TextMsg tm = new TextMsg("hello Subscribe");
        tm.setMsgType("text");
        tm.setCreateTime(new Date().getTime());
        return tm;
    }

}
