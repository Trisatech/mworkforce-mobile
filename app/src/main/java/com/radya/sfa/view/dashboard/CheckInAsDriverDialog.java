package com.radya.sfa.view.dashboard;

import android.graphics.Bitmap;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.radya.sfa.R;
import com.radya.sfa.util.Base64Utils;
import com.radya.sfa.util.EditTextUtils;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.assignment.gasoline.AssignmentGasolineInputActivity;
import com.radya.sfa.view.home.HomeActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-03-12.
 */

public class CheckInAsDriverDialog extends DialogFragment {

    Unbinder unbinder;
    @BindView(R.id.edtInputKm)
    EditText edtInputKm;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.imgSpedoMeter)
    ImageView imgSpedoMeter;
    @BindView(R.id.addPhotoSpedoMeter)
    ImageView addPhotoSpedoMeter;

    private File spedoMeterPhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.checkin_as_driver_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();

        return view;

    }

    private void initView() {
        EditTextUtils.setCurrency(edtInputKm);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnClose, R.id.btnAdd, R.id.imgSpedoMeter, R.id.addPhotoSpedoMeter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                getDialog().dismiss();
                break;
            case R.id.btnAdd:

                String inputKm = edtInputKm.getText().toString();
                if (TextUtils.isEmpty(inputKm)) {
                    edtInputKm.setError(getResources().getString(R.string.alertMessageInputKmEmpty));
                } else if (spedoMeterPhoto == null){
                    ToastUtils.showToast("Harap ambil foto spedo meter terlebih dahulu");
                } else {
                    ((HomeActivity) getActivity()).checkInDriver(inputKm.replace(".", ""), spedoMeterPhoto);
                    getDialog().dismiss();
                }
                break;
            case R.id.imgSpedoMeter:
                ((HomeActivity) getActivity()).launchCamera(0);
                break;
            case R.id.addPhotoSpedoMeter:
                ((HomeActivity) getActivity()).launchCamera(0);
                break;
        }
    }

    public void setImageCaptured(File file, Bitmap rotatedBitmap) {

        spedoMeterPhoto = file;
//        imgSpedoMeter.setImageBitmap(rotatedBitmap);

        addPhotoSpedoMeter.setVisibility(View.GONE);

        String base64Image = Base64Utils.getBase64ofImage(file);
        Bitmap imgBitmap = Base64Utils.getBitmapOfBase64(base64Image);
        imgSpedoMeter.setImageBitmap(imgBitmap);

    }
}
