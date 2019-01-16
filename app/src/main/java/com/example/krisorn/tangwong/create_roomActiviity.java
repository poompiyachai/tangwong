package com.example.krisorn.tangwong;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class create_roomActiviity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public TextView txt_data;
    public TextView txt_name_room;
    public TextView txt_type_room;
    public Long roomid;
    public Long count_own_room_count;
    public Button btn_create_room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room_activiity);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        txt_data = findViewById(R.id.txt_create_room_detail);
        txt_name_room = findViewById(R.id.txt_create_room_name);
        txt_type_room=findViewById(R.id.txt_create_room_type);
        btn_create_room=(Button)findViewById(R.id.btn_create_room);


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                roomid = dataSnapshot.child("room").getChildrenCount()+1;
                Log.d("crateRoomid", String.valueOf(roomid));
                count_own_room_count=dataSnapshot.child("user").child(user.getUid()).child("owner").getChildrenCount();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_create_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d("roomid",Long.toString(roomid));
                mDatabase.child("room").child(Long.toString(roomid)).child("photoPath").setValue("https://firebasestorage.googleapis.com/v0/b/tangwong-862c9.appspot.com/o/Photos%2Fwww.maxpixel.net-Taxes-Control-Smart-Home-Icon-Technology-Home-3317459.png?alt=media&token=329741c8-000f-4c85-bd9f-ca5b4e99442d");
                mDatabase.child("room").child(Long.toString(roomid)).child("name").setValue(txt_name_room.getText().toString());
                mDatabase.child("room").child(Long.toString(roomid)).child("type").setValue(txt_type_room.getText().toString());
                mDatabase.child("room").child(Long.toString(roomid)).child("data").setValue(txt_data.getText().toString());

                mDatabase.child("user").child(user.getUid()).child("owner").child(String.valueOf(count_own_room_count)).setValue((Long.toString(roomid)));

            }
        });


        //side bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_user);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //      NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user);
//        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.bringToFront();
        //end side bar

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_user);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d("can select nav","can select nav");
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_room) {
            Intent i = new Intent(this,user_roomActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_add_room) {
            Intent i = new Intent(this,create_roomActiviity.class);
            startActivity(i);

        } else if (id == R.id.nav_profile) {
            Intent i = new Intent(this,UsersActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_cart) {
            Intent i = new Intent(this,Cart.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_myroom) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_user);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}