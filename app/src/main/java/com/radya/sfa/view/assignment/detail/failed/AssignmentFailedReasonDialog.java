package com.radya.sfa.view.assignment.detail.failed;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.R;
import com.radya.sfa.data.source.preference.PrefManager;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.util.ImageUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.util.RequestBodyUtils;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.assignment.AssignmentViewModel;
import com.radya.sfa.view.assignment.detail.AssignmentDetailActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by aderifaldi on 2018-03-12.
 */

public class AssignmentFailedReasonDialog extends DialogFragment {

    Unbinder unbinder;
    @BindView(R.id.btnAdd)
    RelativeLayout btnAdd;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.txtReason)
    TextView txtReason;
    @BindView(R.id.imgReason)
    ImageView imgReason;
    @BindView(R.id.addPhotoReason)
    ImageView addPhotoReason;
    @BindView(R.id.edtReasonPlus)
    EditText edtReasonPlus;
    @BindView(R.id.containerReasonPlus)
    RelativeLayout containerReasonPlus;
    @BindView(R.id.inputPhotoReason)
    RelativeLayout inputPhotoReason;

    private AssignmentViewModel viewModel;
    private File failedReasonPhoto;
    private String FailedReasonPlus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.assignment_failed_reason_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViewModel();

        return view;

    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnClose, R.id.btnAdd, R.id.txtReason, R.id.imgReason, R.id.addPhotoReason})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                dismissDialog();
                break;
            case R.id.btnAdd:

                String reason = txtReason.getText().toString();

                if (reason.equals("Pilih alasan kunjungan batal")) {
                    ToastUtils.showToast("Pilih alasan kunjungan batal terlebih dahulu");
                } else {

                    String reasonCode = ((AssignmentDetailActivity) getActivity()).getAssignmentReason().getReason_code();

                    if (reasonCode.equals("000")){

                        FailedReasonPlus = edtReasonPlus.getText().toString();
                        if (TextUtils.isEmpty(FailedReasonPlus)){
                            edtReasonPlus.setError("Masukan alasan laiinya terlebih dulu");
                        }else {
                            setAssignmentFailed();
                        }

                    }else {
                        setAssignmentFailed();
                    }
                }

                break;
            case R.id.txtReason:
                AssignmentFailedReasonSelectorDialog reasonSelectorDialog = new AssignmentFailedReasonSelectorDialog();
                reasonSelectorDialog.show(getFragmentManager(), "");
                break;
            case R.id.imgReason:
                ((AssignmentDetailActivity) getActivity()).launchCamera();
                break;
            case R.id.addPhotoReason:
                ((AssignmentDetailActivity) getActivity()).launchCamera();
                break;
        }
    }

    private void setAssignmentFailed() {
        progressLoading.setVisibility(View.VISIBLE);

        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), "" + MyApplication.getInstance().latitude());
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), "" + MyApplication.getInstance().longitude());
        RequestBody assignmentComplete = RequestBody.create(MediaType.parse("text/plain"), Constant.Data.AssignmentCode.TASK_FAILED);
        RequestBody failedReasonCode = RequestBody.create(MediaType.parse("text/plain"), ((AssignmentDetailActivity) getActivity()).getAssignmentReason().getReason_code());
        RequestBody failedReasonPlus = RequestBody.create(MediaType.parse("text/plain"), FailedReasonPlus);

        String taskId = ((AssignmentDetailActivity) getActivity()).getAssgnmentId();

        RequestBody requestFileFailedReasonPhoto;
        MultipartBody.Part FailedReasonPhoto;

        File failedReasonPhotoFile = failedReasonPhoto;
        if (failedReasonPhotoFile == null) {
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.null_image);
            failedReasonPhotoFile = ImageUtils.persistImage(getActivity(), bm, "null_image");
        }

        requestFileFailedReasonPhoto = RequestBodyUtils.getImage(failedReasonPhotoFile);
        FailedReasonPhoto = MultipartBody.Part.createFormData("file", failedReasonPhotoFile.getName(), requestFileFailedReasonPhoto);

        viewModel.assignmentFailed(NetworkUtils.getConnectionManager(), taskId,
                latitude, longitude, assignmentComplete, failedReasonCode, FailedReasonPhoto, failedReasonPlus);
        viewModel.getAssignmentFailedReaponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                dismissDialog();
                getActivity().onBackPressed();
//                PrefManager.saveBoolean(Constant.Preference.ASSIGNMENT_COMPLETE, true);
            }
        });
    }

    private void dismissDialog() {
        getDialog().dismiss();
    }

    public void setAssignmentReason(AssignmentFailedReason.AssignmentReason assignmentReason) {

        if (assignmentReason.getReason_code().equals("000")){
            containerReasonPlus.setVisibility(View.VISIBLE);
        }else {
            containerReasonPlus.setVisibility(View.GONE);
        }

        txtReason.setText(assignmentReason.getReason_name());
    }

    public void setImageCaptured(File file, Bitmap rotatedBitmap) {
        failedReasonPhoto = file;
        imgReason.setImageBitmap(rotatedBitmap);
        addPhotoReason.setVisibility(View.GONE);
    }
}
