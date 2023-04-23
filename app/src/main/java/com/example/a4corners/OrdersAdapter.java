package com.example.a4corners;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.Myclass>{
    ArrayList<GetterSetterOrder> al;
    Context context;
    String url = "http://10.0.117.121:80/4corners/index.php";

    public OrdersAdapter(Context context, ArrayList<GetterSetterOrder> al) {
        this.context = context;
        this.al = al;
    }

    @NonNull
    @Override
    public Myclass onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowfile_order,viewGroup,false);
        return new Myclass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final Myclass myclass, int i) {
        final GetterSetterOrder gl = al.get(i);

        myclass.allItems.setText(gl.getItems());
        String delivery = gl.getDelivery();
        myclass.delivery.setText(delivery);
        if (delivery.equals("Food Delivery")){
            myclass.addressLayout.setVisibility(View.VISIBLE);
            myclass.deliveryAddress.setText(gl.getAddress());
        }else{
            myclass.addressLayout.setVisibility(View.GONE);
        }
        myclass.totalPrice.setText(gl.getTotal());
        myclass.ccName.setText(gl.getCcName());
        myclass.ccNumber.setText(gl.getCcNumber());

        myclass.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(view.getContext());
                StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jSONObject = new JSONObject(response);
                            String status = jSONObject.getString("status");
                            if (status.equals("success")){
                                Toast.makeText(context, "Order cancelled successfully", Toast.LENGTH_LONG).show();
                                SharedPreferences.Editor nameEditor = context.getSharedPreferences("orderRefresh", 0).edit();
                                nameEditor.putString("orderRefresh", "orderRefresh");
                                nameEditor.apply();
                            }else{
                                Toast.makeText(view.getContext(), "Error occurred. Try again later", Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(), "Fail to get response = " + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("action", "deleteOrder");
                        params.put("id", gl.getId());
                        return params;
                    }
                };
                queue.add(request);


            }
        });

    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class Myclass extends RecyclerView.ViewHolder {
        View mview;
        TextView allItems;
        TextView delivery;
        LinearLayout addressLayout;
        TextView deliveryAddress;
        TextView totalPrice;
        TextView ccName;
        TextView ccNumber;
        Button cancel;

        public Myclass(@NonNull View itemView) {
            super(itemView);
            allItems = itemView.findViewById(R.id.allItems);
            delivery = itemView.findViewById(R.id.delivery);
            addressLayout = itemView.findViewById(R.id.addressLayout);
            deliveryAddress = itemView.findViewById(R.id.deliveryAddress);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            ccName = itemView.findViewById(R.id.ccName);
            ccNumber = itemView.findViewById(R.id.ccNumber);
            cancel = itemView.findViewById(R.id.cancel);
            mview = itemView;

        }
    }
}
