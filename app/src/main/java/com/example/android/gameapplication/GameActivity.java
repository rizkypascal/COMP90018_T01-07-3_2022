package com.example.android.gameapplication;

/**
 @author Tony Shu
 @date 30/09/2022
 @desc control game activity
 */

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import com.example.android.gameapplication.databases.Database;
import com.example.android.gameapplication.game_tools.GameTools;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private GameContext gameContext;
    private Database database;
    private int screenX, screenY = 0;
    private FragmentTransaction transaction;
    private List<GameTools> gameTools;
    public ConstraintLayout constraintLayout;
    public String subject, week, user_name;
    public ArrayList<String> monsterInfo;

    public String getSubject() {
        return subject;
    }

    public String getWeek() {
        return week;
    }

    public String getUser_name() {
        return user_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_game);
        // get game activity
        constraintLayout = (ConstraintLayout) findViewById(R.id.game_activity);

        //use screen size to set game context
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        screenX = metrics.widthPixels;
        screenY = metrics.heightPixels;
        this.database = new Database();

        /**
         * Get all passed values from MainActivity
         */
        Bundle bundle = getIntent().getExtras();
        gameTools = (List<GameTools>) bundle.getSerializable("gameTools");
        subject = bundle.getString("subject");
        week = bundle.getString("week");
        user_name = bundle.getString("user_name");
        monsterInfo = bundle.getStringArrayList("monsters");

        gameContext = new GameContext (this, screenX, screenY, database);
        constraintLayout.addView(gameContext);

        GameToolsFragment gameToolsFragment = new GameToolsFragment(gameContext);
        FragmentManager manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.game_tool_fragment, gameToolsFragment, "game_activity_game_tools");
        transaction.commit();

        //sticky immersive mode for game activity
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.hide();
    }

    /**
     * show alert when user accidentally press back button
     */
    @Override
    public void onBackPressed() {
        String alertMessage = "";
        if(gameTools == null || gameTools.size() == 0){
            alertMessage = getString(R.string.confirm_exit);
        } else {
            alertMessage = getString(R.string.confirm_exit_tool);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setMessage(alertMessage);
        builder.setTitle(getString(R.string.warning));

        /**
         * set Cancelable false for when the user clicks
         * on the outside the Dialog Box then
         * it will remain show
         */
        builder.setCancelable(false);

        builder.setPositiveButton(getString(R.string.yes), (DialogInterface.OnClickListener) (dialog, which) -> {
            // if user click yes then close this activity
            finish();
        });

        builder.setNegativeButton(getString(R.string.no), (DialogInterface.OnClickListener) (dialog, which) -> {
            // if user click no, user is still remain in this activity
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public List<GameTools> getGameTools() {
        return gameTools;
    }

    public void setGameTools(List<GameTools> gameTools) {
        this.gameTools = gameTools;
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

    @Override
    protected void onStop() {
        super.onStop();
    }

}