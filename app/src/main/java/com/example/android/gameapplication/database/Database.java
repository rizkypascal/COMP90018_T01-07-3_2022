package com.example.android.gameapplication.database;
import android.util.Log;

import androidx.annotation.NonNull;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.*;



public class Database {
    private static final String TAG = "Database";
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();



    public Database() {
        // activate the listener
        addlistListener();
        addpassListener("a");
        addmonsterListener("subject1","week1");
        addboardListener("subject1","week1");

    }
    private ArrayList<String> monsters = new ArrayList<String>();
    private void addmonsterListener(String subject, String week) {
        // [START post_value_event_listener]
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                monsters = (ArrayList<String>) snapshot.child("monster").child(subject).child(week).getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });

        //mpassReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
    }
    // this method will return all the monster's positions and type
    public ArrayList<String> getMonsters(String subject, String week){

        addmonsterListener(subject, week);

        return monsters;
    }




    private ArrayList<String> boards = new ArrayList<String>();
    private void addboardListener(String subject, String week) {
        // [START post_value_event_listener]
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boards = (ArrayList<String>) snapshot.child("board").child(subject).child(week).getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });

        //mpassReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
    }



    // this method will return all the board's positions
    public ArrayList<String> getBoards(String subject, String week){

        addboardListener(subject, week);

        return boards;
    }


    // all the following methods need to be re-witten/modified
    //You may modify them in this file, or implement them in another
    // the temppassword is once we get the user's password from firebase
    // basic logic is similar
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
        // change from addlist
        //mpassReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
    }


    public void writeNewUser(String username, String password) {
        // this method for update user information to firebase
        users.add(username);
        // set value for the list of username and the users password
        mDatabase.child("users").setValue(users);
        mDatabase.child("password").child(username).setValue(password);
    }


    // init a user list to store all registered usernames.
    private ArrayList<String> users = new ArrayList<String>();
    // codes form firebase guideline with some change, add listener at beginning
    private void addlistListener() {
        // [START value_event_listener]
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // store the information from firebase
                users = (ArrayList<String>) snapshot.child("users").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // error log
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
        // do not need any more
        //mlistReference.addValueEventListener(postListener);
        // [END post_value_event_listener]
    }
    // function that can check if the username match the password
    public boolean UsernameMatchPassword(String username, String password){
        addpassListener(username);
        addlistListener();
        // just for test
        //addmonsterListener("subject1","week1");
        //addboardListener("subject1","week1");
        //System.out.println("monster:"+monsters);
        //System.out.println("board:"+boards);
        if (users.contains(username)) {
            if (password.equals(temppassword)) {
                // only work when we have the user can the password is correct
                return true;
            } else {
                return false;
            }
        }
        else {
            return false;
        }
        // check if username exist, if password correct. return t/f.
    }

    public boolean CheckAddNewAccount(String username, String password){
        addlistListener();
        if (users.contains(username)){
            // if the username is already in the firebase, we tell the user it not work
            return false;
        }
        else{
            // if we do not have the username, help the user register
            writeNewUser(username,password);
            return true;
        }
        // XQ check if the username unique. add it to db and return true if unique
        // otherwise return false.
    }

}
