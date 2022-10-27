package com.example.android.gameapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.media.Image;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.android.gameapplication.database.Database;
import com.example.android.gameapplication.games.Board;
import com.example.android.gameapplication.games.BombEffect;
import com.example.android.gameapplication.games.Bullet;
import com.example.android.gameapplication.games.CollisionUtils;
import com.example.android.gameapplication.games.FireworkEffect;
import com.example.android.gameapplication.games.Jumper;
import com.example.android.gameapplication.games.MonsterType;
import com.example.android.gameapplication.games.StaticBoard;
import com.example.android.gameapplication.games.Status;
import com.example.android.gameapplication.games.Monster;
import com.example.android.gameapplication.sensors.OrientationMessage;
import com.example.android.gameapplication.sensors.OrientationSensor;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import pl.droidsonroids.gif.GifDrawable;

public class GameContext extends View implements Runnable{

    private OrientationSensor orientationSensor;
    private GameActivity activity;
    private Thread thread;
    private boolean isPlaying = true;
    private boolean isExit, isComplete = false;
    private int initialBoards = 100;
    private int widthRatio = 5;
    private final float changeY = 22f;
    private final float gravityY = 10f;
    private final int lowerthreshold;

    private int screenX, screenY;

    private ArrayList<Board> boards;
    private Jumper jumper;
    private ArrayList<Monster> monsters = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private BombEffect bomb;
    private FireworkEffect firework;
    private Database database;

    public GameContext(GameActivity activity, int screenX, int screenY) {
        super(activity);

        /** Init sensor variables*/
        this.orientationSensor = new OrientationSensor(getContext());
        EventBus.getDefault().register(this);

        this.activity = activity;
        this.screenX = screenX; //1440
        this.screenY = screenY;   //3040
        this.lowerthreshold = screenY * 9 / 10;

        int jumperX = screenX/14;
        int jumperY = screenY/7;
        int size = screenX/10;

        // database = new Database();

        // random generate the boards for full screen
        this.boards = random_generate(screenY, screenX);

        //some monsters for testing purpose
        Monster monster = new Monster(getContext(), 500, 500,size, 10,MonsterType.EXAM);
        this.monsters.add(monster);
        Monster monster2 = new Monster(getContext(), 800, 700,size, 10,MonsterType.QUIZ);
        this.monsters.add(monster2);
//        int initBoardX = boards.get(0).getPosX();
//        int initBoardY = boards.get(0).getPosY();
//        Log.i("generate","initLoc"+initBoardX+"Y"+initBoardY);
        this.jumper = new Jumper(getContext(),jumperX,jumperY, jumperX,gravityY,
                screenX, R.drawable.jumperone);

        Log.i("generate","Game view");

    }

    @Override
    public void run() {
        while (isPlaying){
            checkStatus();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //jumper shoots a bullet when user touches the screen
        switch (event.getAction())
        {
            case MotionEvent.ACTION_UP:
                bullets.add(jumper.shoot(getContext(),70));
                break;
            case MotionEvent.ACTION_DOWN:
                bullets.add(jumper.shoot(getContext(),70));
                break;
            case MotionEvent.ACTION_MOVE:
                bullets.add(jumper.shoot(getContext(),70));
                break;
        }

        return false;
    }

    /**
     *
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //all boards move down if the jumper's status is stayStill
        for (Board board : boards){
            // boards move down if jumper above 1/2 screen
            if (jumper.getStatus() == Status.stayStill){
                board.move(0f, (float) jumper.getBoardMove());
            } else if (jumper.getStatus() == Status.onCopter){
                board.move(0f,50f);
            } else if (jumper.getStatus() == Status.onRocket){
                board.move(0f,80f);
            }
            // boards move ip if jumper below 9/10 screen
            if (jumper.getPosY() >= lowerthreshold){
                board.move(0f, (float) jumper.getBoardMove());
            }

            if (board.getPosY() > 0 & board.getPosY() < screenY){
                board.draw(canvas);
            }
        }

        jumper.draw(canvas);

        //drawing bomb until the bomb gif is finished
        if(bomb != null){
            bomb.draw(canvas);
        }


        //draw all monsters
        for (Monster monster : monsters){
            if (monster.getAlive()){
                monster.move(1f, 1f, screenY, screenX);
                monster.draw(canvas);
            }
        }

        //draw all bullets and bullets movement
        if(bullets.size()>0)
        {
            for (Bullet bullet: bullets) {
                if(bullet.getPosY()>0)
                {
                    bullet.draw(canvas);
                }
                bullet.move(gravityY);
            }
        }

        //detect collision between monsters and bullets
        for (Bullet bullet: bullets) {
            for (Monster monster: monsters) {
                if (monster.getAlive()){
                    if(CollisionUtils.bulletMonsterCollision(bullet,monster))
                    {
                        monster.setAlive(false);
                        firework(monster.getPosX(),monster.getPosY());
                        break;
                    }
                }
            }
        }

        //drawing firework until the bomb gif is finished
        if(firework != null){
            firework.draw(canvas);
        }

        if (isExit){
            exit();
        } else{
            invalidate();
        }
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

            // get current dark mode setting
            // Determine bard pictures via dark mode setting
            Board bar = null;
            int nightModeFlags =
                    this.getResources().getConfiguration().uiMode &
                            Configuration.UI_MODE_NIGHT_MASK;
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_NO) {
                bar = new StaticBoard(getContext(), posX, y, width,
                        screenX, R.drawable.book_board);
            }
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                bar = new StaticBoard(getContext(), posX, y, width,
                        screenX, R.drawable.book_board_dark);
            }


            newboards.add(bar);
//            Log.i(String.valueOf(bar.y),String.valueOf(bar.height));
            y -= bar.getBoardHeight()*3*(random.nextInt(2)+1);
            i += 1;
            lastX = bar.getPosX();
        }

        return newboards;
    }

    private void generate_monsters(){
        ArrayList<String> lists = new ArrayList<>();
        lists = database.getMonsters("subject1", "week1");
        Log.i("monster","list:"+lists.size());
    }

    private void checkStatus(){
        if (boards.get(0).getPosY() <= 0){
            if (jumper.getStatus() == Status.movingDown){
                isExit = true;
            }
        }
        int alive = 0;
        for (Monster monster : monsters){
            if (monster.getAlive()){
                alive += 1;
            }
        }

        if (boards.get(boards.size()-1).getPosY() >= jumper.getPosY()){
            if (alive == 0){
                isComplete = true;
            }
            isExit = true;
        }
    }

    private void update () {

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
        jumper.move(moveX,gravityY,screenY/2, lowerthreshold);

        // not a good solution, as collision detection should be done within the context class
        for (Board bar : boards){
            if(CollisionUtils.jumperBoardCollision(jumper,bar) && jumper.getStatus().equals(Status.movingDown))
            {
                jumper.setSpeedY(changeY);
                jumper.setStatus(Status.movingUp);
                break;
            }
            //set jumper to have constant speed without changing status
            else if(jumper.getStatus().equals(Status.onCopter) || jumper.getStatus().equals(Status.onRocket))
            {
                jumper.setSpeedY(gravityY);
                break;
            }
        }
    }

    public void exit (){

        firework(screenX/2, screenY/5);
        activity.constraintLayout.addView(firework);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String msg = getResources().getString(R.string.finish_game);
        String title = getResources().getString(R.string.done);
        if (isComplete){
            msg += getResources().getString(R.string.complete_game);
        }else{
            msg += getResources().getString(R.string.incomplete_game);
            title = getResources().getString(R.string.good);
        }
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.got_it),
                (DialogInterface.OnClickListener) (dialog, which) -> {
            activity.finish();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void resume () {
        isPlaying = true;
        orientationSensor.enableSensor();
        thread = new Thread(this);
        thread.start();
    }

    public void pause () {
        try {
            isPlaying = false;
            orientationSensor.disableSensor();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * set jumper status outside game context
     * @param stats
     */
    public void setJumperStatus(Status stats){
        jumper.setStatus(stats);
        // if using fly items, then set maximum fly move as the fly height threshold
        if(stats.equals(Status.onRocket) || stats.equals(Status.onCopter)){
            if(stats.equals(Status.onRocket)){
                jumper.setJumper(GifDrawable.createFromResource(getResources(), R.drawable.jumperone_rocket));
            }else if(stats.equals(Status.onCopter)){
                jumper.setJumper(GifDrawable.createFromResource(getResources(), R.drawable.jumperone_copter));
            }
            jumper.setRadius(130);
            jumper.setFlyMove((float) screenY);
        }
    }

    /**
     * draw the bomb effect when clear monster
     */
    public void clearMonsters(){
        InputStream is = getResources().openRawResource(R.raw.bomb);
        Movie movie = Movie.decodeStream(is);
        int duration = movie.duration();

        CountDownTimer cd = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                bomb = null;
                for (Monster monster : monsters){
                    monster.setAlive(false);
                }
            }
        };
        cd.start();

        bomb = new BombEffect(getContext(), 550, 1000,400, R.drawable.bomb);
    }

    /**
     * draw the firework effect when game complete and finish
     */
    public void firework(int posX, int posY){

        InputStream is = getResources().openRawResource(R.raw.fireworks);
        Movie movie = Movie.decodeStream(is);
        int duration = movie.duration();
        CountDownTimer cd = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                Log.i("i","finish");
                firework = null;
            }
        };
        cd.start();
        firework = new FireworkEffect(getContext(),posX,posY,
                screenX/2, R.drawable.fireworks);
    }

    private void PopToast(String text, int duration){
        Toast toast = Toast.makeText(getContext(), null, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
        toast.setText(text);
        toast.show();
    }




}