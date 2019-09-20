package com.tosunapp.mystoreapp.Model;

import android.graphics.Color;

import com.google.gson.annotations.SerializedName;

public class Store {

    @SerializedName("date")
    private String date;

    @SerializedName("month")
    private String month;

    @SerializedName("marketName")
    private String marketName;

    @SerializedName("orderName")
    private String orderName;

    @SerializedName("productPrice")
    private double productPrice;

    @SerializedName("productState")
    private String productState;

    @SerializedName("productDetail")
    private ProductDetail productDetail;

    //for expand storedetail row.
    private boolean isClicked;



    public String getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public String getMarketName() {
        return marketName;
    }

    public String getOrderName() {
        return orderName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductState() {
        return productState;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }


    public boolean isClicked() {
        return isClicked;
    }
    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }


}


