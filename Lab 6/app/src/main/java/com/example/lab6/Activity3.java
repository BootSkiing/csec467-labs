package com.example.lab6;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.security.keystore.UserNotAuthenticatedException;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.concurrent.Executor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.biometric.BiometricPrompt.PromptInfo;
import androidx.core.content.ContextCompat;

public class Activity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
    }

    public void authenticate(View v){
        // Authenticate User
        Executor executor = ContextCompat.getMainExecutor(this);
        PromptInfo details = new PromptInfo.Builder()
                .setTitle("Lab 6 Authenticator")
                .setSubtitle("Please authenticate yourself to proceed")
                .setAllowedAuthenticators(
                        BiometricManager.Authenticators.BIOMETRIC_STRONG |
                        BiometricManager.Authenticators.DEVICE_CREDENTIAL
                ).build();
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(Activity3.this, "Authentication Succeeded! You may now try again", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(Activity3.this, "Authentication Failed!", Toast.LENGTH_LONG).show();
            }
        });
        biometricPrompt.authenticate(details);
    }

    public void createFile(View v) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableEntryException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        EditText fileName = (EditText) findViewById(R.id.txtFilename);
        EditText data = (EditText) findViewById(R.id.txtData);
        String path = getFilesDir().toString();
        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");

        try {
            // Fetch key from keystore
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            SecretKey k = ((KeyStore.SecretKeyEntry) ks.getEntry(username, null)).getSecretKey();

            // Write/Encrypt file
            Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
            c.init(Cipher.ENCRYPT_MODE, k);
            byte[] iv = c.getIV();
            byte[] cipherText = c.doFinal(data.getText().toString().getBytes());
            //String path = getFilesDir().toString();
            FileOutputStream ctOut = new FileOutputStream(path + File.separator + fileName.getText().toString());
            for (Byte b : cipherText) {
                ctOut.write(b);
            }
            ctOut.close();

            // Save initialization vector to file (or other form of persistence)
            FileOutputStream ivOut = new FileOutputStream(path + File.separator + fileName.getText().toString() + "_iv");
            for (Byte b : cipherText) {
                ivOut.write(b);
            }
            ivOut.close();

            // Clear data field in UI
            data.setText("");

            // Success
            Toast.makeText(this, "File saved successfully!", Toast.LENGTH_LONG).show();
        } catch(UserNotAuthenticatedException e){
            // Authenticate User
            authenticate(v);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showFile(View v) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableEntryException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        EditText fileName = (EditText) findViewById(R.id.txtFilename);
        EditText data = (EditText) findViewById(R.id.txtData);
        String path = getFilesDir().toString();
        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");

        try{
            // Fetch key from keystore
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            SecretKey k = ((KeyStore.SecretKeyEntry) ks.getEntry(username, null)).getSecretKey();

            // Get IV
            byte[] iv;
            File ivFile = new File(path + File.separator + fileName.getText().toString() + "_iv");
            if (ivFile.exists()){
                Path p = Paths.get(path + File.separator + fileName.getText().toString() + "_iv");
                iv = Files.readAllBytes(p);
            } else {
                Toast.makeText(this, "ERROR: IV does not exist", Toast.LENGTH_SHORT).show();
                return;
            }

            // Get encrypted file
            byte[] cipherText;
            File cipherFile = new File(path + File.separator + fileName.getText().toString());
            if (cipherFile.exists()){
                Path p = Paths.get(path + File.separator + fileName.getText().toString());
                cipherText = Files.readAllBytes(p);
            } else {
                Toast.makeText(this, "ERROR: Cipher file doesn't exist", Toast.LENGTH_SHORT).show();
                return;
            }

            // Decrypt file
            GCMParameterSpec params = new GCMParameterSpec(128, iv);
            Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
            c.init(Cipher.DECRYPT_MODE, k, params);

            byte[] plainText = c.doFinal(cipherText);
            String plaintext = new String(plainText, "UTF-8");

            // Display decrypted data in data field of UI
            data.setText(plaintext);
        } catch(UserNotAuthenticatedException | InvalidAlgorithmParameterException e) {
            // Authenticate User
            authenticate(v);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void generateKey(View v) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        String path = getFilesDir().toString();

        // Replace old key in keystore with new key
        KeyGenParameterSpec keySpec = new KeyGenParameterSpec
                .Builder(username,
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

        Toast.makeText(this, "New key successfully generated!", Toast.LENGTH_LONG).show();
    }
}