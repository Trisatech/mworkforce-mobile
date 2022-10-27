package com.radya.sfa.view.invoice.verification;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.R;
import com.radya.sfa.data.source.preference.PrefManager;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.data.source.sync.SyncManager;
import com.radya.sfa.util.Base64Utils;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.ImageUtils;
import com.radya.sfa.util.LocationUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.view.assignment.Assignment;
import com.radya.sfa.view.assignment.AssignmentViewModel;
import com.radya.sfa.view.assignment.detail.AssignmentDetail;
import com.radya.sfa.view.invoice.InvoiceActivity;
import com.radya.sfa.view.invoice.InvoiceViewModel;
import com.radya.sfa.view.invoice.payment.InvoicePayment;

import java.io.File;
import java.util.ArrayList;
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

public class InvoiceVerificationFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.edtVerificationCode)
    EditText edtVerificationCode;
    @BindView(R.id.btnSend)
    RelativeLayout btnSend;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.resendOTPRequest)
    TextView resendOTPRequest;
    @BindView(R.id.containerInput)
    LinearLayout containerInput;
    @BindView(R.id.containerSuccess)
    FrameLayout containerSuccess;
    @BindView(R.id.skipVerification)
    TextView skipVerification;

    private InvoiceViewModel viewModel;
    private Assignment assignmentDetail;

    private String otpCode;

    private List<InvoicePayment> paymentGiro;

    private File giroPhoto;
    private File giroPhoto1;
    private File giroPhoto2;
    private File giroPhoto3;
    private File giroPhoto4;

    long transferAmout;
    long cashAmount;
    String transferBank;
    long paymentAmount;
    long paymentDebt;
    String assignmentId;

    public InvoiceVerificationFragment() {
        // Requires empty public constructor
    }

    public static InvoiceVerificationFragment newInstance() {
        return new InvoiceVerificationFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invoice_verification_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        paymentGiro = new ArrayList<>();

        getAssignmentDetail();
        initViewModel();
        initCurrentLocation();
        return view;
    }

    private void getAssignmentDetail() {
        assignmentDetail = ((InvoiceActivity) getActivity()).getAssignmentDetail();
    }

    private void initViewModel() {
        viewModel = ((InvoiceActivity) getActivity()).obtainViewModel();
        requestOTP();
    }

    private void requestOTP() {
        progressLoading.setVisibility(View.VISIBLE);
        setButtonDisable();

        Assignment assignmentDetail = ((InvoiceActivity) getActivity()).getAssignmentDetail();
        String assignmentId = assignmentDetail.getAssignmentId();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constant.Api.Params.PAYMENT_METHOD, "");
        jsonObject.addProperty(Constant.Api.Params.TOTAL_PAYMENT, ((InvoiceActivity) getActivity()).getPaymentAmount());

        viewModel.requestOTP(NetworkUtils.getConnectionManager(), assignmentId, jsonObject);
        viewModel.getRequestOTPResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                progressLoading.setVisibility(View.GONE);
                setButtonEnable();

                InvoiceVerification response = (InvoiceVerification) apiResponse.getData();
                if (response != null) {
                    ((InvoiceActivity) getActivity()).setOTPCode(response.getData());
                    edtVerificationCode.setText(response.getData());
                }
            }
        });
    }

    private void inputInvoicePayment() {

        progressLoading.setVisibility(View.VISIBLE);
        setButtonDisable();

        //Foto giro 1
        RequestBody requestFile;
        MultipartBody.Part GiroAttachment;

        giroPhoto = paymentGiro.get(0).getGiro_photo();
        if (giroPhoto == null){
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.null_image);
            giroPhoto = ImageUtils.persistImage(getActivity(), bm, "null_image");
        }

        requestFile = RequestBody.create(MediaType.parse("image/jpg"), giroPhoto);
        GiroAttachment = MultipartBody.Part.createFormData(Constant.Api.Params.GIRO_ATACHMENT, giroPhoto.getName(), requestFile);

        //Giro 2
        RequestBody requestFile1;
        MultipartBody.Part GiroAttachment1;

        giroPhoto1 = paymentGiro.get(1).getGiro_photo();
        if (giroPhoto1 == null){
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.null_image);
            giroPhoto1 = ImageUtils.persistImage(getActivity(), bm, "null_image");
        }

        requestFile1 = RequestBody.create(MediaType.parse("image/jpg"), giroPhoto1);
        GiroAttachment1 = MultipartBody.Part.createFormData(Constant.Api.Params.GIRO_ATACHMENT_1, giroPhoto1.getName(), requestFile1);

        //Giro 3
        RequestBody requestFile2;
        MultipartBody.Part GiroAttachment2;

        giroPhoto2 = paymentGiro.get(2).getGiro_photo();
        if (giroPhoto2 == null){
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.null_image);
            giroPhoto2 = ImageUtils.persistImage(getActivity(), bm, "null_image");
        }

        requestFile2 = RequestBody.create(MediaType.parse("image/jpg"), giroPhoto2);
        GiroAttachment2 = MultipartBody.Part.createFormData(Constant.Api.Params.GIRO_ATACHMENT_2, giroPhoto2.getName(), requestFile2);

        //Giro 4
        RequestBody requestFile3;
        MultipartBody.Part GiroAttachment3;

        giroPhoto3 = paymentGiro.get(3).getGiro_photo();
        if (giroPhoto3 == null){
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.null_image);
            giroPhoto3 = ImageUtils.persistImage(getActivity(), bm, "null_image");
        }

        requestFile3 = RequestBody.create(MediaType.parse("image/jpg"), giroPhoto3);
        GiroAttachment3 = MultipartBody.Part.createFormData(Constant.Api.Params.GIRO_ATACHMENT_3, giroPhoto3.getName(), requestFile3);

        //Giro 5
        RequestBody requestFile4;
        MultipartBody.Part GiroAttachment4;

        giroPhoto4 = paymentGiro.get(4).getGiro_photo();
        if (giroPhoto4 == null){
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.null_image);
            giroPhoto4 = ImageUtils.persistImage(getActivity(), bm, "null_image");
        }

        requestFile4 = RequestBody.create(MediaType.parse("image/jpg"), giroPhoto4);
        GiroAttachment4 = MultipartBody.Part.createFormData(Constant.Api.Params.GIRO_ATACHMENT_4, giroPhoto4.getName(), requestFile4);

        transferAmout = ((InvoiceActivity) getActivity()).getInvoiceTransfer().getTransfer_amount();
//        giroAmount = ((InvoiceActivity) getActivity()).getInvoiceGiro().getGiro_amount();
        cashAmount = ((InvoiceActivity) getActivity()).getInvoiceCash().getCash_amount();
        otpCode = edtVerificationCode.getText().toString();
        transferBank = ((InvoiceActivity) getActivity()).getInvoiceTransfer().getBank();
        paymentAmount = ((InvoiceActivity) getActivity()).getNotaPembayaran();
        paymentDebt = ((InvoiceActivity) getActivity()).getNotaKredit();
        assignmentId = assignmentDetail.getAssignmentId();
        String giroNumber = ((InvoiceActivity) getActivity()).getInvoiceGiro().getNo_giro();

        RequestBody AssignmentId = RequestBody.create(MediaType.parse("text/plain"), assignmentId);
        RequestBody InvoiceCode = RequestBody.create(MediaType.parse("text/plain"), "12345");
        RequestBody InvoiceAmount = RequestBody.create(MediaType.parse("text/plain"), "" + ((InvoiceActivity) getActivity()).getPaymentAmount());
        RequestBody PaymentAmount = RequestBody.create(MediaType.parse("text/plain"), "" + paymentAmount);
        RequestBody PaymentDebt = RequestBody.create(MediaType.parse("text/plain"), "" + paymentDebt);
        RequestBody PaymentChannel = RequestBody.create(MediaType.parse("text/plain"), "" + 1);
        RequestBody CashAmount = RequestBody.create(MediaType.parse("text/plain"), "" + cashAmount);
        RequestBody Otp = RequestBody.create(MediaType.parse("text/plain"), otpCode);
        RequestBody TransferBank = RequestBody.create(MediaType.parse("text/plain"), transferBank);
        RequestBody TransferAmount = RequestBody.create(MediaType.parse("text/plain"), "" + transferAmout);
        //GIRO 1
        RequestBody GiroNumber = RequestBody.create(MediaType.parse("text/plain"), paymentGiro.get(0).getNo_giro());
        RequestBody GiroPhoto = RequestBody.create(MediaType.parse("text/plain"), "Foto giro 1");
        RequestBody GiroAmount = RequestBody.create(MediaType.parse("text/plain"), "" + paymentGiro.get(0).getAmount());
        RequestBody GiroDueDate = RequestBody.create(MediaType.parse("text/plain"), paymentGiro.get(0).getGiro_due_date());
        //GIRO 2
        RequestBody GiroNumber1 = RequestBody.create(MediaType.parse("text/plain"), paymentGiro.get(1).getNo_giro());
        RequestBody GiroPhoto1 = RequestBody.create(MediaType.parse("text/plain"), "Foto giro 2");
        RequestBody GiroAmount1 = RequestBody.create(MediaType.parse("text/plain"), "" + paymentGiro.get(1).getAmount());
        RequestBody GiroDueDate1 = RequestBody.create(MediaType.parse("text/plain"), paymentGiro.get(1).getGiro_due_date());
        //GIRO 3
        RequestBody GiroNumber2 = RequestBody.create(MediaType.parse("text/plain"), paymentGiro.get(2).getNo_giro());
        RequestBody GiroPhoto2 = RequestBody.create(MediaType.parse("text/plain"), "Foto giro 3");
        RequestBody GiroAmount2 = RequestBody.create(MediaType.parse("text/plain"), "" + paymentGiro.get(2).getAmount());
        RequestBody GiroDueDate2 = RequestBody.create(MediaType.parse("text/plain"), paymentGiro.get(2).getGiro_due_date());
        //GIRO 4
        RequestBody GiroNumber3 = RequestBody.create(MediaType.parse("text/plain"), paymentGiro.get(3).getNo_giro());
        RequestBody GiroPhoto3 = RequestBody.create(MediaType.parse("text/plain"), "Foto giro 4");
        RequestBody GiroAmount3 = RequestBody.create(MediaType.parse("text/plain"), "" + paymentGiro.get(3).getAmount());
        RequestBody GiroDueDate3 = RequestBody.create(MediaType.parse("text/plain"), paymentGiro.get(3).getGiro_due_date());
        //GIRO 2
        RequestBody GiroNumber4 = RequestBody.create(MediaType.parse("text/plain"), paymentGiro.get(4).getNo_giro());
        RequestBody GiroPhoto4 = RequestBody.create(MediaType.parse("text/plain"), "Foto giro 5");
        RequestBody GiroAmount4 = RequestBody.create(MediaType.parse("text/plain"), "" + paymentGiro.get(4).getAmount());
        RequestBody GiroDueDate4 = RequestBody.create(MediaType.parse("text/plain"), paymentGiro.get(4).getGiro_due_date());

        viewModel.invoicePayment(NetworkUtils.getConnectionManager(), AssignmentId, InvoiceCode,
                InvoiceAmount, PaymentAmount, PaymentDebt, PaymentChannel, TransferAmount, GiroAmount,
                CashAmount, Otp, TransferBank, GiroAttachment, GiroPhoto, GiroNumber,  GiroDueDate,
                GiroNumber1, GiroPhoto1, GiroAmount1, GiroDueDate1, GiroNumber2, GiroPhoto2,
                GiroAmount2, GiroDueDate2, GiroNumber3, GiroPhoto3, GiroAmount3, GiroDueDate3,
                GiroNumber4,  GiroPhoto4, GiroAmount4, GiroDueDate4, GiroAttachment1, GiroAttachment2,
                GiroAttachment3, GiroAttachment4);
        viewModel.getInvoicePaymentResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                progressLoading.setVisibility(View.GONE);
                setButtonEnable();

                BaseModel response = (BaseModel) apiResponse.getData();
                if (response != null) {
                    completeAssignment();
                    ((InvoiceActivity) getActivity()).setTitle(getString(R.string.labelSuccess));
                    ((InvoiceActivity) getActivity()).hideIndicator();

                    containerInput.setVisibility(View.GONE);
                    skipVerification.setVisibility(View.GONE);
                    containerSuccess.setVisibility(View.VISIBLE);

                } else {

                    storeInvoicePaymentToSync();

                }

            }
        });
    }

    private void storeInvoicePaymentToSync() {
        String base64GiroPhoto = Base64Utils.getBase64ofImage(giroPhoto);
        String base64GiroPhoto1 = Base64Utils.getBase64ofImage(giroPhoto1);
        String base64GiroPhoto2 = Base64Utils.getBase64ofImage(giroPhoto2);
        String base64GiroPhoto3 = Base64Utils.getBase64ofImage(giroPhoto3);
        String base64GiroPhoto4 = Base64Utils.getBase64ofImage(giroPhoto4);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("assignment_id", assignmentId);
        jsonObject.addProperty("invoice_code", "1234");
        jsonObject.addProperty("invoice_amount", "" + ((InvoiceActivity) getActivity()).getPaymentAmount());
        jsonObject.addProperty("payment_amount", "" + paymentAmount);
        jsonObject.addProperty("payment_debt", "" + paymentDebt);
        jsonObject.addProperty("payment_channel", "" + 1);
        jsonObject.addProperty("cash_amount", "" + cashAmount);
        jsonObject.addProperty("otp_code", otpCode);
        jsonObject.addProperty("transfer_bank", transferBank);
        jsonObject.addProperty("transfer_amount", "" + transferAmout);
        jsonObject.addProperty("giro_number", paymentGiro.get(0).getNo_giro());
        jsonObject.addProperty("giro_photo", "Foto giro 1");
        jsonObject.addProperty("giro_amount", "" + paymentGiro.get(0).getAmount());
        jsonObject.addProperty("giro_due_date", paymentGiro.get(0).getGiro_due_date());
        jsonObject.addProperty("giro_number1", paymentGiro.get(1).getNo_giro());
        jsonObject.addProperty("giro_photo1", "Foto giro 2");
        jsonObject.addProperty("giro_amount1", "" + paymentGiro.get(1).getAmount());
        jsonObject.addProperty("giro_due_date1", paymentGiro.get(1).getGiro_due_date());
        jsonObject.addProperty("giro_number2", paymentGiro.get(2).getNo_giro());
        jsonObject.addProperty("giro_photo2", "Foto giro 3");
        jsonObject.addProperty("giro_amount2", "" + paymentGiro.get(2).getAmount());
        jsonObject.addProperty("giro_due_date2", paymentGiro.get(2).getGiro_due_date());
        jsonObject.addProperty("giro_number3", paymentGiro.get(3).getNo_giro());
        jsonObject.addProperty("giro_photo3", "Foto giro 4");
        jsonObject.addProperty("giro_amount3", "" + paymentGiro.get(3).getAmount());
        jsonObject.addProperty("giro_due_date3", paymentGiro.get(3).getGiro_due_date());
        jsonObject.addProperty("giro_number4", paymentGiro.get(4).getNo_giro());
        jsonObject.addProperty("giro_photo4", "Foto giro 5");
        jsonObject.addProperty("giro_amount4", "" + paymentGiro.get(4).getAmount());
        jsonObject.addProperty("giro_due_date4", paymentGiro.get(4).getGiro_due_date());

        jsonObject.addProperty("giro_photo_string", base64GiroPhoto);
        jsonObject.addProperty("giro_photo_string1", base64GiroPhoto1);
        jsonObject.addProperty("giro_photo_string2", base64GiroPhoto2);
        jsonObject.addProperty("giro_photo_string3", base64GiroPhoto3);
        jsonObject.addProperty("giro_photo_string4", base64GiroPhoto4);

        SyncManager.storeSync(MyApplication.obtainLocalDB(), assignmentId,
                Constant.SyncType.INPUT_INVOICE_PAYMENT, jsonObject.toString(), 1);
    }

    private void initCurrentLocation() {
        LocationUtils locationUtils = new LocationUtils(getActivity());
        locationUtils.setCurrentLocation();
    }

    private void completeAssignment() {

        progressLoading.setVisibility(View.VISIBLE);
        setButtonDisable();

        AssignmentViewModel assignmentViewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);

        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), "" + MyApplication.getInstance().latitude());
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), "" + MyApplication.getInstance().longitude());
        RequestBody ProcessedTime = RequestBody.create(MediaType.parse("text/plain"), DateUtils.getCurrentTime(Constant.DateFormat.SERVER));
        RequestBody assignmentComplete = RequestBody.create(MediaType.parse("text/plain"), Constant.Data.AssignmentCode.TASK_COMPLETED);

        assignmentViewModel.assignmentComplete(NetworkUtils.getConnectionManager(),
                assignmentDetail.getAssignmentId(),
                latitude, longitude, ProcessedTime, assignmentComplete, true);
        assignmentViewModel.getAssignmentCompleteResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                progressLoading.setVisibility(View.GONE);
                setButtonEnable();

                BaseModel response = (BaseModel) apiResponse.getData();
                if (response != null) {
                    ((InvoiceActivity) getActivity()).setTitle(getString(R.string.labelSuccess));
                    ((InvoiceActivity) getActivity()).hideIndicator();

                    containerInput.setVisibility(View.GONE);
                    skipVerification.setVisibility(View.GONE);
                    containerSuccess.setVisibility(View.VISIBLE);

//                    PrefManager.saveBoolean(Constant.Preference.ASSIGNMENT_COMPLETE, true);
                }
            }
        });

    }

    private void storeAssignmentCompleteToSync() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("latitude", "" + MyApplication.getInstance().latitude());
        jsonObject.addProperty("longitude", "" + MyApplication.getInstance().longitude());
        jsonObject.addProperty("assignment_complete", Constant.Data.AssignmentCode.TASK_COMPLETED);

        SyncManager.storeSync(MyApplication.obtainLocalDB(), assignmentId,
                Constant.SyncType.ASSIGNMENT_COMPLETE, jsonObject.toString(), 1);

    }

    private void setButtonEnable() {
        btnSend.setEnabled(true);
        resendOTPRequest.setEnabled(true);
    }

    private void setButtonDisable() {
        btnSend.setEnabled(false);
        resendOTPRequest.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSend)
    public void onViewClicked() {

        String otpCode = edtVerificationCode.getText().toString();
        if (TextUtils.isEmpty(otpCode)){
            edtVerificationCode.setError(getString(R.string.alertMessageOTPCodeEmpty));
        }else {

            initPaymentGiro();
            inputInvoicePayment();

        }

    }

    private void initPaymentGiro() {
        paymentGiro = ((InvoiceActivity) getActivity()).getGiroPayment();

        if (paymentGiro.size() == 0 || paymentGiro == null){
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
        } else if (paymentGiro.size() == 1){
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
        }else if (paymentGiro.size() == 2){
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
        }else if (paymentGiro.size() == 3){
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
        }else if (paymentGiro.size() == 4){
            paymentGiro.add(new InvoicePayment(Constant.Data.PaymentMethod.GIRO, 0, "", null, ""));
        }
    }

    @OnClick(R.id.skipVerification)
    public void skipVerification(){
        initPaymentGiro();
        inputInvoicePayment();
    }

    @OnClick(R.id.resendOTPRequest)
    public void resendOTPRequest() {
        requestOTP();
    }

}
