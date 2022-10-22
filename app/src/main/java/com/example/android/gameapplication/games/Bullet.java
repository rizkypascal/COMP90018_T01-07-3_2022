package com.example.android.gameapplication.games;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.android.gameapplication.R;

/**
 * @author Tony Shu
 * @date 30/09/2022
 * @desc a bullet that the jumper can shoot
 */
public class Bullet extends View
{
    private Integer posX;
    private Integer posY;
    private Integer size;
    private Drawable bullet;
    private Rect imageBounds;


    public Bullet(Context context,Integer posX, Integer posY, Integer size) {
        super(context);
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.imageBounds = new Rect(posX-size,posY-size,posX, posY+size);
        this.bullet = context.getResources().getDrawable(R.drawable.bullet);
        this.bullet.setBounds(this.imageBounds);

    }

    public void move(Float velocityY)
    {
        posY += Math.round(velocityY);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        imageBounds.set(posX-size,posY-size,posX, posY+size);
        bullet.setBounds(imageBounds);
        bullet.draw(canvas);
        invalidate();
    }
}
