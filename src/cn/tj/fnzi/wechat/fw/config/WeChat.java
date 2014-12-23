package cn.tj.fnzi.wechat.fw.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WeChat {

    public static String WECHAT_XML_PATH = "/wechat.xml";

    private WeChat() {
        try {
            InputStream fileinputstream = getClass().getResourceAsStream(WECHAT_XML_PATH);
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = db.parse(fileinputstream);

            Element root = document.getDocumentElement();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static WeChat instance = new WeChat();

    public static WeChat getInstance() {
        return instance;
    }

    private Map<String, Event> events = new HashMap<String, Event>();
    private Map<String, Message> messages = new HashMap<String, Message>();

    public Map<String, Event> getEvents() {
        return events;
    }

    public Map<String, Message> getMessages() {
        return messages;
    }

}
