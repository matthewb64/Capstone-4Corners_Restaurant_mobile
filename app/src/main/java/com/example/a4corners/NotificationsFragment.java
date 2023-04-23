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
import android.widget.Button;
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


public class NotificationsFragment extends Fragment {
    LinearLayout specialLayout, rewardsLayout;
    Button specialDays, rewards;
    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        specialLayout = view.findViewById(R.id.specialLayout);
        rewardsLayout = view.findViewById(R.id.rewardsLayout);
        specialDays = view.findViewById(R.id.specialDays);
        rewards = view.findViewById(R.id.rewards);

        specialDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                specialLayout.setVisibility(View.VISIBLE);
                rewardsLayout.setVisibility(View.GONE);
            }
        });
        rewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                specialLayout.setVisibility(View.GONE);
                rewardsLayout.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }



}
