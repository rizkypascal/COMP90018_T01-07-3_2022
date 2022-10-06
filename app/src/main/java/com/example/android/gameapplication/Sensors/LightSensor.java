package com.example.android.gameapplication.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class LightSensor implements SensorEventListener{
    private Context mContext;
    private SensorManager mSensorManager;
    private Sensor lightSensor;
    private float[] mLight;

    /**
     * @author Changwen Li
     * @description light sensor constructor
     * @param context usually gained from "this" in main activity
     */
    public LightSensor(Context context) {
        mContext = context;
        enableSensor();
    }

    /**
     * @author Changwen Li
     * @description Enable sensor. It is possible that the hardware does NOT support the sensors.
     * */
    public void enableSensor() {
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (mSensorManager == null) Log.v("sensor..", "Sensors not supported!");
        else Log.v("sensor..", "Sensors supported");

        if (lightSensor == null) Log.v("sensor..", "Sensor TYPE_LIGHT not supported!");
        else Log.v("sensor..", "Sensor TYPE_LIGHT supported");

        mSensorManager.registerListener(this, lightSensor,
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
        if (event.sensor.getType() == Sensor.TYPE_LIGHT)
            mLight = event.values;

        if (mLight != null) {
//            Log.v("[onSensorChanged]", ": Light lux" + String.valueOf(mLight[0]));
//            EventBus.getDefault().post(new LightMessage(mLight));
        }
    }
}
