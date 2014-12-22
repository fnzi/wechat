package cn.tj.fnzi.wechat.fw.handle;

import cn.tj.fnzi.wechat.fw.message.req.BaseReq;
import cn.tj.fnzi.wechat.fw.message.resp.BaseMsg;

public interface WeChatHandler<P extends BaseReq, R extends BaseMsg> {

    public R handle(P param);

}
