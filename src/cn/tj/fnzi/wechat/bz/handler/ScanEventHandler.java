package cn.tj.fnzi.wechat.bz.handler;

import java.util.Date;

import org.springframework.stereotype.Service;

import cn.tj.fnzi.wechat.fw.handle.WeChatHandler;
import cn.tj.fnzi.wechat.fw.message.req.QrCodeEvent;
import cn.tj.fnzi.wechat.fw.message.resp.BaseMsg;
import cn.tj.fnzi.wechat.fw.message.resp.TextMsg;

@Service("scanEventHandler")
public class ScanEventHandler implements WeChatHandler<QrCodeEvent, BaseMsg> {

    @Override
    public BaseMsg handle(QrCodeEvent param) {
        TextMsg tm = new TextMsg("hello");
        tm.setMsgType("text");
        tm.setCreateTime(new Date().getTime());
        return tm;
    }

}
