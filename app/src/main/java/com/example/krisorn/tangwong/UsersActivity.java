package com.example.krisorn.tangwong;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
//import android.widget.TextView;

import com.example.krisorn.tangwong.databinding.ActivityUsersBindingImpl;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class UsersActivity extends AppCompatActivity {
    public DatabaseReference nameCard;
    private UsersViewModel viewModel;
    private FirebaseAuth mAuth;
    // TODO Step 1: Declare binding instance instead view's (binding class is auto-generated)
    //private TextView textView;
    ActivityUsersBindingImpl binding;

    private EditText mtypeField;
    private EditText mdataField;
    private EditText nameField;
    private long roomid;



    private ProgressDialog mProgressDialog;

    private Button mselectImage;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    // [END declare_auth]
    private static final int GALLERY_INTENT =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        viewModel = new UsersViewModel(this);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        mProgressDialog= new ProgressDialog(this);
        mStorage=FirebaseStorage.getInstance().getReference();
        mselectImage=(Button) findViewById(R.id.btn_addImage);

        mDatabase = FirebaseDatabase.getInstance().getReference();
       /* mselectImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });*/

        new DownloadImageTask((ImageView)findViewById(R.id.profile)).execute("https://firebasestorage.googleapis.com/v0/b/tangwong-862c9.appspot.com/o/Photos%2Fstorage%2Femulated%2F0%2FDCIM%2FCamera%2FIMG_20181216_222350.jpg?alt=media&token=804a1f60-af35-4fe6-beb2-dabf51c3dd5a");
        initView();
        //get firebase


        nameCard = database.getReference();

        nameCard.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uid = user.getUid();
               /* Map map =(Map)dataSnapshot.getValue();
                String name = String.valueOf(map.get("name"));*/
              String name=dataSnapshot.child("user").child(uid).child("name").getValue(String.class);

              String pathPhoto=dataSnapshot.child(uid).child("pathPhoto").getValue(String.class);
              viewModel.setName(name);

             // viewModel.setPathPhoto(pathPhoto);


              binding.name.setText(viewModel.getName());

              new DownloadImageTask((ImageView)findViewById(R.id.profile)).execute(pathPhoto);



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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        nameCard = database.getReference();
        if(mDatabase.child("room").child("0").child("name").toString().equals("eiei"))
        {
            mDatabase.child("room").child("0").child("name").setValue("qwe");
        }
        else
        {
            mDatabase.child("room").child("0").child("name").setValue("eiei");
        }

        if(view.getId()==R.id.button){
        int current=parseInt(viewModel.getString(),10);
        current++;
        String strCurrent= String.valueOf(current);

        viewModel.setString(strCurrent);
        binding.textView2.setText(viewModel.getString());}
        else if(view.getId()==R.id.btn_addImage){
            Intent intent =new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,GALLERY_INTENT);
        }
        else if(view.getId()==R.id.addroom){
            setContentView(R.layout.activity_addroom);

        }
        else if(view.getId()==R.id.createroom){


            nameCard.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                    while (roomid<=0)
                    {
                        roomid = dataSnapshot.child("room").getChildrenCount() ;
                    }
                    roomid = dataSnapshot.child("room").getChildrenCount() + 1;

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

                    mtypeField = findViewById(R.id.type);
            mdataField = findViewById(R.id.data);
            nameField=findViewById(R.id.name);

            mDatabase.child("room").child(Long.toString(roomid)).child("name").setValue(nameField.getText().toString());
            mDatabase.child("room").child(Long.toString(roomid)).child("type").setValue(mtypeField.getText().toString());
            mDatabase.child("room").child(Long.toString(roomid)).child("data").setValue(mdataField.getText().toString());
            setContentView(R.layout.activity_users);
        }
    }
    public void signOut(View view) {
        viewModel.setLogoutSatus();
        mAuth.signOut();
        Intent i = new Intent(this,EmailPasswordActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
/*
        tempData =data;
        tempRequestCode=requestCode;
        tempResultCode=resultCode;
        */

        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK){

            Uri uri=data.getData();
            final StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
           // mProgressDialog.setMessage("Uploading....");
            mProgressDialog.setMessage("uploading....");
            mProgressDialog.show();

           /* filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(UsersActivity.this,"upload Done",Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();

                    }
            });

            */

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                           // Log.d(TAG, "onSuccess: uri= "+ uri.toString());
                            String url = uri.toString();
                            mDatabase.child(mAuth.getCurrentUser().getUid()).child("pathPhoto").setValue(url);
                            Toast.makeText(UsersActivity.this,"upload Done",Toast.LENGTH_LONG).show();
                            mProgressDialog.dismiss();
                        }
                    });
                }
            });

            // Create a reference to the file we want to download


         //   mDatabase.child(mAuth.getCurrentUser().getUid()).child("pathPhoto1").setValue(ref);
//            mDatabase.child(mAuth.getCurrentUser().getUid()).child("pathPhoto2").setValue(filepath.getDownloadUrl().getResult().toString());
           // mDatabase.child(mAuth.getCurrentUser().getUid()).child("pathPhoto3").setValue(filepath.getDownloadUrl().getResult().toString());
       //     mDatabase.child(mAuth.getCurrentUser().getUid()).child("pathPhoto4").setValue(filepath.getPath());
         //   mDatabase.child(mAuth.getCurrentUser().getUid()).child("pathPhoto5").setValue(filepath.getName());

        }

    }


}
