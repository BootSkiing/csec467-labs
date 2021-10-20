package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
    }

    public void createAccount(View v){
        TextView username = (TextView) findViewById(R.id.txtUsername);
        TextView password = (TextView) findViewById(R.id.txtPassword2);

        // Store username/password in Android account manager
        // Create encryption key for user
        // Store encryption key in Android KeyStore
        // Call Activity 3
    }
}