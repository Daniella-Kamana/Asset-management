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
    ArrayAdapter<Project> projectAdapter;
    ArrayAdapter<Client> clientAdapter;
    ArrayList<Client> clientList = new ArrayList<>();
    ArrayList<Project> projectList = new ArrayList<>();
    RequestQueue requestQueue;

    private static final String Base_URL = "http://10.0.2.2/loss_reduction_backend/";
    private static final String GET_CLIENTS_URL = Base_URL + "get_clients.php";
    private static final String GET_PROJECTS_URL = Base_URL + "get_projects.php?client_id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leading_page);

        selectClient = findViewById(R.id.spinnerClient);
        selectProject = findViewById(R.id.spinnerProject);
        btnGo = findViewById(R.id.btnGo);
        requestQueue = Volley.newRequestQueue(this);

        // 1) Set up spinner for clients
        clientAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                clientList
        );
        clientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectClient.setAdapter(clientAdapter);

        // 2) Set up spinner for projects
        projectAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                projectList
        );
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectProject.setAdapter(projectAdapter);

        // 3) Load clients from backend
        loadClients();

        // 4) When user picks a client, load that client’s projects
        selectClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Client selectedClient = clientList.get(position);
                loadProjects(selectedClient.id); // Load projects for selected client
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing
            }
        });

        selectProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(
                        LeadingPage.this,
                        "Selected Project: "+ projectList.get(position),
                        Toast.LENGTH_SHORT
                ).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // 5) “Go To” button now reads both spinners and navigates
        btnGo.setOnClickListener( v ->{
            Client selectedClientObj = (Client) selectClient.getSelectedItem();
            Project selectedProjectObj = (Project) selectProject.getSelectedItem();

            if(selectedClientObj == null || selectedProjectObj == null){
                Toast.makeText(
                        LeadingPage.this,
                        "Please select both a client and a project",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            Intent intent = new Intent(LeadingPage.this, TabsActivity.class);
            intent.putExtra("client_id", selectedClientObj.id);
            intent.putExtra("client_name", selectedClientObj.name);
            intent.putExtra("project_id", selectedProjectObj.id);
            intent.putExtra("project_name", selectedProjectObj.name);
            startActivity(intent);
            finish();
        });
    }

    private void loadClients() {
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                GET_CLIENTS_URL,
                null,
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
        String url = GET_PROJECTS_URL + clientId;
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    projectList.clear();
                    for(int i = 0; i <  response.length(); i++) {
                        try{
                            JSONObject project = response.getJSONObject(i);
                            int id = project.getInt("id");
                            String name = project.getString("name");
                            projectList.add(new Project(id,name));
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