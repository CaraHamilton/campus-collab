package com.example.campuscollab.domain;

import com.google.firebase.Timestamp;

public class Message {

    private String sender;
    private String senderName;
    private String receiver;
    private String receiverName;
    private String content;
    private Timestamp sentDate;
    private String id;

    public Message() {}

    public Message(String sender, String senderName, String receiver, String receiverName, String content, Timestamp sentDate) {
        this.sender = sender;
        this.senderName = senderName;
        this.receiver = receiver;
        this.receiverName = receiverName;
        this.content = content;
        this.sentDate = sentDate;
    }

    public Message(String sender, String senderName, String receiver, String receiverName, String content, Timestamp sentDate, String id) {
        this.sender = sender;
        this.senderName = senderName;
        this.receiver = receiver;
        this.receiverName = receiverName;
        this.content = content;
        this.sentDate = sentDate;
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getSentDate() {
        return sentDate;
    }

    public void setSentDate(Timestamp sentDate) {
        this.sentDate = sentDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
