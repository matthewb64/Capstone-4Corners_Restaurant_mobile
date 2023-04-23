package com.example.a4corners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    EditText name, phone, email, address, password;
    Button register;
    TextView login;

    String url = "http://10.0.117.121/4corners/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getName = name.getText().toString();
                String getPhone = phone.getText().toString();
                String getEmail = email.getText().toString();
                String getAddress = address.getText().toString();
                String getPassword = password.getText().toString();

                RequestQueue queue = Volley.newRequestQueue(SignupActivity.this);
                StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jSONObject = new JSONObject(response);
                            String status = jSONObject.getString("status");
                            if (status.equals("success")){
                                Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignupActivity.this, MainActivity.class));

                            }else{
                                Toast.makeText(getApplicationContext(), "Error occurred. Try again with a different email.", Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignupActivity.this, "Fail to get response = " + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("action", "registerUser");
                        params.put("name", getName);
                        params.put("phone", getPhone);
                        params.put("email", getEmail);
                        params.put("address", getAddress);
                        params.put("password", getPassword);
                        return params;
                    }
                };
                queue.add(request);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }
}