package cn.tj.fnzi.wechat.fw.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.tj.fnzi.wechat.fw.api.config.ApiConfig;
import cn.tj.fnzi.wechat.fw.api.enums.QrcodeType;
import cn.tj.fnzi.wechat.fw.api.response.BaseResponse;
import cn.tj.fnzi.wechat.fw.api.response.QrcodeResponse;
import cn.tj.fnzi.wechat.fw.util.BeanUtil;
import cn.tj.fnzi.wechat.fw.util.JSONUtil;

/**
 * 二维码相关API
 *
 * @author peiyu
 * @since 1.2
 */
public class QrcodeAPI extends BaseAPI {

    private static final Logger LOG = LogManager.getLogger(QrcodeAPI.class);

    public QrcodeAPI(ApiConfig config) {
        super(config);
    }

    /**
     * 创建二维码
     *
     * @param actionName    二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久
     * @param sceneId       场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @param expireSeconds 该二维码有效时间，以秒为单位。 最大不超过1800
     * @return 二维码对象
     */
    public QrcodeResponse createQrcode(QrcodeType actionName, String sceneId, Integer expireSeconds) {
        BeanUtil.requireNonNull(actionName, "actionName is null");
        BeanUtil.requireNonNull(sceneId, "actionInfo is null");

        LOG.debug("创建二维码信息.....");

        QrcodeResponse response = null;
        String url = BASE_API_URL + "cgi-bin/qrcode/create?access_token=#";

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("action_name", actionName);
        Map<String, Object> actionInfo = new HashMap<String, Object>();
        Map<String, Object> scene = new HashMap<String, Object>();
        scene.put("scene_id", sceneId);
        actionInfo.put("scene", scene);
        param.put("action_info", actionInfo);
        if (BeanUtil.nonNull(expireSeconds) && 0 != expireSeconds) {
            param.put("expire_seconds", expireSeconds);
        }
        BaseResponse r = executePost(url, JSONUtil.toJson(param));
        if (null == r.getErrcode() || "".equals(r.getErrcode())) {
            response = JSONUtil.toBean(r.getErrmsg(), QrcodeResponse.class);
        }
        return response;
    }
}
