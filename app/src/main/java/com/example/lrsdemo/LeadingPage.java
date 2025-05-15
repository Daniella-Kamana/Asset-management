package com.example.lrsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class LeadingPage extends AppCompatActivity {

    Button btnGo;
    Spinner selectClient, selectProject;
    ArrayAdapter<String> projectAdapter;
    ArrayAdapter<Client> clientAdapter;
    ArrayList<Client> clientList = new ArrayList<>();
    ArrayList<String> projectList = new ArrayList<>();
    RequestQueue requestQueue;

    String Base_URL = "http://10.0.2.2/loss_reduction_backend/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leading_page);

        selectClient = findViewById(R.id.spinnerClient);
        selectProject = findViewById(R.id.spinnerProject);
        btnGo = findViewById(R.id.btnGo);
        requestQueue = Volley.newRequestQueue(this);

        clientAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, clientList);
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectClient.setAdapter(clientAdapter);

        projectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, projectList);
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectProject.setAdapter(projectAdapter);

        loadClients();

        selectClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Client selectedClient = clientList.get(position);
                loadProjects(selectedClient.id); // Load projects for selected client
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        selectProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LeadingPage.this, "Selected Project: "+ projectList.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnGo.setOnClickListener( v ->{
            Intent intent = new Intent(LeadingPage.this, TabsActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void loadClients() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, Base_URL  + "get_clients.php", null,
                response -> {
                    clientList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject client = response.getJSONObject(i);
                            int id = client.getInt("id");
                            String name = client.getString("name");
                            clientList.add(new Client(id, name));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    clientAdapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Error loading clients", Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }

    private void loadProjects(int clientId){
        String url = Base_URL + "get_projects.php?client_id=" + clientId;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    projectList.clear();
                    for(int i = 0; i <  response.length(); i++) {
                        try{
                            JSONObject project = response.getJSONObject(i);
                            projectList.add(project.getString("name"));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    projectAdapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(this, "Error loading projects", Toast.LENGTH_SHORT).show());
        requestQueue.add(request);
    }
}