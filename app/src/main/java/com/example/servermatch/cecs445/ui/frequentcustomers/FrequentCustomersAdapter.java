/**
 * author: Howard Chen
 */
package com.example.servermatch.cecs445.ui.frequentcustomers;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servermatch.cecs445.R;

import java.util.ArrayList;

public class FrequentCustomersAdapter extends RecyclerView.Adapter<FrequentCustomersAdapter.ViewHolder>{

    private static final String TAG = "FrequentCustomersRecyclerView";
    private ArrayList<String> mCustomerNames;
    private Context mContext;

    public FrequentCustomersAdapter(ArrayList<String> mCustomerNames, Context mContext) {
        this.mCustomerNames = mCustomerNames;
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_frequent_customers, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.customersButton.setText(mCustomerNames.get(position));

        holder.customersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mCustomerNames.get(position));
                Toast.makeText(mContext, mCustomerNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCustomerNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Stores the itemView in a public final member variable that can be used
        // to access the context from any ViewHolder instance.

        Button customersButton;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            customersButton = itemView.findViewById(R.id.bu_frequent_customers);
            parentLayout = itemView.findViewById(R.id.parent_layout_frequent_customers);
        }

    }
}
