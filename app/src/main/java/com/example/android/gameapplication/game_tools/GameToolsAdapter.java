package com.example.android.gameapplication.game_tools;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.gameapplication.BR;
import com.example.android.gameapplication.GameToolsFragment;
import com.example.android.gameapplication.R;
import com.example.android.gameapplication.databinding.ListItemBinding;

import java.util.List;

/**
 * @Author: Rizky Paskalis Totong
 * @Date: 01/10/22
 * This adapter used to show the current available Game Tools
 * Current listener -> update SelectedGameToolsAdapter when a tool is tapped
 */
public class GameToolsAdapter extends RecyclerView.Adapter<GameToolsAdapter.ViewHolder> implements GameToolsClickListener {

    private List<GameTools> gameTools;
    private GameToolsFragment fragment;

    public GameToolsAdapter(List<GameTools> gameTools, GameToolsFragment fragment){
        this.gameTools = gameTools;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        gameTools.get(position).setPosition(position);
        GameTools i = gameTools.get(position);
        holder.binding.setVariable(BR.gameTools, i);
        holder.binding.executePendingBindings();
        holder.binding.setClickListener(this);
    }

    @Override
    public int getItemCount() {
        return gameTools.size();
    }

    public void updateQuantity(GameTools gameToolsParams) {
        gameTools.get(gameToolsParams.getPosition()).setQuantity(gameToolsParams.getQuantity());
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListItemBinding binding;
        public ViewHolder(ListItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public void addItemToSelectedGameTools(GameTools gameToolsParams){
        if(gameToolsParams.getQuantity() > 0){
            fragment.updateSelectedItemsAdapter(gameToolsParams);
            notifyDataSetChanged();
        }
    }

    @Override
    public void deleteItemFromSelectedGameTools(GameTools i) {

    }

    public List<GameTools> getItems(){
        return gameTools;
    }
}
