package com.example.creator;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags,  int startId){
        // Define notification ID
        String NOTIFICATION_CHANNEL_ID = "com.example.creator";

        // Create and start foreground service notification
//        Intent notifIntent = new Intent(this, MainActivity.class); // Context is null...?
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifIntent, 0);
//        Notification notification = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID).setContentTitle("Notification Title").setContentText("Check out my notif").setContentIntent(pendingIntent).build();
//        startForeground(1, notification);

        // Get wifi info; extract SSID
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();

        // Display SSID in a Toast
        Toast.makeText(this, ssid, Toast.LENGTH_LONG).show();

        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}