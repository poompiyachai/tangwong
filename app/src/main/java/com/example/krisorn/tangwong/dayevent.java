package com.example.krisorn.tangwong;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class dayevent extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String id="1";
    private int i=0;
    public DatabaseReference nameCard;
    private FirebaseAuth mAuth;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_dayevent);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid ();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nameCard =database.getReference();

        nameCard.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                id = dataSnapshot.child ("user").child (uid).child ("livenow").getValue (String.class);
              /* if(dataSnapshot.child ("room").child (id).child ("time_noti").child ("status") .getValue (String.class).equals ("1"))
                {
                    long num = dataSnapshot.child ("room").child (id).child ("people_live").getChildrenCount ();
                    for(long i=0;i<num;i++)
                    {
                        if(dataSnapshot.child ("room").child (id).child("people_live").child (Long.toString (i)).child ("uid").getValue (String.class)!=null)
                        {
                            String tempUid =  dataSnapshot.child ("room").child (id).child("people_live").child (Long.toString (i)).child ("uid").getValue (String.class);
                            String text = dataSnapshot.child ("room").child (id).child ("time_noti").child ("text") .getValue (String.class);
                            mDatabase.child ("user").child (tempUid).child ("time").child ("status") .setValue ("1");
                            mDatabase.child ("user").child (tempUid).child ("time").child ("text") .setValue (text);
                        }

                    }
                    mDatabase.child ("room").child (id).child ("time_noti").child ("status") .setValue ("0");
                }
                */

                i = (int) dataSnapshot.child ("room").child (id).child ("date_event").getChildrenCount ();








            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void click(View view) {



        if(view.getId()==R.id.set_){
            mDatabase.child ("asd").setValue ("asd");
            mDatabase.child ("asd").setValue ("ddsa");
            //Log.d("aasda", String.valueOf (i));
            mDatabase.child ("asd").setValue ("asd");
            mDatabase.child ("asd").setValue ("ddsa");



            Calendar c = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("HH");

            String H = df.format(c.getTime());


            SimpleDateFormat mm = new SimpleDateFormat("mm");

            String m = mm.format(c.getTime());
            SimpleDateFormat ss = new SimpleDateFormat("ss");

            String s = ss.format(c.getTime());

            Log.d("aasda", H);
            Log.d("aasda", m);
            Log.d("aasda", s);
            EditText hr = findViewById(R.id.hr_);
            EditText mi = findViewById(R.id.min_);
            EditText se = findViewById(R.id.sec_);
            EditText text = findViewById(R.id.text_);
            String time = hr+":"+mi+":"+se;

            long a = Integer.parseInt (hr.getText ().toString ());
            long aa = Integer.parseInt (H);
            aa=aa*3600;
            a=a*3600;

            long b = Integer.parseInt (mi.getText ().toString ());
            long bb = Integer.parseInt (m);
            b=b*60;
            bb=bb*60;

            long d = Integer.parseInt (se.getText ().toString ());
            long cc = Integer.parseInt (s);

            int asd = Integer.parseInt(String.valueOf (a+b+d));
            int asdasd = Integer.parseInt(String.valueOf (aa+bb+cc));
            int totaltime;
            if(asd-asdasd>0)
            {
                totaltime = asd-asdasd;
                mDatabase.child ("room").child (id).child ("date_event").child (String.valueOf (i)).child ("totaltime").setValue (totaltime);
                mDatabase.child ("room").child (id).child ("date_event").child (String.valueOf (i)).child ("time").setValue (time);
                mDatabase.child ("room").child (id).child ("date_event").child (String.valueOf (i)).child ("text").setValue (text.getText ().toString ());
            }
            else
            {
                totaltime = 0;
            }
            Log.d("aasdaa", String.valueOf (totaltime));

            /*new CountDownTimer (asd, 1000) {
                EditText text = findViewById(R.id.text);

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {

                    mDatabase.child ("room").child (id).child ("time_noti").child ("status") .setValue ("1");
                    mDatabase.child ("room").child (id).child ("time_noti").child ("text") .setValue (text.getText ().toString ());

                }
            }.start();
*//*
            Intent i = new Intent(this,UsersActivity.class);
            startActivity(i);*/




        }
    }

}
