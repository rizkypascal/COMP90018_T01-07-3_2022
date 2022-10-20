package com.example.android.gameapplication.game_tools;

/**
 * @Author: Rizky Paskalis Totong
 * @Date: 30/09/22
 * @Desc: Parent class to all Game Tools objects
 */
public class GameTools {
    private int image;
    private int quantity;
    private int position;
    private String name;
    private String codeName;

    public GameTools(int image, String name){
        this.image = image;
        this.name = name;
    }

    public GameTools() {
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
}
