package com.example.android.gameapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.android.gameapplication.games.Board;
import com.example.android.gameapplication.games.CollisionUtils;
import com.example.android.gameapplication.games.Jumper;
import com.example.android.gameapplication.games.StaticBoard;
import com.example.android.gameapplication.games.Status;
import com.example.android.gameapplication.sensors.OrientationMessage;
import com.example.android.gameapplication.sensors.OrientationSensor;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Random;

public class GameContext extends View implements Runnable{

    private OrientationSensor orientationSensor;
    private Thread thread;
    private boolean isPlaying = true;
    private boolean isUpdate = false;
    private int initialBoards = 200;
    private int widthRatio = 5;

    public static int screenX, screenY;

    public ArrayList<Board> boards;
    public Jumper jumper;

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
            update();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("reminder","reach "+event.getY()+" screen" + screenY);
        if (event.getY()*2 < screenY){
            Log.i("i", "1/2");
        }
        return true;
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
            if (jumper.getStatus() == Status.movingUp & jumper.getPosY() < screenY/2){
                Log.i("i","speed: "+ jumper.getSpeedY());
                board.move(0f,jumper.getSpeedY());
            }
            if (board.getPosY() > 0 & board.getPosY() < screenY){
                board.draw(canvas);
            }
        }
        invalidate();

    }

    private ArrayList<Board> random_generate(int startY, int screenX){
        ArrayList<Board> newboards = new ArrayList<>();
        Random random = new Random();
        int y = startY;
        int width = screenX/widthRatio;
        int lastX = -1;
        int i = 0;
        while (i < initialBoards){
            int posX = width * random.nextInt(widthRatio);
            while (posX == lastX){
                posX = width * random.nextInt(widthRatio);
            }
            Board bar = new StaticBoard(getContext(), posX, y, width,
                    screenX, R.drawable.basic_board);
            newboards.add(bar);
//            Log.i(String.valueOf(bar.y),String.valueOf(bar.height));
            y -= bar.getBoardHeight()*3*(random.nextInt(2)+1);
            i += 1;
            lastX = bar.getPosX();
        }

        return newboards;
    }

    private void checkJumper(){
        if (jumper.getPosY()*2 < screenY){
//            Log.i("reminder","reach "+jumper.getPosY()+" screen" + screenY);
//            // jumper.setPosY(jumper.getPosY()+screenY/2);
//            update();
        }
        if (jumper.getPosY() < screenY){
            //jumper.setPosY(0);
            // isPlaying = false
        }
        // isPlaying = true;
    }

    private void update () {
//        float speed = jumper.getSpeedY();
//        if (speed < 10.0 & speed > 9.0){
//            //isUpdate = false;
//            Log.i("i", "=0");
//            int rangeY = beginY - jumper.getPosY();
//            for (Board board : boards){
//                int change = 0;
//                int before = board.getPosY();
//                while (change < rangeY){
//                    Log.i("i", "change:"+change);
//                    board.move(0f, 20f);
//                    int after = board.getPosY() - before;
//                    Log.i("i", "before:"+before);
//                    Log.i("i", "after:"+board.getPosY());
//                    change += after;
//                }
//            }
//        }
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
            if(CollisionUtils.JumperBoardCollision(jumper,bar) && jumper.getStatus().equals(Status.movingDown))
            {
                jumper.setSpeedY(20f);
                jumper.setStatus(Status.movingUp);
                break;
            }
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
