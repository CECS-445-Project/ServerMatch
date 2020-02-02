/**
 * author: Howard Chen
 */
package com.example.servermatch.cecs445.ui.frequentcustomers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servermatch.cecs445.R;

import java.util.ArrayList;

public class TopItemsAdapter extends RecyclerView.Adapter<TopItemsAdapter.ViewHolder>{

    public static final String TAG = "TopItemsRecyclerView";
    private ArrayList<String> mItemNames;
    private Context mContext;

    public TopItemsAdapter(ArrayList<String> mItemNames, Context mContext) {
        this.mItemNames = mItemNames;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_top_items, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

    }

    @Override
    public int getItemCount() {
        return mItemNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImageView;
        LinearLayout parentLayout;

        public ViewHolder(View itemView){
            super(itemView);
            foodImageView = itemView.findViewById(R.id.iv_pizza);
            parentLayout = itemView.findViewById(R.id.parent_layout_top_items);
        }

    }
}
