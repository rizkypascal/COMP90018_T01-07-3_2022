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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.android.gameapplication.database.Database;
import com.example.android.gameapplication.generated.callback.OnClickListener;
import com.google.android.material.tabs.TabLayout;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class UserFragment extends Fragment {


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
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        activity = (MainActivity) getActivity();
        context = activity.getApplicationContext();

        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.sign_in)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.sign_up)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getChildFragmentManager(),getActivity(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                               @Override
                                               public void onTabSelected(TabLayout.Tab tab) {
                                                   viewPager.setCurrentItem(tab.getPosition());
                                               }

                                               @Override
                                               public void onTabUnselected(TabLayout.Tab tab) {

                                               }

                                               @Override
                                               public void onTabReselected(TabLayout.Tab tab) {

                                               }
                                           });

        tabLayout.setTranslationY(300);
        tabLayout.setAlpha(v);
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();


        database = new Database();
        //TODO: XUEQING may need to re-initiate
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("hello world");
    }

    //@OnClick(R.id.signInButton)
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




}
