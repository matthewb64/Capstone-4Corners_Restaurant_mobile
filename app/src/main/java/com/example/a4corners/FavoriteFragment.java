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


public class FavoriteFragment extends Fragment {
    RecyclerView rev;
    ArrayList<GetterSetterFavorite> al;
    LinearLayout noFavorite;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        noFavorite = view.findViewById(R.id.noFavorite);
        rev = view.findViewById(R.id.rev);
        rev.setLayoutManager(new LinearLayoutManager(getContext()));
        viewData();

        return view;
    }


    public void viewData(){
        al = new ArrayList<>();
        SharedPreferences sharedPref = getContext().getSharedPreferences("favorite", MODE_PRIVATE);
        Map<String, ?> prefsMap = sharedPref.getAll();
        int count = 0;
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
            String cartKey = entry.getKey();
            String cartValue = entry.getValue().toString();

            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            HashMap<String, String> itemHash = gson.fromJson(cartValue, type);
            String hashName = itemHash.get("name");
            String hashImage = itemHash.get("image");
            al.add(new GetterSetterFavorite(hashName, hashImage));
            count++;
        }
        if (count == 0){
            noFavorite.setVisibility(View.VISIBLE);
            rev.setVisibility(View.GONE);
        }else{
            noFavorite.setVisibility(View.GONE);
            rev.setVisibility(View.VISIBLE);
            rev.setAdapter(new FavoriteAdapter(getContext(), al));

        }
    }

}
