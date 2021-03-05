package com.hangzup.worldofmeters.models;

public class Wallpapers {
    private int id;
    private String medium_url;

    public Wallpapers() {
    }

    public Wallpapers(int id, String medium_url) {
        this.id = id;
        this.medium_url = medium_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedium_url() {
        return medium_url;
    }

    public void setMedium_url(String medium_url) {
        this.medium_url = medium_url;
    }
}
