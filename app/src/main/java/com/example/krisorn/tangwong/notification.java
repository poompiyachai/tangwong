package com.example.krisorn.tangwong;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class notification extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String id="2";

    private FirebaseAuth mAuth;
    public DatabaseReference nameCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_notification);

    }

    public void click(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if(view.getId()==R.id.setnoti){
            EditText roomq = findViewById (R.id.q);
            mDatabase.child ("room").child (id).child ("q").child (roomq.getText ().toString ()).child ("noti_status").setValue ("1");
            FirebaseDatabase database2 = FirebaseDatabase.getInstance();


            //nameCard =database.getReference();
            mDatabase.child("eiei").setValue("asdasd");
            mDatabase.child("eiei").setValue("dsacfvf");
            //mDatabase.child ("room").child (id).child ("q").child (roomq).child ("uid").setValue ("0");


            nameCard = database.getReference();

            ValueEventListener valueEventListener = nameCard.addValueEventListener (new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //final FirebaseUser user = mAuth.getCurrentUser ();
                    //String uid = user.getUid ();
                    EditText roomq = findViewById (R.id.q);
                    EditText message = findViewById (R.id.text);

                    Log.d("aasd",dataSnapshot.child ("room").child (id).child ("q").child (roomq.getText ().toString ()).child ("noti_status").getValue ((String.class)));

                    if (dataSnapshot.child ("room").child (id).child ("q").child (roomq.getText ().toString ()).child ("noti_status").getValue ((String.class)).equals ("1")) {
                        //roomq = findViewById(R.id.roomid).toString ();
                        String tempuid = dataSnapshot.child ("room").child (id).child ("q").child (roomq.getText ().toString ()).child ("uid").getValue ((String.class));

                        Log.d ("aasd", tempuid);
                        mDatabase.child ("room").child (id).child ("q").child (roomq.getText ().toString ()).child ("text").setValue (message.getText ().toString ());
                        mDatabase.child ("user").child (tempuid).child ("notification").setValue ("1");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

}
