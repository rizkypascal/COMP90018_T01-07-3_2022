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

    }


    //TODO: XQ all the following methods need to be re-witten/modified
    //You may modify them in this file, or implement them in another

    private String temppassword = new String("1");
    private void addpassListener(DatabaseReference mpassReference, String username) {
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                temppassword = (String) dataSnapshot.child("password").child(username).getValue();
                // ..

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println("faildata"+temppassword);
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mpassReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
    }




    public void writeNewUser(String username, String password) {



        users.add(username);


        mDatabase.child("users").setValue(users);



        mDatabase.child("password").child(username).setValue(password);
    }



    private ArrayList<String> users = new ArrayList<String>();




    private void addlistListener(DatabaseReference mlistReference) {
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                users = (ArrayList<String>) dataSnapshot.child("users").getValue();
                // ..

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mlistReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
    }

    public boolean UsernameMatchPassword(String username, String password){



        addpassListener(mDatabase,username);
        addlistListener(mDatabase);
        System.out.println("run"+temppassword);
        System.out.println("r"+username);
        System.out.println("y"+users);
        if (users.contains(username)) {
            if (password.equals(temppassword)) {
                System.out.println("success"+temppassword);
                return true;
            } else {
                System.out.println("fail"+password);
                return false;
            }
        }
        else {
            System.out.println("fail1"+password);
            return false;
        }
        //TODO: XQ check if username exist, if password correct. return t/f.
    }

    public boolean CheckAddNewAccount(String username, String password){

        addlistListener(mDatabase);

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
