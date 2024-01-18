package com.radya.sfa.view.assignment.act;


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
import com.radya.sfa.view.assignment.list.AssignmentListDataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aderifaldi on 2018-01-04.
 */

public class AssignmentActAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    private List<AssignmentAct> listItem;
    private AdapterView.OnItemClickListener onItemClickListener;
    private ViewDataBinding binding;

    public AssignmentActAdapter() {
        listItem = new ArrayList<>();
    }

    public List<AssignmentAct> getData() {
        return listItem;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.assignment_act_item, parent, false);
        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        AssignmentAct itemData = listItem.get(position);
        AssignmentActDataBinding dataBinding = new AssignmentActDataBinding(itemData);
        holder.bindModel(BR.actItem, dataBinding, position, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
