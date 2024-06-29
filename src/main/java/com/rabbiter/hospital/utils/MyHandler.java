package com.rabbiter.hospital.utils;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyHandler extends TextWebSocketHandler {

    // 用于存储每个用户的 WebSocketSession
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // 解析收到的消息，获取接收者和消息内容
        // 假设消息格式为 "receiver:message"
        String payload = message.getPayload();
        String[] parts = payload.split(":", 2);
        String receiver = parts[0];
        String text = parts[1];

        // 根据接收者找到对应的 WebSocketSession
        WebSocketSession receiverSession = sessions.get(receiver);

        if (receiverSession != null && receiverSession.isOpen()) {
            // 将消息发送给指定的接收者
            try {
                receiverSession.sendMessage(new TextMessage(text));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 连接建立后，将 WebSocketSession 存入 sessions
        // 假设 session 的 id 就是用户名
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 连接关闭后，从 sessions 中移除 WebSocketSession
        sessions.remove(session.getId());
    }
}
