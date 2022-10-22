package com.example.android.gameapplication.games;

import android.content.Context;

public class SpringBoard extends Board{
    private Float jumpEnhance;

    public SpringBoard(Context context, Integer posX, Integer posY, Integer width, Integer screenSize, Integer imageID) {
        super(context, posX, posY, width, screenSize, imageID);
        this.boardType = "Spring";
    }

    public Float getJumpEnhance(){
        return jumpEnhance;
    }
}
