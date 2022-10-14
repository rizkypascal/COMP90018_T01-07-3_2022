package com.example.android.gameapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.android.gameapplication.databinding.ActivityMainBinding;
import com.example.android.gameapplication.specialitems.Items;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.ButterKnife;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import com.example.android.gameapplication.Sensors.LightMessage;
import com.example.android.gameapplication.Sensors.LightSensor;

import java.util.List;


public class MainActivity extends AppCompatActivity implements GameFragment.SendMessages, UserFragment.SendMessages{
    private String user_name = "";
    public BottomNavigationView navView;
    private LightSensor lightSensor;
    private ActivityMainBinding binding;
    private GameToolsSelectionFragment gameToolsSelectionFragment;
    private List<Items> items;
    private OrientationSensor orientationSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** Init sensor variables*/
        lightSensor = new LightSensor(this);
        EventBus.getDefault().register(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setting for Navigation Bar
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().findItem(R.id.navigation_game).setChecked(true); // set the initial selected icon to be the one in middle

        if(savedInstanceState != null){
            gameToolsSelectionFragment = (GameToolsSelectionFragment) getSupportFragmentManager().getFragment(savedInstanceState, "gameToolsSelectionFragment");
        } else {
            gameToolsSelectionFragment = new GameToolsSelectionFragment();
        }
        // Setting for Fragments
        GameFragment gameFragment = new GameFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, gameFragment)
                .addToBackStack(null)
                .commit();
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
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.layout_fragment, gameToolsSelectionFragment)
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
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        orientationSensor.disableSensor();
        // lightSensor.disableSensor();
        super.onDestroy();
    }

    /**
     * @author Changwen Li
     * @description Please get the value of changed sensor signal here. You may change the name of function.
     * @param OrientationEvent see OrientationMessage.java
     * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void orientationUpdate(OrientationMessage OrientationEvent) { // place to get sensor value from orientation
//        orientationValue.setText(String.valueOf(OrientationEvent.getOrientations()[2]));
        Log.d("[Subscription]" , "Orientations: " + String.valueOf(OrientationEvent.getOrientations()[2]));
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
        lightSensor.disableSensor();
    }

    public void setItems(List<Items> i) {
        items = i;
    }

    public List<Items> getItems() {
        return items;
    }
}
