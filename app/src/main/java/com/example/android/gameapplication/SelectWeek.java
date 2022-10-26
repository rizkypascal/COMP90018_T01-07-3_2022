package com.example.android.gameapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.android.gameapplication.game_tools.GameTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class SelectWeek extends Fragment {


//    private Unbinder unbinder;
    private MainActivity activity;
    private Context context;


    // set variables, interface for communication between activity & fragment, fragment & fragment
    private String user_name="";
    private GameFragment.SendMessages sendMessages;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendMessages = (GameFragment.SendMessages) context;
    }

    public interface SendMessages {
        void iAmMSG(String msg);
    }

    public void fragmentReceiveMsg(String msg) {
        if (msg.startsWith("Faculty")){
            Log.d("UserFragment", ": "+msg);
        }
        else {
            Log.d("UserFragment", "receive msg: "+msg);
            user_name = msg;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_week, container, false);
        activity = (MainActivity) getActivity();
        context = activity.getApplicationContext();
        if (!Objects.equals(user_name, "")) {

            //userRank.setText("Your world rank is: "+String.valueOf(UserRank(user_name)));


        }



        return view;
    }








    public void play_Game() {

        GameFragment startmenu = new GameFragment();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, startmenu)
                .addToBackStack(null)
                .commit();
        startmenu.fragmentReceiveMsg(user_name);

        Bundle bundle = new Bundle();
        bundle.putSerializable("gameTools", (Serializable) activity.getSelectedGameToolsGameTools());
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);


        // storing game tools quantity locally after game started

        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if(activity.getGameTools() != null){
            for(GameTools gameTools : activity.getGameTools()){
                editor.putInt(gameTools.getCodeName(), gameTools.getQuantity());
            }
            editor.apply();
        }
        //reset the selected game tools box on the GameToolsFragment
        activity.setSelectedGameTools(new ArrayList<GameTools>());
    }



}
