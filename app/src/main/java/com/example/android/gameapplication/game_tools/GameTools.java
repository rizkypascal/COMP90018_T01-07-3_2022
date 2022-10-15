package com.example.android.gameapplication.game_tools;

/**
 * @Author: Rizky Paskalis Totong
 * @Date: 30/09/22
 */
public class GameTools {
    private int image;
    private String name;

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
}
