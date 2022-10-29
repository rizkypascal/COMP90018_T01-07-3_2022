package com.example.android.gameapplication.games;

import android.content.Context;
import android.util.Log;

import java.util.Random;

public class StaticBoard extends Board{

    public StaticBoard(Context context, Integer posX, Integer posY , Integer width,Integer screenSize,Integer imageID) {
        super(context, posX, posY, width, screenSize, imageID);
        this.boardType = "Static";
    }

    @Override
    public void move(Float velocityX, Float velocityY)
    {

        if (this.seed <= 2){
            Integer nextX = Math.round(velocityX * this.moveDirection) + posX;
            Log.d("Board", "move: " + this.posX);
            if (nextX >= screenSize - this.width/2){
                this.moveDirection = -1;
            }
            else if (nextX <= this.width/2){
                this.moveDirection = 1;
                this.posX = this.width/2;
            }
            else{
                this.posX = nextX;
            }

        }
        this.posY += Math.round(velocityY);

    }
}
