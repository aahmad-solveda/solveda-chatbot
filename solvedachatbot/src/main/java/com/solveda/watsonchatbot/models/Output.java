package com.solveda.watsonchatbot.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Output {
    @SerializedName("text")
    List<String> text;


    @SerializedName("text1")
    String plainText ;


    @SerializedName("nodes_visited")
    List<String> nodes_visited;


    @SerializedName("log_messages")
    List<String> log_messages;

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public List<String> getNodes_visited() {
        return nodes_visited;
    }

    public void setNodes_visited(List<String> nodes_visited) {
        this.nodes_visited = nodes_visited;
    }

    public List<String> getLog_messages() {
        return log_messages;
    }

    public void setLog_messages(List<String> log_messages) {
        this.log_messages = log_messages;
    }

    public String getPlainText() {
        return plainText;
    }
}
