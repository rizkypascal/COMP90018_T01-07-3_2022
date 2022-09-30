package com.example.android.gameapplication.GameClasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.example.android.gameapplication.R;

/**
 * @author Tony Shu
 * @date 30/09/2022
 * @desc main character that the player controls
 */
public class Jumper extends View
{
    private Integer posX;
    private Integer posY;
    private Float speedX;
    private Float speedY;
    private Float accX; // acceleration on X axis
    private Float accY; // acceleration on Y axis
    private Enum status;
    private Integer score; // can be stored in game activity instead
    private Boolean alive;
    private Integer radius; // size of jumper
    private Drawable jumper; // image resource
    Rect imageBounds; // jumper image is drawn based on this rectangle size
    private Integer screenSize;


    public Jumper(Context context, Integer posX, Integer posY,Integer radius ,Integer screenSize,Integer imageID) {
        super(context);
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        this.screenSize = screenSize;
        this.jumper = context.getResources().getDrawable(imageID);
        this.imageBounds = new Rect(posX-radius,posY-radius,posX+radius, posY+radius);
        jumper.setBounds(imageBounds);
    }

    /**
     * @param velocityX
     * @param velocityY
     * @return
     */
    public void move(Float velocityX, Float velocityY)
    {
        posX += Math.round(velocityX);
    }


    /**
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        posX = Math.floorMod(posX, screenSize);
        imageBounds.set(posX-radius,posY-radius,posX+radius, posY+radius);
        jumper.setBounds(imageBounds);
        jumper.draw(canvas);
        invalidate();
    }

    /**
     *
     * @param status enum Status
     */
    public void changeStatus(Status status)
    {

    }

    /**
     *
     * @param newScore score changed
     */
    public void changeScore(Integer newScore)
    {
        this.score += newScore;
    }

    public void shoot()
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

    public Float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(Float speedX) {
        this.speedX = speedX;
    }

    public Float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(Float speedY) {
        this.speedY = speedY;
    }

    public Float getAccX() {
        return accX;
    }

    public void setAccX(Float accX) {
        this.accX = accX;
    }

    public Float getAccY() {
        return accY;
    }

    public void setAccY(Float accY) {
        this.accY = accY;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Drawable getJumper() {
        return jumper;
    }

    public void setJumper(Drawable jumper) {
        this.jumper = jumper;
    }

    public Rect getImageBounds() {
        return imageBounds;
    }

    public void setImageBounds(Rect imageBounds) {
        this.imageBounds = imageBounds;
    }
}
