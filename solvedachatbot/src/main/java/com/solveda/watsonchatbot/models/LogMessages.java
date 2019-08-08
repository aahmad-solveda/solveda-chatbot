package com.solveda.watsonchatbot.models;

import com.google.gson.annotations.SerializedName;

public class LogMessages {

    @SerializedName("level")
    private String level;


    @SerializedName("msg")
    private String msg;

    @SerializedName("node_id")
    private String node_id;
}
