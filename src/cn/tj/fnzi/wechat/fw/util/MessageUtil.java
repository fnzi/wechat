package cn.tj.fnzi.wechat.fw.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 消息工具类
 * 用于解析微信平台消息xml报文
 *
 * @author peiyu
 */
public final class MessageUtil {

    private static final Logger LOG = LogManager.getLogger(MessageUtil.class);

    /**
     * 此类不需要实例化
     */
    private MessageUtil() {
    }

    /**
     * 解析从微信服务器来的请求，将消息或者事件返回出去
     *
     * @param request http请求对象
     * @return 微信消息或者事件Map
     */
    public static Map<String, String> parseXml(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();

        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader reader = factory.createXMLEventReader(inputStream);
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    String tagName = event.asStartElement().getName()
                            .toString();
                    if (!"xml".equals(tagName)) {
                        String text = reader.getElementText();
                        map.put(tagName, text);
                    }
                }
            }
        } catch (IOException e) {
            LOG.error("IO出现异常", e);
        } catch (XMLStreamException e) {
            LOG.error("XML解析出现异常", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOG.error("IO出现异常", e);
            }
        }
        return map;
    }
}
