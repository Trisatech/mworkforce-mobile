package com.radya.sfa.view.assignment.act;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.R;
import com.radya.sfa.data.source.preference.PrefManager;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.data.source.sync.SyncManager;
import com.radya.sfa.util.AlertUtils;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.ListView;
import com.radya.sfa.view.assignment.Assignment;
import com.radya.sfa.view.assignment.AssignmentViewModel;
import com.radya.sfa.view.assignment.detail.AssignmentDetail;
import com.radya.sfa.view.assignment.product.AssignmentProductActivity;
import com.radya.sfa.view.assignment.survey.AssignmentSurveyActivity;
import com.radya.sfa.view.invoice.InvoiceActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class AssignmentActFragment extends Fragment implements ListView {

    Unbinder unbinder;
    @BindView(R.id.listInvoiceType)
    RecyclerView listInvoiceType;
    @BindView(R.id.btnDone)
    RelativeLayout btnDone;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private AssignmentActAdapter adapter;

    private List<AssignmentAct> activities;

    private Assignment assignmentDetail;

    private String taskId;

    private Bundle bundle;

    private AssignmentViewModel viewModel;

    private boolean isPaymentComplete, isOrderComplete, isSurveiComplete;

    public AssignmentActFragment() {
        // Requires empty public constructor
    }

    public static AssignmentActFragment newInstance() {
        return new AssignmentActFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assignment_act_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViewModel();
        initData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAssignmentDetail();
    }

    private void getAssignmentDetail() {
        progressLoading.setVisibility(View.VISIBLE);
        viewModel.getAssignmentDetail(NetworkUtils.getConnectionManager(), taskId);
        viewModel.getAssignmentDetailResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                Assignment data = assignmentDetail;
                if (data != null) {
                    if (data.getPayments().size() != 0) {
                        isPaymentComplete = true;

                        adapter.getData().get(0).setSelected(true);
                        adapter.notifyDataSetChanged();
                    }

                    if (data.getOrders().size() != 0) {
                        isOrderComplete = true;

                        adapter.getData().get(1).setSelected(true);
                        adapter.notifyDataSetChanged();
                    }

                    if (isPaymentComplete && isOrderComplete && isSurveiComplete) {
                        btnDone.setVisibility(View.GONE);
                    } else {
                        btnDone.setVisibility(View.VISIBLE);
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
            taskId = assignmentDetail.getAssignmentId();
            setupList();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setupList() {
        adapter = new AssignmentActAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        listInvoiceType.setAdapter(adapter);
        listInvoiceType.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                AssignmentAct act = adapter.getData().get(i);

                if (!act.isSelected()) {
                    if (act.getActId() == Constant.Data.AssignmentActivity.INVOICE) {
                        bundle = new Bundle();
                        bundle.putSerializable(Constant.SERIALIZABLE_NAME, assignmentDetail);
                        IntentUtils.intentTo(getActivity(), InvoiceActivity.class, false, assignmentDetail);
                    } else if (act.getActId() == Constant.Data.AssignmentActivity.PRODUCT) {
                        bundle = new Bundle();
                        bundle.putSerializable(Constant.SERIALIZABLE_NAME, assignmentDetail);
                        IntentUtils.intentTo(getActivity(), AssignmentProductActivity.class, false, assignmentDetail);
                    } else {
                        bundle = new Bundle();
                        bundle.putSerializable(Constant.SERIALIZABLE_NAME, assignmentDetail.getSurvey().get(0));
                        IntentUtils.intentTo(getActivity(), AssignmentSurveyActivity.class, false, bundle);
                    }
                }

            }
        });

        loadData();
    }

    @Override
    public void loadData() {
        activities = new ArrayList<>();

        int survey;
        int invoices;

        if (assignmentDetail.getSurvey() != null) {
            survey = assignmentDetail.getSurvey().size();
        } else {
            survey = 0;
        }

        if (assignmentDetail.getInvoices() != null) {
            invoices = assignmentDetail.getInvoices().size();
        } else {
            invoices = 0;
        }

        if (invoices != 0) {
            activities.add(new AssignmentAct(Constant.Data.AssignmentActivity.INVOICE, "Proses invoice", false));
        }

        if (survey != 0) {
            String surveyTitle = assignmentDetail.getSurvey().get(0).getName();
            activities.add(new AssignmentAct(Constant.Data.AssignmentActivity.SURVEY, surveyTitle, false));
        }

        activities.add(new AssignmentAct(Constant.Data.AssignmentActivity.PRODUCT, "Order produk", false));

        storeDataToList();
    }

    @Override
    public void storeDataToList() {

        for (int i = 0; i < activities.size(); i++) {
            adapter.getData().add(activities.get(i));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }
        adapter.notifyDataSetChanged();

    }

    @OnClick(R.id.btnDone)
    public void onViewClicked() {
        AlertUtils alertUtils = new AlertUtils(getActivity());
        alertUtils.showAlert(getString(R.string.labelConfirmCompleteTask), new AlertUtils.negativeButton() {
            @Override
            public void onNo(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        }, new AlertUtils.positiveButton() {
            @Override
            public void onYes(DialogInterface dialogInterface) {
                completeTask();
            }
        }, Constant.Data.NEGATIVE_BUTTON, Constant.Data.POSITIVE_BUTTON);
    }

    private void completeTask() {

        progressLoading.setVisibility(View.VISIBLE);
        setButtonDisable();

        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), "" + MyApplication.getInstance().latitude());
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), "" + MyApplication.getInstance().longitude());
        RequestBody ProcessedTime = RequestBody.create(MediaType.parse("text/plain"), DateUtils.getCurrentTime(Constant.DateFormat.SERVER));
        RequestBody assignmentComplete = RequestBody.create(MediaType.parse("text/plain"), Constant.Data.AssignmentCode.TASK_COMPLETED);


        viewModel.assignmentComplete(NetworkUtils.getConnectionManager(),
                assignmentDetail.getAssignmentId(), latitude, longitude, ProcessedTime, assignmentComplete, true);
        viewModel.getAssignmentCompleteResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                setButtonEnable();
                BaseModel response = (BaseModel) apiResponse.getData();

                getActivity().onBackPressed();

                if (response != null) {
                    ToastUtils.showToast(getString(R.string.labelAssignmentFinished));
                } else {
                    storeAssignmentCompleteToSync();
                }
            }
        });

    }

    private void storeAssignmentCompleteToSync() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("latitude", "" + MyApplication.getInstance().latitude());
        jsonObject.addProperty("longitude", "" + MyApplication.getInstance().longitude());
        jsonObject.addProperty("processed_time", DateUtils.getCurrentTime(Constant.DateFormat.SERVER));
        jsonObject.addProperty("assignment_complete", Constant.Data.AssignmentCode.TASK_COMPLETED);

        SyncManager.storeSync(MyApplication.obtainLocalDB(),taskId,
                Constant.SyncType.ASSIGNMENT_COMPLETE, jsonObject.toString(), 1);
    }

    private void setButtonEnable() {
        btnDone.setEnabled(true);
    }

    private void setButtonDisable() {
        btnDone.setEnabled(false);
    }

}
