package com.example.krisorn.tangwong;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.krisorn.tangwong.Database.Database;
import com.example.krisorn.tangwong.Model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class item_detail extends AppCompatActivity {

    TextView item_name,item_price,item_description;
    ImageView item_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    String itemId;
    private FirebaseAuth mAuth;

    FirebaseDatabase mDatabase;
    DatabaseReference mUser;
    DatabaseReference mRoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        mDatabase=FirebaseDatabase.getInstance();
        mUser = mDatabase.getReference("user");
        mRoom = mDatabase.getReference("room");

        //Init View

        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)findViewById(R.id.btnCart);

        item_description = (TextView)findViewById(R.id.item_description);
        item_price = (TextView)findViewById(R.id.item_price);
        item_name  = (TextView)findViewById(R.id.item_name);
        item_image = (ImageView)findViewById(R.id.img_item);

        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpendedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


         btnCart.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 mAuth=FirebaseAuth.getInstance();
                 FirebaseUser user = mAuth.getCurrentUser();
                 final String uid = user.getUid();
                 mUser.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         String livenow = dataSnapshot.child(uid).child("livenow").getValue(String.class);
                         final String liveItemNow = dataSnapshot.child(uid).child("liveItemNow").getValue(String.class);
                         Log.d("liveItemNow","liveItemNow"+liveItemNow);
                         mRoom.child(livenow).child("menu").child(liveItemNow).addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                 //collapsingToolbarLayout.setTitle(dataSnapshot.child("name").getValue(String.class));
                                 new Database(getBaseContext()).addToCart(new Order(
                                        liveItemNow,
                                         dataSnapshot.child("name").getValue(String.class),
                                         numberButton.getNumber(),
                                         dataSnapshot.child("price").getValue(String.class)

                                 ));
                                /* item_price.setText(dataSnapshot.child("price").getValue(String.class));
                                 item_name.setText(dataSnapshot.child("name").getValue(String.class));
                                 item_description.setText(dataSnapshot.child("detail").getValue(String.class));
                                 Picasso.get().load(dataSnapshot.child("pathPhoto").getValue(String.class)).into(item_image);
                            */
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError databaseError) {

                             }
                         });

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });

             }
         });


        getDetailItem();


    }

    private void getDetailItem() {
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        final String uid = user.getUid();
        mUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String livenow = dataSnapshot.child(uid).child("livenow").getValue(String.class);
                String liveItemNow = dataSnapshot.child(uid).child("liveItemNow").getValue(String.class);
                Log.d("liveItemNow","liveItemNow"+liveItemNow);
                mRoom.child(livenow).child("menu").child(liveItemNow).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       collapsingToolbarLayout.setTitle(dataSnapshot.child("name").getValue(String.class));
                       item_price.setText(dataSnapshot.child("price").getValue(String.class));
                       item_name.setText(dataSnapshot.child("name").getValue(String.class));
                       item_description.setText(dataSnapshot.child("detail").getValue(String.class));
                       Picasso.get().load(dataSnapshot.child("pathPhoto").getValue(String.class)).into(item_image);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
