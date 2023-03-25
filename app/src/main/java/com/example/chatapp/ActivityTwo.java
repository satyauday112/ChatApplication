package com.example.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityTwo extends AppCompatActivity {

    private ListView lview;
    ArrayAdapter<String> adapter;
    ArrayList<String> names;
    ArrayList<String> uid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        lview = findViewById(R.id.lview);
        updateData();
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ActivityTwo.this,chat.class);
                intent.putExtra("Name",names.get(position));
                intent.putExtra("UID",uid.get(position));
                startActivity(intent);
            }
        });
    }
    public void updateData() {
            FirebaseDatabase fdb = FirebaseDatabase.getInstance();
            names = new ArrayList<>();
            uid = new ArrayList<>();
            adapter = new ArrayAdapter<>(this, R.layout.userviewholder, names);
            lview.setAdapter(adapter);
            fdb.getReference().child("Users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        adapter.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            names.add((String) snapshot1.getValue());
                            uid.add(snapshot1.getKey());
                            System.out.println(usr.getUid());
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }