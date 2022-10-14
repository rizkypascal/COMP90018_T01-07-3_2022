package com.example.android.gameapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.ButterKnife;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import com.example.android.gameapplication.Sensors.LightMessage;
import com.example.android.gameapplication.Sensors.LightSensor;


public class MainActivity extends AppCompatActivity implements GameFragment.SendMessages, UserFragment.SendMessages{
    private String user_name = "";
    public BottomNavigationView navView;
    private LightSensor lightSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Init sensor variables*/
        lightSensor = new LightSensor(this);
        EventBus.getDefault().register(this);

        setContentView(R.layout.activity_main);

        // Setting for Navigation Bar
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().findItem(R.id.navigation_game).setChecked(true); // set the initial selected icon to be the one in middle


        // Setting for Fragments
        GameFragment gameFragment = new GameFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, gameFragment)
                .addToBackStack(null)
                .commit();
        ButterKnife.bind(this);
        gameFragment.fragmentReceiveMsg(user_name);
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
                    GameToolsSelectionFragment gameToolsSelectionFragment = new GameToolsSelectionFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.layout_fragment, gameToolsSelectionFragment)
                            .addToBackStack(null)
                            .commit();
                    gameToolsSelectionFragment.fragmentReceiveMsg(user_name);
                    return true;
                }

            }
            return false;
        }
    };

    // receive data form fragments
    @Override
    public void iAmMSG(String msg) {
        user_name = msg;
        Log.d("MainActivity", "Receive data: "+msg);
    }

    //todo: Arthur dark mode...
    /**
     * @author Changwen Li
     * @desc Please get the value of changed sensor signal here. You may change the name of function.
     * @param LightEvent see LightMessage.java
     * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void lightUpdate(LightMessage LightEvent) { // place to get sensor value from light
        Log.d("[Subscription]", "Light: " + String.valueOf(LightEvent.getLight()[0]));
        EventBus.getDefault().unregister(this);
        lightSensor.disableSensor();
    }
}
