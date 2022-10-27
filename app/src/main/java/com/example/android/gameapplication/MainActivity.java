package com.example.android.gameapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import com.example.android.gameapplication.database.Database;

import com.example.android.gameapplication.broadcaster.GameToolsBroadcastReceiver;

import com.example.android.gameapplication.game_tools.GameTools;
import com.example.android.gameapplication.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import com.example.android.gameapplication.sensors.LightMessage;
import com.example.android.gameapplication.sensors.LightSensor;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity implements GameFragment.SendMessages, UserFragment.SendMessages{
    private String user_name = "";
    private String subject = "";
    public BottomNavigationView navView;
    private LightSensor lightSensor;
    private ActivityMainBinding binding;
    private GameToolsSelectionFragment gameToolsSelectionFragment;
    private List<GameTools> selectedGameTools;
    private List<GameTools> gameTools;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private BroadcastReceiver broadcastReceiver;
    private Calendar scheduledCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Init sensor variables*/
        lightSensor = new LightSensor(this);
        EventBus.getDefault().register(this);

        setContentView(R.layout.activity_main);

        Log.d("[Subscription]", "main activity create");


        // Setting for Navigation Bar
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().findItem(R.id.navigation_game).setChecked(true); // set the initial selected icon to be the one in middle

        // Initialize the Game Tools Fragment to help locally store state
        if(savedInstanceState != null){
            gameToolsSelectionFragment = (GameToolsSelectionFragment) getSupportFragmentManager().getFragment(savedInstanceState, "gameToolsSelectionFragment");
        }
        // Setting for Fragments
        GameFragment gameFragment = new GameFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, gameFragment)
                .addToBackStack(null)
                .commit();

        gameFragment.fragmentReceiveMsg(user_name);

        //set background job for refresh game tools
        registerBackgroundJobForRefreshGameTools();
    }

    /**
     * register alarm to refresh game tools
     */
    private void registerBackgroundJobForRefreshGameTools() {
        //reset game tools at the next day 00:00:00 AM AEST
        Calendar scheduledCal= Calendar.getInstance(TimeZone.getTimeZone("Australia/Melbourne"));
        scheduledCal.add(Calendar.DAY_OF_MONTH, 1);
        scheduledCal.set(Calendar.HOUR, 0);
        scheduledCal.set(Calendar.MINUTE, 0);
        scheduledCal.set(Calendar.SECOND, 0);
        long intendedTime = scheduledCal.getTimeInMillis();

        Log.i("AlarmGameTools", "Going to register Intent.RegisterAlarmBroadcast");

        Intent intent = new Intent(this, GameToolsBroadcastReceiver.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT );
        }

        alarmManager = (AlarmManager) getSystemService( Context.ALARM_SERVICE );

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                intendedTime,
                AlarmManager.INTERVAL_DAY,
                pendingIntent );
    }

    // Click listener for choosing different navigation tabs
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_user: {
                    Log.d("Navigation", "user clicked.");
                    if (user_name==""){
                        UserFragment userFragment = new UserFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.layout_fragment, userFragment)
                                .addToBackStack(null)
                                .commit();
                        userFragment.fragmentReceiveMsg(user_name);
                    }
                    else{
                        UserFragmentAfterLogin userFragment = new UserFragmentAfterLogin();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.layout_fragment, userFragment)
                                .addToBackStack(null)
                                .commit();
                        userFragment.fragmentReceiveMsg(user_name);
                    }

                    return true;
                }
                case R.id.navigation_game: {
                    Log.d("Navigation", "game clicked.");
                    GameFragment gameFragment = new GameFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.layout_fragment, gameFragment)
                            .addToBackStack(null)
                            .commit();
                    gameFragment.fragmentReceiveMsg(user_name);
                    return true;
                }
                case R.id.navigation_game_tools: {
                    Log.d("Navigation", "game tools clicked.");
                    if(gameToolsSelectionFragment == null){
                        gameToolsSelectionFragment = new GameToolsSelectionFragment();
                    }
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.layout_fragment, gameToolsSelectionFragment, "gameToolsSelection")
                            .addToBackStack(null)
                            .commit();
                    return true;
                }

            }
            return false;
        }
    };

    // receive data form fragments
    @Override
    public void iAmMSG(String msg) {

        if (msg.startsWith("Subject:")){
            Log.d("UserFragment", ": "+msg);
        }
        else {
            Log.d("UserFragment", "receive msg: "+msg);
            user_name = msg;
        }
    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        lightSensor.disableSensor();
        super.onDestroy();
    }


    /**
     * @author Changwen Li
     * @desc Please get the value of changed sensor signal here. You may change the name of function.
     * @param LightEvent see LightMessage.java
     * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void lightUpdate(LightMessage LightEvent) { // place to get sensor value from light
        float lightValue = LightEvent.getLight()[0];
        EventBus.getDefault().unregister(this);
        lightSensor.disableSensor();
        boolean askIfChangeToDark = false;
        boolean askIfChangeToBright = false;
        String questionInfo = "";
        Log.d("[Subscription]", "Light: " + lightValue);
        // Log.d("[Subscription]", String.valueOf(AppCompatDelegate.getDefaultNightMode()));
        int nightModeFlags =
                this.getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        if ((nightModeFlags == Configuration.UI_MODE_NIGHT_NO) && lightValue<50) {
            askIfChangeToDark = true;
            questionInfo = "It seems that you are in a dark place. Do you want to play the game in dark mode?";
        }
        if ((nightModeFlags == Configuration.UI_MODE_NIGHT_YES) && lightValue>=50) {
            askIfChangeToBright = true;
            questionInfo = "It seems that you are in a bright place. Do you want to play the game in bright mode?";
        }

        // The dialog box only appear if the user is in dark place and his phone is in bright mode;
        // or, the user is in bright place and his phone is in dark mode.
        if (askIfChangeToBright || askIfChangeToDark){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // setting for dialog listener
            boolean finalAskIfChangeToBright = askIfChangeToBright;
            boolean finalAskIfChangeToDark = askIfChangeToDark;
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            if (finalAskIfChangeToBright) {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            }
                            else if (finalAskIfChangeToDark) {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            }
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            builder.setMessage(questionInfo).setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    /**
     * This set method is utilized by Game Tools Fragments
     * @param gameTools
     */
    public void setSelectedGameTools(List<GameTools> gameTools) {
        this.selectedGameTools = gameTools;
    }

    /**
     * The return object is used on Game Tools Fragments
     * @return gameTools
     */
    public List<GameTools> getSelectedGameToolsGameTools() {
        return selectedGameTools;
    }

    /**
     * Prepare to save the fragment state
     * in case the application is not on screen
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        GameToolsSelectionFragment fragment = (GameToolsSelectionFragment) getSupportFragmentManager().findFragmentByTag("gameToolsSelection");
        if(fragment != null) {
            getSupportFragmentManager().putFragment(outState, "gameToolsSelectionFragment", gameToolsSelectionFragment);
        }
    }

    /**
     * This set method is utilized by Game Tools Selection Fragments
     * @return gameTools
     */
    public List<GameTools> getGameTools() {
        return gameTools;
    }

    /**
     * This set method is utilized by Game Tools Selection Fragments
     * @param gameTools
     */
    public void setGameTools(List<GameTools> gameTools) {
        this.gameTools = gameTools;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public String getUserName() {
        return this.user_name;
    }
}
