package com.example.android.gameapplication.GameClasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.android.gameapplication.R;

/**
 * @author Tony Shu
 * @date 15/10/2022
 * @desc represents quiz, assignments, exam etc.
 */
public class Monster extends View {
    private Integer posX;
    private Integer posY;
    private Integer size;
    private Boolean alive;
    private Rect imageBounds;
    private Drawable monster;

    public Monster(Context context, Integer posX, Integer posY, Integer size, Boolean alive, MonsterType monsterType) {
        super(context);
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.alive = alive;
        this.imageBounds = new Rect(posX-size,posY-size,posX+size, posY+size);
        switch (monsterType){
            case EXAM:
                this.monster = context.getResources().getDrawable(R.drawable.exam);
                break;
            case QUIZ:
                this.monster = context.getResources().getDrawable(R.drawable.quiz);
                break;
            case TEST:
                this.monster = context.getResources().getDrawable(R.drawable.test);
                break;
            case HOMEWORK:
                this.monster = context.getResources().getDrawable(R.drawable.homework);
            default:
                break;
        }
    }

    /**
     * @param velocityX
     * @param velocityY
     * @return
     */
    public void move(Float velocityX, Float velocityY)
    {
        posX += Math.round(velocityX);
        posY += Math.round(velocityY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        imageBounds.set(posX-size,posY-size,posX+size, posY+size);
        monster.setBounds(imageBounds);
        monster.draw(canvas);
        invalidate();
    }
}
