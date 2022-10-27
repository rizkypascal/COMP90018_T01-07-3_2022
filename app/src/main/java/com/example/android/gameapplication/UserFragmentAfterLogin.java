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
    private Database db = new Database();
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

        ListAdapter adapterPersonalScore = new ListAdapter(getActivity(), R.layout.list_instance, getPersonalScores(user_name));
        listView = view.findViewById(R.id.personal_score_list_view);
        listView.setAdapter(adapterPersonalScore);


        if (!Objects.equals(user_name, "")) {
            textLoginInfo.setText(getString(R.string.sign_in_as)+" "+user_name);

        }

        return view;
    }

    private ArrayList<ListTuple> getPersonalScores(String user_name) {
        return  getPersonalScoresFromDB(user_name);
    }





    //TODO: XQ all the following methods need to be re-witten/modified
    //You may modify them in this file, or implement them in another
    private ArrayList<ListTuple> getPersonalScoresFromDB(String user_name) {
        //TODO: XQ return a ListTuple arrayList. This list records the best N (e.g. 10) scores of user.
        // ListTuple is a simple self-defined data structure with 2 Strings. see ListTuple.java.
        ArrayList<ListTuple> personalScoreList = new ArrayList<>();
        String subject = activity.getSubject();
        if (Objects.equals(subject, "")) {
            subject = getString(R.string.subject1);
        }
        //db.getScore( subject,  "week1", user_name);
        Log.d("UserFragmentAfterLogin", "getPersonalScoresFromDB: "+ user_name + " " + subject);
        for (int i = 1; i < 13; i++) {
            //personalScoreList.add(new ListTuple("week"+i, db.getScore( subject,  "week"+i, user_name)));
        }
        return  personalScoreList;
    }
}