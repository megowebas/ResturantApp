package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    CircleImageView cir1, cir2, cir3, cir4, cir5;
    ListView listView;
    TextView name, description;
    MealsAdapter adapter;
    MealsAdapter.ViewHolder holder;
    ArrayList<String> nameMeal = new ArrayList<>();
    ArrayList<String> idMeal = new ArrayList<>();
    ArrayList<String> thumb = new ArrayList<>();
    ArrayList<Integer> price = new ArrayList<>();
    String img, n, d, date, json;
    int p, ordernumber ,orderId,preferencesInt;
    SimpleDateFormat sdf;
    ExecutorService pool;
    int availableProcessors;
    long insert;
    SharedPreferences  preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences("data", MODE_PRIVATE);
        preferencesInt = preferences.getInt("orderId", 0);
        EventBus.getDefault().register(this);
        cir1 = findViewById(R.id.cir_1);
        cir2 = findViewById(R.id.cir_2);
        cir3 = findViewById(R.id.cir_3);
        cir4 = findViewById(R.id.cir_4);
        cir5 = findViewById(R.id.cir_5);
        listView = findViewById(R.id.listView_containt);
        this.name = findViewById(R.id.name);
        description = findViewById(R.id.description);
//        this.orderId =getIntent().getIntExtra("orderId",0);
        ordernumber= preferencesInt ;
        availableProcessors = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(availableProcessors);
        getJson("Chicken");
        this.name.setText("Chicken");
        description.setText("Chicken is a type of domesticated fowl, a subspecies of the red junglefowl");
        adapter = new MealsAdapter(this, nameMeal);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(MainActivity.this);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(new Date());

    }


    @Override
    protected void onDestroy() {
        pool.shutdown();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onMassage(CartActivity.Massage massage) {
        ordernumber = massage.getName();

    }

    public String getJson(String string) {
        json = "";
        try {
            InputStream is = getAssets().open("json/meal.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(string);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                nameMeal.add(object.getString("Meal"));
                idMeal.add(object.getString("id"));
                thumb.add(object.getString("MealThumb"));
                price.add(object.getInt("price"));
            }
        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void btn_cir_1(View view) {
        nameMeal.clear();
        thumb.clear();
        idMeal.clear();
        price.clear();
        getJson("Chicken");
        name.setText("Chicken");
        description.setText("Chicken is a type of domesticated fowl, a subspecies of the red junglefowl");
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(MainActivity.this);
    }

    public void btn_cir_2(View view) {
        name.setText("Sea Fish");
        nameMeal.clear();
        thumb.clear();
        idMeal.clear();
        price.clear();
        getJson("SeaFish");
        description.setText("Seafood is any form of sea life regarded as food by humans. Seafood prominently includes fish and shellfish");
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(MainActivity.this);
    }

    public void btn_cir_3(View view) {
        nameMeal.clear();
        thumb.clear();
        idMeal.clear();
        price.clear();
        getJson("desserts");
        name.setText("Dessert");
        description.setText("Dessert is a course that concludes a meal. The course usually consists of sweet foods, such as confections dishes or fruit, and possibly a beverage such as dessert wine or liqueur");
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(MainActivity.this);
    }

    public void btn_cir_4(View view) {
        nameMeal.clear();
        thumb.clear();
        idMeal.clear();
        price.clear();
        getJson("fried");
        name.setText("French Fries");
        description.setText("Shepody potatoes to make their French Fries.");
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(MainActivity.this);
    }

    public void btn_cir_5(View view) {
        nameMeal.clear();
        thumb.clear();
        idMeal.clear();
        price.clear();
        getJson("pizza");
        name.setText("Pizza");
        description.setText("Pizza is a savory dish of Italian origin consisting of a usually round");
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(MainActivity.this);
    }

    public void btn_cir_6(View view) {
        nameMeal.clear();
        thumb.clear();
        idMeal.clear();
        price.clear();
        ;
        getJson("sideOrder");
        name.setText("Side Order");
        description.setText("an extra dish of food");
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(MainActivity.this);
    }

    public void btn_cir_7(View view) {
        nameMeal.clear();
        thumb.clear();
        idMeal.clear();
        price.clear();
        getJson("sandawich");
        name.setText("Sandawich");
        description.setText("Pizza is a savory dish of Italian origin consisting of a usually round");
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(MainActivity.this);
    }

    public void btn_cir_8(View view) {
        nameMeal.clear();
        thumb.clear();
        idMeal.clear();
        price.clear();
        getJson("freshjuice");
        name.setText("Fresh Juice");
        description.setText("Glasses of fresh juice and fruits on table");
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(MainActivity.this);
    }

    public void btn_cir_9(View view) {
        nameMeal.clear();
        thumb.clear();
        idMeal.clear();
        price.clear();
        getJson("drink");
        name.setText("Drink");
        description.setText("A drink (or beverage) is a liquid intended for human consumption");
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(MainActivity.this);
    }

    public void btn_cir_10(View view) {
        nameMeal.clear();
        thumb.clear();
        idMeal.clear();
        price.clear();
        getJson("apitizer");
        name.setText("Appetizer");
        description.setText("An easy bite-size cream cheese appetizer baked inside flaky pie crust and topped with cranberry");
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(MainActivity.this);
    }

    public void btn_cir_11(View view) {
        nameMeal.clear();
        thumb.clear();
        idMeal.clear();
        price.clear();
        getJson("beef");
        name.setText("Beef");
        description.setText("Beef is the culinary name for meat from cattle, particularly skeletal muscle");
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(MainActivity.this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        n = nameMeal.get(position).toString();
        img = thumb.get(position).toString();
        p = price.get(position);
        d = idMeal.get(position).toString();
//        ordernumber = ordernumber;

        Intent in = new Intent(MainActivity.this, ShowActivity.class);
        in.putExtra("name", n);
        in.putExtra("img", img);
        in.putExtra("price", p);
        in.putExtra("idMeal", d);
        in.putExtra("ordernumber", ordernumber);
        startActivity(in);
    }

    public void floatingCartButton(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra("orderIdMain", ordernumber);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void aboutUs(MenuItem item) {
        Intent in = new Intent(this, AboutUsActivity.class);
        startActivity(in);
    }

    class MealsAdapter extends ArrayAdapter<String> {
        public MealsAdapter(@NonNull Context context, ArrayList<String> Meals_list) {
            super(context, 0, Meals_list);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_list_view, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.cm_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Order order = new Order();
                    order.orderId = String.valueOf(ordernumber);
                    order.photo = thumb.get(position).toString();
                    order.price = price.get(position);
                    order.quantity = "1";
                    order.date = date;
                    order.productName = nameMeal.get(position).toString();
                    order.totalPrice = price.get(position);
                    order.productId = idMeal.get(position).toString();
                    pool.execute(() -> {
                        insert = OrderDatabase.getInstance(MainActivity.this).orderDAO().insert(order);
                        runOnUiThread(() -> {
                            if (insert > 0) {
                                Toast.makeText(MainActivity.this, "Add To Order", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Error in Add", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });

                }
            });
            holder.cm_name.setText(nameMeal.get(position).toString());
            if (thumb.get(position).equals("")) {
                holder.cm_image.setImageResource(R.drawable.notfound);
            } else {
                Glide.with(MainActivity.this).load(thumb.get(position).toString()).into(holder.cm_image);
            }
            return convertView;
        }

        class ViewHolder {
            TextView cm_name;
            ImageView cm_image, cm_add;

            public ViewHolder(View convertView) {
                cm_name = convertView.findViewById(R.id.cm_name);
                cm_image = convertView.findViewById(R.id.cm_img);
                cm_add = convertView.findViewById(R.id.cm_btn);

            }
        }
    }
}