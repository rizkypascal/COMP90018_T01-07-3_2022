package com.example.android.gameapplication.list_content;


public class ListTuple {
    private String element0;
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
