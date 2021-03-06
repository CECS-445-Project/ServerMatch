/**
 * @author Andrew Delgado
 */
package com.example.servermatch.cecs445.Utils;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.ui.description.DescriptionFragment;
import com.example.servermatch.cecs445.ui.menu.BillViewModel;
import com.example.servermatch.cecs445.ui.menu.MenuViewModel;
import com.example.servermatch.cecs445.models.MenuItem;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuRecyclerAdapter extends RecyclerView.Adapter {

    private List<MenuItem> mMenuItem;
    private Context mContext;
    private static final String TAG = "MenuRecyclerAdapter";
    private MenuViewModel mMenuViewModel;
    private BillViewModel mBillViewModel;
    private Context thisContext;

    public MenuRecyclerAdapter(Context ft, MenuViewModel menuViewModel, BillViewModel billViewModel, Context context, List<MenuItem> menuItems){
        thisContext = ft;
        mMenuViewModel = menuViewModel;
        mContext = context;
        mMenuItem = menuItems;
        mBillViewModel = billViewModel;
    }


    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_menu_list_item,parent,false);

        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        ((ViewHolder) holder).parent_layout.setOnClickListener(v -> {
            Log.d(TAG, "onClick: clicked on: " + mMenuItem.get(position).toString());
            mBillViewModel.addNewValue(mMenuItem.get(position));
        });

        ((ViewHolder)holder).parent_layout.setOnLongClickListener(v -> {
            Log.d(TAG, "onLongClick: clicked on: " + mMenuItem.get(position).toString());


            DescriptionFragment descriptionFragment = new DescriptionFragment();
            Bundle bundle = new Bundle();

            bundle.putString("itemName",mMenuItem.get(position).getItemName());
            bundle.putDouble("itemCost",mMenuItem.get(position).getItemCost());
            bundle.putString("itemDescription",mMenuItem.get(position).getItemDesc());
            bundle.putString("itemUrl",mMenuItem.get(position).getImage());
            bundle.putStringArrayList("tags", (ArrayList<String>) mMenuItem.get(position).getTags());
            descriptionFragment.setArguments(bundle);

            FragmentTransaction transaction = ((AppCompatActivity)thisContext).getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right,R.anim.slide_in_right,R.anim.slide_out_right);

            transaction.replace(R.id.nav_host_fragment,descriptionFragment);
            transaction.addToBackStack(null);
            transaction.commit();

           return true;
        });



        ((ViewHolder)holder).bindView(position);

        RequestOptions defaultOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(mMenuItem.get(position).getImage())
                .into(((ViewHolder)holder).mItemImage);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mMenuItem.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mItemImage;
        private TextView mItemText;
        private TextView mItemCost;
        private MaterialCardView parent_layout;

        public ViewHolder(View itemView){
            super(itemView);
            mItemImage = itemView.findViewById(R.id.imageView4);
            mItemText = itemView.findViewById(R.id.item_name);
            mItemCost = itemView.findViewById(R.id.item_cost);
            parent_layout = itemView.findViewById(R.id.material_parent_layout);
        }

        public void bindView(int position){
            mItemText.setText(mMenuItem.get(position).getItemName());
            mItemCost.setText(String.format("$%.2f", mMenuItem.get(position).getItemCost()));
        }
    }
}
