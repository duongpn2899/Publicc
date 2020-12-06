package com.example.activity1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private TextView register;
    private FirebaseAuth mAuth;
    private Button login;
    private EditText text_email,text_password;
    private FirebaseDatabase database;
    private DatabaseReference playerRef;
    private String playerName="";
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        login=findViewById(R.id.signIn);
        login.setOnClickListener(this);
        text_email=findViewById(R.id.email_address);
        text_password=findViewById(R.id.password_login);
        database=FirebaseDatabase.getInstance();
        SharedPreferences preferences=getSharedPreferences("Press",0);
        progressBar=findViewById(R.id.simpleProgressBar);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signIn:
                login();
                break;
        }
    }

    private void login() {
        String email=text_email.getText().toString().trim();
        String password=text_password.getText().toString().trim();
        if(email.isEmpty()) {
            text_email.setError("Email is required!");
            text_email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            text_email.setError("Please provide valid email");
            text_email.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            text_password.setError("Password is required!");
            text_password.requestFocus();
            return;
        }
        if(password.length() <6) {
            text_password.setError("Min password leng is 6 characters");
            text_password.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    startActivity(new Intent(Login.this,Home.class));
                }else {
                    Toast.makeText(Login.this,"Failed to login! Please check your credentials!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}