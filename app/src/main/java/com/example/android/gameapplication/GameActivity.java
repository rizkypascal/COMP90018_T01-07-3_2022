package com.example.android.gameapplication;

/**
 @author Tony Shu
 @date 30/09/2022
 @desc control game activity
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

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

public class GameActivity extends AppCompatActivity {

    private OrientationSensor orientationSensor;
    private Jumper jumper;
    private Board board1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Init sensor variables*/
        orientationSensor = new OrientationSensor(this);
        EventBus.getDefault().register(this);

        setContentView(R.layout.activity_game);
        // get game activity
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.game_activity);

        //get screen size
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        Integer width = metrics.widthPixels;

        // initialize jumper
        jumper = new Jumper(this,100,500,100,10f,width,R.drawable.jumperone);
        constraintLayout.addView(jumper);

        board1 = new StaticBoard(this,500,1500,250,width,R.drawable.basic_board);
        constraintLayout.addView(board1);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        orientationSensor.disableSensor();
        super.onDestroy();
    }


    /**
     * @author Changwen Li
     * @description Please get the value of changed sensor signal here. You may change the name of function.
     * @param OrientationEvent see OrientationMessage.java
     * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void orientationUpdate(OrientationMessage OrientationEvent) { // place to get sensor value from orientation
        Log.d("[Subscription]" , "Orientations: " + String.valueOf(OrientationEvent.getOrientations()[2]));
        Float moveX = 50*OrientationEvent.getOrientations()[2];
        jumper.move(moveX,10f);

        //not a good solution, as collision detection should be done within the context class
        if(CollisionUtils.JumperBoardCollision(jumper,board1))
        {
            jumper.setSpeedY(20f);
        }

        board1.move(10f,0f);

    }


}