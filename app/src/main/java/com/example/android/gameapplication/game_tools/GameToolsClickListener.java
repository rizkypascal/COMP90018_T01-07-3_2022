package com.example.android.gameapplication.game_tools;

/**
 * @Author: Rizky Paskalis Totong
 * @Date: 30/09/22
 * This interface will be used in the layout file of Game Tools List and Selection
 * This interface will also be implemented in SelectedGameToolsAdapter and GameToolsAdapter
 */
public interface GameToolsClickListener {
    void addItemToSelectedGameTools(GameTools gameTools);
    void deleteItemFromSelectedGameTools(GameTools gameTools);
}
