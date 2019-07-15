package com.solveda.watsonchatbot.messages;

public class MessageData implements IMessageData
{
    public static final int TYPE_TEXT=1;
    public static final int TYPE_IMAGE=2;
    public static final int TYPE_VIDEO=3;

    private String id;
    private String message;
    private String dateTime;
    private String ImageUrl;
    private String VideoUrl;
    private int messageType;
    private long timeInMillis;
    private boolean isBotMessage;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    @Override
    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    @Override
    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    @Override
    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

    @Override
    public boolean isBotMessage() {
        return isBotMessage;
    }

    public void setBotMessage(boolean botMessage) {
        isBotMessage = botMessage;
    }

    @Override
    public String getId() {
        return id;
    }
}
