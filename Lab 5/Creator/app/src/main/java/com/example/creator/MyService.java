package com.example.creator;

import static android.os.Environment.getExternalStorageState;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class MyService extends IntentService {

    public MyService(){
        super("MyService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Get wifi info; extract SSID
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();

        // Write SSID to external storage
        String extStorageState = getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory().toString(), "ssid.txt"));
                fileOutputStream.write(ssid.getBytes(StandardCharsets.UTF_8));
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    @SuppressLint("MissingPermission")
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        // Get wifi info; extract SSID
//        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//        String ssid = wifiInfo.getSSID();
//
//        // Display SSID in a Toast
//        // Toast.makeText(this, SSID, Toast.LENGTH_LONG).show();
//
//        // Write SSID to external storage
//        // String extStorageState = getExternalStorageState();
//        // if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
//        //     try {
//        //         FileOutputStream fileOutputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory().toString(), "ssid.txt"));
//        //         fileOutputStream.write(ssid.getBytes(StandardCharsets.UTF_8));
//        //         fileOutputStream.close();
//        //     } catch (IOException e) {
//        //         e.printStackTrace();
//        //     }
//        // }
//
//        // Get location
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        LocationListener locationListener = new MyLocationListner();
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
//
//
//        // Write location to external storage
//        String extStorageState = getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
//            try {
//                FileOutputStream fileOutputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory().toString(), "location.txt"));
//                fileOutputStream.write(locationListener.getLon().getBytes(StandardCharsets.UTF_8));
//                fileOutputStream.write(locationListener.getLat().getBytes(StandardCharsets.UTF_8));
//                fileOutputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return Service.START_NOT_STICKY;
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//
//
//    public class MyLocationListner implements LocationListener{
//
//        public double lon;
//        public double lat;
//
//        public double getLon(){
//            return lon;
//        }
//
//        public double getLat(){
//            return lat;
//        }
//
//        @Override
//        public void onLocationChanged(@NonNull Location location) {
//            lon = location.getLongitude();
//            lat = location.getLatitude();
//        }
//
//        @Override
//        public void onLocationChanged(@NonNull List<Location> locations) {
//
//        }
//
//        @Override
//        public void onFlushComplete(int requestCode) {
//
//        }
//
//        @Override
//        public void onProviderEnabled(@NonNull String provider) {
//
//        }
//
//        @Override
//        public void onProviderDisabled(@NonNull String provider) {
//
//        }
//    }
}