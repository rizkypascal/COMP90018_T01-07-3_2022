package com.example.android.gameapplication.Sensors;

public class LightMessage {
    public float[] getLight() {
        return mLight;
    }

    private float[] mLight;

    public LightMessage(float[] mLight) {
        this.mLight = mLight;
    }

}