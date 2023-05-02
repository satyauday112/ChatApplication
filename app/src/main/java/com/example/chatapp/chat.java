package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

public class chat extends AppCompatActivity {

    FirebaseDatabase fdb;
    FirebaseAuth mAuth;
    FirebaseUser usr;
    private TextView header;
    private EditText msg;
    private Button btn;
    private String uid;
    private String receiver;

    private RecyclerView rcview;
    private List<message> msgs;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        header = findViewById(R.id.header);
        rcview = findViewById(R.id.chat);
        msg = findViewById(R.id.msg);
        btn = findViewById(R.id.sendBtn);
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();
        usr = mAuth.getCurrentUser();
        msgs = new ArrayList<message>();
        adapter = new Adapter(getApplicationContext(),msgs);
        rcview.setLayoutManager(new LinearLayoutManager(this));
        rcview.setAdapter(adapter);
        receiver = getIntent().getStringExtra("Name");
        uid = getIntent().getStringExtra("UID");
        header.setText(receiver);
        fdb.getReference().child(usr.getUid()).child("chat").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("In snapsnot");
                if (snapshot.exists()) {
                    System.out.println("Entered snapsnot");
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        System.out.println("Received from Database: "+snapshot1.getValue());
                        msgs.add(new message((String) snapshot1.getValue(),false));
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
        String msgtext = msg.getText().toString();
        if(!msgtext.isEmpty()){
            boolean sent;
            fdb.getReference().child(uid).child("chat").child(usr.getUid()).push().setValue(msgtext).isSuccessful();
//            System.out.println("SENT: "+sent);
            msgs.add(new message(msgtext,true));
            msg.setText("");
            adapter.notifyDataSetChanged();
        }
    }
}