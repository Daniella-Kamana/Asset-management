package com.example.lrsdemo;

public class Asset {
    public final int imageResId;
    public final String name;
    public final String category;
    public final int quantity;

    public Asset(int imageResId, String name, String category, int quantity){
        this.imageResId = imageResId;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
    }
}
