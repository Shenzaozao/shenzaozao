package com.rabbiter.hospital.service.serviceImpl;

import cn.hutool.json.JSONObject;
import com.rabbiter.hospital.mapper.MessageMapper;
import com.rabbiter.hospital.pojo.Message;
import com.rabbiter.hospital.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.List;

@Slf4j
@Service("MessageService")
public class MessageServiceImpl implements MessageService {


    @Autowired
    private MessageMapper messageMapper;
    @Override
    public void saveOfflineMessage(String message) {
        Message offlineMessage = new Message( );
    }

    @Override
    public List<Message> findUnreadMessagesByUsername(int userId) {
        List<Message> message = messageMapper.findUnreadMessagesByUsername(userId);
        return null;
    }

    @Override
    public void updateMessagesStatus(List<Message> unreadMessages) {
        if (unreadMessages != null && unreadMessages.size() > 0){
            for (Message message : unreadMessages){
                Integer messageId = message.getMessageId();
                messageMapper.updateMessagesStatus(messageId);
            }
        }
    }

    @Override
    public void saveAllMessage(JSONObject message, Session toSession) {
        log.info(toSession.toString());
        String from = toSession.getId();
        String to = message.getStr("to");
        String context = message.getStr("context");
        messageMapper.saveMessage(context, from,to,true, true);
    }
}
