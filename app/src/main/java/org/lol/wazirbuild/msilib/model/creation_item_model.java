package org.lol.wazirbuild.msilib.model;

public class creation_item_model {

    String description;
    String imageUrl;
    String title;

    public creation_item_model() {
    }

    public creation_item_model(String description, String imageUrl, String title) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }
}
