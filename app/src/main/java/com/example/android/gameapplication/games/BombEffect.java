package com.example.android.gameapplication.games;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;

/**
 * @Author Rizky Paskalis Totong
 * @Date: 26/10/22
 * @Desc: View class for clear monster effect
 */
public class BombEffect extends View
{
    private Integer posX;
    private Integer posY;
    private Integer size;
    private GifDrawable bomb;
    private Rect imageBounds;

    /*
     * @param context the context of the game
     * @param posX the x position of the bomb
     * @param posY the y position of the bomb
     * @param size the size of the bomb
     * @param imageID the image of the bomb
     */
    public BombEffect(Context context, Integer posX, Integer posY, Integer size, Integer imageID) {
        super(context);
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.bomb = GifDrawable.createFromResource(context.getResources(), imageID);
        this.imageBounds = new Rect(posX-size,posY-size,posX+size, posY+size);
        this.bomb.setBounds(imageBounds);
    }

    /**
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        imageBounds.set(posX-size,posY-size,posX+size, posY+size);
        bomb.setBounds(imageBounds);
        bomb.draw(canvas);
        invalidate();
    }
}
