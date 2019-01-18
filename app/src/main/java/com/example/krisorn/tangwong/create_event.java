package com.example.krisorn.tangwong;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class create_event extends AppCompatActivity {
    public DatabaseReference nameCard;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    CalendarView calendarView;
    TextView myDate2;
    EditText text;
    String roomid="1";
    String date;
    boolean check = false;
    int i=0;
    int j=0;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_create_event);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child ("aa").setValue ("asd");
        mDatabase.child ("aa").setValue ("ddsa");

        nameCard =database.getReference();
        nameCard.addValueEventListener (new ValueEventListener () {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("aasd", String.valueOf (i));
                while(true)
                {
                    if(!dataSnapshot.child("room").child(roomid).child("event").child (String.valueOf (i)).hasChild("status"))
                    {
                        break;
                    }
                    i++;
                }


            }


            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = (month+1)+"/" + dayOfMonth + "/" + year;
                Log.d("aasd", date);

            }
        });


        calendarView = (CalendarView) findViewById (R.id.calender);
        calendarView.setOnDateChangeListener (new CalendarView.OnDateChangeListener () {



            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = (month+1)+"/" + dayOfMonth + "/" + year;
                Log.d("aasd", date);

            }
        });




    }

    public void click(View view) {
        calendarView = (CalendarView) findViewById (R.id.calender);
        myDate2 = (TextView) findViewById (R.id.myDate);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child ("aa").setValue ("asd");
        mDatabase.child ("aa").setValue ("ddsa");

        calendarView.setOnDateChangeListener (new CalendarView.OnDateChangeListener () {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("wrgs", String.valueOf (dataSnapshot.child("room").child(roomid).child("event").child (String.valueOf (i)).child("status").getValue (String.class).equals ("1")));


            }


            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                 date = (month+1)+"/" + dayOfMonth + "/" + year;
                Log.d("aasd", date);

            }
        });


        if(view.getId()==R.id.create_event){


     Log.d("aasd", String.valueOf ("asd"));
            Log.d("wrgs",date);

            text = findViewById (R.id.text);
            mDatabase.child("room").child(roomid).child("event").child (String.valueOf (i)).child("text").setValue(text.getText ().toString ());
            mDatabase.child("room").child(roomid).child("event").child (String.valueOf (i)).child("status").setValue("1");
            mDatabase.child("room").child(roomid).child("event").child (String.valueOf (i)).child("date").setValue(date);



            mDatabase.child ("asd").setValue ("asd");
            mDatabase.child ("asd").setValue ("ddsa");



        }

        else if(view.getId()==R.id.delete){

            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child ("ab").setValue ("asd");
            mDatabase.child ("ab").setValue ("ddsa");
            j=0;
            nameCard =database.getReference();
            mDatabase.child ("ab").addValueEventListener (new ValueEventListener () {
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int count = (int) dataSnapshot.child("room").child("1").child("event").getChildrenCount ();
                    Log.d("qwe", String.valueOf (count));
                    while(true)
                    {

                        if(dataSnapshot.child("room").child(roomid).child("event").hasChild (String.valueOf (j)))
                        {

                            //Log.d("aasd", dataSnapshot.child("room").child(roomid).child("event").child (String.valueOf (j)).child("date").getValue (String.class));
                            if(dataSnapshot.child("room").child(roomid).child("event").child (String.valueOf (j)).child("date").getValue (String.class).equals (date))
                            {
                                Log.d("aasd", "eiei");
                                check = true;
                                break;
                            }
                        }

                        if(j==(count))
                        {
                            break;
                        }
                        j++;


                    }

                }


                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });

/*
            mDatabase = FirebaseDatabase.getInstance().getReference();
            Log.d("aasd", String.valueOf ("asd"));
            if(check==true)
            {
                mDatabase.child("room").child(roomid).child("event").child (String.valueOf (i)).child("text").setValue(null);
                mDatabase.child("room").child(roomid).child("event").child (String.valueOf (i)).child("status").setValue(null);
                mDatabase.child("room").child(roomid).child("event").child (String.valueOf (i)).child("date").setValue(null);
                check=false;
            }




            mDatabase.child ("asd").setValue ("asd");
            mDatabase.child ("asd").setValue ("ddsa");


*/
        }



    }
}
