package com.radya.sfa.view.invoice.payment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.radya.sfa.BR;
import com.radya.sfa.R;
import com.radya.sfa.view.GenericViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aderifaldi on 2018-01-04.
 */

public class InvoicePaymentAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    private List<InvoicePayment> listItem;
    private AdapterView.OnItemClickListener onItemClickListener;
    private ViewDataBinding binding;

    public InvoicePaymentAdapter() {
        listItem = new ArrayList<>();
    }

    public List<InvoicePayment> getData() {
        return listItem;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.invoice_payment_item, parent, false);
        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        InvoicePayment itemData = listItem.get(position);
        InvoicePaymentDataBinding dataBinding = new InvoicePaymentDataBinding(itemData);
        holder.bindModel(BR.paymentItem, dataBinding, position, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
