package com.example.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

public class File {
    private String artist;
    private String title;
    private String filename;
    private String category;

    public File(String artist, String title, String filename, String category) {
        this.artist = artist;
        this.title = title;
        this.filename = filename;
        this.category = category;
    }

    public File(JSONObject jsonObject) {
        try {
            this.artist = jsonObject.getString("artist");
            this.title = jsonObject.getString("title");
            this.filename = jsonObject.getString("filename");
            this.category = jsonObject.getString("category");
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
