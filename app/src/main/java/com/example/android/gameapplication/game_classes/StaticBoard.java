package com.example.android.gameapplication.game_classes;

import android.content.Context;

public class StaticBoard extends Board{

    public StaticBoard(Context context, Integer posX, Integer posY , Integer width,Integer screenSize,Integer imageID) {
        super(context, posX, posY, width, screenSize, imageID);
        this.boardType = "Static";
    }

    @Override
    public void move(Float velocityX, Float velocityY)
    {
        this.posY += Math.round(velocityY);
    }
}
