package com.example.krisorn.tangwong;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class showtime extends AppCompatActivity {
    private TimePicker time;
    public DatabaseReference nameCard;
    private String hr,mi;
    private ListView lvItems;
    private List<Product> lstProducts;
    private String id ;
    private String uid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        nameCard = database.getReference();
        lvItems = (ListView) findViewById(R.id.lvItems);



        lstProducts = new ArrayList<> ();
        Log.d ("GGGG","pass");
        nameCard.addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d ("GGGG","passDATA");
                uid = dataSnapshot.child ("temp").getValue (String.class);
                id = dataSnapshot.child ("user").child (uid).child ("livenow").getValue (String.class);
                // เดี๋ยววนตามจำนวนnotfi
                long value_child_notifi = dataSnapshot.child ("room").child (id).child ("time_notifi").getChildrenCount ();
                 for(int i = 0 ;i < value_child_notifi;i++){
                    String count = dataSnapshot.child ("room").child (id).child ("time_notifi").child (String.valueOf (i)).child ("countdown") .getValue (String.class).toString ();
                    String text =  dataSnapshot.child ("room").child (id).child ("time_notifi").child (String.valueOf (i)).child ("text") .getValue (String.class).toString ();
                    String time  = dataSnapshot.child ("room").child (id).child ("time_notifi").child (String.valueOf (i)).child ("time") .getValue (String.class);
                    lstProducts.add(new Product(text, System.currentTimeMillis() + Integer.parseInt(count) ));
                    Log.d ("GGGG",count);
                 }
                lvItems.setAdapter(new CountdownAdapter(showtime.this, lstProducts));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private class Product {
        String name;
        long expirationTime;

        public Product(String name, long expirationTime) {
            this.name = name;
            this.expirationTime = expirationTime;
        }
    }


    public class CountdownAdapter extends ArrayAdapter<Product> {

        private LayoutInflater lf;
        private List<ViewHolder> lstHolders;
        private Handler mHandler = new Handler ();
        private Runnable updateRemainingTimeRunnable = new Runnable() {
            @Override
            public void run() {
                synchronized (lstHolders) {
                    long currentTime = System.currentTimeMillis();
                    for (ViewHolder holder : lstHolders) {
                        holder.updateTimeRemaining(currentTime);
                    }
                }
            }
        };

        public CountdownAdapter(showtime context, List<Product> objects) {
            super(context, 0, objects);
            lf = LayoutInflater.from(context);
            lstHolders = new ArrayList<>();
            startUpdateTimer();
        }

        private void startUpdateTimer() {
            Timer tmr = new Timer();
            tmr.schedule(new TimerTask () {
                @Override
                public void run() {
                    mHandler.post(updateRemainingTimeRunnable);
                }
            }, 1000, 1000);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = lf.inflate(R.layout.activity_showtime, parent, false);
                holder.tvProduct = (TextView) convertView.findViewById(R.id.tvProduct);
                holder.tvTimeRemaining = (TextView) convertView.findViewById(R.id.tvTimeRemaining);
                convertView.setTag(holder);
                synchronized (lstHolders) {
                    lstHolders.add(holder);
                }
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.setData(getItem(position));

            return convertView;
        }
    }

    private class ViewHolder {
        TextView tvProduct;
        TextView tvTimeRemaining;
        Product mProduct;

        public void setData(Product item) {
            mProduct = item;
            tvProduct.setText(item.name);
            updateTimeRemaining(System.currentTimeMillis());
        }

        public void updateTimeRemaining(long currentTime) {
            long timeDiff = mProduct.expirationTime - currentTime;
            if (timeDiff > 0) {
                int seconds = (int) (timeDiff / 1000) % 60;
                int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
                int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);
                tvTimeRemaining.setText(hours + " hrs " + minutes + " mins " + seconds + " sec");
            } else {
                tvTimeRemaining.setText("หมดเวลาเเล้ว");
            }
        }
    }
}