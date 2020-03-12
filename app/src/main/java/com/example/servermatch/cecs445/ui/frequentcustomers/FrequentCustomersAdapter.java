/**
 * author: Howard Chen
 */
package com.example.servermatch.cecs445.ui.frequentcustomers;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.models.Customer;

import java.util.List;

public class FrequentCustomersAdapter extends RecyclerView.Adapter<FrequentCustomersAdapter.ViewHolder>{

    private static final String TAG = "FrequentCustomersRecyclerView";
    private List<Customer> mFrequentCustomerNames;
    private Context mContext;
    private FrequentCustomersViewModel mFrequentCustomersViewModel;
    private static String currentEmail;


    public FrequentCustomersAdapter(FrequentCustomersViewModel mFrequentCustomersViewModel, Context context, List<Customer> mFrequentCustomerNames){
        this.mFrequentCustomersViewModel = mFrequentCustomersViewModel;
        this.mFrequentCustomerNames = mFrequentCustomerNames;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_frequent_customers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        ((FrequentCustomersAdapter.ViewHolder)holder).bindView(position);

        holder.customersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mFrequentCustomerNames.get(position));
                currentEmail = mFrequentCustomerNames.get(position).getEmail();
                mFrequentCustomersViewModel.setTopCustomerEmail(currentEmail);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFrequentCustomerNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button customersButton;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            customersButton = itemView.findViewById(R.id.bu_frequent_customers);
            parentLayout = itemView.findViewById(R.id.parent_layout_frequent_customers);
        }

        public void bindView(int position){

            String customerName = mFrequentCustomerNames.get(position).getFirstName() + " " +
                    mFrequentCustomerNames.get(position).getLastName();
            customersButton.setText(customerName);
        }

    }
}
