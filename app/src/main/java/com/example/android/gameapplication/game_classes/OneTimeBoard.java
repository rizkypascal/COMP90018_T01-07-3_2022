package com.example.android.gameapplication.game_classes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

public class OneTimeBoard extends Board{
    private Boolean isbroken;

    public OneTimeBoard(Context context, Integer posX, Integer posY, Integer width, Integer screenSize, Integer imageID) {
        super(context, posX, posY, width, screenSize, imageID);
        this.boardType = "OneTime";
        isbroken = false;
    }

    public void setIsbroken(){
        isbroken = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!isbroken){
            posX = Math.floorMod(posX, screenSize);
            this.imageBounds = new Rect(posX-this.width/2,posY-this.height/2,posX+this.width/2, posY+this.height/2);
            this.boardDrawable.setBounds(this.imageBounds);
            this.boardDrawable.draw(canvas);
            invalidate();
        }
        else{
            this.setVisibility(View.GONE);
            Log.d("OneTimeBoard", "isbroken: ");
        }
    }
}
