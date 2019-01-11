package com.example.krisorn.tangwong.Model;



public class Sender {
    public Notification2 notification;
    public String to;

    public Sender() {
    }

    public  Sender(String to, Notification2 notification)
    {
        this.notification = notification;
        this.to=to;
    }

    public Notification2 getNotification() {
        return notification;
    }

    public String getTo() {
        return to;
    }
}
