package com.example.own_it;

import java.io.Serializable;
import java.util.List;

public class Advertisement implements Serializable {

    private String idAdvertisement;
    private String state;
    private String category;
    private String title;
    private String value;
    private String phone;
    private String description;
    private List<String> photo;
}
