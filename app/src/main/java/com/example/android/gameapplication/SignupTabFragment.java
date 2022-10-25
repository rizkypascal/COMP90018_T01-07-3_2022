package com.example.android.gameapplication;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.android.gameapplication.database.Database;
import com.google.android.material.tabs.TabLayout;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author Tony Shu
 * @date 25/10/2022
 * @desc
 */
public class SignupTabFragment extends Fragment {
    //private Unbinder unbinder;
    //@BindView(R.id.signInButton)
    Button signInButton;
    //@BindView(R.id.signUpButton)
    Button signUpButton;

    private EditText signInNameValue, signInPasswordValue, signUpNameValue, signUpPasswordValue0, signUpPasswordValue1;
    private TextView textLoginInfo;

    private MainActivity activity;
    private Context context;

    int duration = Toast.LENGTH_SHORT;

    // set variables, interface for communication between activity & fragment, fragment & fragment
    private String user_name="";
    private GameFragment.SendMessages sendMessages;
    private Database database;

    TabLayout tabLayout;
    ViewPager viewPager;
    float v = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);
//        signInNameValue = view.findViewById(R.id.signInNameValue);
//        signInPasswordValue = view.findViewById(R.id.signInPasswordValue);
        signUpNameValue = view.findViewById(R.id.signUpNameValue);
        signUpPasswordValue0 = view.findViewById(R.id.signUpPasswordValue0);
        signUpPasswordValue1 = view.findViewById(R.id.signUpPasswordValue1);
        //signInButton = (Button) view.findViewById(R.id.signInButton);
        database = new Database();
        signUpButton = (Button) view.findViewById(R.id.signUpButton);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpButtonOnClick();
            }
        });
        //unbinder = ButterKnife.bind(this, view);
        activity = (MainActivity) getActivity();
        context = activity.getApplicationContext();
        if (user_name!="") {
            textLoginInfo.setText(getString(R.string.sign_in_as)+" "+user_name);
        }


        //TODO: XUEQING may need to re-initiate
        return view;
    }

    void SignInButtonOnClick(){
        Log.d("UserFragment", "signInButton clicked.");
        String username_temp = signInNameValue.getText().toString();
        String password_temp = signInPasswordValue.getText().toString();
        try {
            if (CheckLogin(username_temp, password_temp)){
                MediaPlayer mp = MediaPlayer.create(activity, R.raw.vista);
                mp.start();
                user_name = username_temp;
                sendMessages.iAmMSG(user_name);
                UserFragmentAfterLogin userFragment = new UserFragmentAfterLogin();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.layout_fragment, userFragment)
                        .addToBackStack(null)
                        .commit();
                userFragment.fragmentReceiveMsg(user_name);
            }else {
                MediaPlayer mp = MediaPlayer.create(activity, R.raw.erro);
                mp.start();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //@OnClick(R.id.signUpButton)
    void SignUpButtonOnClick(){
        Log.d("UserFragment", "signUpButton clicked.");
        String username_temp = signUpNameValue.getText().toString();
        String password_temp0 = signUpPasswordValue0.getText().toString();
        String password_temp1 = signUpPasswordValue1.getText().toString();
        if (CheckRegister(username_temp, password_temp0, password_temp1)){
            MediaPlayer mp = MediaPlayer.create(activity, R.raw.winxp);
            mp.start();
        }else {
            MediaPlayer mp = MediaPlayer.create(activity, R.raw.erro);
            mp.start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }

    boolean CheckLogin(String user_name, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Log.d("UserFragment", "print out username+password: "+user_name+" "+password);
        if (user_name.length()==0){
            PopToast(getString(R.string.username_empty));
            return false;
        }
        else if (password.length()==0){
            PopToast(getString(R.string.password_empty));
            return false;
        }
        else{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(password.getBytes("UTF-8"));
            String encodePassword = byte2Hex(messageDigest.digest());
            Log.d("password", encodePassword+" "+password);
            if (database.UsernameMatchPassword(user_name, password)) {
                PopToast(getString(R.string.login_as)+user_name);
                Log.d("UserFragment","password username matches");
                return true;
            }
            PopToast(getString(R.string.something_wrong));
            return false;
        }
    }

    boolean CheckRegister(String user_name, String password0, String password1){
        if (user_name.length()==0){
            PopToast(getString(R.string.username_empty));
            return false;
        }
        else if (password0.length()==0){
            PopToast(getString(R.string.password_empty));
            return false;
        }
        else if (password1.length()==0){
            PopToast(getString(R.string.confirm_empty));
            return false;
        }
        else if(!Objects.equals(password0,password1)){
            PopToast(getString(R.string.not_identical));
            return false;
        }
        else{
            try{
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
                messageDigest.update(password0.getBytes("UTF-8"));
                String encodePassword = byte2Hex(messageDigest.digest());
                Log.d("password", encodePassword+" "+password0);
                if (database.CheckAddNewAccount(user_name, password0)) {
                    PopToast(getString(R.string.sign_up_successful));
                    return true;
                }
                PopToast(getString(R.string.username_exists));
                return false;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return false;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            }
        }
    }


    void PopToast(String text){
        Toast toast = Toast.makeText(context, null, duration);
        toast.setText(text);
        toast.show();
    }

    public static String byte2Hex(byte[] bytes) {
        char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
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
        Log.d("UserFragment", "receive msg: "+msg);
        user_name = msg;
    }
}
