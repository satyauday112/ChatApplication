package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chat extends AppCompatActivity {

    FirebaseDatabase fdb;
    FirebaseAuth mAuth;
    FirebaseUser usr;
    private TextView header;
    private ListView lstview;
    private EditText msg;
    private Button btn;
    private String uid;
    private String receiver;
    private ArrayList<String> msgs;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        header = findViewById(R.id.header);
        lstview = findViewById(R.id.chat);
        msg = findViewById(R.id.msg);
        btn = findViewById(R.id.sendBtn);
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        usr = mAuth.getCurrentUser();
        msgs = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.sentmsg, msgs);
        lstview.setAdapter(adapter);
        receiver = getIntent().getStringExtra("Name");
        uid = getIntent().getStringExtra("UID");
        header.setText(receiver);
        fdb.getReference().child(usr.getUid()).child("chat").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("In snapsnot");
                if (snapshot.exists()) {
                    System.out.println("ENter snapsnot");
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        System.out.println("Received from Database: "+snapshot1.getValue());
                        msgs.add((String) snapshot1.getValue());
                        fdb.getReference().child(usr.getUid()).child("chat").child(uid).removeValue();
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });

    }

    private void sendMsg() {
        String message = msg.getText().toString();
        if(!message.isEmpty()){
            boolean sent;
            fdb.getReference().child(uid).child("chat").child(usr.getUid()).push().setValue(message).isSuccessful();
//            System.out.println("SENT: "+sent);
            msgs.add(message);
            msg.setText("");
            adapter.notifyDataSetChanged();
        }
    }
}