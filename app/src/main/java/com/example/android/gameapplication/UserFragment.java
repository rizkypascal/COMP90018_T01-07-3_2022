package com.example.android.gameapplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class UserFragment extends Fragment {


    private Unbinder unbinder;
    @BindView(R.id.signInButton)
    Button signInButton;
    @BindView(R.id.signUpButton)
    Button signUpButton;

    private EditText signInNameValue, signInPasswordValue, signUpNameValue, signUpPasswordValue0, signUpPasswordValue1;
    private TextView textLoginInfo, unionInfo;

    private MainActivity activity;
    private Context context;

    int duration = Toast.LENGTH_SHORT;

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
        Log.d("UserFragment", "receive msg: "+msg);
        user_name = msg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        signInNameValue = view.findViewById(R.id.signInNameValue);
        signInPasswordValue = view.findViewById(R.id.signInPasswordValue);
        signUpNameValue = view.findViewById(R.id.signUpNameValue);
        signUpPasswordValue0 = view.findViewById(R.id.signUpPasswordValue0);
        signUpPasswordValue1 = view.findViewById(R.id.signUpPasswordValue1);
        textLoginInfo = view.findViewById(R.id.textLoginInfo);
        unionInfo = view.findViewById(R.id.unionInfo);
        unbinder = ButterKnife.bind(this, view);
        activity = (MainActivity) getActivity();
        context = activity.getApplicationContext();
        if (user_name!="") {
            textLoginInfo.setText("You have signed in as "+user_name);
            unionInfo.setText(GetUnionInfo(user_name));
        }
        return view;
    }

    @OnClick(R.id.signInButton)
    void SignInButtonOnClick(){
        Log.d("UserFragment", "signInButton clicked.");
        String username_temp = signInNameValue.getText().toString();
        String password_temp = signInPasswordValue.getText().toString();
        if (CheckLogin(username_temp, password_temp)){
            user_name = username_temp;
//            textLoginInfo.setText("You have signed in as "+user_name);
//
//            String union = GetUnion(user_name);
//            if (union!=""){
//                unionInfo.setText("You are a member of union: "+union);
//            }
            sendMessages.iAmMSG(user_name);
            UserFragmentAfterLogin userFragment = new UserFragmentAfterLogin();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layout_fragment, userFragment)
                    .addToBackStack(null)
                    .commit();
            userFragment.fragmentReceiveMsg(user_name);

        }
    }

    @OnClick(R.id.signUpButton)
    void SignUpButtonOnClick(){
        Log.d("UserFragment", "signUpButton clicked.");
        String username_temp = signUpNameValue.getText().toString();
        String password_temp0 = signUpPasswordValue0.getText().toString();
        String password_temp1 = signUpPasswordValue1.getText().toString();
        if (CheckRegister(username_temp, password_temp0, password_temp1)){
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    boolean CheckLogin(String user_name, String password){
        Log.d("UserFragment", "print out username+password: "+user_name+" "+password);
        if (user_name.length()==0){
            PopToast("Username is empty!");
            return false;
        }
        else if (password.length()==0){
            PopToast("Password is empty!");
            return false;
        }
        else{
            if (UsernameMatchPassword(user_name, password)) {
                PopToast("Successfully logged in as "+user_name);
                Log.d("UserFragment","password username matches");
                return true;
            }
            PopToast("Something wrong with username/password!");
            return false;
        }
    }

    boolean CheckRegister(String user_name, String password0, String password1){
        if (user_name.length()==0){
            PopToast("Username is empty!");
            return false;
        }
        else if (password0.length()==0){
            PopToast("Password is empty!");
            return false;
        }
        else if (password1.length()==0){
            PopToast("Confirmed password is empty!");
            return false;
        }
        else if(password0!=password1){
            PopToast("Password and confirmed password aren't identical!");
            return false;
        }
        else{
            if (CheckAddNewAccount(user_name, password0)) {
                PopToast("Successfully sign up!");
                return true;
            }
            PopToast("Username already exists!");
            return false;
        }
    }

    String GetUnionInfo(String username){
        if (GetUnion(username)=="") return "You are not a member of any union.";
        else return "You are a member of union: "+GetUnion(username);
    }

    void PopToast(String text){
        Toast toast = Toast.makeText(context, null, duration);
        toast.setText(text);
        toast.show();
    }



    //TODO: XQ all the following methods need to be re-witten/modified
    //You may modify them in this file, or implement them in another
    String GetUnion(String username){
        return "UnionTest0"; // TODO: XQ please return "" if the user hasn't joined as union.
    }

    boolean UsernameMatchPassword(String username, String password){
        return true;
        //TODO: XQ check if username exist, if password correct. return t/f.
    }

    boolean CheckAddNewAccount(String username, String password){
        return true;
        // TODO: XQ check if the username unique. add it to db and return true if unique
        // otherwise return false.
    }
}
