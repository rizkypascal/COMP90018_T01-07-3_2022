package com.example.android.gameapplication.game_tools;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.gameapplication.BR;
import com.example.android.gameapplication.GameToolsSelectionFragment;
import com.example.android.gameapplication.R;
import com.example.android.gameapplication.databinding.ListSelectedItemBinding;

import java.util.List;

/**
 * @Author: Rizky Paskalis Totong
 * @Date: 01/10/22
 * This adapter used to manage the selected Game Tools
 * Current listener -> delete game tools when tapped
 */
public class SelectedGameToolsAdapter extends RecyclerView.Adapter<SelectedGameToolsAdapter.ViewHolder> implements GameToolsClickListener {

    private List<GameTools> gameTools;
    private GameToolsSelectionFragment fragment;

    public SelectedGameToolsAdapter(List<GameTools> gameTools, GameToolsSelectionFragment fragment){
        this.gameTools = gameTools;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListSelectedItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_selected_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GameTools i = gameTools.get(position);
        holder.binding.setVariable(BR.gameTools, i);
        holder.binding.executePendingBindings();
        holder.binding.setClickListener(this);
    }

    @Override
    public int getItemCount() {
        return gameTools.size();
    }

    @Override
    public void addItemToSelectedGameTools(GameTools gameToolsParams) {

    }

    @Override
    public void deleteItemFromSelectedGameTools(GameTools gameToolsParams) {
        gameTools.remove(gameToolsParams);
        fragment.setTextItemsFull("");
        notifyDataSetChanged();
    }

    public void updateSelectedItems(GameTools gameToolsParams){
        int duplicateItem = 0;
        if(gameTools.size() < 3)
        {
            for(int j = 0; j < gameTools.size(); j++) {
                if(gameTools.get(j).getImage() == gameToolsParams.getImage())
                {
                    duplicateItem++;
                    fragment.setTextItemsFull("Duplicate items detected!");
                    break;
                }
            }

            if(duplicateItem == 0) {
                gameTools.add(gameToolsParams);
                if(gameTools.size() >= 3){
                    fragment.setTextItemsFull("You have picked all items");
                } else {
                    fragment.setTextItemsFull("");
                }
                notifyDataSetChanged();
            }
        } else {
            fragment.setTextItemsFull("You have picked all items");
        }
    }

    public List<GameTools> getItems(){
        return gameTools;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListSelectedItemBinding binding;
        public ViewHolder(ListSelectedItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
