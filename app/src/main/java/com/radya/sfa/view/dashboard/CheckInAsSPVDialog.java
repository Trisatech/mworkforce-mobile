package com.radya.sfa.view.dashboard;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.radya.sfa.R;
import com.radya.sfa.view.agent.Agent;
import com.radya.sfa.view.agent.AgentListSelectorDialog;
import com.radya.sfa.view.home.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-03-12.
 */

public class CheckInAsSPVDialog extends DialogFragment {

    Unbinder unbinder;

    @BindView(R.id.txtAgent)
    TextView txtAgent;
    @BindView(R.id.checkBoxWithSales)
    AppCompatCheckBox checkBoxWithSales;
    @BindView(R.id.containerSelectSales)
    RelativeLayout containerSelectSales;

    private Agent selectedAgent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.checkin_as_spv_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        initSelectedAgent();

        checkBoxWithSales.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    containerSelectSales.setVisibility(View.VISIBLE);
                }else {
                    containerSelectSales.setVisibility(View.GONE);
                }
            }
        });

        return view;

    }

    private void initView() {
        containerSelectSales.setVisibility(View.GONE);
    }

    private void initSelectedAgent() {
        selectedAgent = ((HomeActivity) getActivity()).getAgent();
        if (selectedAgent != null) {
            txtAgent.setText(selectedAgent.getUser_name());
        }
    }

    public void setAgent(String agentName) {
        txtAgent.setText(agentName);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnClose, R.id.txtAgent, R.id.btnAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                getDialog().dismiss();
                break;
            case R.id.txtAgent:
                AgentListSelectorDialog agentListSelectorDialog = new AgentListSelectorDialog();
                agentListSelectorDialog.setCancelable(false);
                agentListSelectorDialog.show(getFragmentManager(), "");
                break;
            case R.id.btnAdd:
                ((HomeActivity) getActivity()).checkInAsSPV();
                getDialog().dismiss();
                break;
        }
    }
}
