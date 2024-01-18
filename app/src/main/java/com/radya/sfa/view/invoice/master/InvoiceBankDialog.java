package com.radya.sfa.view.invoice.master;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.view.ListView;
import com.radya.sfa.view.invoice.InvoiceActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-03-12.
 */

public class InvoiceBankDialog extends DialogFragment implements ListView{

    Unbinder unbinder;
    @BindView(R.id.btnClose)
    ImageView btnClose;
    @BindView(R.id.listPaymentMethod)
    RecyclerView listPaymentMethod;

    private LinearLayoutManager linearLayoutManager;
    private InvoiceBankAdapter adapter;

    private List<InvoiceBank> bankAccounts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.invoice_payment_method_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();

        return view;

    }

    private void initView() {
        setupList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                getDialog().dismiss();
                break;
        }
    }

    @Override
    public void setupList() {

        adapter = new InvoiceBankAdapter();
        linearLayoutManager = new LinearLayoutManager(getActivity());

        listPaymentMethod.setAdapter(adapter);
        listPaymentMethod.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InvoiceBank bank = adapter.getData().get(i);
                ((InvoiceActivity)getActivity()).setBankAccount(bank);

                getDialog().dismiss();
            }
        });

        loadData();
    }

    @Override
    public void loadData() {
        bankAccounts = new ArrayList<>();
        bankAccounts.add(new InvoiceBank(Constant.Data.BankAccount.BRI_CODE, Constant.Data.BankAccount.BRI));
        bankAccounts.add(new InvoiceBank(Constant.Data.BankAccount.MANDIRI_CODE, Constant.Data.BankAccount.MANDIRI));
        bankAccounts.add(new InvoiceBank(Constant.Data.BankAccount.BCA_CODE, Constant.Data.BankAccount.BCA));

        storeDataToList();
    }

    @Override
    public void storeDataToList() {

        for (int i = 0; i < bankAccounts.size(); i++) {
            adapter.getData().add(bankAccounts.get(i));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }
        adapter.notifyDataSetChanged();

    }
}
