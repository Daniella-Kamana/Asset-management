package com.example.lrsdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerView.Adapter to bind a list of Asset objects to the item_asset.xml layout.
 */
public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.AssetViewHolder> {
    private final Context context;
    private final List<Asset> assetList;

    public AssetAdapter(Context context, List<Asset> assetList){
        this.context = context;
        this.assetList = assetList;
    }

    @NonNull
    @Override
    public AssetAdapter.AssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_asset, parent, false);
        return new AssetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssetViewHolder holder, int position) {
        Asset current = assetList.get(position);
        // 1) Asset image
        holder.imageAsset.setImageResource(current.imageResId);
        // 2) Asset name
        holder.textAssetName.setText(current.name);
        // 3) Category subtitle
        holder.textAssetCategory.setText("Category: " + current.category);
        // 4) Quantity
        holder.textQuantity.setText(String.valueOf(current.quantity));
    }

    public int getItemCount(){
        return assetList.size();
    }

    static class AssetViewHolder extends RecyclerView.ViewHolder{
        ImageView imageAsset;
        TextView textAssetName, textAssetCategory, textQuantity;

        AssetViewHolder(@NonNull View itemsView){
            super(itemsView);
            imageAsset = itemsView.findViewById(R.id.imageAsset);
            textAssetName = itemsView.findViewById(R.id.textAssetName);
            textAssetCategory = itemsView.findViewById(R.id.textAssetCategory);
            textQuantity = itemsView.findViewById(R.id.textQuantity);
        }
    }
}
