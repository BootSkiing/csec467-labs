package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        Intent act2 = new Intent(this, Activity2.class);
        startActivity(act2);
    }

    public void login(View v){
        TextView username = (TextView) findViewById(R.id.txtUserName);
        TextView password = (TextView) findViewById(R.id.txtPassword);

        // get accounts from AccountManager
        AccountManager accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccounts();

        // Find account
        for(Account a : accounts){
            if(a.name.equals(username.toString())){

                // Compare passwords
                if(accountManager.getPassword(a).equals(password.toString())){

                    // Activity 3
                    Intent act3 = new Intent(this, Activity3.class);
                    startActivity(act3);
                }
            }
        }

        // No account or wrong password
        Toast.makeText(this, "Wrong username or password :(", Toast.LENGTH_LONG).show();
    }
}