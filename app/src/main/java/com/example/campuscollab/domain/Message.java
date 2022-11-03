package com.example.campuscollab.domain;

import com.google.firebase.Timestamp;

public class Message {

    private String sender;
    private String receiver;
    private String content;
    private Timestamp sentDate;
    private String id;

    public Message() {}

    public Message(String sender, String receiver, String content, Timestamp sentDate) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.sentDate = sentDate;
    }

    public Message(String sender, String receiver, String content, Timestamp sentDate, String id) {
        this.sender = sender;
        this.receiver = receiver;
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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
