package com.example.android.gameapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.gameapplication.game_tools.GameTools;
import com.example.android.gameapplication.game_tools.GameToolsAdapter;
import com.example.android.gameapplication.game_tools.ClearMonsters;
import com.example.android.gameapplication.game_tools.FlyItems;
import com.example.android.gameapplication.game_tools.GameToolsName;
import com.example.android.gameapplication.game_tools.Reborn;
import com.example.android.gameapplication.databinding.FragmentGameToolsSelectionBinding;

import java.util.ArrayList;

/**
 * @Author: Rizky Paskalis Totong
 * @Date: 02/10/22
 */
public class GameToolsSelectionFragment extends Fragment {

    private FragmentGameToolsSelectionBinding binding;
    private GameToolsFragment fragment;
    private Bundle savedState = null;
    private MainActivity activity;

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
        if(savedInstanceState != null && savedState == null) {
            savedState = savedInstanceState.getBundle("fulltext");
        }
        if(savedState != null) {
            binding.textItemsFull.setText(savedState.getCharSequence("fulltext"));
        }
        savedState = null;

        GameToolsAdapter adapter = new GameToolsAdapter(getGameTools(), fragment);
        binding.setGameToolsAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.itemSelectionRv.setLayoutManager(layoutManager);

        return binding.getRoot();
    }

    /**
     * This method statically set the items to be set in GameToolsAdapter
     * TODO -> maybe call from the database
     * @return items
     */
    private ArrayList<GameTools> getGameTools(){
        ArrayList<GameTools> gameTools = new ArrayList<>();

        gameTools.add(new FlyItems(GameToolsName.COPTER));
        gameTools.add(new FlyItems(GameToolsName.ROCKET));
        gameTools.add(new ClearMonsters());
        gameTools.add(new Reborn());
        return gameTools;
    }

    /**
     * This method is called in SelectedGameToolsAdapter
     * @param text
     */
    public void setTextItemsFull(String text){
        binding.textItemsFull.setText(text);
    }

    /**
     * Store the fragment state when replaced
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(activity.getGameTools().size() >= 3){
            savedState = saveState();
        }
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