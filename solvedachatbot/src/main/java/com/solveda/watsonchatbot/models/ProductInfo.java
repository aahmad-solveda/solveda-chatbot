package com.solveda.watsonchatbot.models;

import com.google.gson.annotations.SerializedName;

public class ProductInfo {

    @SerializedName("product_id")
    private String product_id;


    @SerializedName("product_name")
    private String product_name;


    @SerializedName("prod_price")
    private String prod_price;


    @SerializedName("prod_image")
    private String prod_image;


    @SerializedName("prod_url")
    private String prod_url;


    @SerializedName("prod_decs")
    private String prod_decs;

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProd_price() {
        return prod_price;
    }

    public String getProd_image() {
        return prod_image;
    }

    public String getProd_url() {
        return prod_url;
    }

    public String getProd_decs() {
        return prod_decs;
    }
}
