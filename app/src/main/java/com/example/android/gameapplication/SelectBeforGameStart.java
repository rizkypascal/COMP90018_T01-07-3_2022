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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.android.gameapplication.game_tools.GameTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class SelectBeforGameStart extends Fragment {


//    private Unbinder unbinder;
    private MainActivity activity;
    private Context context;

    private TextView textLoginInfo, userRank;
    private String[] drapdown_Array1 = {"AAA", "BBB", "CCC"};
    private String[] drapdown_Array2 = {""};
    private Spinner sp1;
    private Spinner sp2;
    private boolean isSpinnerFirst = true;
    private Button confirmButton;

    // set variables, interface for communication between activity & fragment, fragment & fragment
    private String user_name="";
    private GameFragment.SendMessages sendMessages;
    private ArrayAdapter<String> selAdapter2;
    private ArrayAdapter<String> selAdapter1;

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
        View view = inflater.inflate(R.layout.fragment_user_after_login, container, false);
        textLoginInfo = view.findViewById(R.id.textLoginInfo);
        activity = (MainActivity) getActivity();
        context = activity.getApplicationContext();
        if (!Objects.equals(user_name, "")) {

            //userRank.setText("Your world rank is: "+String.valueOf(UserRank(user_name)));
            textLoginInfo.setText(getString(R.string.sign_in_as)+ " " + user_name);

        }
        initSpinner(view);
        confirmButton = view.findViewById(R.id.button5);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( !isSpinnerFirst && !sp2.getSelectedItem().toString().equals("")) {
                    sp1.setEnabled(false);
                    sp2.setEnabled(false);
                    Toast.makeText(getActivity(), "Faculty "+ sp1.getSelectedItem().toString() + " Subject "+sp2.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    sendMessages.iAmMSG("Faculty "+ sp1.getSelectedItem().toString() + " Subject "+sp2.getSelectedItem().toString());
                    play_Game();
                }else{
                    Toast.makeText(getActivity(), "Please select a subject", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sp1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
                    Toast.makeText(getActivity(), "Faculty ", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        return view;
    }


    private void initSpinner(View view) {

        selAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, new ArrayList<String>(Arrays.asList(drapdown_Array1)));
        selAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, new ArrayList<String>(Arrays.asList(drapdown_Array2)));

        selAdapter1.setDropDownViewResource(R.layout.dropdown);
        selAdapter2.setDropDownViewResource(R.layout.dropdown);

        sp1 = view.findViewById(R.id.spinner1);
        sp2 = view.findViewById(R.id.spinner2);

        sp1.setAdapter(selAdapter1);
        sp2.setAdapter(selAdapter2);

        sp2.setSelection(0);
        sp2.setEnabled(false);

        sp1.setOnItemSelectedListener(new SelectedListener());
        sp2.setOnItemSelectedListener(new SelectedListener2());
    }

    class SelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (isSpinnerFirst) {
                view.setVisibility(View.INVISIBLE);
                sp2.setEnabled(false);
                isSpinnerFirst = false;
            }else{
                selAdapter1.remove("");
                if (i == 0) {
                    selAdapter2.clear();
                    selAdapter2.addAll("", "1", "2", "3");
                    sp2.setSelection(0);
                } else if (i == 1) {
                    selAdapter2.clear();
                    selAdapter2.addAll("", "A", "B", "C");
                    sp2.setSelection(0);
                } else if (i == 2) {
                    selAdapter2.clear();
                    selAdapter2.addAll("", "X", "Y", "Z");
                    sp2.setSelection(0);
                }else{
                    sp2.setEnabled(false);
                    selAdapter2.clear();
                }
                sp2.setEnabled(true);
            }
            isSpinnerFirst = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            sp2.setEnabled(false);
        }
    }

    class SelectedListener2 implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (!sp2.getSelectedItem().toString().equals("")){
                selAdapter2.remove("");
            }

            //Toast.makeText(getActivity(), "You have selected " + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
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
