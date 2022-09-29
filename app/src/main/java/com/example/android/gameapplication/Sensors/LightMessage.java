package com.example.android.gameapplication.Sensors;

public class LightMessage {

    /**
     * @author Changwen Li
     * @description get the light value
     * @return float[]. [0] is the value of the light.
     * */
    public float[] getLight() {
        return mLight;
    }

    private float[] mLight;

    /**
     * @author Changwen Li
     * @description constructor of light message
     * @param mLight float[] usually gained from sensor event
     * */
    public LightMessage(float[] mLight) {
        this.mLight = mLight;
    }

}