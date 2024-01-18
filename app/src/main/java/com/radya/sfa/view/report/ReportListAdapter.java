package com.radya.sfa.view.report;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.radya.sfa.BR;
import com.radya.sfa.R;
import com.radya.sfa.view.GenericViewHolder;
import com.radya.sfa.view.contact.Contact;
import com.radya.sfa.view.contact.list.ContactListDataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aderifaldi on 2018-01-04.
 */

public class ReportListAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    private List<Report> listItem;
    private AdapterView.OnItemClickListener onItemClickListener;
    private ViewDataBinding binding;

    public ReportListAdapter() {
        listItem = new ArrayList<>();
    }

    public List<Report> getData() {
        return listItem;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.report_item, parent, false);
        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        Report itemData = listItem.get(position);
        ReportListDataBinding dataBinding = new ReportListDataBinding(itemData);
        holder.bindModel(BR.reportItem, dataBinding, position, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
