/**
 * @author Andrew Delgado
 */
package com.example.servermatch.cecs445.ui.description;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.servermatch.cecs445.R;
import com.squareup.picasso.Picasso;

public class DescriptionFragment extends Fragment {

    private View view;
    private ImageView mImageMenuItem;
    private TextView mNameMenuItem;
    private TextView mCostMenuItem;
    private TextView mDescriptionMenuItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu_item_description,container,false);

        mImageMenuItem = view.findViewById(R.id.item_image_description);
        mNameMenuItem = view.findViewById(R.id.item_name_description);
        mCostMenuItem = view.findViewById(R.id.item_cost_description);
        mDescriptionMenuItem = view.findViewById(R.id.item_description);

        initItemInfo();

        return view;
    }

    private void initItemInfo(){
        Bundle bundle = getArguments();
        if(bundle != null){


            Picasso.get().load(bundle.get("itemUrl").toString()).fit().into(mImageMenuItem);
            mNameMenuItem.setText(bundle.get("itemName").toString());
            mCostMenuItem.setText("$" + String.format("%.2f",bundle.get("itemCost")));
            mDescriptionMenuItem.setText(bundle.get("itemDescription").toString());
        }
    }
}
