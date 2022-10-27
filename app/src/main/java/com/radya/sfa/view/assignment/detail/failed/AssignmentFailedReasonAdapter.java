package com.radya.sfa.view.assignment.detail.failed;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;

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

public class AssignmentFailedReasonAdapter extends RecyclerView.Adapter<GenericViewHolder> {
    private List<AssignmentFailedReason.AssignmentReason> listItem;
    private AdapterView.OnItemClickListener onItemClickListener;
    private ViewDataBinding binding;

    public AssignmentFailedReasonAdapter() {
        listItem = new ArrayList<>();
    }

    public List<AssignmentFailedReason.AssignmentReason> getData() {
        return listItem;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.assignment_failed_reason_item, parent, false);
        return new GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        AssignmentFailedReason.AssignmentReason itemData = listItem.get(position);
        AssignmentFailedReasonDataBinding dataBinding = new AssignmentFailedReasonDataBinding(itemData);
        holder.bindModel(BR.assignmentReasonItem, dataBinding, position, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
