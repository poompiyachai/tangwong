package com.example.krisorn.tangwong.ownRoom;


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

class OwnRoomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txtDetail;
    public TextView txtNameRoom, txtSumPrice,txtStatus,txtNumberOfItem;
    public ImageView imageView;

    public TextView getTxtNameRoom() {
        return txtNameRoom;
    }

    public void setTxtNameRoom(TextView txtNameRoom) {
        this.txtNameRoom = txtNameRoom;
    }

    public TextView getTxtSumPrice() {
        return txtSumPrice;
    }

    public void setTxtSumPrice(TextView txtSumPrice) {
        this.txtSumPrice = txtSumPrice;
    }
    public TextView getTxtStatus() {
        return txtStatus;
    }

    public void setTxtStatus(TextView txtStatus) {
        this.txtStatus = txtStatus;
    }

    public TextView getTxtNumberOfItem() {
        return txtNumberOfItem;
    }

    public void setTxtNumberOfItem(TextView txtNumberOfItem) {
        this.txtNumberOfItem = txtNumberOfItem;
    }

    public OwnRoomViewHolder(@NonNull View itemView) {
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

public class OwnRoomAdapter extends RecyclerView.Adapter<OwnRoomViewHolder> {

    private Context context;
    public DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public int countOrderNow;


    public OwnRoomAdapter(int countOrderNow,Context context){
        this.countOrderNow=countOrderNow;
        this.context = context;
    }
    @NonNull
    @Override
    public OwnRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.card_user_room,parent,false);


        return new OwnRoomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final OwnRoomViewHolder holder, final int position) {
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        //  TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(position).getQuanlity(),Color.RED);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(),AdminDashBoradView.class);
                context.startActivity(i);

                Log.d("statusPage","can click");

                mDatabase.child("user").child(user.getUid()).child("owner").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String room = dataSnapshot.child(String.valueOf(position)).getValue(String.class);
                        mDatabase.child("user").child(user.getUid()).child("livenow").setValue(room);
                      //  Log.d("ttttttttttttt",mDatabase.getPath().getParent().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        Log.d("list data","can not get firebase");
        try {
            mDatabase.child("user").child(user.getUid()).child("owner").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String room = dataSnapshot.child(String.valueOf(position)).getValue(String.class);
                    Log.d("list data", "can get fribase1" + room + " " + position );
                    try {


                        mDatabase.child("room").child(room).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                holder.txtNameRoom.setText(dataSnapshot.child("name").getValue(String.class));
                                holder.txtDetail.setText(dataSnapshot.child("data").getValue(String.class));
                                Picasso.get().load(dataSnapshot.child("photoPath").getValue(String.class)).into(holder.imageView);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }catch (Exception e){}
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }catch (Exception e ){


        }


        // holder.txtNameRoom.setText();

       /* Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);


        holder.txt_price.setText(fmt.format(price));
        holder.txt_cart_name.setText(listData.get(position).getProductName());*/

    }

    @Override
    public int getItemCount() {
        Log.d("listtttttttttttttttt", String.valueOf(countOrderNow));
        return countOrderNow;
    }


}
