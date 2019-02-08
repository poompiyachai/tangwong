package com.example.krisorn.tangwong;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class notification extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private String id="2";

    private FirebaseAuth mAuth;
    public DatabaseReference nameCard;
    private String iduser;
    private String uid;
    private Spinner name;
    private  List<String> namelist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_notification);
        mAuth = FirebaseAuth.getInstance();



        name = (Spinner) findViewById(R.id.list);




        final FirebaseUser user = mAuth.getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        uid = user.getUid ();
/*        mDatabase.child ("asd").setValue ("asd");
        mDatabase.child ("asd").setValue ("ddsa");*/
        mDatabase = FirebaseDatabase.getInstance().getReference();
        nameCard =database.getReference();
        name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                iduser = String.valueOf (position);
                Log.d("aaaaaaaaa",iduser);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        nameCard.addValueEventListener (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                id = dataSnapshot.child ("user").child (uid).child ("livenow").getValue (String.class);

                int loop = (int) dataSnapshot.child ("room").child (id).child ("people_live").getChildrenCount ();
                Log.d("follows",String.valueOf (loop));
                namelist.add(" ");
                for (int i=1;i<=loop;i++)
                {
                    String tempuid = dataSnapshot.child ("room").child (id).child ("people_live").child (String.valueOf (i)).child ("uid").getValue (String.class);
                    String tempname = dataSnapshot.child ("user").child (tempuid).child ("name").getValue (String.class);
                    namelist.add(String.valueOf (i)+" "+tempname);
                }


                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(notification.this, android.R.layout.simple_spinner_item, namelist);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                name.setAdapter(dataAdapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
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

            nameCard.addListenerForSingleValueEvent (new ValueEventListener () {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //final FirebaseUser user = mAuth.getCurrentUser ();
                    //String uid = user.getUid ();
                    // String iduser = String.valueOf(name.getId ());
                    EditText roomq = findViewById (R.id.q);
                    EditText message = findViewById (R.id.text);

                    Log.d("aasd",dataSnapshot.child ("room").child (id).child ("q").child (roomq.getText ().toString ()).child ("noti_status").getValue ((String.class)));

                    if (dataSnapshot.child ("room").child (id).child ("q").child (roomq.getText ().toString ()).child ("noti_status").getValue ((String.class)).equals ("1")) {
                        //roomq = findViewById(R.id.roomid).toString ();
                     /*   String tempuid = dataSnapshot.child ("room").child (id).child ("people_live").child (roomq.getText ().toString ()).child ("uid").getValue ((String.class));

                        Log.d ("aasd", tempuid);
                        mDatabase.child ("room").child (id).child ("q").child (roomq.getText ().toString ()).child ("text").setValue (message.getText ().toString ());
                        mDatabase.child ("user").child (tempuid).child ("notification").child ("status").setValue ("1");
                        mDatabase.child ("user").child (tempuid).child ("notification").child ("text").setValue (message.getText ().toString ());
                        mDatabase.child ("user").child (tempuid).child ("notification").child ("room").setValue (id);*/
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }

}