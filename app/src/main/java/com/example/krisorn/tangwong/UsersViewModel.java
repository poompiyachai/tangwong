package com.example.krisorn.tangwong;


import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

public class UsersViewModel extends ViewModel {

    private String string;
    private String name;
    private String faculty;
    private String sername;
    private String university;
    private String phoneNumber;
    private Context current;
    private String pathPhoto;
    private boolean logout=false;
    private String imageUrl;

    public UsersViewModel(Context current1){
        this.string = "0";
        this.name="NULL";
        this.current=current1;
        this.imageUrl = imageUrl;
    }
    public boolean getLogoutSatus(){return this.logout;}
    public void setLogoutSatus(){this.logout=false;}
    public String getString() {
        return string;
    }

    public String getName(){return name;}
    public String getFaculty(){return faculty;}
    public String getSername(){return sername;}
    public String getPhoneNumber(){return phoneNumber;}
    public String getUniversity(){return university;}
    public String getPathPhoto(){return pathPhoto;}


    public void setString(String string) {
        this.string = string;
    }
    public void setName(String name){this.name = name;}
    public void setSername(String sername){this.sername=sername;}
    public void setUniversity(String university){this.university=university;}
    public void setFaculty(String faculty){this.faculty=faculty;}
    public void setPhoneNumber(String phoneNumber){this.phoneNumber=phoneNumber;}
    public void setPathPhoto(String pathPhoto){this.pathPhoto=pathPhoto;}

    public void gotoUsers(){
        Intent i = new Intent(current, UsersActivity.class);
        current.startActivity(i);
    }

    public void signOut(View view){
        Intent i = new Intent(current, UsersActivity.class);
        this.logout=true;
        current.startActivity(i);
    }




}



class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {

            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}