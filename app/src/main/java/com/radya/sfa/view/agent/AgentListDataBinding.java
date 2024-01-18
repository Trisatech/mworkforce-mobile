package com.radya.sfa.view.agent;

import androidx.databinding.BaseObservable;

public class AgentListDataBinding extends BaseObservable {

    private Agent data;

    public AgentListDataBinding(Agent data) {
        this.data = data;
    }

    public String getUser_id() {
        return data.getUser_id();
    }

    public String getUser_code() {
        return data.getUser_code();
    }

    public String getUser_name() {
        return data.getUser_name();
    }

    public String getUser_phone() {
        return data.getUser_phone();
    }

}
