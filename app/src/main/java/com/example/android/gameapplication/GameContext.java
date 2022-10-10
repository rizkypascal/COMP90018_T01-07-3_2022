package com.example.android.gameapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.android.gameapplication.GameClasses.Board;
import com.example.android.gameapplication.GameClasses.CollisionUtils;
import com.example.android.gameapplication.GameClasses.Jumper;
import com.example.android.gameapplication.GameClasses.StaticBoard;
import com.example.android.gameapplication.Sensors.OrientationMessage;
import com.example.android.gameapplication.Sensors.OrientationSensor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Random;

public class GameContext extends View implements Runnable{

    private OrientationSensor orientationSensor;
    private Thread thread;
    private boolean isPlaying, isUpdate = true;
    public static int screenX, screenY;

    public ArrayList<Board> boards;
    public Jumper jumper;

    private GameActivity activity;

    public GameContext(Context contest, int screenX, int screenY) {
        super(contest);

        /** Init sensor variables*/
        this.orientationSensor = new OrientationSensor(getContext());
        EventBus.getDefault().register(this);

        this.screenX = screenX;
        this.screenY = screenY;

        // random generate the boards for full screen
        this.boards = random_generate(screenY, screenX);
        int initBoardX = boards.get(0).getPosX();
        int initBoardY = boards.get(0).getPosY();
        Log.i("generate","initLoc"+initBoardX+"Y"+initBoardY);
        this.jumper = new Jumper(getContext(),100,500,100,10f,
                screenX, R.drawable.jumperone);

        Log.i("generate","Game view");
    }


    @Override
    public void run() {
        while (isPlaying){
            Log.i("i","is playing");
            checkJumper();
        }
    }

    /**
     *
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.i("jumper loc","reach "+jumper.getPosY()+" screen" + screenY);
        jumper.draw(canvas);
        for (Board board : boards){
            board.draw(canvas);
        }
        invalidate();

    }

    private ArrayList<Board> random_generate(int startY, int width){
        ArrayList<Board> newboards = new ArrayList<>();
        Random random = new Random();
        int y = startY;
        int x = width/5;
        while (y > 0){
            Board bar = new StaticBoard(getContext(),
                    x * random.nextInt(5),y,250,width,
                    R.drawable.basic_board);
            newboards.add(bar);
//            Log.i(String.valueOf(bar.y),String.valueOf(bar.height));
            y -= bar.getBoardHeight()*3*(random.nextInt(2)+1);
        }
        return newboards;
    }

    private void checkJumper(){
        if (jumper.getPosY()*2 < screenY){
            Log.i("reminder","reach "+jumper.getPosY()+" screen" + screenY);
            // jumper.setPosY(jumper.getPosY()+screenY/2);
            update();
        }
        if (jumper.getPosY() < screenY){
            jumper.setPosY(0);
            // isPlaying = false
        }
        // isPlaying = true;
    }

    private void update () {
        this.isPlaying = false;
        // update the bars downward half screen

        for (Board board : boards){
            board.setPosY(board.getPosY()+screenY/2);
        }
        Log.i("i","update bar");

        // remove the bar with y out of screen
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            boards.removeIf(board -> board.getPosY() > screenY);
        }

        Log.i("i","remove bar");

        // random generate the bars for half screen on top
        boards.addAll(random_generate(screenY/2, screenX));
        Log.i("reminder","finish update");
        this.isPlaying = true;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("reminder","reach "+event.getY()+" screen" + screenY);
        if (event.getY()*2 < screenY){
            Log.i("i", "1/2");
            update ();
        }
        return false;
    }

    /**
     * @author Changwen Li
     * @description Please get the value of changed sensor signal here. You may change the name of function.
     * @param OrientationEvent see OrientationMessage.java
     * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void orientationUpdate(OrientationMessage OrientationEvent) { // place to get sensor value from orientation
        //Log.d("[Subscription]" , "Orientations: " + String.valueOf(OrientationEvent.getOrientations()[2]));
        Float moveX = 50*OrientationEvent.getOrientations()[2];
        jumper.move(moveX,10f);

        // not a good solution, as collision detection should be done within the context class
        for (Board bar : boards){
            if(CollisionUtils.JumperBoardCollision(jumper,bar))
            {
                jumper.setSpeedY(20f);
            }
            bar.move(10f,0f);
        }

    }

    public void resume () {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause () {

        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
