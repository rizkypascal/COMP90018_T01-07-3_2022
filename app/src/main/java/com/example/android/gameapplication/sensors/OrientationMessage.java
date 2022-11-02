package com.example.android.gameapplication.sensors;

public class OrientationMessage {
    /**
     * @author Changwen Li
     * @description get the orientation value
     * @return float[]. [0, 1, 2] is the value of the orientation.
     * */
    public float[] getOrientations() {
        return orientations;
    }

    private final float[] orientations;

    /**
     * @author Changwen Li
     * @description constructor of orientation message
     * @param orientations float[] usually gained from sensor event
     * */
    public OrientationMessage(float[] orientations) {
        this.orientations = orientations;
    }
}
