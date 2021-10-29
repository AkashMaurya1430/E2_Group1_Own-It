package com.example.ownit;

import okhttp3.RequestBody;

public class Advertisement {
    private String userid;
    private String id;
    private String title;
    private String description;
    private String category;
    private RequestBody photo;
    private String price;
    private String message;

    public String getMessage() {
        return message;
    }

    public String getUserid() {
        return userid;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public RequestBody getPhoto() {
        return photo;
    }

    public String getPrice() {
        return price;
    }


    public Advertisement(String userid, String title, String description, String category, RequestBody photo, String price) {
        this.userid = userid;
        this.title = title;
        this.description = description;
        this.category = category;
        this.photo = photo;
        this.price = price;
    }
}
