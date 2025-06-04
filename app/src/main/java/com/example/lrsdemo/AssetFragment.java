package com.example.lrsdemo;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * AssetFragment: shows assets for a particular client + project.
 * Expects "client_id", "client_name", and "project_name" to have been passed in via setArguments().
 */
public class AssetFragment extends Fragment {

    private int clientId;
    private String clientName;
    private String projectName;

    public AssetFragment() {
        // Required empty public constructor
    }

    /**
     * If you need a factory method that creates an AssetFragment with arguments,
     * you can use this. However, since TabsActivity set the bundle via setArguments(...),
     * you don’t strictly need this newInstance(...) method. It’s included for completeness.
     */
    public static AssetFragment newInstance(int clientId, String clientName, String projectName) {
        AssetFragment fragment = new AssetFragment();
        Bundle args = new Bundle();
        args.putInt("client_id",clientId);
        args.putString("client_name",clientName);
        args.putString("project_name",projectName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1) Read the arguments that TabsActivity placed here
        Bundle args = getArguments();
        if (args != null) {
            clientId = args.getInt("client_id",-1);
            clientName = args.getString("client_name");
            projectName = args.getString("project_name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 2) Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_asset, container, false);

        FloatingActionButton fabAddAsset = view.findViewById(R.id.fabAddAsset);
        fabAddAsset.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddAssetActivity.class);
            intent.putExtra("client_id", clientId);
            intent.putExtra("client_name", clientName);
            intent.putExtra("project_name", projectName);
            startActivity(intent);
        });
        return view;
    }


}