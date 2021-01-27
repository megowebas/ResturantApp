package com.example.restaurantapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orderdetails")
public class Order {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String productId, productName, date, orderId, photo, quantity;
    public int price, totalPrice;
}
