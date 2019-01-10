package com.example.krisorn.tangwong;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class addqActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText jroomid;
    private DatabaseReference mDatabase;
    private String livenow;
    private Long turnq;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addq);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        Log.d("addq","can create activity"+user.getUid());
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDatabase.child("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("click addq","livenow11111111111111");
                livenow=dataSnapshot.child("nowlive").getValue(String.class);
                Log.d("click addq","livenow "+livenow);

                try {
                    mDatabase.child("room").child(livenow).child("q").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            turnq = dataSnapshot.getChildrenCount()+1;
                            Log.d("click addq","cildentCont="+turnq.toString());
                            Log.d("click addq","cildentCont !!!!!!!!!!!");

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }catch (Exception e){}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
        Log.d("click addq","livenow="+livenow);



    }

    public void click(View view){
          Log.d("click addq","can click");
         if(view.getId()==R.id.addq){

             FirebaseUser user = mAuth.getCurrentUser();

             Log.d("click addq","livenow "+livenow);
             Log.d("click addq","turnq "+turnq);
             //livenow=mDatabase.child("user").child(user.getUid()).child("nowli;
             mDatabase.child("room").child(livenow).child("q").child(Long.toString(turnq)).setValue(user.getUid());


        }
    }
}
