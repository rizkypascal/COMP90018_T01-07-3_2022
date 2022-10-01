package com.example.android.gameapplication.GameClasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.example.android.gameapplication.R;

/**
 * @author Runzhe Hua
 * @version 01/10/2021
 */
public class Board extends View
{
    private Integer posX;
    private Integer posY;
    private Integer width; // length of the board
    private Enum status;
    private Integer score; // can be stored in game activity instead
    private Integer height = 70;
    private Drawable boardDrawable; // image resource
    Rect imageBounds; // jumper image is drawn based on this rectangle size
    private Integer screenSize;

    private String boardType;


    public Board(Context context, Integer posX, Integer posY , Integer width,Integer screenSize,Integer imageID) {
        /*
        * @param context the context of the game
        * @param posX the x position of the board
        * @param posY the y position of the board
        * @param screenSize the size of the screen
        * @param imageID the image of the board
        */
        super(context);
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.screenSize = screenSize;
        this.boardDrawable = context.getResources().getDrawable(imageID);
        this.imageBounds = new Rect(posX-width/2,posY-this.height/2,posX+width/2, posY+this.height/2);
        boardDrawable.setBounds(imageBounds);
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


    /**
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        posX = Math.floorMod(posX, screenSize);
        this.imageBounds = new Rect(posX-this.width/2,posY-this.height/2,posX+this.width/2, posY+this.height/2);
        boardDrawable.setBounds(this.imageBounds);
        boardDrawable.draw(canvas);
        invalidate();
    }

    /**
     *
     * @param status enum Status
     */
    public void changeStatus(Status status)
    {

    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }


    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    public Drawable getboard() {
        return this.boardDrawable;
    }
}
