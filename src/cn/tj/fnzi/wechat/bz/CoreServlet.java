package cn.tj.fnzi.wechat.bz;

import cn.tj.fnzi.wechat.fw.servlet.WeixinServletSupport;

public class CoreServlet extends WeixinServletSupport {

    private static final long serialVersionUID = 1L;

    @Override
    protected String getToken() {
        return "fnzi";
    }
}
