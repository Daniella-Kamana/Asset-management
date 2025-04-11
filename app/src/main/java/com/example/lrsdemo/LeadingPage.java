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

public class LeadingPage extends AppCompatActivity {

    Button btnGo;
    Spinner selectClient, selectProject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leading_page);

        selectClient = findViewById(R.id.spinnerClient);
        selectProject = findViewById(R.id.spinnerProject);
        btnGo = findViewById(R.id.btnGo);

        btnGo.setOnClickListener( v ->{
            Intent intent = new Intent(LeadingPage.this, TabsActivity.class);
            startActivity(intent);
            finish();
        });

        // create ArrayAdapter for Spinners
        ArrayAdapter<CharSequence> adapterClients = ArrayAdapter.createFromResource(
                this, R.array.spinnerClients, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterProject = ArrayAdapter.createFromResource(
                this, R.array.spinnerProjects, android.R.layout.simple_spinner_item);

        // Set Adapters to Spinners
        selectClient.setAdapter(adapterClients);
        selectProject.setAdapter(adapterProject);

        selectClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedClient = parent.getItemAtPosition(position).toString();
                Toast.makeText(LeadingPage.this, "Selected Client: " + selectedClient, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        selectProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedProject = parent.getItemAtPosition(position).toString();
                Toast.makeText(LeadingPage.this, "Selected Project: "+ selectedProject, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}