package com.example.android.gameapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.gameapplication.game_tools.GameTools;
import com.example.android.gameapplication.game_tools.SelectedGameToolsAdapter;
import com.example.android.gameapplication.databinding.FragmentGameToolsBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Rizky Paskalis Totong
 * @Date: 02/10/22
 */
public class GameToolsFragment extends Fragment {

    private List<GameTools> gameTools;
    private FragmentGameToolsBinding binding;
    private SelectedGameToolsAdapter adapter;
    private MainActivity mainActivity;
    private GameActivity gameActivity;
    private GameToolsSelectionFragment fragment;
    private GameContext gameContext;

    public GameToolsFragment(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    public GameToolsFragment() {
    }

    /**
     * Inflate the view with View Binding
     * Get the current Activity to get the current selected tools
     * Set an adapter to generate list of selected game tools
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return binding.getRoot()
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGameToolsBinding.inflate(inflater, container, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);

        String activityName = getActivity().getClass().getSimpleName();
        switch(activityName){
            case "MainActivity":
                mainActivity = (MainActivity) getActivity();
                // initialize game tools from main activity
                if(mainActivity.getGameTools() == null) {
                    gameTools = new ArrayList<GameTools>();
                } else {
                    gameTools = mainActivity.getSelectedGameToolsGameTools();
                }
                break;
            case "GameActivity":
                gameActivity = (GameActivity) getActivity();
                // initialize game tools from game activity
                if(gameActivity.getGameTools() == null) {
                    gameTools = new ArrayList<GameTools>();
                } else {
                    gameTools = gameActivity.getGameTools();
                }
                break;
        }
        fragment = (GameToolsSelectionFragment) getParentFragment();
        adapter = new SelectedGameToolsAdapter(gameTools, fragment, activityName, gameContext);
        binding.setSelectedGameToolsAdapter(adapter);
        binding.itemRv.setLayoutManager(layoutManager);
        return binding.getRoot();
    }

    /**
     * This method will be called in GameToolsAdapter
     * @param gameTools
     */
    public void updateSelectedItemsAdapter(GameTools gameTools) {
        adapter = binding.getSelectedGameToolsAdapter();
        adapter.updateSelectedItems(gameTools);
    }

    /**
     * Store the fragment state when replaced
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mainActivity != null){
            mainActivity.setSelectedGameTools(adapter.getItems());
        }
    }
}