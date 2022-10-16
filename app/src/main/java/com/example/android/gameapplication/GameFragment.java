package com.example.android.gameapplication;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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
            textLoginInfo.setText("You have signed in as "+user_name);
            signInUpButton.setText("LOG OUT");
        }

        return view;
    }

    @OnClick(R.id.gameButton)
    void GameButtonOnClick(){
        Log.d("GameFragment", "GameButton clicked.");
        if (user_name=="") PopToast("You are playing as a tourist.");
        else PopToast("You are playing as user "+user_name);

        startActivity(new Intent(context, GameActivity.class));
        //TODO: tony or arthur: need passing msg of user account
    }

    @OnClick(R.id.signInUpButton)
    void SignInUpButtonOnClick(){
        Log.d("GameFragment", "SignInUpButton clicked.");
        if (signInUpButton.getText().toString()=="LOG OUT"){
            MediaPlayer mp = MediaPlayer.create(activity, R.raw.winxpshutdown);
            mp.start();

            user_name = "";
            textLoginInfo.setText("You have not signed in yet");
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
}
