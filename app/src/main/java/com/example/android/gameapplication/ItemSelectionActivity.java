package com.example.android.gameapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.gameapplication.databinding.ActivityItemSelectionBinding;
import com.example.android.gameapplication.databinding.FragmentItemBinding;
import com.example.android.gameapplication.specialitems.ClearMonsters;
import com.example.android.gameapplication.specialitems.FlyItems;
import com.example.android.gameapplication.specialitems.ItemName;
import com.example.android.gameapplication.specialitems.Items;
import com.example.android.gameapplication.specialitems.Reborn;
import com.example.android.gameapplication.specialitems.SpecialItemsAdapter;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ItemSelectionActivity extends AppCompatActivity {

    private ActivityItemSelectionBinding binding;
    private List<Items> selectedItems = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemSelectionBinding.inflate(getLayoutInflater());
        ItemFragment f = (ItemFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);

        SpecialItemsAdapter adapter = new SpecialItemsAdapter(getItems(), f, this);
        binding.setSpecialItemsAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.itemSelectionRv.setLayoutManager(layoutManager);

        binding.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GameActivity.class);
                EventBus.getDefault().postSticky(f);
                startActivity(i);
            }
        });

        View view = binding.getRoot();
        setContentView(view);
    }

    private ArrayList<Items> getItems(){
        ArrayList<Items> items = new ArrayList<>();

        items.add(new FlyItems(ItemName.COPTER));
        items.add(new FlyItems(ItemName.ROCKET));
        items.add(new ClearMonsters());
        items.add(new Reborn());
        return items;
    }

}