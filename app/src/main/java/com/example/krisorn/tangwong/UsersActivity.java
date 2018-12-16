package com.example.krisorn.tangwong;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
//import android.widget.TextView;

import com.example.krisorn.tangwong.databinding.ActivityUsersBindingImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import static java.lang.Integer.parseInt;

public class UsersActivity extends AppCompatActivity {
    public DatabaseReference nameCard;
    private UsersViewModel viewModel;
    private FirebaseAuth mAuth;
    // TODO Step 1: Declare binding instance instead view's (binding class is auto-generated)
    //private TextView textView;
    ActivityUsersBindingImpl binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        viewModel = new UsersViewModel(this);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();

        initView();
        //get firebase


        nameCard = database.getReference();

        nameCard.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uid = user.getUid();
               /* Map map =(Map)dataSnapshot.getValue();
                String name = String.valueOf(map.get("name"));*/
              String name=dataSnapshot.child(uid).child("name").getValue(String.class);
                viewModel.setName(name);
                binding.kri.setText(viewModel.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initView(){

       // FirebaseUser currentUser = mAuth.getCurrentUser();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users);
        binding.setViewmodel(viewModel);

    }

    public void click(View view) {

        int current=parseInt(viewModel.getString(),10);
        current++;
        String strCurrent= String.valueOf(current);

        viewModel.setString(strCurrent);
        binding.textView2.setText(viewModel.getString());
    }
    public void signOut(View view) {
        viewModel.setLogoutSatus();
        mAuth.signOut();
        Intent i = new Intent(this,EmailPasswordActivity.class);
        startActivity(i);
    }

}
