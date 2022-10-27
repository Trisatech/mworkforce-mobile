package com.radya.sfa.view.assignment.detail;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.R;
import com.radya.sfa.data.source.preference.PrefManager;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.data.source.sync.SyncManager;
import com.radya.sfa.databinding.TaskDetailFragmentBinding;
import com.radya.sfa.util.AlertUtils;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.LocationUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.assignment.Assignment;
import com.radya.sfa.view.assignment.AssignmentViewModel;
import com.radya.sfa.view.assignment.act.AssignmentActActivity;
import com.radya.sfa.view.assignment.detail.failed.AssignmentFailedReasonDialog;
import com.radya.sfa.view.invoice.InvoiceActivity;
import com.radya.sfa.view.invoice.InvoiceViewModel;
import com.radya.sfa.view.invoice.verification.InvoiceVerification;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class AssignmentDetailFragment extends Fragment implements OnMapReadyCallback {

    Unbinder unbinder;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.btnCancel)
    RelativeLayout btnCancel;
    @BindView(R.id.btnStart)
    RelativeLayout btnStart;
    @BindView(R.id.txtBtnStart)
    TextView txtBtnStart;
    @BindView(R.id.imgCaptured)
    ImageView imgCaptured;
    @BindView(R.id.addPicture)
    CardView addPicture;

    private Bundle bundle;
    private Assignment taskDetail;

    private TaskDetailFragmentBinding fragmentBinding;
    private AssignmentDetailDataBinding dataBinding;

    private SupportMapFragment map;

    private AssignmentViewModel viewModel;
    private InvoiceViewModel invoiceViewModel;

    private boolean isStart;

    private String status;

    private File file;
    private boolean isComplete;

    private String userRole;
    private int assignmentType;

    public AssignmentDetailFragment() {
        // Requires empty public constructor
    }

    public static AssignmentDetailFragment newInstance() {
        return new AssignmentDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.task_detail_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentBinding.getRoot());

        bundle = getArguments();

        initViewModel();

        userRole = MyApplication.getInstance().userRole();

        if (bundle != null) {
            taskDetail = (Assignment) bundle.getSerializable(Constant.SERIALIZABLE_NAME);
            Assignment data = taskDetail;

            assignmentType = data.getAssignmentType();

            checkStatus();

            dataBinding = new AssignmentDetailDataBinding(data);
            fragmentBinding.setTaskDetail(dataBinding);

            map = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
            map.getMapAsync(this);
        }

        return fragmentBinding.getRoot();
    }

    private void checkStatus() {
        status = taskDetail.getAssignmentStatusCode();

        if (status.equals(Constant.Data.AssignmentCode.AGENT_ARRIVED)) {

        } else if (status.equals(Constant.Data.AssignmentCode.AGENT_STARTED)) {
            isStart = true;

            if (userRole.equals(Constant.Data.Agent.SALES)) {
                txtBtnStart.setText(R.string.labelProcessAssignment);
                addPicture.setVisibility(View.GONE);
            } else {
                if (userRole.equals(Constant.Data.Agent.SPV)){
                    if (assignmentType == 2){
                        txtBtnStart.setText(R.string.labelFinishAssignment);
                    }else {
                        txtBtnStart.setText(R.string.labelProcessAssignment);
                    }

                    addPicture.setVisibility(View.GONE);
                }else {
                    txtBtnStart.setText(R.string.labelFinishAssignment);
                    addPicture.setVisibility(View.GONE);
                }
            }

        } else if (status.equals(Constant.Data.AssignmentCode.TASK_COMPLETED)) {
            isComplete = true;

            btnCancel.setVisibility(View.GONE);
            btnStart.setVisibility(View.GONE);

            addPicture.setVisibility(View.GONE);

            ToastUtils.showToast(getString(R.string.labelAssignmentFinished));

        } else if (status.equals(Constant.Data.AssignmentCode.TASK_FAILED)) {

        } else if (status.equals(Constant.Data.AssignmentCode.TASK_RECEIVED)) {

        } else if (status.equals(Constant.Data.AssignmentCode.TASK_NO_OTP)){
            isComplete = true;

            btnCancel.setVisibility(View.GONE);
            btnStart.setVisibility(View.VISIBLE);

            txtBtnStart.setText("Masukkan kode verifikasi");

            addPicture.setVisibility(View.GONE);
        }

    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);
        invoiceViewModel = ViewModelProviders.of(this).get(InvoiceViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (taskDetail != null) {
            double latitude = taskDetail.getAssignmentLatitude();
            double longitude = taskDetail.getAssignmentLongitude();

            LatLng location = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(location));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(16.8f), 2000, null);
        }
    }

    public void setImageCaptured(File file, Bitmap bitmap) {
        addPicture.setVisibility(View.GONE);
        imgCaptured.setVisibility(View.VISIBLE);

        this.file = file;
        imgCaptured.setImageBitmap(bitmap);
    }

    @OnClick({R.id.btnCancel, R.id.btnStart, R.id.addPicture, R.id.btnDirection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addPicture:
                if (isStart) {
//                    ((AssignmentDetailActivity) getActivity()).launchCamera();
                } else {
                    ToastUtils.showToast(getString(R.string.labelAssignmentNotStart));
                }
                break;
            case R.id.btnCancel:
                if (userRole.equals(Constant.Data.Agent.SALES)) {
                    getActivity().onBackPressed();
                } else {
                    ((AssignmentDetailActivity) getActivity()).showAssignmentFailedResponse();
                }
                break;
            case R.id.btnStart:

                if (!isStart) {
                    progressLoading.setVisibility(View.VISIBLE);
                    setButtonDisable();
                    startTask();
                } else {
                    if (status.equals(Constant.Data.AssignmentCode.TASK_NO_OTP)){

                        progressLoading.setVisibility(View.VISIBLE);
                        double totalPayment = 0;

                        if (taskDetail.getPayments().size() != 0){
                            for (int i = 0; i < taskDetail.getPayments().size(); i++) {
                                totalPayment += taskDetail.getPayments().get(i).getPaymentAmount();
                            }

                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty(Constant.Api.Params.PAYMENT_METHOD, "");
                            jsonObject.addProperty(Constant.Api.Params.TOTAL_PAYMENT, totalPayment);

                            invoiceViewModel.requestOTP(NetworkUtils.getConnectionManager(), taskDetail.getAssignmentId(), jsonObject);
                            invoiceViewModel.getRequestOTPResponse().observe(this, new Observer<ApiResponse>() {
                                @Override
                                public void onChanged(@Nullable ApiResponse apiResponse) {

                                    progressLoading.setVisibility(View.GONE);
                                    setButtonEnable();

                                    InvoiceVerification response = (InvoiceVerification) apiResponse.getData();
                                    if (response != null) {


                                        ((AssignmentDetailActivity) getActivity()).showAddOTPDialog();


                                    }
                                }
                            });

                        }

                    }else if (status.equals(Constant.Data.AssignmentCode.TASK_NO_PAYMENT)){

                    }else {
                        if (//userRole.equals(Constant.Data.Agent.SPV) ||
                                userRole.equals(Constant.Data.Agent.SALES)) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Constant.SERIALIZABLE_NAME, taskDetail);

                            IntentUtils.intentTo(getActivity(), AssignmentActActivity.class, true, bundle);
                        } else if (userRole.equals(Constant.Data.Agent.SPV)){
                            if (assignmentType == 2){
                                AlertUtils alertUtils = new AlertUtils(getActivity());
                                alertUtils.showAlert(getString(R.string.labelConfirmCompleteTask), new AlertUtils.negativeButton() {
                                    @Override
                                    public void onNo(DialogInterface dialogInterface) {
                                        dialogInterface.dismiss();
                                    }
                                }, new AlertUtils.positiveButton() {
                                    @Override
                                    public void onYes(DialogInterface dialogInterface) {
                                        completeAssignment();
                                    }
                                }, Constant.Data.NEGATIVE_BUTTON, Constant.Data.POSITIVE_BUTTON);
                            }else {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Constant.SERIALIZABLE_NAME, taskDetail);

                                IntentUtils.intentTo(getActivity(), AssignmentActActivity.class, true, bundle);
                            }
                        }
                        else {

                            AlertUtils alertUtils = new AlertUtils(getActivity());
                            alertUtils.showAlert(getString(R.string.labelConfirmCompleteTask), new AlertUtils.negativeButton() {
                                @Override
                                public void onNo(DialogInterface dialogInterface) {
                                    dialogInterface.dismiss();
                                }
                            }, new AlertUtils.positiveButton() {
                                @Override
                                public void onYes(DialogInterface dialogInterface) {
                                    completeAssignment();
                                }
                            }, Constant.Data.NEGATIVE_BUTTON, Constant.Data.POSITIVE_BUTTON);

                        }
                    }

                }

                break;
            case R.id.btnDirection:

                String latitude = "" + taskDetail.getAssignmentLatitude();
                String longitude = "" + taskDetail.getAssignmentLongitude();

                LocationUtils locationUtils = new LocationUtils(getActivity());
                locationUtils.getDirection(latitude, longitude);

                break;
        }
    }

    private void startTask() {

//        if (PrefManager.getBoolean(Constant.Preference.ASSIGNMENT_COMPLETE)){
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constant.Api.Params.START_TIME, DateUtils.getCurrentTime(Constant.DateFormat.SERVER));
        jsonObject.addProperty(Constant.Api.Params.LATITUDE, MyApplication.getInstance().latitude());
        jsonObject.addProperty(Constant.Api.Params.LONGITUDE, MyApplication.getInstance().longitude());
        jsonObject.addProperty(Constant.Api.Params.STATE, "");
        jsonObject.addProperty(Constant.Api.Params.REMARKS, taskDetail.getRemarks());

        viewModel.assignmentStart(NetworkUtils.getConnectionManager(), taskDetail.getAssignmentId(), jsonObject, true);
        viewModel.getAssignmentStartResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                progressLoading.setVisibility(View.GONE);
                setButtonEnable();

                BaseModel response = (BaseModel) apiResponse.getData();
                if (response != null) {
                    if (response.getAlert().getCode() != 406){
                        isStart = true;

                        if (//userRole.equals(Constant.Data.Agent.SPV) ||
                                userRole.equals(Constant.Data.Agent.SALES)) {
                            txtBtnStart.setText(R.string.labelProcessInvoice);
                        } else if (userRole.equals(Constant.Data.Agent.SPV)){
                            if (assignmentType == 2){
                                txtBtnStart.setText(R.string.labelFinishAssignment);
                            }else {
                                txtBtnStart.setText(R.string.labelProcessInvoice);
                            }
                        }
                        else {
                            txtBtnStart.setText(R.string.labelFinishAssignment);
                        }
                    }

                } else {
                    isStart = true;

                    if (//userRole.equals(Constant.Data.Agent.SPV) ||
                            userRole.equals(Constant.Data.Agent.SALES)) {
                        txtBtnStart.setText(R.string.labelProcessInvoice);
                    } else if (userRole.equals(Constant.Data.Agent.SPV)){
                        if (assignmentType == 2){
                            txtBtnStart.setText(R.string.labelFinishAssignment);
                        }else {
                            txtBtnStart.setText(R.string.labelProcessInvoice);
                        }
                    }
                    else {
                        txtBtnStart.setText(R.string.labelFinishAssignment);
                    }

                    SyncManager.storeSync(MyApplication.obtainLocalDB(), taskDetail.getAssignmentId(),
                            Constant.SyncType.START_ASSIGNMENT, jsonObject.toString(), 0);
                }

            }
        });

//        }else {
//            ToastUtils.showToast("Harap selesaikan kunjungan sebelumnya");
//        }

    }

    public void completeAssignment() {

        progressLoading.setVisibility(View.VISIBLE);
        setButtonDisable();

        AssignmentViewModel assignmentViewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);

        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), "" + MyApplication.getInstance().latitude());
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), "" + MyApplication.getInstance().longitude());
        RequestBody ProcessedTime = RequestBody.create(MediaType.parse("text/plain"), DateUtils.getCurrentTime(Constant.DateFormat.SERVER));
        RequestBody assignmentComplete = RequestBody.create(MediaType.parse("text/plain"), Constant.Data.AssignmentCode.TASK_COMPLETED);

        assignmentViewModel.assignmentComplete(NetworkUtils.getConnectionManager(),
                taskDetail.getAssignmentId(),
                latitude, longitude, ProcessedTime, assignmentComplete, true);
        assignmentViewModel.getAssignmentCompleteResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                progressLoading.setVisibility(View.GONE);
                setButtonEnable();

                BaseModel response = (BaseModel) apiResponse.getData();
                if (response != null) {

                    btnStart.setVisibility(View.GONE);
                    btnCancel.setVisibility(View.GONE);

//                    PrefManager.saveBoolean(Constant.Preference.ASSIGNMENT_COMPLETE, true);

                }else {
                    btnStart.setVisibility(View.GONE);
                    btnCancel.setVisibility(View.GONE);

//                    PrefManager.saveBoolean(Constant.Preference.ASSIGNMENT_COMPLETE, true);
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

        SyncManager.storeSync(MyApplication.obtainLocalDB(), taskDetail.getAssignmentId(),
                Constant.SyncType.ASSIGNMENT_COMPLETE, jsonObject.toString(), 1);

    }

    private void setButtonDisable() {
        btnCancel.setEnabled(false);
        btnStart.setEnabled(false);
    }

    private void setButtonEnable() {
        btnCancel.setEnabled(true);
        btnStart.setEnabled(true);
    }

}
