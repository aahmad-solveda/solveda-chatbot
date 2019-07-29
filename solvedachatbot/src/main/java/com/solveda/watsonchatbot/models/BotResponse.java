package com.solveda.watsonchatbot.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BotResponse {

    @SerializedName("intents")
    List<Intents> intents;

    @SerializedName("entities")
    List<Entities> entities;

    @SerializedName("output")
    Output output;

    @SerializedName("context")
    BotContext context;

    public List<Intents> getIntents() {
        return intents;
    }

    public void setIntents(List<Intents> intents) {
        this.intents = intents;
    }

    public List<Entities> getEntities() {
        return entities;
    }

    public void setEntities(List<Entities> entities) {
        this.entities = entities;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }

    public BotContext getContext() {
        return context;
    }

    public void setContext(BotContext context) {
        this.context = context;
    }
}
