package com.example.android.gameapplication.specialitems;

public class Items {
    private int image;
    private String name;

    public Items(int image, String name){
        this.image = image;
        this.name = name;
    }

    public Items() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
