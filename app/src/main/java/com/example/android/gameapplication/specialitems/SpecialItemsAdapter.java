package com.example.android.gameapplication.specialitems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.gameapplication.BR;
import com.example.android.gameapplication.ItemFragment;
import com.example.android.gameapplication.R;
import com.example.android.gameapplication.databinding.ActivityItemSelectionBinding;
import com.example.android.gameapplication.databinding.ListItemBinding;

import java.util.List;

public class SpecialItemsAdapter extends RecyclerView.Adapter<SpecialItemsAdapter.ViewHolder> implements ItemClickListener {

    private List<Items> items;
    private Context context;
    private ItemFragment f;

    public SpecialItemsAdapter(List<Items> items, ItemFragment f, Context context){
        this.items = items;
        this.context = context;
        this.f = f;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Items i = items.get(position);
        holder.binding.setVariable(BR.item, i);
        holder.binding.executePendingBindings();
        holder.binding.setItemListener(this);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemBinding binding;
        public ViewHolder(ListItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void addItemToSelectedItems(Items i){
        f.updateSelectedItemsAdapter(i);
    }

    @Override
    public void deleteItemFromSelectedItems(Items i) {

    }
}
