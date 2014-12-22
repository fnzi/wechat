package cn.tj.fnzi.wechat.fw.servlet;

import static cn.tj.fnzi.wechat.fw.util.BeanUtil.isNull;
import static cn.tj.fnzi.wechat.fw.util.BeanUtil.nonNull;
import static cn.tj.fnzi.wechat.fw.util.StrUtil.isNotBlank;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.tj.fnzi.wechat.fw.handle.WeChatHandler;
import cn.tj.fnzi.wechat.fw.message.req.BaseEvent;
import cn.tj.fnzi.wechat.fw.message.req.BaseReq;
import cn.tj.fnzi.wechat.fw.message.req.BaseReqMsg;
import cn.tj.fnzi.wechat.fw.message.req.EventType;
import cn.tj.fnzi.wechat.fw.message.req.ImageReqMsg;
import cn.tj.fnzi.wechat.fw.message.req.LinkReqMsg;
import cn.tj.fnzi.wechat.fw.message.req.LocationEvent;
import cn.tj.fnzi.wechat.fw.message.req.LocationReqMsg;
import cn.tj.fnzi.wechat.fw.message.req.MenuEvent;
import cn.tj.fnzi.wechat.fw.message.req.QrCodeEvent;
import cn.tj.fnzi.wechat.fw.message.req.ReqType;
import cn.tj.fnzi.wechat.fw.message.req.TextReqMsg;
import cn.tj.fnzi.wechat.fw.message.req.VideoReqMsg;
import cn.tj.fnzi.wechat.fw.message.req.VoiceReqMsg;
import cn.tj.fnzi.wechat.fw.message.resp.BaseMsg;
import cn.tj.fnzi.wechat.fw.util.MessageUtil;
import cn.tj.fnzi.wechat.fw.util.SpringUtil;

/**
 * 微信公众平台交互操作基类，提供几乎所有微信公众平台交互方式 基于javaee servlet框架，方便使用此框架的项目集成
 */
public abstract class WeixinServletSupport extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * 子类用于提供token用于绑定微信公众平台
     *
     * @return token值
     */
    protected abstract String getToken();

    /**
     * 重写servlet中的get方法，用于处理微信服务器绑定，置为final方法，用户已经无需重写这个方法啦
     *
     * @param request
     *            http请求对象
     * @param response
     *            http响应对象
     * @throws ServletException
     *             servlet异常
     * @throws IOException
     *             IO异常
     */
    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (isLegal(request)) {
            // 绑定微信服务器成功
            PrintWriter pw = response.getWriter();
            pw.write(request.getParameter("echostr"));
            pw.flush();
            pw.close();
        }
    }

    /**
     * 重写servlet中的post方法，用于接收微信服务器发来的消息，置为final方法，用户已经无需重写这个方法啦
     *
     * @param request
     *            http请求对象
     * @param response
     *            http响应对象
     * @throws ServletException
     *             servlet异常
     * @throws IOException
     *             IO异常
     */
    @Override
    protected final void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isLegal(request)) {
            return;
        }
        // 设置响应编码格式
        response.setCharacterEncoding("UTF-8");
        String resp = processRequest(request);
        PrintWriter pw = response.getWriter();
        pw.write(resp);
        pw.flush();
        pw.close();
    }

    /**
     * 处理微信服务器发来的请求方法
     *
     * @param request
     *            http请求对象
     * @return 处理消息的结果，已经是接口要求的xml报文了
     */
    private String processRequest(HttpServletRequest request) {
        Map<String, String> reqMap = MessageUtil.parseXml(request);
        String fromUserName = reqMap.get("FromUserName");
        String toUserName = reqMap.get("ToUserName");
        String msgType = reqMap.get("MsgType");

        BaseMsg msg = null;
        
        WeChatHandler handler = null;

        if (msgType.equals(ReqType.EVENT)) {
            String eventType = reqMap.get("Event");
            String ticket = reqMap.get("Ticket");
            if (isNotBlank(ticket)) {
                String eventKey = reqMap.get("EventKey");
                QrCodeEvent event = new QrCodeEvent(eventKey, ticket);
                buildBasicEvent(reqMap, event);
                
                handler = (WeChatHandler) SpringUtil.getBean("scanEventHandler");
                msg = handler.handle(event);
            }
            if (eventType.equals(EventType.SUBSCRIBE)) {
                BaseEvent event = new BaseEvent();
                buildBasicEvent(reqMap, event);
                
                handler = (WeChatHandler) SpringUtil.getBean("subscribeEventHandler");
                msg = handler.handle(event);
            } else if (eventType.equals(EventType.UNSUBSCRIBE)) {
                BaseEvent event = new BaseEvent();
                buildBasicEvent(reqMap, event);
                
                handler = (WeChatHandler) SpringUtil.getBean("unsubscribeEventHandler");
                msg = handler.handle(event);
            } else if (eventType.equals(EventType.CLICK)) {
                String eventKey = reqMap.get("EventKey");
                MenuEvent event = new MenuEvent(eventKey);
                buildBasicEvent(reqMap, event);
                
                handler = (WeChatHandler) SpringUtil.getBean("clickEventHandler");
                msg = handler.handle(event);
            } else if (eventType.equals(EventType.VIEW)) {
                String eventKey = reqMap.get("EventKey");
                MenuEvent event = new MenuEvent(eventKey);
                buildBasicEvent(reqMap, event);
                
                handler = (WeChatHandler) SpringUtil.getBean("viewEventHandler");
                msg = handler.handle(event);
            } else if (eventType.equals(EventType.LOCATION)) {
                double latitude = Double.parseDouble(reqMap.get("Latitude"));
                double longitude = Double.parseDouble(reqMap.get("Longitude"));
                double precision = Double.parseDouble(reqMap.get("Precision"));
                LocationEvent event = new LocationEvent(latitude, longitude, precision);
                buildBasicEvent(reqMap, event);
                
                handler = (WeChatHandler) SpringUtil.getBean("locationEventHandler");
                msg = handler.handle(event);
            }

        } else {
            if (msgType.equals(ReqType.TEXT)) {
                String content = reqMap.get("Content");
                TextReqMsg textReqMsg = new TextReqMsg(content);
                buildBasicReqMsg(reqMap, textReqMsg);
                
                handler = (WeChatHandler) SpringUtil.getBean("textMessageHandler");
                msg = handler.handle(textReqMsg);
            } else if (msgType.equals(ReqType.IMAGE)) {
                String picUrl = reqMap.get("PicUrl");
                String mediaId = reqMap.get("MediaId");
                ImageReqMsg imageReqMsg = new ImageReqMsg(picUrl, mediaId);
                buildBasicReqMsg(reqMap, imageReqMsg);
                
                handler = (WeChatHandler) SpringUtil.getBean("imageMessageHandler");
                msg = handler.handle(imageReqMsg);
            } else if (msgType.equals(ReqType.VOICE)) {
                String format = reqMap.get("Format");
                String mediaId = reqMap.get("MediaId");
                String recognition = reqMap.get("Recognition");
                VoiceReqMsg voiceReqMsg = new VoiceReqMsg(mediaId, format, recognition);
                buildBasicReqMsg(reqMap, voiceReqMsg);
                
                handler = (WeChatHandler) SpringUtil.getBean("voiceMessageHandler");
                msg = handler.handle(voiceReqMsg);
            } else if (msgType.equals(ReqType.VIDEO)) {
                String thumbMediaId = reqMap.get("ThumbMediaId");
                String mediaId = reqMap.get("MediaId");
                VideoReqMsg videoReqMsg = new VideoReqMsg(mediaId, thumbMediaId);
                buildBasicReqMsg(reqMap, videoReqMsg);
                
                handler = (WeChatHandler) SpringUtil.getBean("videoMessageHandler");
                msg = handler.handle(videoReqMsg);
            } else if (msgType.equals(ReqType.LOCATION)) {
                double locationX = Double.parseDouble(reqMap.get("Location_X"));
                double locationY = Double.parseDouble(reqMap.get("Location_Y"));
                int scale = Integer.parseInt(reqMap.get("Scale"));
                String label = reqMap.get("Label");
                LocationReqMsg locationReqMsg = new LocationReqMsg(locationX, locationY, scale, label);
                buildBasicReqMsg(reqMap, locationReqMsg);
                
                handler = (WeChatHandler) SpringUtil.getBean("locationMessageHandler");
                msg = handler.handle(locationReqMsg);
            } else if (msgType.equals(ReqType.LINK)) {
                String title = reqMap.get("Title");
                String description = reqMap.get("Description");
                String url = reqMap.get("Url");
                LinkReqMsg linkReqMsg = new LinkReqMsg(title, description, url);
                buildBasicReqMsg(reqMap, linkReqMsg);
                
                handler = (WeChatHandler) SpringUtil.getBean("linkMessageHandler");
                msg = handler.handle(linkReqMsg);
            }

        }
        String result = "";
        if (nonNull(msg)) {
            msg.setFromUserName(toUserName);
            msg.setToUserName(fromUserName);
            result = msg.toXml();
        }
        return result;
    }

    private void buildBasicReqMsg(Map<String, String> reqMap, BaseReqMsg reqMsg) {
        addBasicReqParams(reqMap, reqMsg);
        reqMsg.setMsgId(reqMap.get("MsgId"));
    }

    private void buildBasicEvent(Map<String, String> reqMap, BaseEvent event) {
        addBasicReqParams(reqMap, event);
        event.setEvent(reqMap.get("Event"));
    }

    private void addBasicReqParams(Map<String, String> reqMap, BaseReq req) {
        req.setMsgType(reqMap.get("MsgType"));
        req.setFromUserName(reqMap.get("FromUserName"));
        req.setToUserName(reqMap.get("ToUserName"));
        req.setCreateTime(Long.parseLong(reqMap.get("CreateTime")));
    }

    private boolean isLegal(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        // return SignUtil.checkSignature(getToken(), signature, timestamp,
        // nonce);
        return true;
    }

}