package com.radya.sfa.view.agent;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.util.AppUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.view.ListView;
import com.radya.sfa.view.dashboard.DashboardViewModel;
import com.radya.sfa.view.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-03-12.
 */

public class AgentListSelectorDialog extends DialogFragment implements ListView {

    Unbinder unbinder;
    @BindView(R.id.btnClose)
    ImageView btnClose;
    @BindView(R.id.listAgent)
    RecyclerView listAgent;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private LinearLayoutManager linearLayoutManager;
    private AgentListAdapter adapter;

    private List<Agent> agentList;

    private DashboardViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.agent_list_selector_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViewModel();
        initView();

        return view;

    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
    }

    private void initView() {
        setupList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                getDialog().dismiss();
                break;
        }
    }

    @Override
    public void setupList() {

        adapter = new AgentListAdapter();
        linearLayoutManager = new LinearLayoutManager(getActivity());

        listAgent.setAdapter(adapter);
        listAgent.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Agent agent = adapter.getData().get(i);
                ((HomeActivity)getActivity()).setAgent(agent);
                getDialog().dismiss();
            }
        });

        loadData();
    }

    @Override
    public void loadData() {
        agentList = new ArrayList<>();
        AppUtils.showLoading(progressLoading);

        String agentRole = Constant.Data.Agent.SALES;

        viewModel.getAgentList(NetworkUtils.getConnectionManager(), agentRole);
        viewModel.getAgentListResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                AppUtils.dismissLoading(progressLoading);

                AgentList response = (AgentList) apiResponse.getData();
                if (response != null){

                    if (response.getData().size() != 0){
                        for (int i = 0; i < response.getData().size(); i++) {
                            agentList.add(response.getData().get(i));
                        }

                        storeDataToList();
                    }

                }
            }
        });

    }

    @Override
    public void storeDataToList() {

        for (int i = 0; i < agentList.size(); i++) {
            adapter.getData().add(agentList.get(i));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }
        adapter.notifyDataSetChanged();

    }

}
