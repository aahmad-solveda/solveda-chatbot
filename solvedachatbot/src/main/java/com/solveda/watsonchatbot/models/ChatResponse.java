package com.solveda.watsonchatbot.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatResponse
{
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_PRODUCT_INFO = "product";
    @SerializedName("type")
    private String type;

    @SerializedName("text")

    private List<String> text;

    @SerializedName("product_info")
    private List<ProductInfo> product_info;

    public String getType() {
        return type;
    }

    public List<String> getText() {
        return text;
    }

    public List<ProductInfo> getProductInfo() {
        return product_info;
    }
}
