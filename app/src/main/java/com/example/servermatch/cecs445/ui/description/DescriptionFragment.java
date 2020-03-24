/**
 * @author Andrew Delgado
 */
package com.example.servermatch.cecs445.ui.description;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.ui.menu.MenuFragment;
import com.squareup.picasso.Picasso;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DescriptionFragment extends Fragment {

    private View view;
    private ImageView mImageMenuItem;
    private TextView mNameMenuItem;
    private TextView mCostMenuItem;
    private TextView mDescriptionMenuItem;
    private TextView mTags;
    private Button mDoneButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu_item_description,container,false);

        mImageMenuItem = view.findViewById(R.id.item_image_description);
        mNameMenuItem = view.findViewById(R.id.item_name_description);
        mCostMenuItem = view.findViewById(R.id.item_cost_description);
        mDescriptionMenuItem = view.findViewById(R.id.item_description);
        mTags = view.findViewById(R.id.description_tags);
        mDoneButton = view.findViewById(R.id.description_back);

        initItemInfo();
        backButtonListener();

        return view;
    }

    private void initItemInfo(){
        Bundle bundle = getArguments();
        if(bundle != null){

            Picasso.get().load(bundle.get("itemUrl").toString()).into(mImageMenuItem);
            mNameMenuItem.setText(bundle.get("itemName").toString());
            mCostMenuItem.setText("$" + String.format("%.2f",bundle.get("itemCost")));
            mDescriptionMenuItem.setText(bundle.get("itemDescription").toString());
            StringBuilder tag = new StringBuilder();
            tag.append("Tags: ");

            List<String> tagsFromBundle = bundle.getStringArrayList("tags");
            Iterator i = tagsFromBundle.iterator();

            while(i.hasNext()){
                tag.append(i.next());
                if(i.hasNext()) tag.append(", ");
            }

            mTags.setText(tag);
        }
    }

    private void backButtonListener(){
        mDoneButton.setOnClickListener(v -> {

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            //transaction.setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_down, R.anim.slide_in_up,R.anim.slide_out_down);
            transaction.replace(R.id.nav_host_fragment, new MenuFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }
}
