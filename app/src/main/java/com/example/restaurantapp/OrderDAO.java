package com.example.restaurantapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Set;

@Dao
public interface OrderDAO {

    @Insert
    long insert(Order order);

    @Query("SELECT SUM(totalPrice) FROM orderdetails WHERE orderId=:orderId")
    int totalAllPrice(String orderId);

    @Query("SELECT productName FROM orderdetails WHERE orderId=:orderId")
    List<String> selectedAllName(String orderId);

    @Query("SELECT photo FROM orderdetails WHERE orderId=:orderId")
    List<String> selectedAllphoto(String orderId);

    @Query("SELECT quantity FROM orderdetails WHERE orderId=:orderId")
    List<String> selectedAllquantity(String orderId);

    @Query("SELECT id FROM orderdetails WHERE orderId=:orderId")
    List<Integer> selectedID(String orderId);
    @Query("SELECT price FROM orderdetails WHERE orderId=:orderId")
    List<Integer> selectedPrice(String orderId);

//    @Query("SELECT MAX(orderId) FROM orderdetails  LIMIT 1" )
//     int selectOrderId (int id);
    @Query("SELECT quantity FROM orderdetails WHERE  id=:id")
    int selectOrderQuant(String id);

//    @Update
//    int update(Order order);

    @Query("UPDATE orderdetails SET quantity=:quantity WHERE id=:id")
     int update(String quantity, String id) ;
    @Query("UPDATE orderdetails SET totalPrice=:totalPrice WHERE id=:id")
    int update2(int totalPrice, String id) ;

    @Query("SELECT orderId FROM orderdetails WHERE id=:id")
    int orderidnew(String id);
@Query("DELETE FROM orderdetails WHERE id=:id")
        int delete(String id);
//    @Delete
//    int delete(Order order);
}
