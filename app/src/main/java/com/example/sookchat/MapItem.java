package com.example.sookchat;

public class MapItem {
    int id;
    String title;
    String description;
    String filename;

    public MapItem(int id, String title, String description, String filename) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public String toString() {
        return "MapItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }
}
