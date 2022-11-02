package com.example.android.gameapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.gameapplication.database.Database;
import com.example.android.gameapplication.game_tools.GameTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;


public class SelectWeekFragment extends Fragment {

    private MainActivity activity;
    private Context context;

    private String mParam1;
    private String mParam2;
    private GameFragment.SendMessages sendMessages;
    private String subject = "";
    private final Database database = new Database();

    public SelectWeekFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendMessages = (GameFragment.SendMessages) context;
    }

    public interface SendMessages {
        void iAmMSG(String msg);
    }

    public void fragmentReceiveMsg(String msg) {
        if (msg.startsWith("Subject")){
            subject = msg.substring(8);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_week, container, false);
        ArrayList<Button> weeks = new ArrayList<>();

        weeks.add(view.findViewById(R.id.button1));
        weeks.add(view.findViewById(R.id.button2));
        weeks.add(view.findViewById(R.id.button3));
        weeks.add(view.findViewById(R.id.button4));
        weeks.add(view.findViewById(R.id.button6));
        weeks.add(view.findViewById(R.id.button7));
        weeks.add(view.findViewById(R.id.button8));
        weeks.add(view.findViewById(R.id.button9));
        weeks.add(view.findViewById(R.id.button10));
        weeks.add(view.findViewById(R.id.button11));
        weeks.add(view.findViewById(R.id.button12));
        weeks.add(view.findViewById(R.id.button13));

        weekSelect(weeks);
        activity = (MainActivity) getActivity();
        assert activity != null;
        context = activity.getApplicationContext();

        return view;
    }

    private void weekSelect(ArrayList<Button> weeks) {
        for (int i = 0; i < weeks.size(); i++) {
            final int finalI = i;
            weeks.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String msg = "week" + (finalI + 1);
                    playGame(msg);
                }
            });
        }

    }


    public void playGame(String week) {

        if (!Objects.equals(subject, "")) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("gameTools", (Serializable) activity.getSelectedGameToolsGameTools());
            bundle.putString("week", week);
            bundle.putString("subject", subject);
            bundle.putString("user_name", activity.getUserName());
            bundle.putSerializable("monsters", database.getMonsters(subject, week));
            Log.i("info",""+database.getMonsters(subject, week));
            Intent intent = new Intent(context, GameActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

            // storing game tools quantity locally after game started

            SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            if (activity.getGameTools() != null) {
                for (GameTools gameTools : activity.getGameTools()) {
                    editor.putInt(gameTools.getCodeName(), gameTools.getQuantity());
                }
                editor.apply();
            }
            //reset the selected game tools box on the GameToolsFragment
            activity.setSelectedGameTools(new ArrayList<GameTools>());

        }
        else{
            SelectBeforeGameStartFragment selectsubject = new SelectBeforeGameStartFragment();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment, selectsubject)
                    .addToBackStack(null)
                    .commit();
        }
    }
}