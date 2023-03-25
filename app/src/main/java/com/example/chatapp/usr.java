package com.example.chatapp;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class usr {
    static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    static FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    static String name;
    static String uid;
    static String phnum;

    public static void setUser(){
        usr.uid = user.getUid();
        fdb.getReference().child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    System.out.println(snapshot1.getKey());
                    if(snapshot1.getKey() == "Name"){
                        usr.name = snapshot1.getValue().toString();
                    }
                    else{
                        usr.phnum = snapshot1.getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        System.out.println(usr.name+usr.uid+usr.phnum);
    }
    public static String getUid(){
        return usr.uid;
    }
    public static String getName() {
        return usr.name;
    }
    public static String getPhone(){
        return usr.phnum;
    }

}
