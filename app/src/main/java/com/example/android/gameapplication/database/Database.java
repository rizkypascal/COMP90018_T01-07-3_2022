package com.example.android.gameapplication.database;
import android.util.Log;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;


import java.util.*;



public class Database {
    private static final String TAG = "Database";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();



    public Database() {
        addlistListener();
        addpassListener("a");
    }


    //TODO: XQ all the following methods need to be re-witten/modified
    //You may modify them in this file, or implement them in another

    private String temppassword = new String("1");
    private void addpassListener(String username) {
        // [START post_value_event_listener]
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                temppassword = (String) snapshot.child("password").child(username).getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });

        //mpassReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
    }




    public void writeNewUser(String username, String password) {



        users.add(username);


        mDatabase.child("users").setValue(users);



        mDatabase.child("password").child(username).setValue(password);
    }



    private ArrayList<String> users = new ArrayList<String>();




    private void addlistListener() {
        // [START post_value_event_listener]
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users = (ArrayList<String>) snapshot.child("users").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });

        //mlistReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
    }

    public boolean UsernameMatchPassword(String username, String password){



        addpassListener(username);
        addlistListener();

        if (users.contains(username)) {
            if (password.equals(temppassword)) {

                return true;
            } else {

                return false;
            }
        }
        else {

            return false;
        }
        //TODO: XQ check if username exist, if password correct. return t/f.
    }

    public boolean CheckAddNewAccount(String username, String password){

        addlistListener();

        if (users.contains(username)){
            return false;
        }
        else{
            writeNewUser(username,password);
            return true;

        }






        // TODO: XQ check if the username unique. add it to db and return true if unique
        // otherwise return false.
    }

}
