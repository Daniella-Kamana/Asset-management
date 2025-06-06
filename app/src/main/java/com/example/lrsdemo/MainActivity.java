package com.example.lrsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etPassword;
    private MaterialButton btnLogin;
    private RequestQueue requestQueue;
    private static final String LOGIN_URL = "http://10.0.2.2/loss_reduction_backend/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        requestQueue = Volley.newRequestQueue(this);

        btnLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin(){
        String username = etUsername.getText() != null ? etUsername.getText().toString().trim() : "" ;
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        if(TextUtils.isEmpty(username)){
            etUsername.setError("Enter username");
            return;
        }
        if(TextUtils.isEmpty(password)){
            etPassword.setError("Enter password");
            return;
        }

        // Send a POST request to login.php
        StringRequest loginRequest = new StringRequest(
                Request.Method.POST,
                LOGIN_URL,
                response -> {
                    Toast.makeText(MainActivity.this, "Raw response:\n" + response, Toast.LENGTH_SHORT).show();
                    Log.d("LOGIN_RESPONSE",response);
                    try {
                        JSONObject json = new JSONObject(response);
                        boolean success = json.getBoolean("success");
                        if(success){
                            // Login succeeded: go to LeadingPage
                            Intent intent = new Intent(MainActivity.this,LeadingPage.class);
                            startActivity(intent);
                            finish();
                        }else {
                            // Show an error message from the server
                            String message = json.optString("message","Login failed");
                            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Invalid server response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
                }
        ){
            protected Map<String, String> getParams(){
             // POST parameters to login.php
             Map<String, String> params = new HashMap<>();
             params.put("username",username);
             params.put("password",password);
             return params;
            }
        };
        requestQueue.add(loginRequest);
    }
}