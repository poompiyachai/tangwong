package com.example.krisorn.tangwong;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.krisorn.tangwong.Database.Database;
import com.example.krisorn.tangwong.Model.Order;
import com.example.krisorn.tangwong.Model.Request;
import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference request;

    TextView txtTotalPrice;
    Button btnPlace;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String livenow;
    private long turnq=0;
    private long noOfOder =0;
    String name = null;
    String phoneNumber= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase
        database = FirebaseDatabase.getInstance();
        request = database.getReference();

        //Init

        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView)findViewById(R.id.total);
        btnPlace = (Button)findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("click","btnplaceClick");
                showAlertDialog();
            }
        });

        loadListItem();
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("name").getValue(String.class);
                phoneNumber= dataSnapshot.child("phoneNumber").getValue(String.class);
                livenow=dataSnapshot.child("livenow").getValue(String.class);



                try {
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            turnq = dataSnapshot.child("room").child(livenow).child("q").getChildrenCount()+1;
                            noOfOder= dataSnapshot.child("user").child(user.getUid()).child("orderNow").getChildrenCount();
                            Log.d("trunq","trunq !!!!!"+turnq);





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

    }

    private void showAlertDialog() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("ระบุเพิ่มเติม");
        alertDialog.setMessage("ระบุเพิ่มเติม");

        Log.d("showAlertDialog","showAlertDialog");
        final EditText edtAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                FirebaseUser user = mAuth.getCurrentUser();

                Request request = new Request(

                        "wait",
                        user.getUid(),

                        phoneNumber,
                        name,
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );

                // user = mAuth.getCurrentUser();
                Log.d("show live now ","livenow"+livenow);
                Log.d("show turnq","turnq"+turnq);
        // mDatabase.child("room").child(livenow).child("q").child(Long.toString(turnq)).child("uid").setValue(user.getUid());
                mDatabase.child("room").child(livenow).child("q").child(Long.toString(turnq)).setValue(request);
                mDatabase.child("user").child(user.getUid()).child("orderNow").child(String.valueOf(noOfOder)).setValue(livenow);

                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this,"Thank you, Order Place",Toast.LENGTH_SHORT).show();
                finish();
            }

        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
     alertDialog.show();
    }

    private void loadListItem(){
    cart = new Database(this).getCarts();
    adapter = new CartAdapter(cart,this);
    recyclerView.setAdapter(adapter);

    int total =0 ;
    for (Order order:cart)
        try{
        total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuanlity()));}
        catch (Exception e){
        total = 0;
        }


        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));

    }
}
