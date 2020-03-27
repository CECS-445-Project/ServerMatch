//Creator: Juan Pasillas
package com.example.servermatch.cecs445.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.repositories.MenuItemRepo;
import com.example.servermatch.cecs445.ui.menu.MenuViewModel;
import com.example.servermatch.cecs445.ui.setuprestaurant.SetupRestaurant;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.List;

import javax.annotation.Nullable;

import static android.content.Context.MODE_PRIVATE;

public class DialogLogout extends DialogFragment {

    private static final String TAG = "DialogLogout";

    private TextView mActionCancel;
    private TextView mActionLogout;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private MenuViewModel menuViewModel;
    SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflator, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflator.inflate(R.layout.dialog_logout, container, false);
        //setting view background to transparent to use rounded window
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mActionLogout = view.findViewById(R.id.logout_action_logout);
        mActionCancel = view.findViewById(R.id.logout_action_cancel);
        menuViewModel = new ViewModelProvider(this.getActivity()).get(MenuViewModel.class);
        menuViewModel.init();

        mActionLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("testLogout", "DialogLogout: LOGOUT");
                prefs = getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("loggedIn", false);
                editor.putBoolean("setupNavHeader", true);
                editor.apply();
                Log.d(TAG, "DiaglLogout: " + mAuth.getCurrentUser().getEmail() + " signed out");
                menuViewModel = null;
                setArguments(null);
                MenuItemRepo.dataSet.clear();
                startActivity(new Intent(getActivity(), SetupRestaurant.class));
                getDialog().dismiss();
                Toast.makeText(getContext(), "Signed Out", Toast.LENGTH_SHORT).show();
            }
        });

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("testLogout", "DialogLogout: CANCEL");
                getDialog().dismiss();
            }
        });

        return view;
    }
}
