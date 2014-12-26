package cn.tj.fnzi.wechat.fw.config;

import java.util.HashMap;
import java.util.Map;

public class WeChat {

    private WeChat() {
    }

    private static WeChat instance = new WeChat();

    public static WeChat getInstance() {
        return instance;
    }

    private Map<String, Event> events = new HashMap<String, Event>();
    private Map<String, Message> messages = new HashMap<String, Message>();
    private Map<String, KeyWord> keyWords = new HashMap<String, KeyWord>();

    public Map<String, Event> getEvents() {
        return events;
    }

    public Map<String, Message> getMessages() {
        return messages;
    }

    public Map<String, KeyWord> getKeyWords() {
        return keyWords;
    }

    public void init(Map<String, Event> events, Map<String, Message> messages, Map<String, KeyWord> keywords) {
        this.events = events;
        this.messages = messages;
        this.keyWords = keywords;
    }

}
