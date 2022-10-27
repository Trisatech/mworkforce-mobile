package com.radya.sfa.view.assignment.product;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.data.source.sync.SyncManager;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.assignment.Assignment;
import com.radya.sfa.view.assignment.AssignmentViewModel;
import com.radya.sfa.view.assignment.detail.AssignmentDetail;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class AssignmentProductFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.listInvoiceType)
    RecyclerView listInvoiceType;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private Bundle bundle;
    private Assignment assignmentDetail;

    private AssignmentViewModel viewModel;

    private AssignmentProductAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    public AssignmentProductFragment() {
        // Requires empty public constructor
    }

    public static AssignmentProductFragment newInstance() {
        return new AssignmentProductFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assignment_product_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViewModel();
        initData();
        initView();
        getProduct();
        return view;
    }

    private void initView() {
        adapter = new AssignmentProductAdapter(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());

        listInvoiceType.setAdapter(adapter);
        listInvoiceType.setLayoutManager(linearLayoutManager);
    }

    private void getProduct() {
        progressLoading.setVisibility(View.VISIBLE);
        viewModel.assignmentProductList(NetworkUtils.getConnectionManager());
        viewModel.getAsignmentProductResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                AssignmentProduct response = (AssignmentProduct) apiResponse.getData();

                if (response != null) {
                    if (response.getData().size() != 0){
                        for (int i = 0; i < response.getData().size(); i++) {
                            adapter.getData().add(response.getData().get(i));
                            adapter.notifyItemInserted(adapter.getData().size() - 1);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);
    }

    private void initData() {
        bundle = getArguments();
        if (bundle != null) {
            assignmentDetail = (Assignment) bundle.getSerializable(Constant.SERIALIZABLE_NAME);
        }
    }

    public void updateOrderQty(int position, int qty){
        adapter.getData().get(position).setOrderQty(qty);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnDone)
    public void onViewClicked() {
        progressLoading.setVisibility(View.VISIBLE);

        JsonArray jsonArray = new JsonArray();

        for (int i = 0; i < adapter.getData().size(); i++) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("product_code", adapter.getData().get(i).getProductCode());
            jsonObject.addProperty("product_name", adapter.getData().get(i).getProductName());
            jsonObject.addProperty("quantity", adapter.getData().get(i).getOrderQty());
            jsonObject.addProperty("product_amount", adapter.getData().get(i).getProductPrice());
            jsonObject.addProperty("discount", 0);

            jsonArray.add(jsonObject);
        }

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("assignment_id", assignmentDetail.getAssignmentId());
        jsonObject.addProperty("customer_id", assignmentDetail.getContact().getContact_id());
        jsonObject.add("products", jsonArray);

        viewModel.assignmentProductOrder(NetworkUtils.getConnectionManager(), jsonObject);
        viewModel.getAssignmentProductOrderResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);

                BaseModel response = (BaseModel) apiResponse.getData();

                if (response != null){
                    ToastUtils.showToast(response.getAlert().getMessage());
                    ((AssignmentProductActivity)getActivity()).back();
                }else {
                    SyncManager.storeSync(MyApplication.obtainLocalDB(),
                            assignmentDetail.getAssignmentId(),
                            Constant.SyncType.ORDER, jsonObject.toString(),
                            0);
                }

            }
        });

    }
}
