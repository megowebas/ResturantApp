package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderItemActivity extends AppCompatActivity {
    String na, p, q, quant, itemId, totalPrice;
    ImageView photo, del_photo, min, plus;
    TextView quantity, name;
    int a, availableProcessors, update, delete, total = 1, update2,selectOrderQuant,pr;
    ExecutorService pool;
    OrderDAO orderDAO;
    int orderidnew;
    SharedPreferences preferences;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item);
        preferences = getSharedPreferences("data", MODE_PRIVATE);
        availableProcessors = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(availableProcessors);
        na = getIntent().getStringExtra("na").toString();
        p = getIntent().getStringExtra("p").toString();
        q = getIntent().getStringExtra("q").toString();
        totalPrice = getIntent().getStringExtra("totalPrice");
        itemId = getIntent().getStringExtra("itemId").toString();
        pr=getIntent().getIntExtra("pr",1);
        photo = findViewById(R.id.photo);
        del_photo = findViewById(R.id.photo_delete);
        min = findViewById(R.id.min);
        plus = findViewById(R.id.plus);
        name = findViewById(R.id.nameOrderItem);
        quantity = findViewById(R.id.qu);
        name.setText(na);
        quantity.setText(q);
        a = Integer.parseInt(q);
        Glide.with(OrderItemActivity.this).load(p).into(photo);


    }

    @Override
    protected void onDestroy() {
        pool.shutdown();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(OrderItemActivity.this, MainActivity.class);
//       Massage2 message2 = new Massage2(orderidnew);
//        EventBus.getDefault().post(message2);
        startActivity(in);
        super.onBackPressed();
    }

    public void plus(View view) {
        a += 1;
        quantity.setText(a + "");
    }

    public void min(View view) {
        if (a == 1) {
            return;
        } else {
            a -= 1;
            quantity.setText(a + "");
        }
    }

    public void edit(View view) {
        quant = quantity.getText().toString();

        pool.execute(() -> {
            orderDAO = OrderDatabase.getInstance(OrderItemActivity.this).orderDAO();
            update = orderDAO.update(quant, itemId);
             orderidnew = orderDAO.orderidnew(itemId);
            selectOrderQuant = orderDAO.selectOrderQuant(itemId);
            total=selectOrderQuant*pr;
            update2 = orderDAO.update2(total, itemId);

            runOnUiThread(() -> {
                edit = preferences.edit();
                edit.putInt("orderId", orderidnew);
                edit.apply();
                if (update > 0) {
                    Toast.makeText(OrderItemActivity.this, "Your Order is Update", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderItemActivity.this, "Your Order is Not Update", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    public void del(View view) {
        pool.execute(() -> {
            orderDAO = OrderDatabase.getInstance(OrderItemActivity.this).orderDAO();
            delete = orderDAO.delete(itemId);
            runOnUiThread(() -> {
                if (delete > 0) {
                    Toast.makeText(OrderItemActivity.this, "Your Order is delete", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrderItemActivity.this, "error is occur in delete", Toast.LENGTH_SHORT).show();
                }
            });

        });
        Toast.makeText(this, "Your Order is Delete", Toast.LENGTH_SHORT).show();
    }
//    class Massage2 {
//        private int name;
//
//        public Massage2(int name) {
//            this.name = name;
//        }
//
//        public int getName() {
//            return name;
//        }
//
//        public void setName(int name) {
//            this.name = name;
//        }
//    }
}