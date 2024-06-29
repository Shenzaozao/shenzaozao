package com.rabbiter.hospital.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer  messageId;

    @Column(name = "sender_id", nullable = false)
    private Integer  senderId;

    @Column(name = "receiver_id", nullable = false)
    private Integer  receiverId;

    @Column(name = "message_content")
    private String messageContent;

    @Column(name = "send_time", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private Date sendTime;

    @Column(name = "is_read", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isRead;

    @Column(name = "is_online", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isOnline;

    // Constructors, getters, and setters

    public Message() {
        // Default constructor
    }

    public Integer  getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer  messageId) {
        this.messageId = messageId;
    }

    public Integer  getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer  senderId) {
        this.senderId = senderId;
    }

    public Integer  getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer  receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
