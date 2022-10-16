package com.example.android.gameapplication.game_tools;

import com.example.android.gameapplication.R;

/**
 * temporary class
 */
class Monsters{
    int count = 0;
}

/**
 * @Author: Rizky Paskalis Totong
 * @Date: 30/09/22
 * assume there is a monster count in the game
 * the function is simply to set count to 0
 */
public class ClearMonsters extends GameTools {
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