package com.example.jmo.workoutv2.data;

public class ProgramTypes {
    private String name;
    private String description;
    private int thumbnail;
    private String uniqueTag;

    public ProgramTypes() {

    }
    public ProgramTypes(String name, String description, int thumbnail, String uniqueTag) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.uniqueTag = uniqueTag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThumbnail () {
        return thumbnail;
    }

    public void setThumbnail() {
        this.thumbnail = thumbnail;
    }

    public String getUniqueTag() {
        return uniqueTag;
    }

    public void setUniqueTag(String uniqueTag) {
        this.uniqueTag = uniqueTag;
    }
}
