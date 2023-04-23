package com.example.a4corners;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.Myclass>{
    ArrayList<GetterSetterSpecial> al;
    Context context;

    public MealAdapter(Context context, ArrayList<GetterSetterSpecial> al) {
        this.context = context;
        this.al = al;
    }

    @NonNull
    @Override
    public Myclass onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowfile_meal,viewGroup,false);
        return new Myclass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myclass myclass, int i) {
        final GetterSetterSpecial gl = al.get(i);

        myclass.name.setText(gl.getName());
        myclass.price.setText("$"+gl.getPrice());
        myclass.description.setText(gl.getDescription());
        int resourceId = myclass.itemView.getContext().getResources().getIdentifier(gl.getImage(), "drawable", myclass.itemView.getContext().getPackageName());
        myclass.image.setImageResource(resourceId);


        myclass.addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> item = new HashMap<>();
                item.put("name", gl.getName());
                item.put("image", gl.getImage());
                Gson gson = new Gson();
                String itemsString = gson.toJson(item);
                SharedPreferences.Editor editor = context.getSharedPreferences("favorite", MODE_PRIVATE).edit();
                editor.putString(gl.getId(), itemsString);
                editor.apply();
                Toast.makeText(context, "Item saved as favorite", Toast.LENGTH_LONG).show();

            }
        });


        myclass.mview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HashMap<String, String> item = new HashMap<>();
                item.put("name", gl.getName());
                item.put("image", gl.getImage());
                item.put("price", gl.getPrice());
                Gson gson = new Gson();
                String itemsString = gson.toJson(item);
                SharedPreferences.Editor editor = context.getSharedPreferences("cart", MODE_PRIVATE).edit();
                editor.putString(gl.getId(), itemsString);
                editor.apply();
                Toast.makeText(context, "Item added to cart", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class Myclass extends RecyclerView.ViewHolder {
        View mview;
        TextView name;
        TextView price;
        TextView description;
        Button addFavorite;
        ImageView image;

        public Myclass(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            description = itemView.findViewById(R.id.description);
            addFavorite = itemView.findViewById(R.id.addFavorite);
            image = itemView.findViewById(R.id.image);
            mview = itemView;

        }
    }
}
