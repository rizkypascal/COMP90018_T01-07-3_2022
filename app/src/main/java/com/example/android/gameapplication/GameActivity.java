package com.example.android.gameapplication;

/**
 @author Tony Shu
 @date 30/09/2022
 @desc control game activity
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.example.android.gameapplication.GameClasses.Board;
import com.example.android.gameapplication.GameClasses.CollisionUtils;
import com.example.android.gameapplication.GameClasses.Jumper;
import com.example.android.gameapplication.GameClasses.OneTimeBoard;
import com.example.android.gameapplication.GameClasses.SpringBoard;
import com.example.android.gameapplication.GameClasses.StaticBoard;
import com.example.android.gameapplication.Sensors.OrientationMessage;
import com.example.android.gameapplication.Sensors.OrientationSensor;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private GameContext gameContext;
    private OrientationSensor orientationSensor;

    private int screenX, screenY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        // get game activity
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.game_activity);

        //use screen size to set game context
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        screenX = metrics.widthPixels;
        screenY = metrics.heightPixels;

        gameContext = new GameContext (this, screenX, screenY);
        constraintLayout.addView(gameContext);


    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        orientationSensor.disableSensor();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameContext.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameContext.resume();
    }


}