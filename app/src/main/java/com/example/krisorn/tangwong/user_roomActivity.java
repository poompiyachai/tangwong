package com.example.krisorn.tangwong;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class user_roomActivity extends AppCompatActivity {

    private RecyclerView rcv;
    private RecyclerView.Adapter<MyViewHolder> adapter;
    private ArrayList<Mydata> dataset;
    private  BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.home:
                    Log.d("click","click home");
                    //  Toast.makeText(UsersActivity.this,"HOME",Toast.LENGTH_SHORT);
                    //jump to activity
                    return  true;
                case R.id.search:
                    Log.d("click","click search");
                    //  Toast.makeText(UsersActivity.this,"SEARCH",Toast.LENGTH_SHORT);
                    //jump to activity
                    return  true;
                case R.id.alert:
                    Log.d("click","click alert");
                    //   Toast.makeText(UsersActivity.this,"ALERT",Toast.LENGTH_SHORT);
                    //jump to activity
                    return  true;

                case R.id.profile:
                    Log.d("click","click profile");
                    //  Toast.makeText(UsersActivity.this,"PROFLIE",Toast.LENGTH_SHORT);
                    //jump to activity
                    return  true;

                default:
                    return  false;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rcv = new RecyclerView(this);
        ImageView temImage = null;
        dataset =new ArrayList<>();
        for(int i = 0; i<20 ;i++){
            int pictureNum =genPictureNum();
          //  int num =500 + (int)(Math.random()*100);
            String str = genTitle();
           // new DownloadImageTask(temImage).execute("https://firebasestorage.googleapis.com/v0/b/tangwong-862c9.appspot.com/o/Photos%2F1934229398?alt=media&token=ec82532d-8a0b-4ab4-bb12-a9a6be8189fd");

            dataset.add(new Mydata(str,pictureNum,temImage));
        }
        //bn_nav
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemReselectedListener((BottomNavigationView.OnNavigationItemReselectedListener) mOnNavigationItemSelectedListener);
        //bn_nav
        rcv.setLayoutManager(new LinearLayoutManager(this));
        adapter =new RecyclerView.Adapter<MyViewHolder>(){

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                 View card = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_user_room,viewGroup,false);

                return new MyViewHolder(card);
            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

                myViewHolder.tvNumber.setText(""+i);
                //myViewHolder.imageView=dataset.get(i).imgGroup;
                String imgUrl = "https://firebasestorage.googleapis.com/v0/b/tangwong-862c9.appspot.com/o/Photos%2F1934229398?alt=media&token=ec82532d-8a0b-4ab4-bb12-a9a6be8189fd";
                Picasso.get().load(imgUrl).into(myViewHolder.imageView);
             //   new PicassoImageGetter()

            }

            @Override
            public int getItemCount() {
                return dataset.size();
            }
        };
        rcv.setAdapter(adapter);
        setContentView(rcv);
    }

    private int genPictureNum() {
        int i =(int)(Math.random()*8);
        switch (i){
            case 0: return R.drawable.profille_kri;
            case 1: return R.drawable.back_groud;
            case 2: return R.drawable.back_groud_login;
            default:return R.drawable.com_facebook_auth_dialog_cancel_background;
        }
    }

    private String genTitle() {
        String[] str ={"boob","big","puk","kit"};
        int n =1+ (int)(Math.random()*3);
        StringBuilder sb= new StringBuilder();
        for(int i=0;i<n;i++){
            sb.append(str[(int)(Math.random()*4)]);
        }
        return sb.toString();
    }


    private class MyViewHolder extends RecyclerView.ViewHolder{
          TextView tvNumber;
          ImageView imageView;
          TextView tvCarddetail;
          String pathPhoto;
        public MyViewHolder(View itemView){
            super(itemView);
            tvNumber=itemView.findViewById(R.id.text_card_user_room);
            imageView=itemView.findViewById(R.id.img_card_user_room);
            
           // new DownloadImageTask((ImageView)findViewById(R.id.img_card_user_room)).execute(pathPhoto);
           // tvCarddetail=itemView,findViewById(R.id.text_card_detail);
            tvCarddetail=itemView.findViewById(R.id.text_card_detail);

        }
    }

    class Mydata{
        int picture;
        String title;
        ImageView imgGroup;
        Mydata(String str,int num,ImageView imgGroup1){
            picture =num;
            title=str;
            imgGroup=imgGroup1;
        }
    }

}


