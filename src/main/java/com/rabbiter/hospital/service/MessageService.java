package com.rabbiter.hospital.service;

import cn.hutool.json.JSONObject;
import com.rabbiter.hospital.pojo.Message;
import netscape.javascript.JSObject;

import javax.websocket.Session;
import java.util.List;

public interface MessageService {
    /**
     * 保存离线消息
     * @param message
     */
    void saveOfflineMessage(String message);
    /**
     * 查询未读消息
     * @param userId
     * @return
     */
    List<Message> findUnreadMessagesByUsername(int userId);

    /**
     * 更新消息状态
     * @param unreadMessages
     */

    void updateMessagesStatus(List<Message> unreadMessages);

    /**
     * 保存所有消息
     * @param message
     * @param toSession
     */
    void saveAllMessage(JSONObject message, Session toSession);
}
