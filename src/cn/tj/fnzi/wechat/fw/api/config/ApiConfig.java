package cn.tj.fnzi.wechat.fw.api.config;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.tj.fnzi.wechat.fw.api.response.GetTokenResponse;
import cn.tj.fnzi.wechat.fw.util.JSONUtil;
import cn.tj.fnzi.wechat.fw.util.NetWorkCenter;

/**
 * API配置类，项目中请保证其为单例
 * @author peiyu
 * @since 1.2
 */
public final class ApiConfig {

    private static final Logger LOG = LogManager.getLogger(ApiConfig.class);

    private final String appid;

    private final String secret;

    private String accessToken;

    public static final AtomicBoolean refreshing = new AtomicBoolean(false);

    /**
     * 唯一构造方法，实现同时获取access_token
     * @param appid 公众号appid
     * @param secret 公众号secret
     */
    public ApiConfig(String appid, String secret) {
        this.appid = appid;
        this.secret = secret;
        init();
    }

    public String getAppid() {
        return appid;
    }

    public String getSecret() {
        return secret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * 初始化微信配置，即第一次获取access_token
     */
    public void init() {
        LOG.debug("开始第一次初始化access_token........");
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + this.appid + "&secret=" + this.secret;
        NetWorkCenter.get(url, null, new NetWorkCenter.ResponseCallback() {
            @Override
            public void onResponse(int resultCode, String resultJson) {
                if (HttpStatus.SC_OK == resultCode) {
                    GetTokenResponse response = JSONUtil.toBean(resultJson, GetTokenResponse.class);
                    LOG.debug("获取access_token:{}", response.getAccessToken());
                    ApiConfig.this.accessToken = response.getAccessToken();
                }
            }
        });
    }
}
