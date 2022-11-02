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
    private final Integer size;
    private final Drawable bullet;
    private final Rect imageBounds;

    public Bullet(Context context,Integer posX, Integer posY, Integer size) {
        super(context);
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.imageBounds = new Rect(posX-size,posY-size,posX, posY+size);
        this.bullet = context.getResources().getDrawable(R.drawable.bullet);
        this.bullet.setBounds(this.imageBounds);

    }

    /**
     *
     * @param velocityY a bullet only moves on the y axis
     */
    public void move(Float velocityY)
    {
        posY -= Math.round(velocityY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        imageBounds.set(posX-size,posY-size,posX, posY+size);
        bullet.setBounds(imageBounds);
        bullet.draw(canvas);
        invalidate();
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }
}
