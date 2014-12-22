package cn.tj.fnzi.wechat.bz.handler;

import java.util.Date;

import org.springframework.stereotype.Service;

import cn.tj.fnzi.wechat.fw.handle.WeChatHandler;
import cn.tj.fnzi.wechat.fw.message.req.LocationEvent;
import cn.tj.fnzi.wechat.fw.message.resp.BaseMsg;
import cn.tj.fnzi.wechat.fw.message.resp.TextMsg;

@Service("locationEventHandler")
public class LocationEventHandler implements WeChatHandler<LocationEvent, BaseMsg> {

    @Override
    public BaseMsg handle(LocationEvent param) {
        TextMsg tm = new TextMsg("hello");
        tm.setMsgType("text");
        tm.setCreateTime(new Date().getTime());
        return tm;
    }

}
