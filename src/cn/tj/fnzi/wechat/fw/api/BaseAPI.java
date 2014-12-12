package cn.tj.fnzi.wechat.fw.api;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.tj.fnzi.wechat.fw.api.config.ApiConfig;
import cn.tj.fnzi.wechat.fw.api.enums.ResultType;
import cn.tj.fnzi.wechat.fw.api.response.BaseResponse;
import cn.tj.fnzi.wechat.fw.api.response.GetTokenResponse;
import cn.tj.fnzi.wechat.fw.util.BeanUtil;
import cn.tj.fnzi.wechat.fw.util.CollectionUtil;
import cn.tj.fnzi.wechat.fw.util.JSONUtil;
import cn.tj.fnzi.wechat.fw.util.NetWorkCenter;

/**
 * API基类，提供一些通用方法
 * 包含自动刷新token、通用get post请求等
 * @author peiyu
 * @since 1.2
 */
public abstract class BaseAPI {

    private static final Logger LOG = LogManager.getLogger(BaseAPI.class);

    protected static final String BASE_API_URL = "https://api.weixin.qq.com/";

    protected final ApiConfig config;

    //用于刷新token时锁住config，防止多次刷新
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private final Lock readLock = lock.readLock();

    private final Lock writeLock = lock.writeLock();

    protected BaseAPI(ApiConfig config) {
        this.config = config;
    }

    /**
     * 刷新token
     */
    protected void refreshToken() {
        LOG.debug("开始刷新access_token......");
        writeLock.lock();
        try {
            if(config.refreshing.compareAndSet(false, true)) {
                String url = BASE_API_URL + "cgi-bin/token?grant_type=client_credential&appid=" + this.config.getAppid() + "&secret=" + this.config.getSecret();
                NetWorkCenter.get(url, null, new NetWorkCenter.ResponseCallback() {
                    @Override
                    public void onResponse(int resultCode, String resultJson) {
                        if (HttpStatus.SC_OK == resultCode) {
                            GetTokenResponse response = JSONUtil.toBean(resultJson, GetTokenResponse.class);
                            BaseAPI.this.config.setAccessToken(response.getAccessToken());
                            LOG.debug("刷新access_token成功.....");
                        } else {
                            LOG.warn("获取access_token失败....");
                            LOG.warn("信息:{}", resultJson);
                        }
                    }
                });
            }
        } finally {
            config.refreshing.set(false);
            writeLock.unlock();
        }
    }

    /**
     * 通用post请求
     * @param url 地址，其中token用#代替
     * @param json 参数，json格式
     * @return 请求结果
     */
    protected BaseResponse executePost(String url, String json) {
        return executePost(url, json, null);
    }

    /**
     * 通用post请求
     * @param url 地址，其中token用#代替
     * @param json 参数，json格式
     * @param file 上传的文件
     * @return 请求结果
     */
    protected BaseResponse executePost(String url, String json, File file) {
        BaseResponse response = null;
        BeanUtil.requireNonNull(url, "url is null");
        String postUrl;
        List<File> files = null;
        if(null != file) {
            files = CollectionUtil.newArrayList(file);
        }
        readLock.lock();
        try {
            //需要传token
            postUrl = url.replace("#",config.getAccessToken());
            response = NetWorkCenter.post(postUrl, json, files);
        } finally {
            readLock.unlock();
        }

        if(null == response || ResultType.ACCESS_TOKEN_TIMEOUT.getCode().toString().equals(response.getErrcode())) {
            if(!config.refreshing.get()) {
                refreshToken();
            }
            readLock.lock();
            try {
                LOG.debug("接口调用重试....");
                TimeUnit.SECONDS.sleep(1);
                postUrl = url.replace("#", config.getAccessToken());
                response = NetWorkCenter.post(postUrl, json, files);
            } catch (InterruptedException e) {
                LOG.error("线程休眠异常", e);
            } catch (Exception e) {
                LOG.error("请求出现异常", e);
            } finally {
                readLock.unlock();
            }
        }

        return response;
    }




    /**
     * 通用post请求
     * @param url 地址，其中token用#代替
     * @return 请求结果
     */
    protected BaseResponse executeGet(String url) {
        BaseResponse response = null;
        BeanUtil.requireNonNull(url, "url is null");
        String getUrl;
        readLock.lock();
        try {
            //需要传token
            getUrl = url.replace("#",config.getAccessToken());
            response = NetWorkCenter.get(getUrl);
        } finally {
            readLock.unlock();
        }

        if(null == response || ResultType.ACCESS_TOKEN_TIMEOUT.getCode().toString().equals(response.getErrcode())) {
            if (!config.refreshing.get()) {
                refreshToken();
            }
            readLock.lock();
            try {
                LOG.debug("接口调用重试....");
                TimeUnit.SECONDS.sleep(1);
                getUrl = url.replace("#", config.getAccessToken());
                response = NetWorkCenter.get(getUrl);
            } catch (InterruptedException e) {
                LOG.error("线程休眠异常", e);
            } catch (Exception e) {
                LOG.error("请求出现异常", e);
            }finally {
                readLock.unlock();
            }
        }
        return response;
    }
}
