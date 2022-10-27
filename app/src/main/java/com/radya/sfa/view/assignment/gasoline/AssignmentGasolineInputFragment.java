package com.radya.sfa.view.assignment.gasoline;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.EditTextUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.util.StringUtils;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.assignment.AssignmentViewModel;

import java.io.File;

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

public class AssignmentGasolineInputFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.edtInputLiter)
    EditText edtInputLiter;
    @BindView(R.id.imgGasolineBill)
    ImageView imgGasolineBill;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.addGasolineBillPhoto)
    ImageView addGasolineBillPhoto;
    @BindView(R.id.edtSPBUAddress)
    EditText edtSPBUAddress;
    @BindView(R.id.edtNote)
    EditText edtNote;
    @BindView(R.id.edtInputAmount)
    EditText edtInputAmount;

    private File gasolineBill;

    private AssignmentViewModel viewModel;

    public AssignmentGasolineInputFragment() {
        // Requires empty public constructor
    }

    public static AssignmentGasolineInputFragment newInstance() {
        return new AssignmentGasolineInputFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assignment_gasoline_input_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViewModel();
        initView();
        return view;
    }

    private void initView() {
        EditTextUtils.setCurrency(edtInputAmount);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);
    }

    private void createAssignmentGasoline() {

        String address = edtSPBUAddress.getText().toString();
        String remarks = edtNote.getText().toString();
        String liter = edtInputLiter.getText().toString();
        String gasolineAmount = edtInputAmount.getText().toString();
        double latitude = MyApplication.getInstance().latitude();
        double longitude = MyApplication.getInstance().longitude();
        String eventDate = DateUtils.getCurrentTime(Constant.DateFormat.SERVER);

        if (TextUtils.isEmpty(liter)) {
            edtInputLiter.setError(getString(R.string.alertMessageLiterEmpty));
        } else if (TextUtils.isEmpty(gasolineAmount)){
            edtInputAmount.setError(getString(R.string.alertMessageGasolineAmountEmpty));
        } else if (gasolineBill == null) {
            ToastUtils.showToast(getString(R.string.alertMessageGasolineBillEmpty));
        } else {
            progressLoading.setVisibility(View.VISIBLE);

            RequestBody requestFile;
            MultipartBody.Part Attachment;

            requestFile = RequestBody.create(MediaType.parse("image/jpg"), gasolineBill);
            Attachment = MultipartBody.Part.createFormData(Constant.Api.Params.ATTACHMENT, gasolineBill.getName(), requestFile);

            RequestBody Address = RequestBody.create(MediaType.parse("text/plain"), address);
            RequestBody Remarks = RequestBody.create(MediaType.parse("text/plain"), remarks);
            RequestBody Amount = RequestBody.create(MediaType.parse("text/plain"), StringUtils.clearSign(gasolineAmount));
            RequestBody Liter = RequestBody.create(MediaType.parse("text/plain"), "" + liter);
            RequestBody Latitude = RequestBody.create(MediaType.parse("text/plain"), "" + latitude);
            RequestBody Longitude = RequestBody.create(MediaType.parse("text/plain"), "" + longitude);
            RequestBody EventDate = RequestBody.create(MediaType.parse("text/plain"), eventDate);

            viewModel.assignmentGasoline(NetworkUtils.getConnectionManager(),
                    Constant.Data.AssignmentCreateType.GASOLINE,
                    Address, Remarks, Liter,
                    Latitude, Longitude, EventDate, Attachment);
            viewModel.getAssignmentGasolineResponse().observe(this, new Observer<ApiResponse>() {
                @Override
                public void onChanged(@Nullable ApiResponse apiResponse) {
                    progressLoading.setVisibility(View.GONE);

                    BaseModel response = (BaseModel) apiResponse.getData();
                    if (response != null) {
                        ToastUtils.showToast(response.getAlert().getMessage());
                        ((AssignmentGasolineInputActivity) getActivity()).back();
                    }

                }
            });
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.imgGasolineBill, R.id.addGasolineBillPhoto, R.id.btnAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgGasolineBill:
                ((AssignmentGasolineInputActivity) getActivity()).launchCamera();
                break;
            case R.id.addGasolineBillPhoto:
                ((AssignmentGasolineInputActivity) getActivity()).launchCamera();
                break;
            case R.id.btnAdd:
                createAssignmentGasoline();
                break;
        }
    }

    public void setImageCaptured(File file, Bitmap bitmap) {
        gasolineBill = file;
        imgGasolineBill.setImageBitmap(bitmap);

        addGasolineBillPhoto.setVisibility(View.GONE);
    }
}
