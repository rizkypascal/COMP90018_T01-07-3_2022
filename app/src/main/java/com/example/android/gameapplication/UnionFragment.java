package com.example.android.gameapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class UnionFragment extends Fragment {

    private Unbinder unbinder;

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
        user_name = msg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_union, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
