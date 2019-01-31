
        package com.example.krisorn.tangwong;

        import android.content.Intent;
        import android.support.annotation.NonNull;
        import android.support.design.widget.BottomNavigationView;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
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

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    public long countRoom = 0;
    String title=null;
    String noRoom=null;
    String detail=null;
    String imgUrl=null;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rcv = new RecyclerView(this);
        ImageView temImage = null;
        dataset =new ArrayList<>();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        mDatabase=FirebaseDatabase.getInstance().getReference();
        final String uid = user.getUid();
        rcv.setLayoutManager(new LinearLayoutManager(this));
        adapter =new RecyclerView.Adapter<MyViewHolder>(){

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View card = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_user_room,viewGroup,false);

                return new MyViewHolder(card);
            }

            @Override
            public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

                mDatabase = database.getReference();

                myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("onClick","onClick"+i);

                        mDatabase.child("user").child(uid).child("live").child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                mDatabase.child("user").child(uid).child("livenow").setValue(dataSnapshot.getValue());

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Intent i = new Intent(user_roomActivity.this,list_itemActivity.class);
                        startActivity(i);
                    }
                });

                //  mDatabase.child("user").child(uid).orderByChild("live").equalTo(i).limitToFirst(1).addValueEventListener(new ValueEventListener() {
                mDatabase.child("user").child(uid).child("live").child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        //eventsRef.orderByChild("Name").equalTo("Fb Meetup").limitToFirst(1)
                        noRoom =dataSnapshot.getValue(String.class);
                        Log.d("live","live "+ title + " " + i);
                        mDatabase.child("room").child(noRoom).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                title = dataSnapshot.child("name").getValue(String.class);
                                detail=dataSnapshot.child("data").getValue(String.class);
                                imgUrl=dataSnapshot.child("photoPath").getValue(String.class);
                                Picasso.get().load(imgUrl).into(myViewHolder.imageView);
                                myViewHolder.tvTitle.setText(title);
                                myViewHolder.tvCarddetail.setText(detail);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        // String pathPhoto=dataSnapshot.child("user").child(uid).child("pathPhoto").getValue(String.class);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //myViewHolder.imageView=dataset.get(i).imgGroup;

                // imgUrl = "https://firebasestorage.googleapis.com/v0/b/tangwong-862c9.appspot.com/o/Photos%2F1934229398?alt=media&token=ec82532d-8a0b-4ab4-bb12-a9a6be8189fd";

                //   new PicassoImageGetter()

            }

            @Override
            public int getItemCount() {
                Log.d("count Room","count room = "+ countRoom);
                return (int) countRoom;
            }
        };

        mDatabase.child("user").child(uid).child("live").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                countRoom= dataSnapshot.getChildrenCount();
                Log.d("countroom dataSNSH","count room = "+countRoom);
                rcv.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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




    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitle;
        ImageView imageView;
        TextView tvCarddetail;
        String pathPhoto;



        public MyViewHolder(View itemView){
            super(itemView);
            tvTitle=itemView.findViewById(R.id.text_card_user_room);
            imageView=itemView.findViewById(R.id.img_card_user_room);

            // new DownloadImageTask((ImageView)findViewById(R.id.img_card_user_room)).execute(pathPhoto);
            // tvCarddetail=itemView,findViewById(R.id.text_card_detail);
            tvCarddetail=itemView.findViewById(R.id.text_card_detail);

        }

        @Override
        public void onClick(View v) {

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


