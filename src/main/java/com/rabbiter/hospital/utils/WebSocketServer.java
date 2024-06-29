package com.rabbiter.hospital.utils;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author websocket服务
 */
@ServerEndpoint(value = "/imserver/{username}/{userId}")
@Component
public class WebSocketServer {
    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * 记录当前在线连接数
     */
    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessionMap.put(username, session);
        log.info("有新用户加入，username={}, 当前在线人数为：{}", username, sessionMap.size());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.set("users", array);
        for (Object key : sessionMap.keySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("username", key);
            // {"username", "zhang", "username": "admin"}
            array.add(jsonObject);
        }
//        {"users": [{"username": "zhang"},{ "username": "admin"}]}
        sendAllMessage(JSONUtil.toJsonStr(result));  // 后台发送消息给所有的客户端
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessionMap.remove(username);
        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, sessionMap.size());
    }
    /**
     * 收到客户端消息后调用的方法
     * 后台收到客户端发送过来的消息
     * onMessage 是一个消息的中转站
     * 接受 浏览器端 socket.send 发送过来的 json数据
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("username") String username) {
        log.info("服务端收到用户username={}的消息:{}", username, message);
        JSONObject obj = JSONUtil.parseObj(message);
        String toUsername = obj.getStr("to"); // to表示发送给哪个用户，比如 admin
        String text = obj.getStr("text"); // 发送的消息文本  hello
        // {"to": "admin", "text": "聊天文本"}
        Session toSession = sessionMap.get(toUsername); // 根据 to用户名来获取 session，再通过session发送消息文本
        if (toSession != null) {
            // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
            // {"from": "zhang", "text": "hello"}
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("from", username);  // from 是 zhang
            jsonObject.set("text", text);  // text 同上面的text
            this.sendMessage(jsonObject.toString(), toSession);
            log.info("发送给用户username={}，消息：{}", toUsername, jsonObject.toString());
        } else {
            log.info("发送失败，未找到用户username={}的session", toUsername);
        }
    }
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
    /**
     * 服务端发送消息给所有客户端
     */
    private void sendAllMessage(String message) {
        try {
            for (Session session : sessionMap.values()) {
                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
                session.getBasicRemote().sendText(message);
            }
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败", e);
        }
    }
}


//package com.rabbiter.hospital.utils;
//
//import cn.hutool.json.JSONArray;
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.rabbiter.hospital.pojo.Message;
//import com.rabbiter.hospital.service.MessageService;
//import netscape.javascript.JSObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author websocket服务
// */
//@ServerEndpoint(value = "/imserver/{username}/{userId}")
//@Component
//public class WebSocketServer {
//
//
//    private static MessageService messageService;
//    @Autowired
//    public void setMessageService(MessageService messageService) {
//        WebSocketServer.messageService = messageService;
//    }
//    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);
//    /**
//     * 记录当前在线连接数
//     */
//    public static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();
//    /**
//     * 连接建立成功调用的方法
//     */
//    @OnOpen
//    public void onOpen(Session session, @PathParam("username") String username, @PathParam("userId") int userId) {
//        sessionMap.put(username, session);
//        log.info("有新用户加入，username={}, 当前在线人数为：{}", username, sessionMap.size());
//        JSONObject result = new JSONObject();
//        JSONArray array = new JSONArray();
//        result.set("users", array);
//        for (Object key : sessionMap.keySet()) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.set("username", key);
//            // {"username", "zhang", "username": "admin"}
//            array.add(jsonObject);
//        }
////        {"users": [{"username": "zhang"},{ "username": "admin"}]}
//        sendAllMessage(JSONUtil.toJsonStr(result));  // 后台发送消息给所有的客户端
//        // 查询医生的未读消息
//        List<Message> unreadMessages = messageService.findUnreadMessagesByUsername(userId);
//        if (unreadMessages != null &&!unreadMessages.isEmpty()) {
//            for (Message message : unreadMessages) {
//                // 发送未读消息提示给医生
//                JSONObject notification = new JSONObject();
//                notification.put("type", "unreadMessage");
//                notification.put("from", message.getSenderId());
//                notification.put("content", message.getMessageContent());
//                sendMessage(notification, session);
//            }
//            log.info("向用户{}发送了未读消息提示", username);
//
//            // 更新数据库中已发送的未读消息状态为已读或删除
//            messageService.updateMessagesStatus(unreadMessages);
//            log.info("已更新未读消息状态为已读");
//        }
//    }
//
//
//
//
//
//    /**
//     * 连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose(Session session, @PathParam("username") String username) {
//        sessionMap.remove(username);
//        log.info("有一连接关闭，移除username={}的用户session, 当前在线人数为：{}", username, sessionMap.size());
//    }
//    /**
//     * 收到客户端消息后调用的方法
//     * 后台收到客户端发送过来的消息
//     * onMessage 是一个消息的中转站
//     * 接受 浏览器端 socket.send 发送过来的 json数据
//     * @param message 客户端发送过来的消息
//     */
//    @OnMessage
//    public void onMessage(String message, Session session, @PathParam("username") String username ,@PathParam("userId") int userId) {
//        log.info("服务端收到用户username={}的消息:{}", username, message);
//        JSONObject obj = JSONUtil.parseObj(message);
//        log.info("obj:"+obj);
//        String toUsername = obj.getStr("to"); // to表示发送给哪个用户，比如 admin
//        String text = obj.getStr("text"); // 发送的消息文本  hello
//        // {"to": "admin", "text": "聊天文本"}
//        Session toSession = sessionMap.get(toUsername); // 根据 to用户名来获取 session，再通过session发送消息文本
//        if (toSession != null) {
//            // 服务器端 再把消息组装一下，组装后的消息包含发送人和发送的文本内容
//            // {"from": "zhang", "text": "hello"}
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.set("from", username);  // from 是 zhang
//            jsonObject.set("text", text);  // text 同上面的text
//            this.sendMessage(jsonObject, toSession);
//            log.info("发送给用户username={}，消息：{}", toUsername, jsonObject.toString());
//        } else {
//            log.info("发送失败，未找到用户username={}的session", toUsername);
//        }
//    }
//    @OnError
//    public void onError(Session session, Throwable error) {
//        log.error("发生错误");
//        error.printStackTrace();
//    }
//    /**
//     * 服务端发送消息给客户端
//     */
//    private void sendMessage(JSONObject message, Session toSession) {
//        try {
//            log.info("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
//            toSession.getBasicRemote().sendText(message.toString());
//            messageService.saveAllMessage(message, toSession);
//        } catch (Exception e) {
//            log.error("服务端发送消息给客户端失败", e);
//        }
//    }
//    /**
//     * 服务端发送消息给所有客户端
//     */
//    private void sendAllMessage(String message) {
//        try {
//            for (Session session : sessionMap.values()) {
//                log.info("服务端给客户端[{}]发送消息{}", session.getId(), message);
//                session.getBasicRemote().sendText(message);
//            }
//        } catch (Exception e) {
//            log.error("服务端发送消息给客户端失败", e);
//        }
//    }
//
//    // 判断医生是否在线
//    private boolean isDoctorOnline(String doctorUsername) {
//        return sessionMap.containsKey(doctorUsername);
//    }
//
//    /**
//     * 存储离线消息
//     */
//    private void saveOfflineMessage(String fromUser, String toUser, String message) {
//        // TODO: 将消息存储到数据库或消息队列中，以便用户上线后获取并回复消息
//        log.info("用户{}给{}发送的消息已存储为离线消息: {}", fromUser, toUser, message);
//    }
//}
