package com.example.android.gameapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.android.gameapplication.game_tools.GameTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class GameFragment extends Fragment {
    private Unbinder unbinder;
    @BindView(R.id.gameButton)
    Button gameButton;
    @BindView(R.id.signInUpButton)
    Button signInUpButton;

    private MainActivity activity;
    private Context context;
    private TextView textLoginInfo;
    int duration = Toast.LENGTH_SHORT;

    // set variables, interface for communication between activity & fragment, fragment & fragment
    private SendMessages sendMessages;
    private String user_name="";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendMessages = (SendMessages) context;
    }

    public interface SendMessages {
        void iAmMSG(String msg);
    }

    public void fragmentReceiveMsg(String msg) {
        Log.d("GameFragment", "receive msg: "+msg);
        user_name = msg;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_game, container, false);
        unbinder = ButterKnife.bind(this, view);
        textLoginInfo = view.findViewById(R.id.textLoginInfo);
        activity = (MainActivity) getActivity();
        context = activity.getApplicationContext();
        if (user_name!="") {
            textLoginInfo.setText(getString(R.string.sign_in_as)+" "+user_name);
            signInUpButton.setText(getString(R.string.log_out));
        }

        return view;
    }

    @OnClick(R.id.gameButton)
    void GameButtonOnClick(){
        Log.d("GameFragment", "GameButton clicked.");
        List<GameTools> selectedGameTools = activity.getSelectedGameToolsGameTools();

        /**
         * condition to check whether the users have picked game tools or not
         * if no, show dialog to warn users
         * if yes, immediately process to the gameplay
         */
        if (selectedGameTools == null || selectedGameTools.size() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage(getString(R.string.play_without_tool));
            builder.setTitle(getString(R.string.warning));
            /**
             * set cancelable false for when the user clicks
             * on the outside the Dialog Box then
             * it will remain show
             */
            builder.setCancelable(false);

            builder.setPositiveButton(getString(R.string.yes), (DialogInterface.OnClickListener) (dialog, which) -> {
                // if user click yes then proceed to the game
                playGame();
            });

            builder.setNegativeButton(getString(R.string.no), (DialogInterface.OnClickListener) (dialog, which) -> {
                // if user click no, user is still remain in this activity
                dialog.cancel();
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            playGame();
        }
        //TODO: tony or arthur: need passing msg of user account
    }

    @OnClick(R.id.signInUpButton)
    void SignInUpButtonOnClick(){
        Log.d("GameFragment", "SignInUpButton clicked.");
        if (signInUpButton.getText().toString().equals(getString(R.string.log_out))){
            MediaPlayer mp = MediaPlayer.create(activity, R.raw.winxpshutdown);
            mp.start();

            user_name = "";
            String message = getString(R.string.not_logged_in);
            textLoginInfo.setText(message);
            Log.d("GameFragment", "send msg: "+user_name);
            sendMessages.iAmMSG(user_name);
            signInUpButton.setText("SIGN IN/UP");
        }
        else{
            UserFragment userFragment = new UserFragment();
            MainActivity activity = (MainActivity) getActivity();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment, userFragment)
                    .addToBackStack(null)
                    .commit();
            userFragment.fragmentReceiveMsg(user_name);
            Log.d("GameFragment", "send msg: "+user_name);

            activity.navView.getMenu().findItem(R.id.navigation_user).setChecked(true);
        }

    }

    void PopToast(String text){
        Toast toast = Toast.makeText(context, null, duration);
        toast.setText(text);
        toast.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * function to switch activity for the gameplay
     */
    public void playGame(){
        if (user_name=="") {
            PopToast(getString(R.string.play_as_tourist));
        }
        else {
            PopToast(getString(R.string.play_as_user)+user_name);
            SelectBeforGameStart selectFragment = new SelectBeforGameStart();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment, selectFragment)
                    .addToBackStack(null)
                    .commit();
            selectFragment.fragmentReceiveMsg(user_name);

        }
        //play_Game_();
    }

    public void play_Game_() {
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
