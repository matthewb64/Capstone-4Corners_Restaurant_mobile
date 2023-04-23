package com.example.a4corners;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener{
    public static FragmentManager fragmentManager;
    DrawerLayout drawerLayout;
    FrameLayout fragment_container;
    String getUser_id,getEmail, getName, getAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer);
        fragment_container = findViewById(R.id.fragment_container);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        (Objects.requireNonNull(getSupportActionBar())).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        SharedPreferences sharedPrefUser = getSharedPreferences("account_data", MODE_PRIVATE);
        getUser_id = sharedPrefUser.getString("user_id", "");
        getEmail = sharedPrefUser.getString("email", "");
        getName = sharedPrefUser.getString("name", "");
        getAddress = sharedPrefUser.getString("address", "");

        View headerView = navigationView.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.name);
        TextView email = headerView.findViewById(R.id.email);
        TextView close = headerView.findViewById(R.id.close);
        userName.setText(getName);
        email.setText(getEmail);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawers();
            }
        });
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState == null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_container, new DashboardFragment(), "");
                fragmentTransaction.commit();
            } else {
                return;
            }
        }

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        drawerLayout.openDrawer((int) GravityCompat.START);
        return true;
    }
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.dashboard) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            drawerLayout.closeDrawers();
        }
        else if (id == R.id.orders) {
            startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
            drawerLayout.closeDrawers();
        }
        else if (id == R.id.notifications) {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            drawerLayout.closeDrawers();
        }
        else if (id == R.id.favorite) {
            startActivity(new Intent(getApplicationContext(), FavoriteActivity.class));
            drawerLayout.closeDrawers();
        }
        else if (id == R.id.cart) {
            startActivity(new Intent(getApplicationContext(), CartActivity.class));
            drawerLayout.closeDrawers();
        }
        else if (id == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("account_data", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            drawerLayout.closeDrawers();
        }
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}