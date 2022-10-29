package com.example.android.gameapplication.games;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * @author Runzhe Hua
 * @version 01/10/2021
 */
public class Board extends View
{
    protected Integer posX;
    protected Integer moveDirection;
    protected Integer posY;
    protected Integer width; // length of the board
    protected Enum status;
    protected Integer height = 50;
    protected Drawable boardDrawable; // image resource
    Rect imageBounds; // jumper image is drawn based on this rectangle size
    protected Integer screenSize;
    protected String boardType;
    protected Integer seed;

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
        this.boardDrawable.setBounds(imageBounds);
        this.moveDirection = 1;

        Random rand =new Random();

        seed = rand.nextInt(10);

        Log.d("RAND",""+ seed);
    }

    /**
     * @param velocityX
     * @param velocityY
     * @return
     */
    public void move(Float velocityX, Float velocityY)
    {

        if (this.seed <= 3){
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

    /**
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.imageBounds = new Rect(posX-this.width/2,posY-this.height/2,posX+this.width/2, posY+this.height);
        this.boardDrawable.setBounds(this.imageBounds);
        this.boardDrawable.draw(canvas);
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

    public Integer getBoardHeight() { return this.height; }

    public Integer getXLeft() { return posX-this.width/2; }

    public Integer getXRight() { return posX+this.width/2; }
}
