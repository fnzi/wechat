package cn.tj.fnzi.wechat.fw.api.request;

import cn.tj.fnzi.wechat.fw.api.entity.Model;
import cn.tj.fnzi.wechat.fw.util.JSONUtil;

/**
 * @author peiyu
 */
public class BaseRequest implements Model {

    public final String toJsonString() {
        return JSONUtil.toJson(this);
    }
}
