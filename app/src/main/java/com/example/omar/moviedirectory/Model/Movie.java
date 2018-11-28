package com.example.omar.moviedirectory.Model;

import java.io.Serializable;

/**
 * Created by OMAR on 10/22/2018.
 */

public class Movie implements Serializable {

    private String title;
    private String type;
    private String image;
    private String id;
    private String years;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }
}
