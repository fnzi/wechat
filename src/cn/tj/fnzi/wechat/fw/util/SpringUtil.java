package cn.tj.fnzi.wechat.fw.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取spring环境
 * 
 * @author fnzi
 *
 */
public final class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    private SpringUtil() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    public static Object getBean(String name) {
        checkApplicationContext();
        return SpringUtil.applicationContext.getBean(name);
    }

    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    private static void checkApplicationContext() {
        if (applicationContext == null)
            throw new IllegalStateException("applicaitonContext is null");
    }
}