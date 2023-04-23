package com.example.a4corners;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {
    Button back, place;
    TextView username, subtotal, tax, total, ccName, ccNumber;
    RecyclerView rev;
    RadioGroup radioGroup;
    String radioBtn = "In-person";
    ArrayList<GetterSetterCart> al;
    String getUser_id,getEmail, getName, getAddress, getSubTotal, getTax, getTotal;

    String url = "http://10.0.117.121:80/4corners/index.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        back = findViewById(R.id.back);
        place = findViewById(R.id.place);
        username = findViewById(R.id.username);
        subtotal = findViewById(R.id.subtotal);
        tax = findViewById(R.id.tax);
        total = findViewById(R.id.total);
        ccName = findViewById(R.id.ccName);
        ccNumber = findViewById(R.id.ccNumber);
        rev = findViewById(R.id.rev);
        rev.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        radioGroup = findViewById(R.id.radioGroup);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                if (radioButton != null) {
                    radioBtn = radioButton.getText().toString();
                    calculateTotal();
                }
            }
        });
        calculateTotal();
        SharedPreferences sharedPrefUser = getSharedPreferences("account_data", MODE_PRIVATE);
        getUser_id = sharedPrefUser.getString("user_id", "");
        getEmail = sharedPrefUser.getString("email", "");
        getName = sharedPrefUser.getString("name", "");
        getAddress = sharedPrefUser.getString("address", "");

        username.setText("User: " + getName);
        viewData();


        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getccName = ccName.getText().toString();
                String getccNumber = ccNumber.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(CheckoutActivity.this);
                StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jSONObject = new JSONObject(response);
                            String status = jSONObject.getString("status");
                            if (status.equals("success")){
                                Toast.makeText(getApplicationContext(), "Order Placed successfully", Toast.LENGTH_LONG).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                startActivity(new Intent(CheckoutActivity.this, OrdersActivity.class));

                            }else{
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
                        Toast.makeText(CheckoutActivity.this, "Fail to get response = " + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sharedPref = getSharedPreferences("cart", MODE_PRIVATE);
                        Map<String, ?> prefsMap = sharedPref.getAll();
                        String allCartItems = "";
                        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
                            String cartValue = entry.getValue().toString();

                            Gson gson = new Gson();
                            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
                            HashMap<String, String> itemHash = gson.fromJson(cartValue, type);
                            String hashName = itemHash.get("name");

                            if (allCartItems.equals("")){
                                allCartItems = allCartItems + hashName;
                            }else{
                                allCartItems = allCartItems +", " + hashName;
                            }

                        }

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("action", "placeOrder");
                        params.put("user_id", getUser_id);
                        params.put("items", allCartItems);
                        params.put("email", getEmail);
                        params.put("delivery", radioBtn);
                        params.put("ccName", getccName);
                        params.put("ccNumber", getccNumber);
                        params.put("address", getAddress);
                        params.put("total", getTotal);
                        return params;
                    }
                };
                queue.add(request);
            }
        });

    }
    public void calculateTotal(){
        SharedPreferences sharedPref = getSharedPreferences("cart", MODE_PRIVATE);
        Map<String, ?> prefsMap = sharedPref.getAll();
        int priceCount = 0;
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
            String cartValue = entry.getValue().toString();

            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            HashMap<String, String> itemHash = gson.fromJson(cartValue, type);
            String hashPrice = itemHash.get("price");
            priceCount = priceCount + Integer.parseInt(hashPrice);

        }

        if (radioBtn.equals("Food Delivery")){
            priceCount = priceCount + 15;
        }

        getSubTotal = String.valueOf(priceCount);
        int taxCount = (int) (priceCount * 0.04);
        getTax = String.valueOf(taxCount);
        int totalCount = priceCount + taxCount;
        getTotal = String.valueOf(totalCount);

        subtotal.setText("$" + getSubTotal);
        tax.setText("$" + getTax);
        total.setText("$" + getTotal);
    }
    public void viewData(){
        al = new ArrayList<>();
        SharedPreferences sharedPref = getSharedPreferences("cart", MODE_PRIVATE);
        Map<String, ?> prefsMap = sharedPref.getAll();
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
            String cartKey = entry.getKey();
            String cartValue = entry.getValue().toString();

            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            HashMap<String, String> itemHash = gson.fromJson(cartValue, type);
            String hashName = itemHash.get("name");
            String hashPrice = itemHash.get("price");
            String hashImage = itemHash.get("image");
            al.add(new GetterSetterCart(cartKey, hashName, hashPrice, hashImage));
        }
        rev.setAdapter(new CheckoutAdapter(getApplicationContext(), al));
    }
}