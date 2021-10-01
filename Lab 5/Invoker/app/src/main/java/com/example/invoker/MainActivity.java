package com.example.invoker;


import androidx.appcompat.app.AppCompatActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start service
        Intent i = new Intent();
        i.setComponent(new ComponentName("com.example.creator", "com.example.creator.MyService"));
        startService(i);
//
//        // Get SSID from external storage
        try {

            // Filestream stuff
            FileInputStream fileInputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory().toString(), "ssid.txt"));
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));

            // Strings fro building
            String ssid;
            String myData = "";

            // Reading file
            while ((ssid = bufferedReader.readLine()) != null){
                myData = myData + ssid;
            }
            dataInputStream.close();

            // SSID in toast
            Toast.makeText(this, myData, Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get location from external storage
//        try {
//
//            // Filestream stuff
//            FileInputStream fileInputStream = new FileInputStream(new File(Environment.getExternalStorageDirectory().toString(), "location.txt"));
//            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
//
//            // Strings fro building
//            String location;
//            String myData = "";
//
//            // Reading file
//            while ((location = bufferedReader.readLine()) != null){
//                myData = myData + location;
//            }
//            dataInputStream.close();
//
//            // location in toast
//            Toast.makeText(this, myData, Toast.LENGTH_LONG).show();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}