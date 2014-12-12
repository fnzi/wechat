package cn.tj.fnzi.wechat.fw.api.response;

import cn.tj.fnzi.wechat.fw.api.entity.Menu;

/**
 * @author peiyu
 */
public class GetMenuResponse extends BaseResponse {

    private Menu menu;

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
