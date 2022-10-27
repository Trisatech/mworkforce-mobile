package com.radya.sfa.view.assignment.detail;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.radya.sfa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-03-12.
 */

public class AddOTPDialog extends DialogFragment {

    Unbinder unbinder;
    @BindView(R.id.btnClose)
    ImageView btnClose;
    @BindView(R.id.txtDialogTitle)
    TextView txtDialogTitle;
    @BindView(R.id.edtInputOTP)
    EditText edtInputOTP;
    @BindView(R.id.containerInput)
    LinearLayout containerInput;
    @BindView(R.id.btnAdd)
    RelativeLayout btnAdd;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.add_otp_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnClose, R.id.btnAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                getDialog().dismiss();
                break;
            case R.id.btnAdd:
                ((AssignmentDetailActivity) getActivity()).completeAssignment();
                break;
        }
    }
}
