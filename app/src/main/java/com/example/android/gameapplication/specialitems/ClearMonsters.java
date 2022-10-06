package com.example.android.gameapplication.specialitems;

/**
 * temporary class
 */
class Monsters{
    int count = 0;
}

/**
 * assume there is a monster count in the game
 * the function is simply to set count to 0
 */
public class ClearMonsters extends Items {
    public ClearMonsters(int image, String name) {
        super(image, name);
    }

    public void clearAllMonsters(Monsters monsters){
        monsters.count = 0;
    }
}