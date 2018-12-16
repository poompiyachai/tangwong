package com.example.krisorn.tangwong;


import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class UsersViewModel extends ViewModel {

    private String string;
    private String name;
    private Context current;

    public UsersViewModel(Context current1){
        this.string = "0";
        this.name="NULL";
        this.current=current1;
    }

    public String getString() {
        return string;
    }
    public String getName(){return name;}

    public void setString(String string) {
        this.string = string;
    }
    public void setName(String name){this.name = name;}

    public void gotoUsers(){
        Intent i = new Intent(current, UsersActivity.class);
        current.startActivity(i);
    }
}
