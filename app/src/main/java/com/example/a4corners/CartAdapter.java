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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Myclass>{
    ArrayList<GetterSetterCart> al;
    Context context;

    public CartAdapter(Context context, ArrayList<GetterSetterCart> al) {
        this.context = context;
        this.al = al;
    }

    @NonNull
    @Override
    public Myclass onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowfile_cart,viewGroup,false);
        return new Myclass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myclass myclass, int i) {
        final GetterSetterCart gl = al.get(i);

        myclass.name.setText(gl.getName());
        myclass.price.setText("$"+gl.getPrice());
        int resourceId = myclass.itemView.getContext().getResources().getIdentifier(gl.getImage(), "drawable", myclass.itemView.getContext().getPackageName());
        myclass.image.setImageResource(resourceId);

        myclass.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("cart", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(gl.getId());
                editor.apply();
                Toast.makeText(context, "Item removed from the cart", Toast.LENGTH_LONG).show();
                SharedPreferences.Editor nameEditor = context.getSharedPreferences("cartRefresh", 0).edit();
                nameEditor.putString("cartRefresh", "cartRefresh");
                nameEditor.apply();

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
        Button remove;
        ImageView image;

        public Myclass(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            remove = itemView.findViewById(R.id.remove);
            image = itemView.findViewById(R.id.image);
            mview = itemView;

        }
    }
}
