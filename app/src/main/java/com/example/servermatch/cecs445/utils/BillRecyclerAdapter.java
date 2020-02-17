/**
 * @author Andrew Delgado
 */
package com.example.servermatch.cecs445.Utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.ui.menu.BillViewModel;
import com.example.servermatch.cecs445.ui.menu.TestData;

import java.util.ArrayList;
import java.util.List;

public class BillRecyclerAdapter extends RecyclerView.Adapter {

    private List<MenuItem> mMenuItems = new ArrayList<>();
    private BillViewModel mBillViewModel;
    private View mView;
    private static final String TAG = "BillRecyclerAdapter";

    public BillRecyclerAdapter(View view, BillViewModel billViewModel, List<MenuItem> billList){
        mView = view;
        mBillViewModel = billViewModel;
        mMenuItems = billList;
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
                .inflate(R.layout.fragment_bill_item,parent,false);

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
        ((ViewHolder)holder).bindView(position);

        //Increment Button
        ((ViewHolder)holder).mPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBillViewModel.addNewValue(mMenuItems.get(position));
            }
        });

        //Subtract Button
        ((ViewHolder)holder).mMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBillViewModel.removeValue(mMenuItems.get(position));
            }
        });
    }

    public void updateBill(){
        TextView billTotalText = mView.findViewById(R.id.bill_total);

        double billTotal = 0.00;

        for (MenuItem m:mMenuItems) {
            billTotal += m.getQuantity() * m.getItemCost();
        }

        billTotalText.setText("$" + (String.format("%.2f",billTotal)));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mMenuItems.size();
    }

    //private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mItemText;
        private TextView mItemQuantity;
        private TextView mItemCost;
        private AppCompatImageButton mPlusButton;
        private AppCompatImageButton mMinusButton;

        public ViewHolder(View itemView){
            super(itemView);
            mItemText = itemView.findViewById(R.id.bill_item_name);
            mItemQuantity = itemView.findViewById(R.id.item_quantity);
            mItemCost = itemView.findViewById(R.id.bill_item_cost);
            mPlusButton = itemView.findViewById(R.id.plus_button);
            mMinusButton = itemView.findViewById(R.id.minus_button);

        }

        public void bindView(int position){
            mItemText.setText(mMenuItems.get(position).getItemName());
            mItemQuantity.setText(String.valueOf(mMenuItems.get(position).getQuantity()));
            mItemCost.setText(String.format("%.2f",mMenuItems.get(position).getItemCost() * mMenuItems.get(position).getQuantity()));
        }
    }
}
