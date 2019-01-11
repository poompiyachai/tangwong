package com.example.krisorn.tangwong.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;



import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.krisorn.tangwong.MainActivity;
import com.example.krisorn.tangwong.R;
import com.example.krisorn.tangwong.UsersActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;





public class MyFirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        showNotification();
    }

    private void showNotification() {
        Log.d ("aaaa","clickASDACSDFS");
        Intent intent = new Intent(this, UsersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(this)
                .setSmallIcon (R.mipmap.ic_launcher_round)
                .setContentTitle ("ASDASD")
                .setContentText ("ASDASD")
                .setAutoCancel (true)
                .setContentIntent (pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService (NOTIFICATION_SERVICE);
        notificationManager.notify (0,builder.build ());






    }
}
