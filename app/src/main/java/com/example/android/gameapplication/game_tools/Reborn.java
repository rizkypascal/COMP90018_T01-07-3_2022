package com.example.android.gameapplication.game_tools;

import com.example.android.gameapplication.R;

/**
 * @Author: Rizky Paskalis Totong
 * @Date: 30/09/22
 */
public class Reborn extends GameTools {
    public Reborn(int image, String name) {
        super(image, name);
    }

    public Reborn(int quantity){
        setImage(R.drawable.reborn);
        setName("Reborn");
        setCodeName(String.valueOf(R.string.reborn));
        setQuantity(quantity);
    }
}
