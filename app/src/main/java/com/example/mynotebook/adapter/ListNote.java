package com.example.mynotebook.adapter;

import java.io.Serializable;

public class ListNote implements Serializable {
    private String title;
    private String description;
    private String uri = "empty";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
