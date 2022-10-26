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

import androidx.fragment.app.Fragment;

import com.example.android.gameapplication.list_content.ListAdapter;
import com.example.android.gameapplication.list_content.ListTuple;

import java.util.ArrayList;


public class UserFragmentAfterLogin extends Fragment {


    //    private Unbinder unbinder;
    private ListView listView;
    private TextView textLoginInfo, userRank;

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
        userRank = view.findViewById(R.id.userRank);

        ListAdapter adapterPersonalScore = new ListAdapter(getActivity(), R.layout.list_instance, getPersonalScores(user_name));
        listView = view.findViewById(R.id.personal_score_list_view);
        listView.setAdapter(adapterPersonalScore);

        ListAdapter adapterWorldScore = new ListAdapter(getActivity(), R.layout.list_instance, getWorldScores());
        listView = view.findViewById(R.id.world_score_list_view);
        listView.setAdapter(adapterWorldScore);

        if (user_name!="") {
            textLoginInfo.setText(getString(R.string.sign_in_as)+" "+user_name);
            userRank.setText(getString(R.string.world_rank)+String.valueOf(UserRank(user_name)));
        }

        return view;
    }

    private ArrayList<ListTuple> getPersonalScores(String user_name) {
        return  getPersonalScoresFromDB(user_name);
    }

    private ArrayList<ListTuple> getWorldScores() {
        return  getWorldScoresFromDB();
    }

    private int UserRank(String username){
        return UserRankFromDB(username);
    }




    //TODO: XQ all the following methods need to be re-witten/modified
    //You may modify them in this file, or implement them in another
    private ArrayList<ListTuple> getPersonalScoresFromDB(String user_name) {
        //TODO: XQ return a ListTuple arrayList. This list records the best N (e.g. 10) scores of user.
        // ListTuple is a simple self-defined data structure with 2 Strings. see ListTuple.java.
        ArrayList<ListTuple> personalScoreList = new ArrayList<>();
        personalScoreList.add(new ListTuple("9999", "01/02/2022"));
        personalScoreList.add(new ListTuple("9998", "02/02/2022"));
        personalScoreList.add(new ListTuple("9997", "03/02/2022"));
        personalScoreList.add(new ListTuple("9996", "04/02/2022"));
        personalScoreList.add(new ListTuple("9995", "05/02/2022"));
        personalScoreList.add(new ListTuple("9994", "06/02/2022"));
        personalScoreList.add(new ListTuple("9993", "07/02/2022"));
        personalScoreList.add(new ListTuple("9992", "08/02/2022"));
        return  personalScoreList;
    }

    private ArrayList<ListTuple> getWorldScoresFromDB() {
        //TODO: XQ return a ListTuple arrayList. This list records the best N (e.g. 10) scores all over the damn world.
        // ListTuple is a simple self-defined data structure with 2 Strings. see ListTuple.java.
        ArrayList<ListTuple> worldScoreList = new ArrayList<>();
        worldScoreList.add(new ListTuple("arthur0", "99999"));
        worldScoreList.add(new ListTuple("arthur1", "99998"));
        worldScoreList.add(new ListTuple("arthur2", "99997"));
        worldScoreList.add(new ListTuple("arthur3", "99996"));
        worldScoreList.add(new ListTuple("arthur4", "99995"));
        worldScoreList.add(new ListTuple("arthur5", "99994"));
        worldScoreList.add(new ListTuple("arthur6", "99993"));

        return  worldScoreList;

    }

    private int UserRankFromDB(String user_name){
        //TODO: XQ return the rank of current user.
        return 666;
    }

}