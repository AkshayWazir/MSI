package org.lol.wazirbuild.msilib.Database.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")
public class notes_category_model {

    @PrimaryKey
    public int id;
    private String title;
    private String date;
    private String notes_provider;
    private String url;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNotes_provider(String notes_provider) {
        this.notes_provider = notes_provider;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Ignore
    public notes_category_model() {
    }

    public notes_category_model(int id, String title, String date, String notes_provider, String url) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.notes_provider = notes_provider;
        this.url = url;
    }

    public String getTitle() {
        return title;
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
