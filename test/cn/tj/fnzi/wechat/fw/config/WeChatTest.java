package cn.tj.fnzi.wechat.fw.config;

import org.junit.Test;

public class WeChatTest {

    @Test
    public void test001() {
        WeChat wc = WeChat.getInstance();
        
        System.out.println(wc.getMessages().size());
    }

}
