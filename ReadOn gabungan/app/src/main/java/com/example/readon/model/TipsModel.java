package com.example.readon.model;

import java.io.Serializable;

public class TipsModel implements Serializable {
    String title;
    String subtitle;
    String description;

    public TipsModel(String title, String subtitle, String description) {

        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
