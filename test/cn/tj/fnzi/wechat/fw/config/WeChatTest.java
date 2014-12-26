package cn.tj.fnzi.wechat.fw.config;

import org.junit.Test;

public class WeChatTest {

    @Test
    public void test001() {
        MappingDeal md = new FromXmlMappingDeal();
        
        md.execute(null);
        
        
        WeChat wc = WeChat.getInstance();
        
        System.out.println(wc.getMessages().size());
    }

}
