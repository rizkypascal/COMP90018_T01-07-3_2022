package com.example.android.gameapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.gameapplication.specialitems.Items;
import com.example.android.gameapplication.specialitems.SelectedItemsAdapter;
import com.example.android.gameapplication.databinding.FragmentGameToolsBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameToolsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameToolsFragment extends Fragment {

    private List<Items> items;
    private FragmentGameToolsBinding binding;
    private SelectedItemsAdapter adapter;
    private MainActivity activity;
    private GameToolsSelectionFragment f;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGameToolsBinding.inflate(inflater, container, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        activity = (MainActivity) getActivity();
        f = (GameToolsSelectionFragment) getParentFragment();
        if(activity.getItems() == null) {
            items = new ArrayList<Items>();
        } else {
            items = activity.getItems();
        }
        adapter = new SelectedItemsAdapter(items, f);
        binding.setSelectedItemsAdapter(adapter);
        binding.itemRv.setLayoutManager(layoutManager);
        return binding.getRoot();
    }

    public void updateSelectedItemsAdapter(Items i) {
        adapter = binding.getSelectedItemsAdapter();
        adapter.updateSelectedItems(i);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        activity.setItems(adapter.getItems());
    }
}