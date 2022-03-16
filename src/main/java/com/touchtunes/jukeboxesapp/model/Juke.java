package com.touchtunes.jukeboxesapp.model;

public class Juke {

    private String id;
    private String model;
    private Component[] components;

    public Juke() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Component[] getComponents() {
        return components;
    }

    public void setComponents(Component[] components) {
        this.components = components;
    }
}
