package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signupActivity extends AppCompatActivity {

    private EditText name,mail,phnumber,password;
    private Button btn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase fdb;
    private FirebaseUser fusr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name = findViewById(R.id.Name);
        mail = findViewById(R.id.Mail);
        phnumber = findViewById(R.id.phnumber);
        password = findViewById(R.id.passwd);
        btn = findViewById(R.id.btn);

        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseDatabase.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nametxt = name.getText().toString();
                String mailtxt = mail.getText().toString();
                String phtxt = phnumber.getText().toString();
                String passtxt = password.getText().toString();

                if(TextUtils.isEmpty(nametxt) || TextUtils.isEmpty(mailtxt) || TextUtils.isEmpty(phtxt) || TextUtils.isEmpty(passtxt) ){
                    Toast.makeText(signupActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    signUp(mailtxt,passtxt,nametxt,phtxt);
                }
            }
        });
    }
    private void signUp(String mailtxt, String passtxt, String nametxt, String phtxt){
        mAuth.createUserWithEmailAndPassword(mailtxt,passtxt).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    continuetodb(nametxt,phtxt);
                }
                else{
                    Toast.makeText(signupActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void continuetodb(String nametxt, String phtxt){

        HashMap<String,String> data = new HashMap<String, String>();
        data.put("Name",nametxt);
        data.put("Phone Number",phtxt);
        fdb.getReference().child(mAuth.getUid()).setValue(data);
        fdb.getReference().child("Users").child(mAuth.getUid()).setValue(nametxt);
        startActivity(new Intent(signupActivity.this, ActivityTwo.class));
    }


}