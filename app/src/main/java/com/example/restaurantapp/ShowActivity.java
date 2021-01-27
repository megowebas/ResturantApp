package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShowActivity extends AppCompatActivity {
    ImageView show_img, btn_plus, btn_minus;
    RadioGroup radioGroup;
    RadioButton small, medium, big;
    TextView price_small, price_medium, price_big, show_name, add_minus;
    String name, img, idMeal, date;
    int ps, pm, pb, num = 1, price ;
    SimpleDateFormat sdf;
    ExecutorService pool;
    int availableProcessors,ordernumber;
    long insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//        EventBus.getDefault().register(this);
        availableProcessors = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(availableProcessors);
        radioGroup = findViewById(R.id.radiogp);
        show_img = findViewById(R.id.show_img);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        add_minus = findViewById(R.id.tv_add_plus);
        show_name = findViewById(R.id.show_name);
        show_img = findViewById(R.id.show_img);
        small = findViewById(R.id.small);
        medium = findViewById(R.id.medium);
        big = findViewById(R.id.big);
        price_small = findViewById(R.id.price_small);
        price_medium = findViewById(R.id.price_medium);
        price_big = findViewById(R.id.price_big);
        ordernumber=getIntent().getIntExtra("ordernumber",0);
        name = getIntent().getStringExtra("name");
        show_name.setText(name);
        img = getIntent().getStringExtra("img");
        if (img.equals("")) {
            show_img.setImageResource(R.drawable.notfound);
        } else {
            Glide.with(this).load(img).into(show_img);
        }
        ps = getIntent().getIntExtra("price", 0);
        price_small.setText(ps + " $");
        pm = ps + (ps * 20 / 100);
        price_medium.setText(pm + " $");
        pb = ps + (ps * 50 / 100);
        price_big.setText(pb + " $");
        idMeal = getIntent().getStringExtra("idMeal");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.small:
                        price = ps;
                        break;
                    case R.id.medium:
                        price = pm;
                        break;
                    case R.id.big:
                        price = pb;
                        break;
                }
            }
        });
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(new Date());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Your Meal");


    }
//    @Subscribe
//    public void onMassage(CartActivity.Massage massage){
//        ordernumber= massage.getName();
//
//    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        pool.shutdown();
        super.onDestroy();
    }

    public void btn_minus(View view) {
        if (num == 1) {
            return;
        } else {
            num -= 1;
            add_minus.setText(num + "");
        }

    }

    public void btn_plus(View view) {
        num += 1;
        add_minus.setText(num + "");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void btn_add_to_cart(View view) {
        Order order = new Order();
        order.orderId = String.valueOf(ordernumber);
        order.productName = name;
        order.productId = idMeal;
        order.price = price;
        order.date = date;
        order.quantity = add_minus.getText().toString();
        order.totalPrice = num * price;
        order.photo = img;
        pool.execute(() -> {
            insert = OrderDatabase.getInstance(ShowActivity.this).orderDAO().insert(order);
          runOnUiThread(() -> {   if (insert > 0) {
              Toast.makeText(ShowActivity.this, "Add To Order", Toast.LENGTH_SHORT).show();
          } else {
              Toast.makeText(ShowActivity.this, "Error in Add", Toast.LENGTH_SHORT).show();
          }
          });
        });



    }

}