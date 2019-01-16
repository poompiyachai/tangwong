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
    int i=0;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_create_event);



    }

    public void click(View view) {
        calendarView = (CalendarView) findViewById (R.id.calender);
        myDate2 = (TextView) findViewById (R.id.myDate);
        text = findViewById (R.id.text);

        calendarView.setOnDateChangeListener (new CalendarView.OnDateChangeListener () {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("aasd", String.valueOf (dataSnapshot.child("room").child(roomid).child("event").child (String.valueOf (i)).child("status").getValue (String.class).equals ("1")));
                while(true)
                {
                    if(!dataSnapshot.child("room").child(roomid).child("event").child (String.valueOf (i)).child("status").getValue (String.class).equals ("1"))
                    {
                        break;
                    }
                    i++;
                }

            }


            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                 date = (month+1)+"/" + dayOfMonth + "/" + year;

            }
        });


        if(view.getId()==R.id.create_event){

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
            });


            mDatabase = FirebaseDatabase.getInstance().getReference();
            Log.d("aasd", String.valueOf ("asd"));
            mDatabase.child("room").child(roomid).child("event").child (String.valueOf (i)).child("text").setValue(text.getText ().toString ());
            mDatabase.child("room").child(roomid).child("event").child (String.valueOf (i)).child("status").setValue("1");
            mDatabase.child("room").child(roomid).child("event").child (String.valueOf (i)).child("date").setValue(date);



            mDatabase.child ("asd").setValue ("asd");
            mDatabase.child ("asd").setValue ("ddsa");



        }



    }
}
