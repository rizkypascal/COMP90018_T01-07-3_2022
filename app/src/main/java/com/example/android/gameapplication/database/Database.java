package com.example.android.gameapplication.database;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.android.gameapplication.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.ValueEventListener;

import java.util.*;


public class Database {
    private static final String TAG = "Database";
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public Database() {
        // activate the listener
        addlistListener();
        addpassListener();
        addmonster1Listener();
        addmonster2Listener();
        addscore1Listener();
        addscore2Listener();
    }

    // set up var
    private String tempscore = "0";
    private HashMap tempscoremap = new HashMap<>();
    private HashMap scoremap1 = new HashMap<>();
    private HashMap scoremap2 = new HashMap<>();

    public void addscore1Listener() {
        // [START post_value_event_listener]
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoremap1 = (HashMap) snapshot.child("subject1").getValue();
                //sort in a static map
                tempmap1(scoremap1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }

    private void addscore2Listener() {
        // [START post_value_event_listener]
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoremap2 = (HashMap) snapshot.child("subject2").getValue();
                tempmap2(scoremap2);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
    }

    // this method will return all the monster's positions and type
    public String getScore(String subject, String week, String username){
        if (subject.equals("subject1")) {
            // copy the newest map
            scoremap1 = subject1;
            tempscoremap = (HashMap) scoremap1.get(week);
            tempscore = (String) tempscoremap.get(username);
        }
        else{
            addscore2Listener();
            scoremap2 = subject2;
            tempscoremap = (HashMap) scoremap2.get(week);
            tempscore = (String) tempscoremap.get(username);
        }
        return tempscore;
    }

    public void updateScore(String subject, String week, String username, String scores){

        if(subject.length() != 0){
            if(week.length() != 0){
                if(username.length() != 0){
                    mDatabase.child(subject).child(week).child(username).setValue(scores);
                }
            }
        }
    }


    private HashMap monstermap2 = new HashMap<>();
    private HashMap monstermap1 = new HashMap<>();
    private ArrayList<String> monsters = new ArrayList<String>();

    private void addmonster2Listener() {
        // [START post_value_event_listener]
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                monstermap2 = (HashMap) snapshot.child("monster").child("subject2").getValue();
                tempmap4(monstermap2);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
        // [END post_value_event_listener]
    }

    private void addmonster1Listener() {
        // [START post_value_event_listener]
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                monstermap1 = (HashMap) snapshot.child("monster").child("subject1").getValue();
                tempmap3(monstermap1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
        // [END post_value_event_listener]
    }

    // this method will return all the monster's positions and type
    public ArrayList<String> getMonsters(String subject, String week){
        if (subject.equals("subject1")) {
            addmonster1Listener();
            monsters = (ArrayList<String>) monster1.get(week);
        }
        if (subject.equals("subject2")) {
            addmonster2Listener();
            monsters = (ArrayList<String>) monster2.get(week);

        }
        return monsters;
    }

    // all the following methods need to be re-witten/modified
    //You may modify them in this file, or implement them in another
    // the temppassword is once we get the user's password from firebase
    // basic logic is similar
    private String temppassword = "1";
    private Map<String,String> passwordmap = new HashMap<>();
    private void addpassListener() {
        // [START post_value_event_listener]
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                passwordmap = (Map<String, String>) snapshot.child("password").getValue();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });
        // [END post_value_event_listener]
    }

    private final String initscore = "0";

    public void writeNewUser(String username, String password) {
        // this method for update user information to firebase
        users.add(username);
        // set value for the list of username and the users password
        mDatabase.child("users").setValue(users);
        mDatabase.child("password").child(username).setValue(password);
        mDatabase.child("subject1").child("week1").child(username).setValue(initscore);
        mDatabase.child("subject1").child("week2").child(username).setValue(initscore);
        mDatabase.child("subject1").child("week3").child(username).setValue(initscore);
        mDatabase.child("subject1").child("week4").child(username).setValue(initscore);
        mDatabase.child("subject1").child("week5").child(username).setValue(initscore);
        mDatabase.child("subject1").child("week6").child(username).setValue(initscore);
        mDatabase.child("subject1").child("week7").child(username).setValue(initscore);
        mDatabase.child("subject1").child("week8").child(username).setValue(initscore);
        mDatabase.child("subject1").child("week9").child(username).setValue(initscore);
        mDatabase.child("subject1").child("week10").child(username).setValue(initscore);
        mDatabase.child("subject1").child("week11").child(username).setValue(initscore);
        mDatabase.child("subject1").child("week12").child(username).setValue(initscore);
        mDatabase.child("subject1").child("week13").child(username).setValue(initscore);
        mDatabase.child("subject1").child("week14").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week1").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week2").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week3").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week4").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week5").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week6").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week7").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week8").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week9").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week10").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week11").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week12").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week13").child(username).setValue(initscore);
        mDatabase.child("subject2").child("week14").child(username).setValue(initscore);
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
        // [END post_value_event_listener]
    }
    // function that can check if the username match the password

    public static HashMap subject1 = new HashMap<>();
    public static HashMap subject2 = new HashMap<>();
    public static HashMap monster1 = new HashMap<>();
    public static HashMap monster2 = new HashMap<>();

    public static void tempmap1(HashMap subjectscore) {
        subject1 = subjectscore;

    }
    public static void tempmap2(HashMap subjectscore) {
        subject2 = subjectscore;

    }
    public static void tempmap3(HashMap monster) {
        monster1 = monster;

    }
    public static void tempmap4(HashMap monster) {
        monster2 = monster;

    }

    public boolean UsernameMatchPassword(String username, String password){
        // activate listener
        addpassListener();
        addlistListener();
        addscore1Listener();
        addscore2Listener();
        addmonster1Listener();
        addmonster1Listener();
        temppassword = passwordmap.get(username);
        if (users.contains(username)) {

            if (password.equals(temppassword)) {
                tempmap1(scoremap1);
                tempmap2(scoremap2);
                tempmap3(monstermap1);
                tempmap4(monstermap2);
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
    }
}
