package com.example.android.gameapplication.GameClasses;

/**
 * @author Tony Shu
 * @date 30/09/2022
 * @desc main character that the player controls
 */
public class Jumper
{
    private Float posX;
    private Float posY;
    private Float speedX;
    private Float speedY;
    private Float accX; // acceleration on X axis
    private Float accY; // acceleration on Y axis
    private Enum status;
    private Integer score; // can be stored in game activity instead
    private Boolean alive;
    private Float radius; // size of jumper

    public Jumper(Float posX, Float posY, Float speedX, Float speedY, Float accX, Float accY, Enum status, Integer score, Boolean alive, Float radius) {
        this.posX = posX;
        this.posY = posY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.accX = accX;
        this.accY = accY;
        this.status = status;
        this.score = score;
        this.alive = alive;
        this.radius = radius;
    }

    /**
     * @param velocityX
     * @param velocityY
     * @return
     */
    public void move(Float velocityX, Float velocityY)
    {

    }

    /**
     *
     * @param posX draw jumper at X
     * @param posY draw jumper at Y
     */
    public void draw(Integer posX, Integer posY)
    {

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


    public Float getPosX() {
        return posX;
    }

    public void setPosX(Float posX) {
        this.posX = posX;
    }

    public Float getPosY() {
        return posY;
    }

    public void setPosY(Float posY) {
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

    public Float getRadius() {
        return radius;
    }

    public void setRadius(Float radius) {
        this.radius = radius;
    }
}
