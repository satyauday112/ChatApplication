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

public class loginActivity extends AppCompatActivity {

    private EditText RegdNo, password;
    private FirebaseAuth mAuth;
    Button btn,btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        RegdNo = findViewById(R.id.RegdNo);
        password = findViewById(R.id.passwd);
        btn = findViewById(R.id.btn);
        btnSignup = findViewById(R.id.btnSignup);
        mAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regd = RegdNo.getText().toString();
                String passwd = password.getText().toString();
                if(TextUtils.isEmpty(regd) || TextUtils.isEmpty(passwd)){
                    Toast.makeText(loginActivity.this, "Enter Username and password", Toast.LENGTH_SHORT).show();
                }
                else{
                    login(regd,passwd);
                }
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this,signupActivity.class));
            }
        });
    }
    private void login(String regd, String passwd){
        mAuth.signInWithEmailAndPassword(regd, passwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            System.out.println("Success!!");
                            usr.setUser();
                            startActivity(new Intent(getApplicationContext(),ActivityTwo.class));
                            finish();

                        } else {
                            Toast.makeText(loginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
