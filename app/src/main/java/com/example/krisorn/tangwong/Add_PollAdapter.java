package com.example.krisorn.tangwong;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.krisorn.tangwong.Model.Order;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class Add_pollViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView  text_chioce;

    public TextView getTxt_Chioce() {
        return text_chioce;
    }

    public void setTxt_Chioce(TextView text_chioce) {
        this.text_chioce = text_chioce;
    }

    public ImageView Imge_Choice;

    public Add_pollViewHolder(@NonNull View itemView) {
        super(itemView);
        text_chioce = (TextView) itemView.findViewById(R.id.firstLine);
        Imge_Choice = (ImageView) itemView.findViewById(R.id.icon);

    }

    @Override
    public void onClick(View v) {

    }
}
public class Add_PollAdapter extends RecyclerView.Adapter <Add_pollViewHolder>  {
    private List<Order> listChioce = new ArrayList<>();
    private Context context;

    public Add_PollAdapter(List<Order> listChioce,Context context){
        this.listChioce = listChioce;
        this.context = context;
    }
    @NonNull
    @Override
    public Add_pollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.chioce_select_card,parent,false);


        return new Add_pollViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Add_pollViewHolder add_pollViewHolder, int i) {
        TextDrawable drawable = TextDrawable.builder().buildRound(""+listChioce.get(i).getQuanlity(),Color.RED);
        add_pollViewHolder.Imge_Choice.setImageDrawable(drawable);

        Locale locale = new Locale("en","US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price;
        try{
            price = (Integer.parseInt(listChioce.get(i).getPrice()))*(Integer.parseInt(listChioce.get(i).getQuanlity()));}
        catch (Exception e){
            price = 0;
        }

        add_pollViewHolder.text_chioce.setText(fmt.format(price));
        add_pollViewHolder.text_chioce.setText(listChioce.get(i).getProductName());

    }


    @Override
    public int getItemCount() {
        return listChioce.size();
    }
}


