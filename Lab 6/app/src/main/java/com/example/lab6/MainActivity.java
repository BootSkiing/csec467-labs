package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void register(View v){
        //TextView username = (TextView) findViewById(R.id.txtUserName);
        //TextView password = (TextView) findViewById(R.id.txtPassword);

        // Activity 2
        Intent act2 = new Intent(this, Activity2.class);
        startActivity(act2);
    }

    public void login(View v){
        EditText username = (EditText) findViewById(R.id.txtUserName);
        EditText password = (EditText) findViewById(R.id.txtPassword);

        // get accounts from AccountManager
        AccountManager accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccountsByType("com.example.lab6.user");

        // Find account
        for(Account a : accounts){

            Log.w("USERNAME --->", a.name);   // Debugging

            if(a.name.equals(username.getText().toString())){

                // Compare passwords
                if(accountManager.getPassword(a).equals(password.getText().toString())){

                    // Activity 3
                    Intent act3 = new Intent(this, Activity3.class);
                    act3.putExtra("username", username.getText().toString());
                    startActivity(act3);
                } else{
                    // Wrong password
                    Toast.makeText(this, "Wrong Password", Toast.LENGTH_LONG).show();
                }
            }
        }

        // No account
        Toast.makeText(this, "No account with that username", Toast.LENGTH_LONG).show();
    }
}