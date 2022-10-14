package com.example.android.gameapplication.specialitems;

import com.example.android.gameapplication.R;

public class FlyItems extends Items{
    private double height;
    private double movedHeight;

    public FlyItems(ItemName name) {
        switch (name){
            case COPTER:
                setImage(R.drawable.copter);
                setName("Helicopter");
                height = 100;
                movedHeight = 0;
                break;
            case ROCKET:
                setImage(R.drawable.rocket);
                setName("Rocket");
                height = 200;
                movedHeight = 0;
                break;
        }
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