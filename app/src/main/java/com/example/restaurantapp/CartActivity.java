package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView_cart;
    TextView totalPrice;
    CartAdapter.ViewHolder holder;
    OrderDAO orderDAO;
    int totalAllPrice = 0, pr;
    List<String> allName, allPhoto, allQuantity;
    List<Integer> allID, allPrice;
    ArrayList<String> resultName = new ArrayList<>();
    ArrayList<String> resultPhoto = new ArrayList<>();
    ArrayList<String> resultQuantity = new ArrayList<>();
    ArrayList<Integer> resultId = new ArrayList<>();
    ExecutorService pool;
    int availableProcessors;
    ArrayAdapter adapter;
    SharedPreferences preferences;
    int orderId;
    SharedPreferences.Editor edit;
    String na, p, q, total, itid;
    Button order_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
//        EventBus.getDefault().register(this);
        preferences = getSharedPreferences("data", MODE_PRIVATE);
        listView_cart = findViewById(R.id.listview_cart);
        totalPrice = findViewById(R.id.totalPrice);
        availableProcessors = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(availableProcessors);
        orderId = getIntent().getIntExtra("orderIdMain", 1);
        order_btn = findViewById(R.id.button2);

        pool.execute(() -> {
            orderDAO = OrderDatabase.getInstance(CartActivity.this).orderDAO();
            allName = orderDAO.selectedAllName(String.valueOf(orderId));
            allPhoto = orderDAO.selectedAllphoto(String.valueOf(orderId));
            allPrice = orderDAO.selectedPrice(String.valueOf(orderId));
            totalAllPrice = orderDAO.totalAllPrice(String.valueOf(orderId));
            allQuantity = orderDAO.selectedAllquantity(String.valueOf(orderId));
            allID = orderDAO.selectedID(String.valueOf(orderId));
            resultName.addAll(allName);
            resultPhoto.addAll(allPhoto);
            resultQuantity.addAll(allQuantity);
            resultId.addAll(allID);

            runOnUiThread(() -> {
                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, resultName);
                CartAdapter adapter1 = new CartAdapter(this, resultName);
                listView_cart.setAdapter(adapter1);
                listView_cart.setOnItemClickListener(CartActivity.this);
                totalPrice.setText(totalAllPrice + " $");
            });
        });


    }
//    @Subscribe
//    public void onMassage(CartActivity.Massage massage) {
//        orderId = massage.getName();
//    }

    @Override
    protected void onDestroy() {
//        EventBus.getDefault().unregister(this);
        pool.shutdown();
        allPhoto.clear();
        resultPhoto.clear();
        resultName.clear();
        allName.clear();
        super.onDestroy();
    }

    public void btn_order(View view) {
        orderId++;
        allPhoto.clear();
        allName.clear();
        edit = preferences.edit();
        edit.putInt("orderId", orderId);
        edit.apply();
        Massage message = new Massage(orderId);
        EventBus.getDefault().post(message);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        q = allQuantity.get(position).toString();
        p = allPhoto.get(position).toString();
        na = allName.get(position).toString();
        itid = allID.get(position).toString();
        pr = allPrice.get(position);
        Intent in = new Intent(CartActivity.this, OrderItemActivity.class);
        in.putExtra("p", p);
        in.putExtra("q", q);
        in.putExtra("na", na);
        in.putExtra("itemId", itid);
        in.putExtra("pr", pr);
        startActivity(in);
    }

    class CartAdapter extends ArrayAdapter<String> {
        public CartAdapter(@NonNull Context context, ArrayList<String> resultName) {
            super(context, 0, resultName);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_listview_cart, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.cm_name.setText(allName.get(position).toString());
            if (resultPhoto.get(position).equals("")) {
                holder.cm_image.setImageResource(R.drawable.notfound);
            } else {
                Glide.with(CartActivity.this).load(allPhoto.get(position).toString()).into(holder.cm_image);
            }
            holder.cm_quantity.setText(allQuantity.get(position).toString());
            return convertView;
        }

        class ViewHolder {
            TextView cm_name, cm_quantity;
            ImageView cm_image;

            public ViewHolder(View convertView) {
                cm_name = convertView.findViewById(R.id.cm_name_cart);
                cm_image = convertView.findViewById(R.id.cm_img_cart);
                cm_quantity = convertView.findViewById(R.id.quntity);
            }
        }
    }

    class Massage {
        private int name;

        public Massage(int name) {
            this.name = name;
        }

        public int getName() {
            return name;
        }

        public void setName(int name) {
            this.name = name;
        }
    }
}