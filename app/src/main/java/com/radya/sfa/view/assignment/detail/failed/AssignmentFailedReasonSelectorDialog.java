package com.radya.sfa.view.assignment.detail.failed;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.view.ListView;
import com.radya.sfa.view.assignment.AssignmentViewModel;
import com.radya.sfa.view.assignment.detail.AssignmentDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-03-12.
 */

public class AssignmentFailedReasonSelectorDialog extends DialogFragment implements ListView {

    Unbinder unbinder;
    @BindView(R.id.btnClose)
    ImageView btnClose;
    @BindView(R.id.listPaymentMethod)
    RecyclerView listPaymentMethod;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private LinearLayoutManager linearLayoutManager;
    private AssignmentFailedReasonAdapter adapter;

    private List<AssignmentFailedReason.AssignmentReason> failedReason;
    private AssignmentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.invoice_payment_method_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViewModel();

        return view;

    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);
        initView();
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

        adapter = new AssignmentFailedReasonAdapter();
        linearLayoutManager = new LinearLayoutManager(getActivity());

        listPaymentMethod.setAdapter(adapter);
        listPaymentMethod.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AssignmentFailedReason.AssignmentReason item = adapter.getData().get(i);
                ((AssignmentDetailActivity)getActivity()).setAssignmentReason(item);
                getDialog().dismiss();
            }
        });

        loadData();
    }

    @Override
    public void loadData() {
        failedReason = new ArrayList<>();

        progressLoading.setVisibility(View.VISIBLE);

        viewModel.assignmentFailedReason(NetworkUtils.getConnectionManager());
        viewModel.getAssignmentReasonResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                AssignmentFailedReason response = (AssignmentFailedReason) apiResponse.getData();

                if (response != null){
                    if (response.getData().size() != 0){
                        for (int i = 0; i < response.getData().size(); i++) {
                            failedReason.add(response.getData().get(i));
                        }

                        AssignmentFailedReason.AssignmentReason reasonPlus
                                = new AssignmentFailedReason.AssignmentReason("000","Lainnya");

                        failedReason.add(reasonPlus);

                        storeDataToList();
                    }
                }

            }
        });
    }

    @Override
    public void storeDataToList() {

        for (int i = 0; i < failedReason.size(); i++) {
            adapter.getData().add(failedReason.get(i));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }
        adapter.notifyDataSetChanged();

    }
}
