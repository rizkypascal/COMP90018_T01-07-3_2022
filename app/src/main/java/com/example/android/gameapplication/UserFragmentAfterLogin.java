package com.example.android.gameapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;


public class UserFragmentAfterLogin extends Fragment {


//    private Unbinder unbinder;
    private ListView listView;
    private TextView textLoginInfo, userRank;
    private String[] drapdown_Array1 = {"AAA", "BBB", "CCC"};
    private String[] drapdown_Array2 = {""};
    private Spinner sp2;
    private boolean isSpinnerFirst = true;

    // set variables, interface for communication between activity & fragment, fragment & fragment
    private String user_name="";
    private GameFragment.SendMessages sendMessages;
    private ArrayAdapter<String> selAdapter2;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sendMessages = (GameFragment.SendMessages) context;
    }

    public interface SendMessages {
        void iAmMSG(String msg);
    }

    public void fragmentReceiveMsg(String msg) {
        Log.d("UserFragment", "receive msg: "+msg);
        user_name = msg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_after_login, container, false);
        textLoginInfo = view.findViewById(R.id.textLoginInfo);
        //userRank = view.findViewById(R.id.userRank);

        //ListAdapter adapterPersonalScore = new ListAdapter(getActivity(), R.layout.list_instance, getPersonalScores(user_name));
        //listView = view.findViewById(R.id.personal_score_list_view);
        //listView.setAdapter(adapterPersonalScore);

        //ListAdapter adapterWorldScore = new ListAdapter(getActivity(), R.layout.list_instance, getWorldScores());
        //listView = view.findViewById(R.id.world_score_list_view);
        //listView.setAdapter(adapterWorldScore);

        if (user_name!="") {

            textLoginInfo.setText("You have signed in as "+user_name);
            //userRank.setText("Your world rank is: "+String.valueOf(UserRank(user_name)));
            textLoginInfo.setText(getString(R.string.sign_in_as)+user_name);
            //userRank.setText(getString(R.string.world_rank)+String.valueOf(UserRank(user_name)));

        }
        initSpinner(view);

        return view;
    }


    private void initSpinner(View view) {
        //声明一个下拉列表的数组适配器
        ArrayAdapter<String> selAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, drapdown_Array1);
        selAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, new ArrayList<String>(Arrays.asList(drapdown_Array2)));
        //设置数组适配器的布局样式
        selAdapter1.setDropDownViewResource(R.layout.dropdown);
        selAdapter2.setDropDownViewResource(R.layout.dropdown);
        //从布局文件中获取名叫sp_dialog的下拉框
        Spinner sp1 = view.findViewById(R.id.spinner1);
        sp2 = view.findViewById(R.id.spinner2);
        //设置下拉框的数组适配器
        sp1.setAdapter(selAdapter1);
        sp2.setAdapter(selAdapter2);
        //设置下拉框默认的显示第一项
        sp2.setSelection(0);
        sp2.setEnabled(false);
        //给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp1.setOnItemSelectedListener(new SelectedListener());
        sp2.setOnItemSelectedListener(new SelectedListener2());
    }

    class SelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if (isSpinnerFirst) {
                view.setVisibility(View.INVISIBLE);
                sp2.setEnabled(false);
            }else{
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
            //Toast.makeText(getActivity(), "You have selected " + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

   //private ArrayList<ListTuple> getPersonalScores(String user_name) {
   //    return  getPersonalScoresFromDB(user_name);
   //}

   //private ArrayList<ListTuple> getWorldScores() {
   //    return  getWorldScoresFromDB();
   //}

   //private int UserRank(String username){
   //    return UserRankFromDB(username);
   //}




   ////TODO: XQ all the following methods need to be re-witten/modified
   ////You may modify them in this file, or implement them in another
   //private ArrayList<ListTuple> getPersonalScoresFromDB(String user_name) {
   //    //TODO: XQ return a ListTuple arrayList. This list records the best N (e.g. 10) scores of user.
   //    // ListTuple is a simple self-defined data structure with 2 Strings. see ListTuple.java.
   //    ArrayList<ListTuple> personalScoreList = new ArrayList<>();
   //    personalScoreList.add(new ListTuple("9999", "01/02/2022"));
   //    personalScoreList.add(new ListTuple("9998", "02/02/2022"));
   //    personalScoreList.add(new ListTuple("9997", "03/02/2022"));
   //    personalScoreList.add(new ListTuple("9996", "04/02/2022"));
   //    personalScoreList.add(new ListTuple("9995", "05/02/2022"));
   //    personalScoreList.add(new ListTuple("9994", "06/02/2022"));
   //    personalScoreList.add(new ListTuple("9993", "07/02/2022"));
   //    personalScoreList.add(new ListTuple("9992", "08/02/2022"));
   //    return  personalScoreList;
   //}

   //private ArrayList<ListTuple> getWorldScoresFromDB() {
   //    //TODO: XQ return a ListTuple arrayList. This list records the best N (e.g. 10) scores all over the damn world.
   //    // ListTuple is a simple self-defined data structure with 2 Strings. see ListTuple.java.
   //    ArrayList<ListTuple> worldScoreList = new ArrayList<>();
   //    worldScoreList.add(new ListTuple("arthur0", "99999"));
   //    worldScoreList.add(new ListTuple("arthur1", "99998"));
   //    worldScoreList.add(new ListTuple("arthur2", "99997"));
   //    worldScoreList.add(new ListTuple("arthur3", "99996"));
   //    worldScoreList.add(new ListTuple("arthur4", "99995"));
   //    worldScoreList.add(new ListTuple("arthur5", "99994"));
   //    worldScoreList.add(new ListTuple("arthur6", "99993"));

   //    return  worldScoreList;

   //}

   //private int UserRankFromDB(String user_name){
   //    //TODO: XQ return the rank of current user.
   //    return 666;
   //}

}
