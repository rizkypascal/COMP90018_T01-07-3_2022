package com.example.android.gameapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.gameapplication.game_tools.GameTools;
import com.example.android.gameapplication.game_tools.GameToolsAdapter;
import com.example.android.gameapplication.game_tools.ClearMonsters;
import com.example.android.gameapplication.game_tools.FlyItems;
import com.example.android.gameapplication.game_tools.GameToolsName;
import com.example.android.gameapplication.databinding.FragmentGameToolsSelectionBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Rizky Paskalis Totong
 * @Date: 02/10/22
 */
public class GameToolsSelectionFragment extends Fragment {

    private FragmentGameToolsSelectionBinding binding;
    private List<GameTools> gameTools;
    private GameToolsFragment fragment;
    private GameToolsAdapter adapter;
    private Bundle savedState = null;
    private MainActivity activity;
    private SharedPreferences sharedPref;

    /**
     * Inflate the view with View Binding
     * Initiate and show new GameToolsFragment
     * Get the current Activity to get the current selected tools
     * Set an adapter to generate list of available game tools
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return binding.getRoot()
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGameToolsSelectionBinding.inflate(getLayoutInflater());
        fragment = (GameToolsFragment) getChildFragmentManager().findFragmentById(R.id.fragment_container_view);
        activity = (MainActivity) getActivity();

        // get saved instance when the activity is destroyed
        if(savedInstanceState != null && savedState == null) {
            savedState = savedInstanceState.getBundle("fulltext");
        }
        if(savedState != null) {
            binding.textItemsFull.setText(savedState.getCharSequence("fulltext"));
        }
        savedState = null;

        // initialize game tools
        if(activity.getGameTools() == null) {
            gameTools = getGameTools();
        } else {
            gameTools = activity.getGameTools();
        }

        adapter = new GameToolsAdapter(gameTools, fragment);
        binding.setGameToolsAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.itemSelectionRv.setLayoutManager(layoutManager);

        setRefreshGameToolsCountdown();

        return binding.getRoot();
    }

    /**
     * This method statically set the items to be set in GameToolsAdapter
     * TODO -> maybe call from the database
     * @return items
     */
    private ArrayList<GameTools> getGameTools(){
        ArrayList<GameTools> gameTools = new ArrayList<>();

        /**
         * Retrieve data for tool quantity from SharedPreferences
         */
        sharedPref = getActivity().getSharedPreferences("gameToolsQty", Context.MODE_PRIVATE);

        int toolDefaultQuantity = getResources().getInteger(R.integer.tool_default_quantity);
        String helicopter = getString(R.string.helicopter);
        String rocket = getString(R.string.rocket_tool);
        String clearMonster = getString(R.string.clear_monsters_tool);
        gameTools.add(new FlyItems(GameToolsName.COPTER, sharedPref.getInt(String.valueOf(R.string.copter), toolDefaultQuantity), helicopter));
        gameTools.add(new FlyItems(GameToolsName.ROCKET, sharedPref.getInt(String.valueOf(R.string.rocket), toolDefaultQuantity), rocket));
        gameTools.add(new ClearMonsters(clearMonster,sharedPref.getInt(String.valueOf(R.string.clear_monsters), toolDefaultQuantity)));
        /**
         * uncomment code below to have unlimited tool quantity (start from 5)
         */
//        gameTools.add(new FlyItems(GameToolsName.COPTER, 5, helicopter));
//        gameTools.add(new FlyItems(GameToolsName.ROCKET, 5,rocket));
//        gameTools.add(new ClearMonsters(clearMonster, 5));
        return gameTools;
    }

    /**
     * called from SelectedGameToolsAdapter
     * to update text quantity on each game tool
     * @param gameTools
     */
    public void updateTextQuantity(GameTools gameTools){
        adapter = binding.getGameToolsAdapter();
        adapter.updateQuantity(gameTools);
    }

    /**
     * the user can see how many times remaining until game tools refreshed
     */
    public void setRefreshGameToolsCountdown(){
        TextView tvCountDown = binding.textCountDown;

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Australia/Melbourne"));
        long startMillis = calendar.getTimeInMillis();

        Calendar tomorrowCalendar = Calendar.getInstance(TimeZone.getTimeZone("Australia/Melbourne"));
        tomorrowCalendar.add(Calendar.DAY_OF_MONTH, 1); //add a day
        tomorrowCalendar.set(Calendar.HOUR_OF_DAY, 0);
        tomorrowCalendar.set(Calendar.MINUTE, 0);
        tomorrowCalendar.set(Calendar.SECOND, 0);
        tomorrowCalendar.set(Calendar.MILLISECOND, 0);

        long endMillis = tomorrowCalendar.getTimeInMillis();

        long totalMillis = endMillis - startMillis;
        CountDownTimer timer = new CountDownTimer(totalMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished);
                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours);

                long minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes);

                long seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished);

                tvCountDown.setText(getString(R.string.time_remaining_placeholder) + hours + " hours " + minutes + " minutes " + seconds + " seconds");
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }


    /**
     * This method is called in SelectedGameToolsAdapter
     * @param text
     */
    public void setTextItemsFull(int text){
        binding.textItemsFull.setText(getString(text));
    }

    /**
     * Store the fragment state when replaced
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //just to update text, nothing to do with GameTools
        if(activity.getSelectedGameToolsGameTools().size() >= 3){
            savedState = saveState();
        }
        activity.setGameTools(adapter.getItems());
    }

    /**
     * Save the state in the bundle when onDestroyView called
     * @return state as Bundle
     */
    private Bundle saveState() { /* called either from onDestroyView() or onSaveInstanceState() */
        Bundle state = new Bundle();
        state.putCharSequence("fulltext", binding.textItemsFull.getText());
        return state;
    }

    /**
     * This method has condition, use Bundle from class if savedState is not null
     * Otherwise generate another bundle
     * @param outState -> Store the state in the given Bundle
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /* If onDestroyView() is called first, we can use the previously savedState but we can't call saveState() anymore */
        /* If onSaveInstanceState() is called first, we don't have savedState, so we need to call saveState() */
        /* => (?:) operator inevitable! */
        outState.putBundle("fulltext", (savedState != null) ? savedState : saveState());
    }
}