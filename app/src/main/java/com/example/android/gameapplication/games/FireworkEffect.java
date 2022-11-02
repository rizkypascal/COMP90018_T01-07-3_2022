package com.example.android.gameapplication.games;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import pl.droidsonroids.gif.GifDrawable;

/**
 * @Author Yuhan Gu
 * @Date: 27/10/22
 * @Desc: View class for game end firework effect
 */
public class FireworkEffect extends View {

    private final Integer posX;
    private final Integer posY;
    private final Integer size;
    private boolean finish;
    private final GifDrawable bomb;
    private final Rect imageBounds;

    public FireworkEffect(Context context, Integer posX, Integer posY, Integer size, Integer imageID) {
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

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }


}
