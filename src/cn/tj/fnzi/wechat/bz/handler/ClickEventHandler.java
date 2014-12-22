package cn.tj.fnzi.wechat.bz.handler;

import java.util.Date;

import org.springframework.stereotype.Service;

import cn.tj.fnzi.wechat.fw.handle.WeChatHandler;
import cn.tj.fnzi.wechat.fw.message.req.MenuEvent;
import cn.tj.fnzi.wechat.fw.message.resp.BaseMsg;
import cn.tj.fnzi.wechat.fw.message.resp.TextMsg;

@Service("clickEventHandler")
public class ClickEventHandler implements WeChatHandler<MenuEvent, BaseMsg> {

    @Override
    public BaseMsg handle(MenuEvent param) {
        TextMsg tm = new TextMsg("hello   click");
        tm.setMsgType("text");
        tm.setCreateTime(new Date().getTime());
        return tm;
    }

}
