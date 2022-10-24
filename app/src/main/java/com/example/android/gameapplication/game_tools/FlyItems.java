package com.example.android.gameapplication.game_tools;

import com.example.android.gameapplication.R;

/**
 * @Author: Rizky Paskalis Totong
 * @Date: 30/09/22
 */
public class FlyItems extends GameTools {
    private double height;
    private double movedHeight;

    public FlyItems(GameToolsName name, int quantity,String toolName) {
        switch (name){
            case COPTER:
                setImage(R.drawable.copter);
                setCodeName(String.valueOf(R.string.copter));
                height = 100;
                break;
            case ROCKET:
                setImage(R.drawable.rocket);
                setCodeName(String.valueOf(R.string.rocket));
                height = 200;
                break;
        }
        setName(toolName);
        setQuantity(quantity);
        movedHeight = 0;
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