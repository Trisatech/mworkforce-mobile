package com.radya.sfa.view.assignment.act;

import android.databinding.BaseObservable;

import com.radya.sfa.util.StringUtils;
import com.radya.sfa.view.invoice.Invoice;

public class AssignmentActDataBinding extends BaseObservable {

    private AssignmentAct data;

    public AssignmentActDataBinding(AssignmentAct data) {
        this.data = data;
    }

    public String getName() {
        return data.getActName();
    }

    public boolean isSelected() {
        return data.isSelected();
    }

}
