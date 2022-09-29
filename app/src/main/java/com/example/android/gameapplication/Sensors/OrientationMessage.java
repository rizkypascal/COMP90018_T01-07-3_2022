package com.example.android.gameapplication.Sensors;

public class OrientationMessage {
    public float[] getOrientations() {
        return orientations;
    }

    private float[] orientations;

    public OrientationMessage(float[] orientations) {
        this.orientations = orientations;
    }

}
