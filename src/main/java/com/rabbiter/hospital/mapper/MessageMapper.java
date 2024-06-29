package com.rabbiter.hospital.mapper;

import com.rabbiter.hospital.pojo.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.websocket.Session;
import java.util.List;

@Mapper
public interface MessageMapper {


    void saveMessage(String message, String userId , String senderId, boolean b ,boolean online);

    List<Message> findUnreadMessagesByUsername(@Param("userId") int userId);

    void updateMessagesStatus(@Param("messageId") Integer messageId);
}
