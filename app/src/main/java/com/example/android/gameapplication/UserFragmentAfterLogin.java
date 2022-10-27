package com.example.android.gameapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.android.gameapplication.database.Database;

import androidx.fragment.app.Fragment;

import com.example.android.gameapplication.list_content.ListAdapter;
import com.example.android.gameapplication.list_content.ListTuple;

import java.util.ArrayList;
import java.util.Objects;


public class UserFragmentAfterLogin extends Fragment {


    //    private Unbinder unbinder;
    private ListView listView;
    private TextView textLoginInfo, userRank;
    private Database db;
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
            Log.d("UserFragmentAfterLogin", ": "+msg);
        }
        else {
            Log.d("UserFragmentAfterLogin", "receive msg: "+msg);
            user_name = msg;
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_after_login_, container, false);
        textLoginInfo = view.findViewById(R.id.textLoginInfo);
        activity = (MainActivity) getActivity();
        context = activity.getApplicationContext();
        db = new Database();

        System.out.println("testoncreat");

        ListAdapter adapterPersonalScore = new ListAdapter(getActivity(), R.layout.list_instance, getPersonalScores(user_name));
        listView = view.findViewById(R.id.personal_score_list_view);

        listView.setAdapter(adapterPersonalScore);
        listView.setItemChecked(0,true);


        if (!Objects.equals(user_name, "")) {
            textLoginInfo.setText(getString(R.string.sign_in_as)+" "+user_name);

        }

        return view;
    }

    private ArrayList<ListTuple> getPersonalScores(String user_name) {
        return  getPersonalScoresFromDB(user_name);
    }






    private ArrayList<ListTuple> getPersonalScoresFromDB(String user_name) {

        ArrayList<ListTuple> personalScoreList = new ArrayList<>();
        String subject = activity.getSubject();
        String[] subjectList = {getString(R.string.subject1),getString(R.string.subject2)};

        personalScoreList.add(new ListTuple(subjectList[0], "00"));
        personalScoreList.add(new ListTuple(subjectList[1], "11"));

        // for (int i = 0; i < subjectList.length; i++) {
        //     String subjectName = subjectList[i];
        //     int subjectScore = 0;
        //     for (int j = 1; j < 13; j++) {
        //         String score = db.getScore(subject, "week" + i, user_name);
        //         subjectScore += Integer.parseInt(score);
        //     }
        //     personalScoreList.add(new ListTuple(subjectName, "" + subjectScore));
        // }




        //Log.d("UserFragmentAfterLogin", "getPersonalScoresFromDB: "+ user_name + " " + subject);
        //for (int i = 1; i < 13; i++) {
        //    personalScoreList.add(new ListTuple("week"+i, db.getScore( subject,  "week"+i, user_name)));
        //}

        return  personalScoreList;
    }
}