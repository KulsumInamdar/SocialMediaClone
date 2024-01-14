package com.example.socialmediaclone;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class UserActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemCLickedInterfaced{

    MyRecyclerViewAdapter myRecyclerViewAdapter;
    RecyclerView recyclerViewUsers;
    public  static  String TAG = "UserActivity.java";
    ArrayList<ProfileObject> mUsersArrayList;
    ArrayList<String> followers, currentFollowers;
    String email;
    String currentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        recyclerViewUsers = findViewById(R.id.userRecyclerView);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        fillData();
    }

    private void fillData()
    {
        FirebaseDatabase.getInstance("https://clone-a8f7e-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference().child("user")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        mUsersArrayList = new ArrayList<>();
                        for(DataSnapshot childSnapshot: snapshot.getChildren())
                        {
                            HashMap<String, String> values = (HashMap<String, String>) childSnapshot.getValue();
                           // String key = childSnapshot.getKey();
                            String Name = values.get("Name");
                            Log.i(TAG, "Name "+Name);

                            Object isFollowing =  values.get("isFollowing");
                            followers = new ArrayList<>();
                            followers = (ArrayList)isFollowing;
                            Log.i(TAG, "followers "+followers);

                            String nameLower = Name.toLowerCase();
                            if(email.contains(nameLower))
                            {
                                currentFollowers = followers;
                                currentName = Name;
                            }

                            mUsersArrayList.add(new ProfileObject(Name,followers,email));
                        }

                        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(UserActivity.this));
                        myRecyclerViewAdapter = new MyRecyclerViewAdapter(UserActivity.this, mUsersArrayList, UserActivity.this, currentFollowers);
                        recyclerViewUsers.setAdapter(myRecyclerViewAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public void onItemCLicked(boolean isChecked, int position) {
        if(isChecked)
        {
            //update current followers list and add it in user followers list
            currentFollowers.add(myRecyclerViewAdapter.getName(position));
            FirebaseDatabase.getInstance("https://clone-a8f7e-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("user").child(currentName).child("isFollowing")
                    .setValue(currentFollowers);
        }
        else {
            currentFollowers.remove(myRecyclerViewAdapter.getName(position));
            FirebaseDatabase.getInstance("https://clone-a8f7e-default-rtdb.asia-southeast1.firebasedatabase.app/")
                    .getReference().child("user").child(currentName).child("isFollowing")
                    .setValue(currentFollowers);
        }

    }
}