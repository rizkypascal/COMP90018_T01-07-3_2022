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
    public ClearMonsters(String clearMonster,int quantity){
        setImage(R.drawable.bomb);
        setName(clearMonster);
        setCodeName(String.valueOf(R.string.clear_monsters));
        setQuantity(quantity);
    }
    public void clearAllMonsters(Monsters monsters){
        monsters.count = 0;
    }
}