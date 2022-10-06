package com.example.android.gameapplication.specialitems;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import org.w3c.dom.Text;

public class BindingUtils {
    @BindingAdapter("itemText")
    public static void setItemText(TextView view, Items i){
        view.setText(i.getName());
    }

    @BindingAdapter("itemImage")
    public static void setItemImage(ImageView view, Items i){
        view.setImageResource(i.getImage());
    }
    @BindingAdapter("itemSelectedImage")
    public static void setItemSelectedImage(ImageView view, Items i){
        view.setImageResource(i.getImage());
    }
}
