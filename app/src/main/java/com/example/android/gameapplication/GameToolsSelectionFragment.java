package com.example.android.gameapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.gameapplication.specialitems.ClearMonsters;
import com.example.android.gameapplication.specialitems.FlyItems;
import com.example.android.gameapplication.specialitems.ItemName;
import com.example.android.gameapplication.specialitems.Items;
import com.example.android.gameapplication.specialitems.Reborn;
import com.example.android.gameapplication.specialitems.SpecialItemsAdapter;
import com.example.android.gameapplication.databinding.FragmentGameToolsSelectionBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameToolsSelectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class GameToolsSelectionFragment extends Fragment {

    private FragmentGameToolsSelectionBinding binding;
    private GameToolsFragment f;
    private Bundle savedState = null;
    private MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGameToolsSelectionBinding.inflate(getLayoutInflater());
        f = (GameToolsFragment) getChildFragmentManager().findFragmentById(R.id.fragment_container_view);
        activity = (MainActivity) getActivity();
        if(savedInstanceState != null && savedState == null) {
            savedState = savedInstanceState.getBundle("fulltext");
        }
        if(savedState != null) {
            binding.textItemsFull.setText(savedState.getCharSequence("fulltext"));
        }
        savedState = null;

        SpecialItemsAdapter adapter = new SpecialItemsAdapter(getItems(), f);
        binding.setSpecialItemsAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.itemSelectionRv.setLayoutManager(layoutManager);

        return binding.getRoot();
    }

    private ArrayList<Items> getItems(){
        ArrayList<Items> items = new ArrayList<>();

        items.add(new FlyItems(ItemName.COPTER));
        items.add(new FlyItems(ItemName.ROCKET));
        items.add(new ClearMonsters());
        items.add(new Reborn());
        return items;
    }

    public void setTextItemsFull(String text){
        binding.textItemsFull.setText(text);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(activity.getItems().size() >= 3){
            savedState = saveState();
        }
    }

    private Bundle saveState() { /* called either from onDestroyView() or onSaveInstanceState() */
        Bundle state = new Bundle();
        state.putCharSequence("fulltext", binding.textItemsFull.getText());
        return state;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /* If onDestroyView() is called first, we can use the previously savedState but we can't call saveState() anymore */
        /* If onSaveInstanceState() is called first, we don't have savedState, so we need to call saveState() */
        /* => (?:) operator inevitable! */
        outState.putBundle("fulltext", (savedState != null) ? savedState : saveState());
    }
}