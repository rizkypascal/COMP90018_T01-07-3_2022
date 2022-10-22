package com.example.android.gameapplication;

/**
 @author Tony Shu
 @date 30/09/2022
 @desc control game activity
 */

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import com.example.android.gameapplication.game_tools.GameTools;
import com.example.android.gameapplication.games.Board;
import com.example.android.gameapplication.games.CollisionUtils;
import com.example.android.gameapplication.games.Jumper;
import com.example.android.gameapplication.games.StaticBoard;
import com.example.android.gameapplication.sensors.OrientationMessage;
import com.example.android.gameapplication.sensors.OrientationSensor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements Runnable {

    private OrientationSensor orientationSensor;
    private Jumper jumper;
    private ArrayList<Board> bars;
    private Boolean isPlaying = true;
    private int screenX, screenY = 0;
    private FragmentTransaction transaction;
    private List<GameTools> gameTools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Init sensor variables*/
        orientationSensor = new OrientationSensor(this);
        EventBus.getDefault().register(this);

        setContentView(R.layout.activity_game);
        // get game activity
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.game_activity);

        //use screen size to set game context
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        screenX = metrics.widthPixels;
        screenY = metrics.heightPixels;

        // initialize jumper
        jumper = new Jumper(this,100,500,100,10f,screenX,R.drawable.jumperone);
        constraintLayout.addView(jumper);

        /**
         * Get all passed values from MainActivity
         */
        Bundle bundle = getIntent().getExtras();
        gameTools = (List<GameTools>) bundle.getSerializable("gameTools");
        GameToolsFragment gameToolsFragment = new GameToolsFragment();
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.game_tool_fragment, gameToolsFragment, "game_activity_game_tools");
        transaction.commit();

        bars = random_generate(screenY, screenX);
        for (Board bar : bars){
            constraintLayout.addView(bar);
        }
    }

    /**
     * show alert when user accidentally press back button
     */
    @Override
    public void onBackPressed() {
        String alertMessage = "";
        if(gameTools == null || gameTools.size() == 0){
            alertMessage = "Are you sure you want to exit?";
        } else {
            alertMessage = "Are you sure you want to exit? Your game tools are already reduced";
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setMessage(alertMessage);
        builder.setTitle("Warning");

        /**
         * set Cancelable false for when the user clicks
         * on the outside the Dialog Box then
         * it will remain show
         */
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            // if user click yes then close this activity
            finish();
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            // if user click no, user is still remain in this activity
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        orientationSensor.disableSensor();
        super.onDestroy();
    }

    @Override
    public void run() {
        while (isPlaying) {
            checkJumper();
        }
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

        // not a good solution, as collision detection should be done within the context class
        for (Board bar : bars){
            if(CollisionUtils.JumperBoardCollision(jumper,bar))
            {
                jumper.setSpeedY(20f);
            }
            bar.move(10f,0f);
        }


    }

    private ArrayList<Board> random_generate(int startY, int width){
        ArrayList<Board> newbars = new ArrayList<>();
        Random random = new Random();
        int y = startY;
        int x = width/5;
        while (y > 0){

            Board bar = new StaticBoard(this,x * random.nextInt(5),y,250,width,R.drawable.basic_board);
            newbars.add(bar);
//            Log.i(String.valueOf(bar.y),String.valueOf(bar.height));
            y -= bar.getBoardHeight()*5*random.nextInt(2);
        }
        return newbars;
    }

    private Boolean checkJumper(){
        Log.i("reminder","reach "+jumper.getPosY()+" screen" + screenY);
        if (jumper.getPosY()*2 < screenY){
            Log.i("i", "1/2");
            update();
        }
        if (jumper.getPosY() > screenY){
            return false;
        }
        return true;
    }

    private void update () {
        this.isPlaying = false;
        // update the bars downward half screen
        for (Board bar : bars){
            bar.setPosY(bar.getPosY()+screenY/2);
        }
        Log.i("i","update bar");

        // remove the bar with y out of screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            bars.removeIf(board -> board.getPosY() > screenY);
        }
        Log.i("i","remove bar");

        // random generate the bars for half screen on top
        bars.addAll(random_generate(screenY/2, screenX));
        Log.i("reminder","finish update");
        this.isPlaying = true;

    }

    public List<GameTools> getGameTools() {
        return gameTools;
    }

    public void setGameTools(List<GameTools> gameTools) {
        this.gameTools = gameTools;
    }
}