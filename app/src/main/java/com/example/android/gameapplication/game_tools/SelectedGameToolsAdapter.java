package com.example.android.gameapplication.game_tools;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.gameapplication.BR;
import com.example.android.gameapplication.GameContext;
import com.example.android.gameapplication.GameToolsSelectionFragment;
import com.example.android.gameapplication.R;
import com.example.android.gameapplication.databinding.ListSelectedItemBinding;
import com.example.android.gameapplication.games.Status;

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
    private String activityName;
    private GameContext gameContext;

    public SelectedGameToolsAdapter(List<GameTools> gameTools, GameToolsSelectionFragment fragment, String activityName, GameContext gameContext){
        this.gameTools = gameTools;
        this.fragment = fragment;
        this.activityName = activityName;
        this.gameContext = gameContext;
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

    /**
     * remove game tools from selected game tools box
     * increase the quantity of the available game tools box
     * @param gameToolsParams
     */
    @Override
    public void deleteItemFromSelectedGameTools(GameTools gameToolsParams) {
        gameTools.remove(gameToolsParams);

        /**
         * if this adapter called from MainActivity
         * it modifies the fragment for selecting game tools
         */
        if(activityName == "MainActivity") {
            gameToolsParams.setQuantity(gameToolsParams.getQuantity() + 1);
            fragment.updateTextQuantity(gameToolsParams);
            fragment.setTextItemsFull(R.string.empty_string);
        }
        /**
         * if gameContext is not null
         * then update the jumper status
         */
        else if (gameContext != null) {
            if(gameToolsParams.getCodeName().equals(String.valueOf(R.string.rocket))){
                gameContext.setJumperStatus(Status.onRocket);
            }else if (gameToolsParams.getCodeName().equals(String.valueOf(R.string.copter))){
                gameContext.setJumperStatus(Status.onCopter);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * this method called to update selected game tools box
     * from GameToolsAdapter
     * @param gameToolsParams
     */
    public void updateSelectedItems(GameTools gameToolsParams){
        int duplicateItem = 0;
        if(gameTools.size() < 3)
        {
            for(int j = 0; j < gameTools.size(); j++) {
                /**
                 * ensure that the tool cannot be chose more than once
                 */
                if(gameTools.get(j).getImage() == gameToolsParams.getImage())
                {
                    duplicateItem++;
                    fragment.setTextItemsFull(R.string.duplicated_tool);
                    break;
                }
            }

            /**
             * add the tool to the selected game tools box
             * reduce the quantity of available game tools box
             */
            if(duplicateItem == 0) {
                gameTools.add(gameToolsParams);
                if(gameTools.size() >= 3){
                    fragment.setTextItemsFull(R.string.all_tools_picked);
                } else {
                    fragment.setTextItemsFull(R.string.empty_string);
                }
                gameToolsParams.setQuantity(gameToolsParams.getQuantity() - 1);
                fragment.updateTextQuantity(gameToolsParams);
                notifyDataSetChanged();
            }
        } else {
            fragment.setTextItemsFull(R.string.all_tools_picked);
        }
    }

    /**
     * this method is accessible to fragments or activities
     * @return gameTools
     */
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
