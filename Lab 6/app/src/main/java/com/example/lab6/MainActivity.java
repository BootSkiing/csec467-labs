package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void register(View v){
        TextView username = (TextView) findViewById(R.id.txtUserName);
        TextView password = (TextView) findViewById(R.id.txtPassword);

        // Activity 2
    }

    public void login(View v){
        TextView username = (TextView) findViewById(R.id.txtUserName);
        TextView password = (TextView) findViewById(R.id.txtPassword);

        // get password from AccountManager
        // Compare passwords
            // If match, call Activity 3
            // If fail, error
    }
}