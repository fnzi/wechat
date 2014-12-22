package cn.tj.fnzi.wechat.bz.handler;

import java.util.Date;

import org.springframework.stereotype.Service;

import cn.tj.fnzi.wechat.fw.handle.WeChatHandler;
import cn.tj.fnzi.wechat.fw.message.req.ImageReqMsg;
import cn.tj.fnzi.wechat.fw.message.resp.BaseMsg;
import cn.tj.fnzi.wechat.fw.message.resp.TextMsg;

@Service("imageMessageHandler")
public class ImageMessageHandler implements WeChatHandler<ImageReqMsg, BaseMsg> {

    @Override
    public BaseMsg handle(ImageReqMsg param) {
        TextMsg tm = new TextMsg("hello");
        tm.setMsgType("text");
        tm.setCreateTime(new Date().getTime());
        return tm;
    }

}
