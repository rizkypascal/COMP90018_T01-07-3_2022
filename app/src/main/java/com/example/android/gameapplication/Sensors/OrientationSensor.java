package com.example.android.gameapplication.Sensors;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;


public class OrientationSensor implements SensorEventListener {

    private Context mContext;
    private SensorManager mSensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private float[] mGravity;
    private float[] mGeomagnetic;

    /**
     * @author Changwen Li
     * @description orientation sensor constructor
     * @param context usually gained from "this" in main activity
     */
    public OrientationSensor(Context context) {
        mContext = context;
        enableSensor();
    }

    /**
     * @author Changwen Li
     * @description Enable sensor. It is possible that the hardware does NOT support the sensors.
     * Two sensors are used (TYPE_ACCELEROMETER & TYPE_MAGNETIC_FIELD)
     * */
    public void enableSensor() {
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        if (mSensorManager == null) Log.v("sensor..", "Sensors not supported!");
        else Log.v("sensor..", "Sensors supported");

        if (accelerometer == null) Log.v("sensor..", "Sensor TYPE_ACCELEROMETER not supported!");
        else Log.v("sensor..", "Sensor TYPE_ACCELEROMETER supported");

        if (magnetometer == null) Log.v("sensor..", "Sensor TYPE_MAGNETIC_FIELD not supported!");
        else Log.v("sensor..", "Sensor TYPE_MAGNETIC_FIELD supported");

        mSensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, magnetometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * @author Changwen Li
     * @description disable sensors. Remember to do it when exiting/destroying current app.
     * */
    public void disableSensor() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
            mSensorManager = null;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }

    /**
     * @author Changwen Li
     * @description detect sensor signal change, post the changed value in event bus.
     * */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == null) {
            return;
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;

        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
//                Log.v("[onSensorChanged]", "Orientation values" + String.valueOf(orientation[0])+" "+
//                        String.valueOf(orientation[1])+" "+String.valueOf(orientation[2]));
                EventBus.getDefault().post(new OrientationMessage(orientation));
            }
        }
    }
}
