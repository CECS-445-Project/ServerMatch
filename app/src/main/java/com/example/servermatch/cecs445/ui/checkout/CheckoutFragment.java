/**
 * @author Andrew Delgado
 */

package com.example.servermatch.cecs445.ui.checkout;
/*
@author -  Andrew Delgado and Howard Chen
 */
import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.servermatch.cecs445.R;
import com.example.servermatch.cecs445.Utils.CheckoutRecyclerAdapter;
import com.example.servermatch.cecs445.models.Bill;
import com.example.servermatch.cecs445.models.MenuItem;
import com.example.servermatch.cecs445.ui.frequentcustomers.FrequentCustomersFragment;
import com.example.servermatch.cecs445.ui.frequentcustomers.FrequentCustomersViewModel;
import com.example.servermatch.cecs445.ui.menu.BillViewModel;
import com.example.servermatch.cecs445.ui.menu.MenuFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kristijandraca.backgroundmaillibrary.BackgroundMail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CheckoutFragment extends Fragment {

    private CheckoutViewModel checkOutViewModel;
    private BillViewModel billViewModel;
    private FrequentCustomersViewModel frequentCustomersViewModel;
    private static String TAG = "CheckoutFragment";
    private View view;
    private Bill bill;
    private RecyclerView recyclerView;
    private TextView totalCost;
    private TextInputLayout mEmail;
    private Button mCheckoutButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_checkout,container,false);
        recyclerView = view.findViewById(R.id.recycler_view_checkout);
        totalCost = view.findViewById(R.id.checkout_total_cost);
        mEmail = view.findViewById(R.id.checkout_email);
        mCheckoutButton = view.findViewById(R.id.checkout_button);
        checkOutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);
        billViewModel = new ViewModelProvider(this).get(BillViewModel.class);
        frequentCustomersViewModel = new ViewModelProvider(this.getActivity()).get(FrequentCustomersViewModel.class);
        frequentCustomersViewModel.init();

        checkOutViewModel.init();
        billViewModel.init();
        checkoutButtonListener();
        setUpBill();
        initRecyclerView();
        checkForEmail2();

        return view;
    }

    private void checkForEmail2(){

        if (frequentCustomersViewModel.getCustomerEmail() != null & frequentCustomersViewModel.getCustomerEmail().getValue() != null){

            if(!frequentCustomersViewModel.getCustomerEmail().getValue().equals("no email")) {
                TextInputEditText edit = view.findViewById(R.id.checkout_edit_email);
                mEmail.setHintAnimationEnabled(false);
                edit.setText(frequentCustomersViewModel.getCustomerEmail().getValue());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void checkoutButtonListener(){
        mCheckoutButton.setOnClickListener(v -> {
            boolean validEmail = validateEmail();
            if(validEmail) {
                //get checkout time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String checkoutTime = dtf.format(now);
                String emailString = mEmail.getEditText().getText().toString().trim().toLowerCase();
                bill.setCustomerID(emailString);
                boolean successfulCheckout = checkOutViewModel.checkOutCustomer(getContext(), bill, checkoutTime);
                Log.d(TAG, mEmail.getEditText().toString());
                Log.d(TAG, bill.toString());


                sendEmail();


                billViewModel.clearBillItems();

                //This did not break it.
                frequentCustomersViewModel.setTopCustomerEmail("no email");

                MenuFragment menuFragment = new MenuFragment();
                Bundle args = new Bundle();
                args.putBoolean("CheckoutComplete", successfulCheckout);
                menuFragment.setArguments(args);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_right,R.anim.slide_out_right,R.anim.slide_in_right,R.anim.slide_out_right);
                transaction.replace(R.id.nav_host_fragment, menuFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                //goes back a MenuFragment

            }

        });
    }

    private void sendEmail(){
        String bill = createBill();

        BackgroundMail bm = new BackgroundMail(this.getContext());
        bm.setGmailUserName("ServerMatchApp@gmail.com");
        bm.setGmailPassword("");
        bm.setMailTo("andrewdelgado017@gmail.com");
        bm.setFormSubject("ServerMatch Receipt");
        bm.setFormBody("Hello " + this.bill.getCustomerID() +"!\n\nHere is your receipt: \n\n"
                + bill
                + "\n Total: $" + this.bill.getTotalCost()
                + "\n- Thank you");
        bm.send();
    }

    @SuppressLint("DefaultLocale")
    private String createBill(){
        StringBuilder billText = new StringBuilder();
        List<MenuItem> menuItems = bill.getBillItems();

        for(int i = 0; i < menuItems.size(); i++) {
            billText.append(menuItems.get(i).getItemName());
            int j = 25 - menuItems.get(i).getItemName().length();
            for(int k = j; k >0; k--){
                billText.append(" ");
            }
            billText.append(menuItems.get(i).getmIntQuantity())
            .append("X")
            .append(" ")
            .append(String.format("$%.2f",menuItems.get(i).getItemCost()))
            .append("\n");
        }
        return billText.toString();
    }


    public void initRecyclerView(){
        CheckoutRecyclerAdapter checkoutRecyclerAdapter = new CheckoutRecyclerAdapter(bill);
        recyclerView.setAdapter(checkoutRecyclerAdapter);
        RecyclerView.LayoutManager checkoutLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(checkoutLayoutManager);
    }


    public void setUpBill(){
        Bundle bundle = getArguments();

        if(bundle != null) {

            ArrayList<String> itemNames;
            ArrayList<String> itemQuantity;
            ArrayList<String> itemCost;
            ArrayList<String> itemImg;
            ArrayList<String> itemDesc;

            List<MenuItem> menuItems = new ArrayList<>();

            itemNames = (ArrayList) bundle.get("billItemNames");
            itemQuantity = (ArrayList) bundle.get("billItemQuantity");
            itemCost = (ArrayList) bundle.get("billItemCost");
            itemImg =  (ArrayList) bundle.get("billItemImage");
            itemDesc = (ArrayList) bundle.get("billItemDesc");

            for (int i = 0; i < itemNames.size(); i++) {
                menuItems.add(new MenuItem(
                        itemNames.get(i), itemDesc.get(i), Double.parseDouble(itemCost.get(i)),
                        itemImg.get(i), Integer.parseInt(itemQuantity.get(i))));
            }

            bill = new Bill((double)bundle.get("billTotal"),menuItems);
            totalCost.setText("$" + String.format("%.2f",bundle.get("billTotal")));
        }
    }

    private boolean validateEmail() {
        String emailInput = mEmail.getEditText().getText().toString().trim();
        if(emailInput.isEmpty()) {
            mEmail.setError("Field can't be empty");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            mEmail.setError("Invalid email address");
            return false;
        } else {
            mEmail.setError(null);
            return true;
        }
    }
}
