package com.example.restaurantapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Order.class}, version = 1)
public abstract class OrderDatabase extends RoomDatabase {
    public abstract OrderDAO orderDAO();

    private static OrderDatabase ourInstance;

    public static OrderDatabase getInstance(Context context) {

        if (ourInstance == null) {

            ourInstance = Room.databaseBuilder(context,
                    OrderDatabase.class, "Orderdetails.db")
                    .createFromAsset("database/Orderdetails.db")
//                    .allowMainThreadQueries()
                    .build();
        }
        return ourInstance;
    }
}
