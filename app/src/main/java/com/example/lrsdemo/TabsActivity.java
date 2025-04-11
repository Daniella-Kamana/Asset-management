package com.example.lrsdemo;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lrsdemo.databinding.ActivityTabsBinding;

public class TabsActivity extends AppCompatActivity {

    TextView tabAsset, tabExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tabs);

        tabAsset = findViewById(R.id.tabAsset) ;
        tabExpense = findViewById(R.id.tabExpense);

        loadFragment(new AssetFragment());

        tabAsset.setOnClickListener(v -> {
            loadFragment(new AssetFragment());
            tabAsset.setBackgroundColor(0xFF9B51E0);
            tabExpense.setBackgroundColor(0xFF7F00FF);
        });

        tabExpense.setOnClickListener( v ->{
            loadFragment(new ExpenseFragment());
            tabExpense.setBackgroundColor(0xFF9B51E0);
            tabAsset.setBackgroundColor(0xFF7F00FF);
        });

    }

    private void loadFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}