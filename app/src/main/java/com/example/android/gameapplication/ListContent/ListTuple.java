package com.example.android.gameapplication.ListContent;


// UserScore class contains score and date
// With automatically generated Setter and Getter
public class ListTuple {
    // fruitImage to store the resource id if fruit image
    private String element0;
    // fruitName to store the string of fruit name
    private String element1;

    public ListTuple(String score, String date) {
        this.element0 = score;
        this.element1 = date;
    }

    public String getElement0() {
        return element0;
    }

    public void setElement0(String element0) {
        this.element0 = element0;
    }

    public String getElement1() {
        return element1;
    }

    public void setElement1(String element1) {
        this.element1 = element1;
    }
}
