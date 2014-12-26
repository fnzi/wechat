package cn.tj.fnzi.wechat.fw.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.tj.fnzi.wechat.fw.util.StrUtil;

/**
 * <pre>
 * 功能描述：解析基于XML的配置文件
 * 创建者：闫世峰
 * 修改者：
 * </pre>
 */
public class FromXmlMappingDeal implements MappingDeal {

    public static String WECHAT_XML_PATH = "/wechat.xml";

    @Override
    public WeChat execute(Map<String, Object> param) {

        WeChat configs = WeChat.getInstance();
        try {
            InputStream fileinputstream = getClass().getResourceAsStream(WECHAT_XML_PATH);

            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = db.parse(fileinputstream);

            Element root = document.getDocumentElement();

            Map<String, Event> events = getEvents(root);
            Map<String, Message> messages = getMessages(root);
            Map<String, KeyWord> keywords = getKeyWord(root);

            configs.init(events, messages, keywords);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return configs;
    }

    private Map<String, Event> getEvents(Element root) {
        Map<String, Event> events = new HashMap<String, Event>();

        NodeList eventList = root.getElementsByTagName("event");
        for (int i = 0; i < eventList.getLength(); i++) {
            Node ccnode = eventList.item(i);
            if (ccnode.getNodeName().equals("event")) {
                NamedNodeMap nodeProp = ccnode.getAttributes();
                Event event = new Event();
                for (int j = 0; j < nodeProp.getLength(); j++) {
                    Attr attr = (Attr) nodeProp.item(j);
                    if (attr.getNodeName().equals("type")) {
                        event.setType(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("handler")) {
                        event.setHandler(attr.getNodeValue());
                    }
                }

                events.put(event.getType(), event);
            }
        }
        return events;
    }

    private Map<String, Message> getMessages(Element root) {
        Map<String, Message> messages = new HashMap<String, Message>();

        NodeList messageList = root.getElementsByTagName("message");
        for (int i = 0; i < messageList.getLength(); i++) {
            Node ccnode = messageList.item(i);
            if (ccnode.getNodeName().equals("message")) {
                NamedNodeMap nodeProp = ccnode.getAttributes();
                Message message = new Message();
                for (int j = 0; j < nodeProp.getLength(); j++) {
                    Attr attr = (Attr) nodeProp.item(j);
                    if (attr.getNodeName().equals("type")) {
                        message.setType(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("handler")) {
                        message.setHandler(attr.getNodeValue());
                    }
                }

                messages.put(message.getType(), message);
            }
        }

        return messages;
    }

    private Map<String, KeyWord> getKeyWord(Element root) {
        Map<String, KeyWord> keywords = new HashMap<String, KeyWord>();

        NodeList messageList = root.getElementsByTagName("keyword");
        for (int i = 0; i < messageList.getLength(); i++) {
            Node ccnode = messageList.item(i);
            if (ccnode.getNodeName().equals("keyword")) {
                NamedNodeMap nodeProp = ccnode.getAttributes();
                KeyWord keyWord = new KeyWord();
                for (int j = 0; j < nodeProp.getLength(); j++) {
                    Attr attr = (Attr) nodeProp.item(j);
                    if (attr.getNodeName().equals("id")) {
                        keyWord.setId(attr.getNodeValue());
                    } else if (attr.getNodeName().equals("handler")) {
                        keyWord.setHandler(attr.getNodeValue());
                    }
                }

                keywords.put(keyWord.getId(), keyWord);
            }
        }

        return keywords;
    }

    /**
     * <pre>
     * [,]断开字符串，并清理[/t/n]特殊字符
     * </pre>
     */
    private List<String> split(String str) {
        List<String> ret = new ArrayList<String>();
        String[] strs = str.split(",");
        for (String s : strs) {
            String t = StrUtil.replaceBlank(s);
            if (!"".equals(t)) {
                ret.add(t);
            }
        }
        return ret;
    }

    /**
     * <pre>
     * 获取标签中的值
     * </pre>
     */
    private String getText(Node ccnode) {
        String ret = "";
        if (ccnode != null && ccnode.getFirstChild() != null) {
            ret = ccnode.getFirstChild().getNodeValue();
        }
        return ret;
    }
}
