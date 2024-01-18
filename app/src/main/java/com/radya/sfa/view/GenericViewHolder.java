package com.radya.sfa.view;

import android.view.View;
import android.widget.AdapterView;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by aderifaldi on 2018-01-04.
 */

public class GenericViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private AdapterView.OnItemClickListener onItemClickListener;
    private ViewDataBinding binding;
    private View itemView;
    private int position;

    public GenericViewHolder(ViewDataBinding dataBinding) {
        super(dataBinding.getRoot());
        itemView = dataBinding.getRoot();
        binding = dataBinding;

        itemView.setOnClickListener(this);
    }

    public void bindModel(int modelType, Object obj, int i, AdapterView.OnItemClickListener onItemClick) {
        onItemClickListener = onItemClick;
        position = i;

        binding.setVariable(modelType, obj);
        binding.executePendingBindings();
    }

    @Override
    public void onClick(View view) {
        onItemClickListener.onItemClick(null, view, position, 0);
    }
}