package com.example.lab6;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

import javax.crypto.KeyGenerator;

public class Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void createAccount(View v) throws CertificateException, NoSuchAlgorithmException, IOException, KeyStoreException, NoSuchProviderException, InvalidAlgorithmParameterException {
        EditText username = (EditText) findViewById(R.id.txtUsername);
        EditText password = (EditText) findViewById(R.id.txtPassword2);

        try {

            // Store username/password in Android account manager
            Account account = new Account(username.getText().toString(), "com.example.lab6.user");
            AccountManager accountManager = AccountManager.get(this);
            accountManager.addAccountExplicitly(account, password.getText().toString(), null);

            // Create encryption key for user
            // Store encryption key in Android KeyStore
            KeyGenParameterSpec keySpec = new KeyGenParameterSpec
                    .Builder(username.getText().toString(),
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setUserAuthenticationRequired(true)
                    .setUserAuthenticationParameters(120, KeyProperties.AUTH_DEVICE_CREDENTIAL)
                    .setKeySize(128)
                    .build();
            KeyGenerator kg = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            kg.init(keySpec);
            kg.generateKey();

        // Call Activity 3
        Intent act3 = new Intent(this, Activity3.class);
        act3.putExtra("username", username.getText().toString());
        startActivity(act3);

        } catch(java.security.InvalidAlgorithmParameterException e){
            Toast.makeText(this, "Configure an authentication method on your device!", Toast.LENGTH_LONG).show();
        }
    }
}