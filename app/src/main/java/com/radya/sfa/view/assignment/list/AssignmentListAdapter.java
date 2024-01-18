package com.radya.sfa.view.assignment.list;


import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.radya.sfa.BR;
import com.radya.sfa.R;
import com.radya.sfa.view.GenericViewHolder;
import com.radya.sfa.view.assignment.Assignment;
import com.radya.sfa.view.assignment.detail.AssignmentDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aderifaldi on 2018-01-04.
 */

public class AssignmentListAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    private List<Assignment> listItem;
    private AdapterView.OnItemClickListener onItemClickListener;
    private ViewDataBinding binding;

    public AssignmentListAdapter() {
        listItem = new ArrayList<>();
    }

    public List<Assignment> getData() {
        return listItem;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.assignment_list_item, parent, false);
        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        Assignment itemData = listItem.get(position);
        AssignmentListDataBinding dataBinding = new AssignmentListDataBinding(itemData);
        holder.bindModel(BR.taskItem, dataBinding, position, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
