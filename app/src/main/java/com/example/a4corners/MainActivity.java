package com.example.a4corners;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button login, twitter, facebook;
    TextView register;
    ImageView passwordToggleImageButton;

    String url = "http://10.0.117.121:80/4corners/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordToggleImageButton = findViewById(R.id.passwordToggleImageButton);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        twitter = findViewById(R.id.twitter);
        facebook = findViewById(R.id.facebook);
        register = findViewById(R.id.register);

        passwordToggleImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean showPassword = password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                int inputType = showPassword ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
                password.setInputType(inputType);
                password.setSelection(password.getText().length());
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail = email.getText().toString();
                String getPassword = password.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jSONObject = new JSONObject(response);
                            String message = jSONObject.getString("message");
                            if (message.equals("success")){
                                String id = jSONObject.getString("id");
                                String name = jSONObject.getString("name");
                                String phone = jSONObject.getString("phonenumber");
                                String address = jSONObject.getString("address");
                                SharedPreferences.Editor nameEditor = getSharedPreferences("account_data", MODE_PRIVATE).edit();
                                nameEditor.putString("user_id", id);
                                nameEditor.putString("email", getEmail);
                                nameEditor.putString("password", getPassword);
                                nameEditor.putString("name", name);
                                nameEditor.putString("phone", phone);
                                nameEditor.putString("address", address);
                                nameEditor.apply();
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));

                            }else{
                                Toast.makeText(getApplicationContext(), "Incorrect Email or Password!!", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("action", "userLogin");
                        params.put("email", getEmail);
                        params.put("password", getPassword);
                        return params;
                    }
                };
                queue.add(request);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com"));
                startActivity(intent);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com"));
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignupActivity.class));
            }
        });
    }
}