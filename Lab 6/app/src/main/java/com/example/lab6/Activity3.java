package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Activity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
    }

    public void createFile(View v){
        TextView fileName = (TextView) findViewById(R.id.txtFilename);
        TextView data = (TextView) findViewById(R.id.txtData);

        // Fetch key from keystore
            // User authenticates
        // Encrypt file
        // Save file
        // Clear data field in UI
    }

    public void showFile(View v){
        TextView fileName = (TextView) findViewById(R.id.txtFilename);

        // Get file by name
        // Decrypt file with key from keystore
            // User authenticates
        // Display decrypted data in data field of UI
    }

    public void generateKey(View v){
        // Generate new key
        // Replace old key in keystore with new key
    }
}