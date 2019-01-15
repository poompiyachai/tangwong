package com.example.krisorn.tangwong;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.lang.Integer.parseInt;

public class time extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String id="1";
    public DatabaseReference nameCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_time);

    }

    public void click(View view) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nameCard =database.getReference();

        nameCard.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if(dataSnapshot.child ("room").child (id).child ("time_noti").child ("status") .getValue (String.class).equals ("1"))
            {
                long num = dataSnapshot.child ("room").child (id).child ("people_live").getChildrenCount ();
                for(long i=0;i<num;i++)
                {
                    if(dataSnapshot.child ("room").child (id).child("people_live").child (Long.toString (i)).child ("uid").getValue (String.class)!=null)
                    {
                        String tempUid =  dataSnapshot.child ("room").child (id).child("people_live").child (Long.toString (i)).child ("uid").getValue (String.class);
                        String time = dataSnapshot.child ("room").child (id).child ("time_noti").child ("time").getValue  (String.class);
                        mDatabase.child ("user").child (tempUid).child ("time").child ("time") .setValue (time);
                        mDatabase.child ("user").child (tempUid).child ("time").child ("status") .setValue ("1");
                    }

                }
            }







            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        if(view.getId()==R.id.set){
            EditText hr = findViewById(R.id.hr);
            EditText mi = findViewById(R.id.min);
            EditText se = findViewById(R.id.sec);
            Calendar c = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

            String formattedDate = df.format(c.getTime());

            String settime = hr.getText().toString()+":"+mi.getText().toString()+":"+se.getText().toString();
            mDatabase.child ("room").child (id).child ("time_noti").child ("time") .setValue (settime);
            mDatabase.child ("room").child (id).child ("time_noti").child ("status") .setValue ("1");
            Log.d("aasd",settime);
            Log.d("aasd",formattedDate);
        }
    }

    private void showNotification(String text) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://devahoy.com/posts/android-notification/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("การเเจ้งเตือน")
                        .setContentText(text)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_SOUND)

                        .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1000, notification);


    }

}
