package com.radya.sfa.view.contact.add;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.radya.sfa.BR;
import com.radya.sfa.R;
import com.radya.sfa.view.GenericViewHolder;
import com.radya.sfa.view.invoice.master.InvoiceBank;
import com.radya.sfa.view.invoice.master.InvoiceBankDataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aderifaldi on 2018-01-04.
 */

public class StoreStatusAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    private List<StoreStatus> listItem;
    private AdapterView.OnItemClickListener onItemClickListener;
    private ViewDataBinding binding;

    public StoreStatusAdapter() {
        listItem = new ArrayList<>();
    }

    public List<StoreStatus> getData() {
        return listItem;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.store_status_item, parent, false);
        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        StoreStatus itemData = listItem.get(position);
        StoreStatusDataBinding dataBinding = new StoreStatusDataBinding(itemData);
        holder.bindModel(BR.storeStatusItem, dataBinding, position, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
