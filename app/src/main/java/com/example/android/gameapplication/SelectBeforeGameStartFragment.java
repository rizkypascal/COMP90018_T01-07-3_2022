package com.example.android.gameapplication;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class SelectBeforeGameStartFragment extends Fragment {

    private MainActivity activity;
    private Context context;

    private TextView textLoginInfo, userRank;
    private final String[] dropdown_Array1 = {"Arts", "Business and Economics", "Engineering and Information Technology"};
    private final String[] dropdown_Array2 = {""};
    private Spinner sp1;
    private Spinner sp2;
    private boolean isSpinnerFirst = true;
    private Button confirmButton;
    private String subject;

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
        if (!msg.startsWith("Faculty")){
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

                    String this_subject = "";

                    if (sp2.getSelectedItem().toString().equals(getString(R.string.subject1))){
                        this_subject = "subject1";
                    }
                    else{
                        this_subject = "subject2";
                    }
                    activity.setSubject(this_subject);
                    subject = "Subject:" + this_subject;
                    playGame();
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
        selAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, new ArrayList<String>(Arrays.asList(dropdown_Array1)));
        selAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, new ArrayList<String>(Arrays.asList(dropdown_Array2)));

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
                    selAdapter2.addAll("");
                    sp2.setSelection(0);
                } else if (i == 1) {
                    selAdapter2.clear();
                    selAdapter2.addAll("");
                    sp2.setSelection(0);
                } else if (i == 2) {
                    selAdapter2.clear();
                    selAdapter2.addAll("", getString(R.string.subject1), getString(R.string.subject2));
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
                if (sp2.getAdapter().getItem(0).equals("")){
                    selAdapter2.remove("");
                    sp2.setSelection(i-1);
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    public void playGame() {
        SelectWeekFragment selectWeek = new SelectWeekFragment();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_fragment, selectWeek)
                .addToBackStack(null)
                .commit();
        selectWeek.fragmentReceiveMsg(subject);
    }



}
