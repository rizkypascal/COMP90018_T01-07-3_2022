package com.example.android.gameapplication;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.FrameMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.gameapplication.databinding.FragmentItemBinding;
import com.example.android.gameapplication.specialitems.ClearMonsters;
import com.example.android.gameapplication.specialitems.FlyItems;
import com.example.android.gameapplication.specialitems.Items;
import com.example.android.gameapplication.specialitems.Reborn;
import com.example.android.gameapplication.specialitems.SelectedItemsAdapter;
import com.example.android.gameapplication.specialitems.SpecialItemsAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ItemFragment extends Fragment {

    private FragmentItemBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ItemFragment newInstance(String param1, String param2) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentItemBinding.inflate(inflater, container, false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        SelectedItemsAdapter adapter = new SelectedItemsAdapter(new ArrayList<Items>(),null,  getContext());
        binding.setSelectedItemsAdapter(adapter);
        binding.itemRv.setLayoutManager(layoutManager);
        return binding.getRoot();
    }

    public void updateSelectedItemsAdapter(Items i) {
        SelectedItemsAdapter adapter = binding.getSelectedItemsAdapter();
        adapter.updateSelectedItems(i);
    }
}