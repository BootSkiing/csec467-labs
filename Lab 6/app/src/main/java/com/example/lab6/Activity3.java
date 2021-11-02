package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Activity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
    }

    public void createFile(View v) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableEntryException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        EditText fileName = (EditText) findViewById(R.id.txtFilename);
        EditText data = (EditText) findViewById(R.id.txtData);
        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");

        // Fetch key from keystore
        KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
        ks.load(null);
        SecretKey k = ((KeyStore.SecretKeyEntry) ks.getEntry(username, null)).getSecretKey();

        // Write/Encrypt file
        Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
        c.init(Cipher.ENCRYPT_MODE, k); // !!!User not authenticated exception
        byte[] iv = c.getIV();
        byte[] cipherText = c.doFinal(data.getText().toString().getBytes());
        String path = getFilesDir().toString();
        FileOutputStream ctOut = new FileOutputStream(path + fileName.getText().toString());
        for(Byte b : cipherText){
            ctOut.write(b);
        }
        ctOut.close();

        // Save initialization vector to file (or other form of persistence)
        FileOutputStream ivOut = new FileOutputStream(path + fileName.getText().toString() + "_iv");
        for(Byte b : cipherText){
            ivOut.write(b);
        }
        ivOut.close();

        // Clear data field in UI
        data.setText("");
    }

    public void showFile(View v){
        EditText fileName = (EditText) findViewById(R.id.txtFilename);

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