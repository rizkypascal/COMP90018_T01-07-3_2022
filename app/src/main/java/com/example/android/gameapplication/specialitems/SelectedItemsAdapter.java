package com.example.android.gameapplication.specialitems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.gameapplication.BR;
import com.example.android.gameapplication.GameToolsFragment;
import com.example.android.gameapplication.GameToolsSelectionFragment;
import com.example.android.gameapplication.R;
import com.example.android.gameapplication.databinding.ListSelectedItemBinding;

import java.util.List;

public class SelectedItemsAdapter extends RecyclerView.Adapter<SelectedItemsAdapter.ViewHolder> implements ItemClickListener {

    private List<Items> items;
    private GameToolsSelectionFragment f;

    public SelectedItemsAdapter(List<Items> items, GameToolsSelectionFragment f){
        this.items = items;
        this.f = f;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListSelectedItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_selected_item, parent, false);
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

    @Override
    public void addItemToSelectedItems(Items i) {

    }

    @Override
    public void deleteItemFromSelectedItems(Items i) {
        items.remove(i);
        f.setTextItemsFull("");
        notifyDataSetChanged();
    }

    public void updateSelectedItems(Items i){
        int duplicateItem = 0;
        if(items.size() < 3)
        {
            for(int j = 0; j < items.size(); j++) {
                if(items.get(j).getImage() == i.getImage())
                {
                    duplicateItem++;
                    f.setTextItemsFull("Duplicate items detected!");
                    break;
                }
            }

            if(duplicateItem == 0) {
                items.add(i);
                if(items.size() >= 3){
                    f.setTextItemsFull("You have picked all items");
                } else {
                    f.setTextItemsFull("");
                }
                notifyDataSetChanged();
            }
        } else {
            f.setTextItemsFull("You have picked all items");
        }
    }

    public List<Items> getItems(){
        return items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListSelectedItemBinding binding;
        public ViewHolder(ListSelectedItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
