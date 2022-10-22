package com.example.android.gameapplication.game_tools;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

/**
 * @Author: Rizky Paskalis Totong
 * @Date: 30/09/22
 * @Desc: Enable the layout to automatically adjusted when bound
 */
public class BindingUtils {
    @BindingAdapter("itemText")
    public static void setItemText(TextView view, GameTools i){
        view.setText(i.getName());
    }

    @BindingAdapter("itemQuantity")
    public static void setItemQuantity(TextView view, GameTools i){
        view.setText(""+i.getQuantity());
    }

    @BindingAdapter("itemImage")
    public static void setItemImage(ImageView view, GameTools i){
        view.setImageResource(i.getImage());
    }
    @BindingAdapter("itemSelectedImage")
    public static void setItemSelectedImage(ImageView view, GameTools i){
        view.setImageResource(i.getImage());
    }
}
