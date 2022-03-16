package com.touchtunes.jukeboxesapp.model;

public class Setting {

    private String id;
    private String[] requires;

    public Setting() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getRequires() {
        return requires;
    }

    public void setRequires(String[] requires) {
        this.requires = requires;
    }
}
