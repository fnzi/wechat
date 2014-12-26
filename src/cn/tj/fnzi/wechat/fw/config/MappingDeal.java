package cn.tj.fnzi.wechat.fw.config;

import java.util.Map;

/**
 * <pre>
 * 功能描述：解析配置文件描述接口
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public interface MappingDeal {

    public WeChat execute(Map<String, Object> param);
}
