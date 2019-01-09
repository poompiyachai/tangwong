package com.example.krisorn.tangwong.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.krisorn.tangwong.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="selectItemDB.db";
    private static final int DB_VER=1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String[] sqlSelect={"productName","productID","quanlity","price"};
        String sqlTable = "orderDerail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order> result =new ArrayList<>();

        if(c.moveToFirst()){
            do{
                result.add(new Order(c.getString(c.getColumnIndex("productID")),
                        c.getString(c.getColumnIndex("productName")),
                        c.getString(c.getColumnIndex("quanlity")),
                        c.getString(c.getColumnIndex("price"))
                       ));
            }while (c.moveToNext());
        }
        return result;

    }

    public void addToCart(Order order){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO orderDerail(productID,productName,quanlity,price) VALUES('%s','%s','%s','%s');",
                order.getProductID(),
                order.getProductName(),
                order.getQuanlity(),
                order.getPrice());

        db.execSQL(query);
    }

    public void cleanCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM orderDerail");
        db.execSQL(query);
    }

}
