package com.solveda.watsonchatbot.messages;

public interface IMessageData {

    String getId();
    int getMessageType();
    boolean isBotMessage();
    long getTimeInMillis();
    String getVideoUrl();
    String getImageUrl();
    String getDateTime();
    String getMessage();
}
