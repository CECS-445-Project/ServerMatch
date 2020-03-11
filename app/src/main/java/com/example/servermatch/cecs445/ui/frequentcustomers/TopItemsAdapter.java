/**
 * author: Howard Chen
 */
package com.example.servermatch.cecs445.ui.frequentcustomers;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.ui.menu.BillViewModel;

import java.util.List;

public class TopItemsAdapter extends RecyclerView.Adapter<TopItemsAdapter.ViewHolder>{

    private static final String TAG = "TopItemsAdapter";
    private List<MenuItem> mItems;
    private String email;
    private Context mContext;
    private FrequentCustomersViewModel mFrequentCustomersViewModel;
    private BillViewModel mBillViewModel;

    public TopItemsAdapter(FrequentCustomersFragment frequentCustomersFragment, FrequentCustomersViewModel mFrequentCustomersViewModel, Context mContext, List<MenuItem> mItems) {
        this.mFrequentCustomersViewModel = mFrequentCustomersViewModel;
        this.mContext = mContext;
        this.mItems = mItems;
    }

    public TopItemsAdapter(BillViewModel billViewModel, FrequentCustomersViewModel mFrequentCustomersViewModel, Context mContext, List<MenuItem> mItems) {
        mBillViewModel = billViewModel;
        this.mFrequentCustomersViewModel = mFrequentCustomersViewModel;
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_listitem_top_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //Log.d(TAG, "onBindViewHolder: called.");
        ((TopItemsAdapter.ViewHolder)holder).bindView(position);

        ((ViewHolder)holder).parentLayout.setOnClickListener(v -> {
            Log.d(TAG,"top item clicked" + mItems.get(position).toString());
            mBillViewModel.addNewValue(mItems.get(position));


        });

        RequestOptions defaultOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(mItems.get(position).getImage())
                .into(((TopItemsAdapter.ViewHolder)holder).foodImageView);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView foodImageView;
        LinearLayout parentLayout;
        TextView textViewItemName;
        TextView textViewItemDesc;
        TextView textViewItemPrice;

        public ViewHolder(View itemView){
            super(itemView);

            foodImageView = itemView.findViewById(R.id.iv_food_image);
            parentLayout = itemView.findViewById(R.id.parent_layout_top_items);
            textViewItemName = itemView.findViewById(R.id.tv_menu_item_name);
            textViewItemDesc = itemView.findViewById(R.id.tv_menu_item_description);
            textViewItemPrice = itemView.findViewById(R.id.tv_menu_item_price);


        }

        public void bindView(int position){
            //Log.d(TAG, "bindView: "+ mItems.get(position).getItemName());
            textViewItemName.setText(mItems.get(position).getItemName());
            textViewItemDesc.setText(mItems.get(position).getItemDesc());
            textViewItemPrice.setText(mItems.get(position).getItemCost().toString());

        }
    }
}
