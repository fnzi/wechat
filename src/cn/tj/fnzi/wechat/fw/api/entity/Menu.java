package cn.tj.fnzi.wechat.fw.api.entity;

import java.util.List;

import cn.tj.fnzi.wechat.fw.exception.WeixinException;
import cn.tj.fnzi.wechat.fw.util.JSONUtil;

/**
 * 菜单对象，包含所有菜单按钮
 * @author peiyu
 */
public class Menu implements Model {

    /**
     * 一级菜单列表，最多3个
     */
    private List<MenuButton> button;

    public List<MenuButton> getButton() {
        return button;
    }

    public void setButton(List<MenuButton> button) {
        if(null == button || button.size() > 3) {
            throw new WeixinException("主菜单最多3个");
        }
        this.button = button;
    }

    @Override
    public String toJsonString() {
        return JSONUtil.toJson(this);
    }
}
