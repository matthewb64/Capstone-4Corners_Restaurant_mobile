package com.example.a4corners;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CartFragment extends Fragment {
    RecyclerView rev;
    TextView clear, checkout;
    ArrayList<GetterSetterCart> al;
    LinearLayout noItem, items;
    Runnable runnable;
    Handler handler = new Handler();
    int delay = 200;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        clear = view.findViewById(R.id.clear);
        checkout = view.findViewById(R.id.checkout);
        noItem = view.findViewById(R.id.noItem);
        items = view.findViewById(R.id.items);
        rev = view.findViewById(R.id.rev);
        rev.setLayoutManager(new LinearLayoutManager(getContext()));
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("cart", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                viewData();
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CheckoutActivity.class));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewData();
        runnable = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                SharedPreferences sharedPrefUser = getContext().getSharedPreferences("cartRefresh", Context.MODE_PRIVATE);
                if (sharedPrefUser.getString("cartRefresh", "No value").equals("cartRefresh")) {
                    viewData();
                    sharedPrefUser.edit().remove("cartRefresh").apply();
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
        SharedPreferences sharedPref = getContext().getSharedPreferences("cart", MODE_PRIVATE);
        Map<String, ?> prefsMap = sharedPref.getAll();
        int count = 0;
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
            count++;
        }
        if (count == 0){
            noItem.setVisibility(View.VISIBLE);
            items.setVisibility(View.GONE);
        }else{
            noItem.setVisibility(View.GONE);
            items.setVisibility(View.VISIBLE);
            rev.setAdapter(new CartAdapter(getContext(), al));

        }
    }

}
