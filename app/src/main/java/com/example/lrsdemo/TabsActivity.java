package com.example.lrsdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

public class TabsActivity extends AppCompatActivity {

    private int clientId;
    private String clientName;
    private String projectName;
    LinearLayout tabAsset, tabExpense;
    MaterialCardView assetIcon, expenseIcon;
    TextView assetLabel, expenseLabel;
    View tabIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        // 1) Read the Intent extras from LeadingPage
        clientId = getIntent().getIntExtra("client_id", -1);
        clientName = getIntent().getStringExtra("client_name");
        projectName = getIntent().getStringExtra("project_name");

        // If either is missing, you might want to bail out:
        if(clientId == -1 || clientName == null || projectName == null){
            Toast.makeText(this, "Missing client/project data",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 2) Now wire up your tabs and initial fragment
        tabAsset = findViewById(R.id.tabAsset);
        tabExpense = findViewById(R.id.tabExpense);
        assetIcon = findViewById(R.id.assetIcon);
        expenseIcon = findViewById(R.id.expanseIcon);
        assetLabel = tabAsset.findViewById(R.id.assetLabel);
        expenseLabel = tabExpense.findViewById(R.id.expenseLabel);
        tabIndicator = findViewById(R.id.tabIndicator);

        // Load initial tab
        selectTab("asset");

        tabAsset.setOnClickListener(v -> selectTab("asset"));
        tabExpense.setOnClickListener(v -> selectTab("expense"));
    }

    private void selectTab(String tabName) {
        if ("asset".equals(tabName)) {
            // 3) When loading AssetFragment, pass client & project in a Bundle
            AssetFragment assetFragment = new AssetFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("client_id", clientId);
            bundle.putString("client_name",clientName);
            bundle.putString("project_name", projectName);
            assetFragment.setArguments(bundle);

            loadFragment(new AssetFragment());

            assetIcon.setCardBackgroundColor(Color.parseColor("#0c7ff2")); // primary
            expenseIcon.setCardBackgroundColor(Color.parseColor("#9CA3AF")); // gray
            assetLabel.setTextColor(Color.parseColor("#0c7ff2")); // primary
            expenseLabel.setTextColor(Color.parseColor("#6B7280")); // gray_dark
            moveIndicatorTo(tabAsset);
        } else {
            // 4) When loading ExpenseFragment, pass the same extras if needed
            ExpenseFragment expenseFragment = new ExpenseFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("client_id",clientId);
            bundle.putString("client_name",clientName);
            bundle.putString("project_name",projectName);
            expenseFragment.setArguments(bundle);

            loadFragment(new ExpenseFragment());

            expenseIcon.setCardBackgroundColor(Color.parseColor("#0c7ff2")); // primary
            assetIcon.setCardBackgroundColor(Color.parseColor("#9CA3AF")); // gray
            expenseLabel.setTextColor(Color.parseColor("#0c7ff2")); // primary
            assetLabel.setTextColor(Color.parseColor("#6B7280")); // gray_dark
            moveIndicatorTo(tabExpense);
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void moveIndicatorTo(View tab) {
        tabIndicator.animate()
                .x(tab.getX() + tab.getWidth() / 2f - tabIndicator.getWidth() / 2f)
                .setDuration(200)
                .start();
    }
}
