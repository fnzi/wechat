package cn.tj.fnzi.wechat.fw.api.response;

import cn.tj.fnzi.wechat.fw.api.entity.Model;
import cn.tj.fnzi.wechat.fw.util.JSONUtil;

/**
 * 微信API响应报文对象基类
 * @author peiyu
 */
public class BaseResponse implements Model {
    private String errcode;

    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public final String toJsonString() {
        return JSONUtil.toJson(this);
    }
}
