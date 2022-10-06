package com.example.android.gameapplication.specialitems;

public class FlyItems extends Items{
    private double height;
    private double movedHeight;

    public FlyItems(int image, String name) {
        super(image, name);
    }

    public double getMovedHeight() {
        return movedHeight;
    }

    public void setMovedHeight(double movedHeight) {
        this.movedHeight = movedHeight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}