package com.example.android.gameapplication.games;

import android.content.Context;


public class FakeBoard extends Board{

        public FakeBoard(Context context, Integer posX, Integer posY , Integer width, Integer screenSize, Integer imageID) {
            super(context, posX, posY, width, screenSize, imageID);
            this.boardType = "Fake";
        }

        @Override
        public void move(Float velocityX, Float velocityY)
        {
            this.posY += Math.round(velocityY);
        }
}
