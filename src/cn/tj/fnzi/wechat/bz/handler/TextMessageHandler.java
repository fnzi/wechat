package cn.tj.fnzi.wechat.bz.handler;

import java.util.Date;

import org.springframework.stereotype.Service;

import cn.tj.fnzi.wechat.bz.service.FanyiService;
import cn.tj.fnzi.wechat.bz.service.WeatherService;
import cn.tj.fnzi.wechat.fw.handle.WeChatHandler;
import cn.tj.fnzi.wechat.fw.message.req.TextReqMsg;
import cn.tj.fnzi.wechat.fw.message.resp.BaseMsg;
import cn.tj.fnzi.wechat.fw.message.resp.TextMsg;

@Service("textMessageHandler")
public class TextMessageHandler implements WeChatHandler<TextReqMsg, BaseMsg> {

    @Override
    public BaseMsg handle(TextReqMsg param) {

        String msg = param.getContent();

        if ("天气预报".equals(msg)) {
            return new WeatherService().handle(param);
        } else if ("翻译".equals(msg)) {
            return new FanyiService().handle(param);
        }

        TextMsg tm = new TextMsg("hello  Text ");
        tm.setMsgType("text");
        tm.setCreateTime(new Date().getTime());
        return tm;
    }

}
