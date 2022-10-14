package com.example.android.gameapplication.specialitems;

import com.example.android.gameapplication.R;

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
    public ClearMonsters(){
        setImage(R.drawable.bomb);
        setName("Clear Monsters");
    }
    public void clearAllMonsters(Monsters monsters){
        monsters.count = 0;
    }
}