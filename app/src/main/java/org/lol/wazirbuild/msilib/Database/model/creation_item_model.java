package org.lol.wazirbuild.msilib.Database.model;

public class creation_item_model {

    String description;
    String imageUrl;
    String title;
    String likedBy;
    String dislikedBy;

    public creation_item_model() {
    }

    public creation_item_model(String description, String imageUrl, String title, String likedBy, String dislikedBy) {
        this.description = description;
        this.imageUrl = imageUrl;
        this.title = title;
        this.likedBy = likedBy;
        this.dislikedBy = dislikedBy;
    }

    public String getDescription() {
        return description;
    }

    public String getLikedBy() {
        return likedBy;
    }

    public String getDislikedBy() {
        return dislikedBy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }
}
