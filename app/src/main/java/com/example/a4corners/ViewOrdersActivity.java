package com.example.a4corners;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewOrdersActivity extends AppCompatActivity {
    RecyclerView rev;
    String getUser_id;

    String url = "http://10.0.117.121:80/4corners/index.php";

    ArrayList<GetterSetterOrder> al;
    LinearLayout noOrder;
    Runnable runnable;
    Handler handler = new Handler();
    int delay = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        rev = findViewById(R.id.rev);
        noOrder = findViewById(R.id.noOrder);
        rev.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        SharedPreferences sharedPrefUser = getSharedPreferences("account_data", MODE_PRIVATE);
        getUser_id = sharedPrefUser.getString("user_id", "");
    }
    @Override
    public void onResume() {
        super.onResume();
        viewData();
        runnable = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                SharedPreferences sharedPrefUser = getSharedPreferences("orderRefresh", Context.MODE_PRIVATE);
                if (sharedPrefUser.getString("orderRefresh", "No value").equals("orderRefresh")) {
                    viewData();
                    sharedPrefUser.edit().remove("orderRefresh").apply();
                }

                handler.postDelayed(runnable, delay);
            }
        };
        handler.postDelayed(runnable, delay);
    }
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }
    public void viewData(){
        al = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(ViewOrdersActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jSONObject = new JSONObject(response);
                    String status = jSONObject.getString("status");
                    if (status.equals("success")){
                        noOrder.setVisibility(View.GONE);
                        rev.setVisibility(View.VISIBLE);
                        String message = jSONObject.getString("message");
                        JSONArray jsonArray = new JSONArray(message);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            int id = obj.getInt("id");
                            String items = obj.getString("items");
                            String delivery = obj.getString("delivery");
                            String ccName = obj.getString("ccName");
                            String ccNumber = obj.getString("ccNumber");
                            String address = obj.getString("address");
                            String total = obj.getString("total");
                            al.add(new GetterSetterOrder(String.valueOf(id), items, delivery, ccName, ccNumber, address, total));
                        }
                        rev.setAdapter(new OrdersAdapter(getApplicationContext(), al));

                    }else if (status.equals("no data")){
                        noOrder.setVisibility(View.VISIBLE);
                        rev.setVisibility(View.GONE);

                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error occurred. Try again later", Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewOrdersActivity.this, "Fail to get response = " + error, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "getOrders");
                params.put("user_id", getUser_id);
                return params;
            }
        };
        queue.add(request);

    }
}