package com.tosunapp.mystoreapp.Model;

import com.google.gson.annotations.SerializedName;

public class ProductDetail {

    @SerializedName("orderDetail")
    private String orderDetail;

    @SerializedName("summaryPrice")
    private double summaryPrice;


    //GETTER METHOTS
    public String getOrderDetail() {
        return orderDetail;
    }
    public double getSummaryPrice() {
        return summaryPrice;
    }
}
