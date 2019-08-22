package org.lol.wazirbuild.msilib.model;


import java.net.URL;

public class notes_category_model {
    private String Title;
    private String date;
    private String notes_provider;
    private String url;

    public notes_category_model() {
    }

    public notes_category_model(String title, String date, String notes_provider, String url) {
        Title = title;
        this.date = date;
        this.notes_provider = notes_provider;
        this.url = url;
    }

    public String getTitle() {
        return Title;
    }

    public String getDate() {
        return date;
    }

    public String getNotes_provider() {
        return notes_provider;
    }

    public String getUrl() {
        return url;
    }
}
