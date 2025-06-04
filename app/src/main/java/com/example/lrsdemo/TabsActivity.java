package com.example.lrsdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

public class TabsActivity extends AppCompatActivity {

    LinearLayout tabAsset, tabExpense;
    MaterialCardView assetIcon, expenseIcon;
    TextView assetLabel, expenseLabel;
    View tabIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

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
            loadFragment(new AssetFragment());

            assetIcon.setCardBackgroundColor(Color.parseColor("#0c7ff2")); // primary
            expenseIcon.setCardBackgroundColor(Color.parseColor("#9CA3AF")); // gray

            assetLabel.setTextColor(Color.parseColor("#0c7ff2")); // primary
            expenseLabel.setTextColor(Color.parseColor("#6B7280")); // gray_dark

            moveIndicatorTo(tabAsset);
        } else {
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
