package com.example.krisorn.tangwong.AddFeature;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.krisorn.tangwong.AdminDashBoradView;
import com.example.krisorn.tangwong.Model.Order;
import com.example.krisorn.tangwong.R;
import com.example.krisorn.tangwong.UsersViewModel;
import com.example.krisorn.tangwong.list_itemActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class AddFeatureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txtDetail;
    public TextView txtNameRoom;
    public ImageView imageView;


    public TextView getTxtNameRoom() {
        return txtNameRoom;
    }

    public void setTxtNameRoom(TextView txtNameRoom) {
        this.txtNameRoom = txtNameRoom;
    }




    public AddFeatureViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView=(ImageView) itemView.findViewById(R.id.img_card_user_room);
        txtNameRoom= (TextView)itemView.findViewById(R.id.text_card_user_room);
        // txtNumberOfItem=(TextView)itemView.findViewById(R.id.NumberOfItem);
        txtDetail= (TextView)itemView.findViewById(R.id.text_card_detail);
        //  txtSumPrice= (TextView)itemView.findViewById(R.id.sumPice);
    }

    @Override
    public void onClick(View v) {
        // Log.d("statusPage","can click");
    }
}

public class AddFeatureAdapter extends RecyclerView.Adapter<AddFeatureViewHolder> {

    private Context context;
    public DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public int countOrderNow;
    public int countFeature =0 ;


    public AddFeatureAdapter(int countOrderNow,Context context){
        this.countOrderNow=countOrderNow;
        this.context = context;
    }
    @NonNull
    @Override
    public AddFeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.card_user_room,parent,false);


        return new AddFeatureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AddFeatureViewHolder holder, final int position) {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==0){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("q");
                                    mDatabase.child("room").child(roomLiveNow).child("q").child("nameOfFeture").setValue("รายการสินค้า(แผงลอย)");
                                    mDatabase.child("room").child(roomLiveNow).child("q").child("detailOfFeture").setValue(dataSnapshot.child("data").getValue(String.class));
                                    mDatabase.child("room").child(roomLiveNow).child("q").child("typeOfFeture").setValue("StallShop");
                                    mDatabase.child("room").child(roomLiveNow).child("q").child("typeOfFetureShow").setValue("both");

                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount()+1)))
                                            .setValue("viewqueue");
                                    mDatabase.child("room").child(roomLiveNow).child("viewqueue").child("nameOfFeture").setValue("ดูคิวลูกค้า");
                                    mDatabase.child("room").child(roomLiveNow).child("viewqueue").child("detailOfFeture").setValue(dataSnapshot.child("data")
                                            .getValue(String.class));
                                    mDatabase.child("room").child(roomLiveNow).child("viewqueue").child("typeOfFeture").setValue("viewqueue");
                                    mDatabase.child("room").child(roomLiveNow).child("viewqueue").child("typeOfFetureShow").setValue("admin");

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
                /*else   if (position==1){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("store");
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("nameOfFeture").setValue("รายการสินค้า(แบบderivery)");
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("detailOfFeture").setValue(dataSnapshot.child("data")

                                            .getValue(String.class));
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("typeOfFeture").setValue("store");

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
                }*/
                /*else   if (position==2){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("viewqueue");
                                    mDatabase.child("room").child(roomLiveNow).child("viewqueue").child("nameOfFeture").setValue("ดูคิวลูกค้า");
                                    mDatabase.child("room").child(roomLiveNow).child("viewqueue").child("detailOfFeture").setValue(dataSnapshot.child("data")
                                            .getValue(String.class));
                                    mDatabase.child("room").child(roomLiveNow).child("viewqueue").child("typeOfFeture").setValue("viewqueue");

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
                }*/
                /*else   if (position==1){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("store");
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("nameOfFeture").setValue("รายการสินค้า(แบบderivery)");
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("detailOfFeture").setValue(dataSnapshot.child("data")

                                            .getValue(String.class));
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("typeOfFeture").setValue("store");

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
                */
                /*else   if (position==2){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("viewqueue");
                                    mDatabase.child("room").child(roomLiveNow).child("viewqueue").child("nameOfFeture").setValue("ดูคิวลูกค้า");
                                    mDatabase.child("room").child(roomLiveNow).child("viewqueue").child("detailOfFeture").setValue(dataSnapshot.child("data")
                                            .getValue(String.class));
                                    mDatabase.child("room").child(roomLiveNow).child("viewqueue").child("typeOfFeture").setValue("viewqueue");

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
                */
                /*else   if (position==3){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("store");
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("nameOfFeture").setValue("รายการสินค้า(แบบderivery)");
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("detailOfFeture").setValue(dataSnapshot.child("data")
                                            .getValue(String.class));

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
                */
                else   if (position==1){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("notification");
                                    mDatabase.child("room").child(roomLiveNow).child("notification").child("nameOfFeture").setValue("แจ้งเตือน");
                                    mDatabase.child("room").child(roomLiveNow).child("notification").child("detailOfFeture").setValue("แจ้งเตือนคนในกลุ่ม");
                                    mDatabase.child("room").child(roomLiveNow).child("notification").child("typeOfFeture").setValue("notification");
                                    mDatabase.child("room").child(roomLiveNow).child("notification").child("typeOfFetureShow").setValue("admin");
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
                }else   if (position==2){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("canlender_settime");
                                    mDatabase.child("room").child(roomLiveNow).child("canlender_settime").child("nameOfFeture").setValue("แจ้งเตือนตั้งเวลา");
                                    mDatabase.child("room").child(roomLiveNow).child("canlender_settime").child("detailOfFeture").setValue("แจ้งเตือนตั้งเวลาคนในกลุ่ม");
                                    mDatabase.child("room").child(roomLiveNow).child("canlender_settime").child("typeOfFeture").setValue("canlender_settime");
                                    mDatabase.child("room").child(roomLiveNow).child("canlender_settime").child("typeOfFetureShow").setValue("admin");

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
                }else   if (position==3){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("calender");
                                    mDatabase.child("room").child(roomLiveNow).child("calender").child("nameOfFeture").setValue("ดูปฎิทิน");
                                    mDatabase.child("room").child(roomLiveNow).child("calender").child("detailOfFeture").setValue("ดูปฎิทิน");
                                    mDatabase.child("room").child(roomLiveNow).child("calender").child("typeOfFeture").setValue("calender");
                                    mDatabase.child("room").child(roomLiveNow).child("calender").child("typeOfFetureShow").setValue("both");

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

/*
             else   if (position==1){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("store");
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("nameOfFeture").setValue("รายการสินค้า(แบบderivery)");
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("detailOfFeture").setValue(dataSnapshot.child("data")
                                            .getValue(String.class));

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
                }else   if (position==2){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("store");
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("nameOfFeture").setValue("รายการสินค้า(แบบderivery)");
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("detailOfFeture").setValue(dataSnapshot.child("data")
                                            .getValue(String.class));

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
                }else   if (position==3){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("store");
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("nameOfFeture").setValue("รายการสินค้า(แบบderivery)");
                                    mDatabase.child("room").child(roomLiveNow).child("store").child("detailOfFeture").setValue(dataSnapshot.child("data")
                                            .getValue(String.class));

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
                }else   if (position==4){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("notification");
                                    mDatabase.child("room").child(roomLiveNow).child("notification").child("nameOfFeture").setValue("แจ้งเตือน");
                                    mDatabase.child("room").child(roomLiveNow).child("notification").child("detailOfFeture").setValue("แจ้งเตือนคนในกลุ่ม");
                                    mDatabase.child("room").child(roomLiveNow).child("notification").child("typeOfFeture").setValue("notification");
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
                }else   if (position==5){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("canlender_settime");
                                    mDatabase.child("room").child(roomLiveNow).child("canlender_settime").child("nameOfFeture").setValue("แจ้งเตือนตั้งเวลา");
                                    mDatabase.child("room").child(roomLiveNow).child("canlender_settime").child("detailOfFeture").setValue("แจ้งเตือนตั้งเวลาคนในกลุ่ม");
                                    mDatabase.child("room").child(roomLiveNow).child("canlender_settime").child("typeOfFeture").setValue("canlender_settime");

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
                }else   if (position==6){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("calender");
                                    mDatabase.child("room").child(roomLiveNow).child("calender").child("nameOfFeture").setValue("ดูปฎิทิน");
                                    mDatabase.child("room").child(roomLiveNow).child("calender").child("detailOfFeture").setValue("ดูปฎิทิน");
                                    mDatabase.child("room").child(roomLiveNow).child("calender").child("typeOfFeture").setValue("calender");

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
                */else   if (position==4){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("poll");
                                    mDatabase.child("room").child(roomLiveNow).child("poll").child("nameOfFeture").setValue("โพล");
                                    mDatabase.child("room").child(roomLiveNow).child("poll").child("detailOfFeture").setValue("โพล");
                                    mDatabase.child("room").child(roomLiveNow).child("poll").child("typeOfFeture").setValue("poll");
                                    mDatabase.child("room").child(roomLiveNow).child("poll").child("typeOfFetureShow").setValue("admin");

                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount()+1)))
                                            .setValue("pollview");
                                    mDatabase.child("room").child(roomLiveNow).child("pollview").child("nameOfFeture").setValue("ดูโพล");
                                    mDatabase.child("room").child(roomLiveNow).child("pollview").child("detailOfFeture").setValue("ดูโพล");
                                    mDatabase.child("room").child(roomLiveNow).child("pollview").child("typeOfFeture").setValue("pollview");
                                    mDatabase.child("room").child(roomLiveNow).child("pollview").child("typeOfFetureShow").setValue("user");


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
                /*
                else   if (position==8){

                    mDatabase.child("user").child(user.getUid()).child("livenow").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final String roomLiveNow= dataSnapshot.getValue(String.class);
                            Log.d("canRetrive","livenow");

                            mDatabase.child("room").child(roomLiveNow).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    mDatabase.child("room").child(roomLiveNow).child("feature").child(String.valueOf((dataSnapshot.child("feature").getChildrenCount())))
                                            .setValue("pollview");
                                    mDatabase.child("room").child(roomLiveNow).child("pollview").child("nameOfFeture").setValue("ดูโพล");
                                    mDatabase.child("room").child(roomLiveNow).child("pollview").child("detailOfFeture").setValue("ดูโพล");
                                    mDatabase.child("room").child(roomLiveNow).child("pollview").child("typeOfFeture").setValue("pollview");

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
                }*/

                Intent i = new Intent(v.getContext(), AdminDashBoradView.class);
                context.startActivity(i);
                Log.d("statusPage","can click addfeature");

            }});
        if(position==0){

            holder.txtNameRoom.setText("เพิ่มร้านค้าแพงลอย");
            holder.txtDetail.setText("เพิ่มร้านค้า สำหรับการขายของ");
            String imgUrl = "http://www.scphtrang.ac.th/main/sites/default/files/10961970_805523669502906_1747785628_n.jpg";
            Picasso.get().load(imgUrl).into(holder.imageView);

        }
       /* if (position==1){
            holder.txtNameRoom.setText("เพิ่มร้านค้า");
            holder.txtDetail.setText("เพิ่มร้านค้า แบบdelivery");
            String imgUrl = "http://fr-asia.com/wp-content/uploads/tmp/delivery2-400x400.jpg";
            Picasso.get().load(imgUrl).into(holder.imageView);
        }*/
      /*  if (position==2){
            holder.txtNameRoom.setText("เพิ่มดูสถาณะร้านค้า");
            holder.txtDetail.setText("เอาไว้ดูลำดับคิวลูกค้า");
            String imgUrl = "http://เซ้งร้าน.com/img/dorm/img_1437667740.jpeg";
            Picasso.get().load(imgUrl).into(holder.imageView);
        }*/
      /*  if (position==1){
            holder.txtNameRoom.setText("เพิ่มตัวจัดการคิว");
            holder.txtDetail.setText("ทุกคนสามารถเข้าคิวผ่านแอพได้");
            String imgUrl = "https://i.ytimg.com/vi/FOrGijZK5m0/maxresdefault.jpg";
            Picasso.get().load(imgUrl).into(holder.imageView);
        }*/
        if (position==1){
            holder.txtNameRoom.setText("แจ้งเตือนกิจกรรม");
            holder.txtDetail.setText("แจ้งเตือนทุกคนในกลุ่ม");
            String imgUrl = "https://png.pngtree.com/element_origin_min_pic/17/08/03/ab218d702435fd76fc2d02616bdf5604.jpg";
            Picasso.get().load(imgUrl).into(holder.imageView);
        }
        if (position==2){
            holder.txtNameRoom.setText("แจ้งเตือนแบบตั้งเวลา");
            holder.txtDetail.setText("แจ้งเตือนทุกคนในกลุ่ม");
            String imgUrl = "https://png.pngtree.com/element_origin_min_pic/17/08/03/ab218d702435fd76fc2d02616bdf5604.jpg";
            Picasso.get().load(imgUrl).into(holder.imageView);
        }
        if (position==3){
            holder.txtNameRoom.setText("ปฎิทิน");
            holder.txtDetail.setText("ดูปฎิทิน");
            String imgUrl = "https://png.pngtree.com/element_origin_min_pic/17/08/03/ab218d702435fd76fc2d02616bdf5604.jpg";
            Picasso.get().load(imgUrl).into(holder.imageView);
        }
        if (position==4){
            holder.txtNameRoom.setText("โพล");
            holder.txtDetail.setText("โพล");
            String imgUrl = "https://png.pngtree.com/element_origin_min_pic/17/08/03/ab218d702435fd76fc2d02616bdf5604.jpg";
            Picasso.get().load(imgUrl).into(holder.imageView);
        }/*if (position==8){
            holder.txtNameRoom.setText("ดูโพล");
            holder.txtDetail.setText("ดูโพล");
            String imgUrl = "https://png.pngtree.com/element_origin_min_pic/17/08/03/ab218d702435fd76fc2d02616bdf5604.jpg";
            Picasso.get().load(imgUrl).into(holder.imageView);
        }*/

    }

    @Override
    public int getItemCount() {

        return 5;
    }
}
