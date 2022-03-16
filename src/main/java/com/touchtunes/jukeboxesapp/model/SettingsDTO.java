package com.touchtunes.jukeboxesapp.model;

public class SettingsDTO {

    private Setting[] settings;

    public SettingsDTO() {}

    public Setting[] getSettings() {
        return settings;
    }

    public void setSettings(Setting[] settings) {
        this.settings = settings;
    }
}
