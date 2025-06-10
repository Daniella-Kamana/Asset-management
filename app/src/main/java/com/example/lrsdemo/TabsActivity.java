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
    private LinearLayout tabAsset, tabExpense;
    private MaterialCardView assetIcon, expenseIcon;
    private TextView assetLabel, expenseLabel;
    private View tabIndicator;

    private enum TAB {ASSET, EXPENSE}

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

        // Set listener
        tabAsset.setOnClickListener(v -> selectTab(TAB.ASSET));
        tabExpense.setOnClickListener(v -> selectTab(TAB.EXPENSE));

        // Load initial tab
        selectTab(TAB.ASSET);
    }

    private void selectTab(TAB which) {
        Fragment frag;
        if (which == TAB.ASSET) {
            // 3) When loading AssetFragment, pass client & project in a Bundle
            AssetFragment assetFragment = new AssetFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("client_id", clientId);
            bundle.putString("client_name",clientName);
            bundle.putString("project_name", projectName);
            assetFragment.setArguments(bundle);
            frag = assetFragment;

            // Style the tabs
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
            frag = expenseFragment;

            expenseIcon.setCardBackgroundColor(Color.parseColor("#0c7ff2")); // primary
            assetIcon.setCardBackgroundColor(Color.parseColor("#9CA3AF")); // gray
            expenseLabel.setTextColor(Color.parseColor("#0c7ff2")); // primary
            assetLabel.setTextColor(Color.parseColor("#6B7280")); // gray_dark
            moveIndicatorTo(tabExpense);
        }

        // Swap in the fragment you actually configured
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer,frag)
                .commit();
    }
    private void moveIndicatorTo(View tab) {
        float targetX = tab.getX() + (tab.getWidth() - tabIndicator.getWidth())/2f;
        tabIndicator.animate()
                .x(targetX)
                .setDuration(200)
                .start();
    }
}
