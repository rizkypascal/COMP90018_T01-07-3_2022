package com.example.android.gameapplication.games;

import static android.os.Build.VERSION_CODES.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import pl.droidsonroids.gif.GifDrawable;

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
    private final Integer radiusDefault;
    private GifDrawable jumper; // image resource
    Rect imageBounds; // jumper image is drawn based on this rectangle size
    private Integer screenSize;
    private Integer boardMove;
    private Float flyMove;
    private final GifDrawable jumperDefault;

    public Jumper(Context context, Integer posX, Integer posY, Integer radius, Float speedY, Integer screenSize, Integer imageID) {
        super(context);
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        this.radiusDefault = radius;
        this.speedY = speedY;
        this.screenSize = screenSize;
        this.jumper = GifDrawable.createFromResource(context.getResources(), imageID);
        this.jumperDefault = GifDrawable.createFromResource(context.getResources(), imageID);
        this.imageBounds = new Rect(posX-radius,posY-radius,posX+radius, posY+radius);
        jumper.setBounds(imageBounds);
        boardMove = 0;
        flyMove = 0f;
    }

    /**
     * @param velocityX
     * @param velocityY
     * @return
     */
    public void move(Float velocityX, Float velocityY, int upperthreshold, int lowerthreshold)
    {
        posX += Math.round(velocityX);
        posY += Math.round(velocityY-this.speedY);
        if(posY < upperthreshold)
        {
            if(flyMove <= 0f){
                this.jumper = this.jumperDefault;
                this.radius = this.radiusDefault;
                this.boardMove = upperthreshold - posY;
                posY = upperthreshold;
                status = Status.stayStill;
            }
        }
        
        if(posY > lowerthreshold)
        {
            this.boardMove = lowerthreshold - posY;
            posY = lowerthreshold;
        }
        
        if(flyMove <= 0f){
            this.jumper = this.jumperDefault;
            this.radius = this.radiusDefault;
            if (velocityY > this.speedY)
            {
                status = Status.movingDown;
            }
        }
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
        //speed on y axis is decreasing continuously
        //if falls onto a board (CollisionUtils.JumperBoardCollision),
        //then the speed is reassigned with a new positive value
        // ideally the new value is 20

        if(flyMove <= 0f) {
            this.speedY -= 0.1f;
        } else {
            this.flyMove -= speedY;
        }
        invalidate();
    }


    /**
     *
     * @param newScore score changed
     */
    public void changeScore(Integer newScore)
    {
        this.score += newScore;
    }

    public Bullet shoot(Context context,Integer size)
    {
        return new Bullet(context,this.posX+this.radius/3,this.posY,size);
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

    public GifDrawable getJumper() {
        return jumper;
    }

    public void setJumper(GifDrawable jumper) {
        this.jumper = jumper;
    }

    public Rect getImageBounds() {
        return imageBounds;
    }

    public void setImageBounds(Rect imageBounds) {
        this.imageBounds = imageBounds;
    }

    public Integer getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(Integer screenSize) {
        this.screenSize = screenSize;
    }

    public Integer getBoardMove() {
        return boardMove;
    }

    public void setBoardMove(Integer boardMove) {
        this.boardMove = boardMove;
    }

    public Float getFlyMove() {
        return flyMove;
    }

    public void setFlyMove(Float flyMove) {
        this.flyMove = flyMove;
    }
}
