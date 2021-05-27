package com.example.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

public class File {
    private String name;
    private String filename;
    private String category;

    public File(String name, String filename, String category) {
        this.name = name;
        this.filename = filename;
        this.category = category;
    }

    public File(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("name");
            this.filename = jsonObject.getString("filename");
            this.category = jsonObject.getString("category");
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
