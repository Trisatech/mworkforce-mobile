package com.radya.sfa.view.settings;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.R;
import com.radya.sfa.data.AppDatabase;
import com.radya.sfa.data.source.preference.PrefManager;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.data.source.sync.Sync;
import com.radya.sfa.data.source.sync.SyncManager;
import com.radya.sfa.util.AppUtils;
import com.radya.sfa.util.Base64Utils;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.ImageUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.JsonObjectUtils;
import com.radya.sfa.util.JsonUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.ListView;
import com.radya.sfa.view.assignment.AssignmentViewModel;
import com.radya.sfa.view.assignment.product.AssignmentProductActivity;
import com.radya.sfa.view.auth.AuthActivity;
import com.radya.sfa.view.auth.AuthViewModel;
import com.radya.sfa.view.invoice.InvoiceActivity;
import com.radya.sfa.view.invoice.InvoiceViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class SettingsFragment extends Fragment implements ListView {

    Unbinder unbinder;
    @BindView(R.id.settingsMenuList)
    RecyclerView settingsMenuList;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.txtSyncItemCount)
    TextView txtSyncItemCount;
    @BindView(R.id.containerSyncItemCount)
    RelativeLayout containerSyncItemCount;
    @BindView(R.id.btnSync)
    CardView btnSync;
    @BindView(R.id.imgView)
    ImageView imgView;

    private SettingsMenuAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private List<SettingsMenu> menuList;

    private AuthViewModel authViewModel;

    private AssignmentViewModel assignmentViewModel;
    private InvoiceViewModel invoiceViewModel;

    public SettingsFragment() {
        // Requires empty public constructor
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViewModel();
        setupList();
        getItemSync();

        return view;
    }

    private void getItemSync() {
        AppDatabase db = MyApplication.obtainLocalDB();
        List<Sync> syncList = db.syncDao().loadSync();

        if (syncList.size() != 0) {
            containerSyncItemCount.setVisibility(View.VISIBLE);
            txtSyncItemCount.setText("" + syncList.size());
        } else {
            containerSyncItemCount.setVisibility(View.GONE);
        }

    }

    private void initViewModel() {
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        assignmentViewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);
        invoiceViewModel = ViewModelProviders.of(this).get(InvoiceViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setupList() {
        adapter = new SettingsMenuAdapter();
        linearLayoutManager = new LinearLayoutManager(getActivity());

        settingsMenuList.setAdapter(adapter);
        settingsMenuList.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SettingsMenu menu = adapter.getData().get(i);
                if (menu.getId() == R.drawable.ic_logout_grey_dark) {
                    logout();
                }
            }
        });

        loadData();

    }

    private void logout() {
        doLogout();
//        progressLoading.setVisibility(View.VISIBLE);
//        authViewModel.logout(NetworkUtils.getConnectionManager());
//        authViewModel.getUserLogoutResponse().observe(this, new Observer<ApiResponse>() {
//            @Override
//            public void onChanged(@Nullable ApiResponse apiResponse) {
//                progressLoading.setVisibility(View.GONE);
//                BaseModel response = (BaseModel) apiResponse.getData();
//                if (response != null) {
//                    doLogout();
//                }
//            }
//        });
    }

    private void doLogout() {
        PrefManager.saveBoolean(Constant.Preference.User.IS_LOGIN, false);
        PrefManager.saveString(Constant.Preference.User.AUTH_TOKEN, Constant.Data.EMPTY_STRING);

        PrefManager.saveString(Constant.Preference.User.USER_NAME, Constant.Data.EMPTY_STRING);
        PrefManager.saveString(Constant.Preference.User.USER_CODE, Constant.Data.EMPTY_STRING);
        PrefManager.saveString(Constant.Preference.User.USER_ROLE_CODE, Constant.Data.EMPTY_STRING);
        PrefManager.saveString(Constant.Preference.User.USER_ROLE_NAME, Constant.Data.EMPTY_STRING);

        Bundle bundle = new Bundle();
        bundle.putInt(Constant.Bundle.PAGE, Constant.Page.LOGIN);

        IntentUtils.backTo(getActivity(), AuthActivity.class, true, bundle);
    }

    @Override
    public void loadData() {

        menuList = new ArrayList<>();
        menuList.add(new SettingsMenu(R.drawable.ic_logout_grey_dark, Constant.Menu.LOGOUT));

        storeDataToList();
    }

    @Override
    public void storeDataToList() {
        for (int i = 0; i < menuList.size(); i++) {
            adapter.getData().add(menuList.get(i));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }

        adapter.notifyDataSetChanged();

    }

    @OnClick(R.id.btnSync)
    public void onViewClicked() {
        sync();
    }

    private void sync() {
        AppDatabase db = MyApplication.obtainLocalDB();
        List<Sync> syncList = db.syncDao().loadSync();

        if (syncList.size() != 0) {
            for (int i = 0; i < syncList.size(); i++) {
                final Sync sync = syncList.get(i);
                final String jsonObject = sync.json;

                if (sync.type.equals(Constant.SyncType.START_ASSIGNMENT)) {
                    syncStartAssignment(sync, jsonObject);
                } else if (sync.type.equals(Constant.SyncType.ORDER)){
                    syncProductOrder(sync, jsonObject);
                } else if (sync.type.equals(Constant.SyncType.INPUT_INVOICE_PAYMENT)) {
                    synInputInvoice(sync, jsonObject);
                } else if (sync.type.equals(Constant.SyncType.ASSIGNMENT_COMPLETE)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            syncAssignmentComplete(sync, jsonObject);
                        }
                    },3000);
                }

//                if (sync.post_type == 0) { // post json
//                    if (sync.type.equals(Constant.SyncType.START_ASSIGNMENT)) {
//                        syncStartAssignment(sync, jsonObject);
//                    }else if (sync.type.equals(Constant.SyncType.ORDER)){
//                        syncProductOrder(sync, jsonObject);
//                    }
//                } else { // post multipart
//                    if (sync.type.equals(Constant.SyncType.INPUT_INVOICE_PAYMENT)) {
//                        synInputInvoice(sync, jsonObject);
//                    }else if (sync.type.equals(Constant.SyncType.ASSIGNMENT_COMPLETE)){
//                        syncAssignmentComplete(sync, jsonObject);
//                    }
//                }

            }
        }
    }

    private void syncProductOrder(final Sync sync, String jsonString) {
        progressLoading.setVisibility(View.VISIBLE);
        JsonObject jsonObject = JsonUtils.getJson(jsonString);

        assignmentViewModel.assignmentProductOrder(NetworkUtils.getConnectionManager(), jsonObject);
        assignmentViewModel.getAssignmentProductOrderResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);

                BaseModel response = (BaseModel) apiResponse.getData();

                if (response != null){
                    SyncManager.deleteSync(MyApplication.obtainLocalDB(), sync.id);
                    getItemSync();
                }

            }
        });

    }

    private void syncAssignmentComplete(final Sync sync, String jsonString) {

        progressLoading.setVisibility(View.VISIBLE);

        SyncAssignmentComplete assignment = (SyncAssignmentComplete) JsonObjectUtils.getJson(jsonString, SyncAssignmentComplete.class);

        String latitude = assignment.getLatitude();
        String longitude = assignment.getLongitude();
        String processedTime = assignment.getProcessed_time();
        String assignmentComplete = assignment.getAssignment_complete();

        RequestBody Latitude = RequestBody.create(MediaType.parse("text/plain"), latitude);
        RequestBody Longitude = RequestBody.create(MediaType.parse("text/plain"), longitude);
        RequestBody ProcessedTime = RequestBody.create(MediaType.parse("text/plain"), processedTime);
        RequestBody AssignmentComplete = RequestBody.create(MediaType.parse("text/plain"), assignmentComplete);

        assignmentViewModel.assignmentComplete(NetworkUtils.getConnectionManager(),
                sync.assignment_id,
                Latitude, Longitude, ProcessedTime, AssignmentComplete, false);
        assignmentViewModel.getAssignmentCompleteResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                progressLoading.setVisibility(View.GONE);

                BaseModel response = (BaseModel) apiResponse.getData();
                if (response != null) {
                    SyncManager.deleteSync(MyApplication.obtainLocalDB(), sync.id);
//                    PrefManager.saveBoolean(Constant.Preference.ASSIGNMENT_COMPLETE, true);
                    getItemSync();
                }
            }
        });

    }

    private void synInputInvoice(final Sync sync, final String jsonString) {
        progressLoading.setVisibility(View.VISIBLE);

        SyncInvoice invoice = (SyncInvoice) JsonObjectUtils.getJson(jsonString, SyncInvoice.class);

        final String assignmentId = invoice.getAssignmentId();
        String invoiceCOde = invoice.getInvoiceCode();
        String invoiceAmount = invoice.getInvoiceAmount();
        String paymentAmount = invoice.getPaymentAmount();
        String paymentDebt = invoice.getPaymentDebt();
        String paymentChannel = invoice.getPaymentChannel();
        String cashAmount = invoice.getCashAmount();
        String otpCode = invoice.getOtpCode();
        String transferBank = invoice.getTransferBank();
        String transferAmount = invoice.getTransferAmount();
        String giroNumber = invoice.getGiroNumber();
        String giroPhoto = invoice.getGiroPhoto();
        String giroAmount = invoice.getGiroAmount();
        String giroDueDate = invoice.getGiroDueDate();
        String giroNumber1 = invoice.getGiroNumber1();
        String giroPhoto1 = invoice.getGiroPhoto1();
        String giroAmount1 = invoice.getGiroAmount1();
        String giroDueDate1 = invoice.getGiroDueDate1();
        String giroNumber2 = invoice.getGiroNumber2();
        String giroPhoto2 = invoice.getGiroPhoto2();
        String giroAmount2 = invoice.getGiroAmount2();
        String giroDueDate2 = invoice.getGiroDueDate2();
        String giroNumber3 = invoice.getGiroNumber3();
        String giroPhoto3 = invoice.getGiroPhoto3();
        String giroAmount3 = invoice.getGiroAmount3();
        String giroDueDate3 = invoice.getGiroDueDate3();
        String giroNumber4 = invoice.getGiroNumber4();
        String giroPhoto4 = invoice.getGiroPhoto4();
        String giroAmount4 = invoice.getGiroAmount4();
        String giroDueDate4 = invoice.getGiroDueDate4();

        String giroPhotoString = invoice.getGiroPhotoString();
        String giroPhotoString1 = invoice.getGiroPhotoString1();
        String giroPhotoString2 = invoice.getGiroPhotoString2();
        String giroPhotoString3 = invoice.getGiroPhotoString3();
        String giroPhotoString4 = invoice.getGiroPhotoString4();

        Bitmap bitmapGiroPhoto = Base64Utils.getBitmapOfBase64(giroPhotoString);
        Bitmap bitmapGiroPhoto1 = Base64Utils.getBitmapOfBase64(giroPhotoString1);
        Bitmap bitmapGiroPhoto2 = Base64Utils.getBitmapOfBase64(giroPhotoString2);
        Bitmap bitmapGiroPhoto3 = Base64Utils.getBitmapOfBase64(giroPhotoString3);
        Bitmap bitmapGiroPhoto4 = Base64Utils.getBitmapOfBase64(giroPhotoString4);

        File giroPhotoFile = ImageUtils.persistImage(getActivity(), bitmapGiroPhoto, "giro_photo");
        File giroPhotoFile1 = ImageUtils.persistImage(getActivity(), bitmapGiroPhoto1, "giro_photo1");
        File giroPhotoFile2 = ImageUtils.persistImage(getActivity(), bitmapGiroPhoto2, "giro_photo2");
        File giroPhotoFile3 = ImageUtils.persistImage(getActivity(), bitmapGiroPhoto3, "giro_photo3");
        File giroPhotoFile4 = ImageUtils.persistImage(getActivity(), bitmapGiroPhoto4, "giro_photo4");

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), giroPhotoFile);
        MultipartBody.Part GiroAttachment = MultipartBody.Part.createFormData(Constant.Api.Params.GIRO_ATACHMENT,
                giroPhotoFile.getName(), requestFile);

        RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpg"), giroPhotoFile1);
        MultipartBody.Part GiroAttachment1 = MultipartBody.Part.createFormData(Constant.Api.Params.GIRO_ATACHMENT_1,
                giroPhotoFile1.getName(), requestFile1);

        RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/jpg"), giroPhotoFile2);
        MultipartBody.Part GiroAttachment2 = MultipartBody.Part.createFormData(Constant.Api.Params.GIRO_ATACHMENT_2,
                giroPhotoFile2.getName(), requestFile2);

        RequestBody requestFile3 = RequestBody.create(MediaType.parse("image/jpg"), giroPhotoFile3);
        MultipartBody.Part GiroAttachment3 = MultipartBody.Part.createFormData(Constant.Api.Params.GIRO_ATACHMENT_3,
                giroPhotoFile3.getName(), requestFile3);

        RequestBody requestFile4 = RequestBody.create(MediaType.parse("image/jpg"), giroPhotoFile4);
        MultipartBody.Part GiroAttachment4 = MultipartBody.Part.createFormData(Constant.Api.Params.GIRO_ATACHMENT_4,
                giroPhotoFile4.getName(), requestFile4);

        RequestBody AssignmentId = RequestBody.create(MediaType.parse("text/plain"), assignmentId);
        RequestBody InvoiceCode = RequestBody.create(MediaType.parse("text/plain"), invoiceCOde);
        RequestBody InvoiceAmount = RequestBody.create(MediaType.parse("text/plain"), invoiceAmount);
        RequestBody PaymentAmount = RequestBody.create(MediaType.parse("text/plain"), paymentAmount);
        RequestBody PaymentDebt = RequestBody.create(MediaType.parse("text/plain"), paymentDebt);
        RequestBody PaymentChannel = RequestBody.create(MediaType.parse("text/plain"), paymentChannel);
        RequestBody CashAmount = RequestBody.create(MediaType.parse("text/plain"), cashAmount);
        RequestBody Otp = RequestBody.create(MediaType.parse("text/plain"), "");
        RequestBody TransferBank = RequestBody.create(MediaType.parse("text/plain"), transferBank);
        RequestBody TransferAmount = RequestBody.create(MediaType.parse("text/plain"), transferAmount);
        //GIRO 1
        RequestBody GiroNumber = RequestBody.create(MediaType.parse("text/plain"), giroNumber);
        RequestBody GiroPhoto = RequestBody.create(MediaType.parse("text/plain"), giroPhoto);
        RequestBody GiroAmount = RequestBody.create(MediaType.parse("text/plain"), giroAmount);
        RequestBody GiroDueDate = RequestBody.create(MediaType.parse("text/plain"), giroDueDate);
        //GIRO 2
        RequestBody GiroNumber1 = RequestBody.create(MediaType.parse("text/plain"), giroNumber1);
        RequestBody GiroPhoto1 = RequestBody.create(MediaType.parse("text/plain"), giroPhoto1);
        RequestBody GiroAmount1 = RequestBody.create(MediaType.parse("text/plain"), giroAmount1);
        RequestBody GiroDueDate1 = RequestBody.create(MediaType.parse("text/plain"), giroDueDate1);
        //GIRO 3
        RequestBody GiroNumber2 = RequestBody.create(MediaType.parse("text/plain"), giroNumber2);
        RequestBody GiroPhoto2 = RequestBody.create(MediaType.parse("text/plain"), giroPhoto2);
        RequestBody GiroAmount2 = RequestBody.create(MediaType.parse("text/plain"), giroAmount2);
        RequestBody GiroDueDate2 = RequestBody.create(MediaType.parse("text/plain"), giroDueDate2);
        //GIRO 4
        RequestBody GiroNumber3 = RequestBody.create(MediaType.parse("text/plain"), giroNumber3);
        RequestBody GiroPhoto3 = RequestBody.create(MediaType.parse("text/plain"), giroPhoto3);
        RequestBody GiroAmount3 = RequestBody.create(MediaType.parse("text/plain"), giroAmount3);
        RequestBody GiroDueDate3 = RequestBody.create(MediaType.parse("text/plain"), giroDueDate3);
        //GIRO 2
        RequestBody GiroNumber4 = RequestBody.create(MediaType.parse("text/plain"), giroNumber4);
        RequestBody GiroPhoto4 = RequestBody.create(MediaType.parse("text/plain"), giroPhoto4);
        RequestBody GiroAmount4 = RequestBody.create(MediaType.parse("text/plain"), giroAmount4);
        RequestBody GiroDueDate4 = RequestBody.create(MediaType.parse("text/plain"), giroDueDate4);

        invoiceViewModel.invoicePayment(NetworkUtils.getConnectionManager(), AssignmentId, InvoiceCode,
                InvoiceAmount, PaymentAmount, PaymentDebt, PaymentChannel, TransferAmount, GiroAmount,
                CashAmount, Otp, TransferBank, GiroAttachment, GiroPhoto, GiroNumber,  GiroDueDate,
                GiroNumber1, GiroPhoto1, GiroAmount1, GiroDueDate1, GiroNumber2, GiroPhoto2,
                GiroAmount2, GiroDueDate2, GiroNumber3, GiroPhoto3, GiroAmount3, GiroDueDate3,
                GiroNumber4,  GiroPhoto4, GiroAmount4, GiroDueDate4, GiroAttachment1, GiroAttachment2,
                GiroAttachment3, GiroAttachment4);
        invoiceViewModel.getInvoicePaymentResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                progressLoading.setVisibility(View.GONE);

                BaseModel response = (BaseModel) apiResponse.getData();
                if (response != null) {
                    SyncManager.deleteSync(MyApplication.obtainLocalDB(), sync.id);
                    completeAssignment(assignmentId, sync.id, jsonString);
                }

            }
        });



    }

    private void completeAssignment(String assignmentId, final int syncId, String jsonString) {

        progressLoading.setVisibility(View.VISIBLE);

        AssignmentViewModel assignmentViewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);

        SyncAssignmentComplete assignment = (SyncAssignmentComplete) JsonObjectUtils.getJson(jsonString, SyncAssignmentComplete.class);
        String processedTime = assignment.getProcessed_time();

        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), "" + MyApplication.getInstance().latitude());
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), "" + MyApplication.getInstance().longitude());
        RequestBody ProcessedTime = RequestBody.create(MediaType.parse("text/plain"), processedTime);
        RequestBody assignmentComplete = RequestBody.create(MediaType.parse("text/plain"), Constant.Data.AssignmentCode.TASK_COMPLETED);

        assignmentViewModel.assignmentComplete(NetworkUtils.getConnectionManager(),
                assignmentId,
                latitude, longitude, ProcessedTime, assignmentComplete, false);
        assignmentViewModel.getAssignmentCompleteResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                progressLoading.setVisibility(View.GONE);

                BaseModel response = (BaseModel) apiResponse.getData();
                if (response != null) {
//                    PrefManager.saveBoolean(Constant.Preference.ASSIGNMENT_COMPLETE, true);
                    SyncManager.deleteSync(MyApplication.obtainLocalDB(), syncId);
                    getItemSync();
                }
            }
        });

    }

    private void syncStartAssignment(final Sync sync, String stringJson) {

        JsonObject jsonObject = JsonUtils.getJson(stringJson);

        progressLoading.setVisibility(View.VISIBLE);
        assignmentViewModel.assignmentStart(NetworkUtils.getConnectionManager(), sync.assignment_id, jsonObject, false);
        assignmentViewModel.getAssignmentStartResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                progressLoading.setVisibility(View.GONE);
                BaseModel response = (BaseModel) apiResponse.getData();
                if (response != null) {
                    SyncManager.deleteSync(MyApplication.obtainLocalDB(), sync.id);
                    getItemSync();
                }

            }
        });
    }
}
