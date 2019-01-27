package com.example.krisorn.tangwong;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class user_search extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_user_search);

    }
    public  void  click(View view){
        if(view.getId() == R.id.ToSearchQRcode){
            Intent i =new Intent(this,user_qrcode.class);
            startActivity(i);
        }else if(view.getId() == R.id.ToSearchId){
            Log.d("haaaaaa","Usersearch");
            Intent i =new Intent(this,user_idSearch.class);
            startActivity(i);}
    }

}

