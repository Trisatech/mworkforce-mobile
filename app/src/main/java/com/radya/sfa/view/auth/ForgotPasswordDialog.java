package com.radya.sfa.view.auth;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.radya.sfa.R;
import com.radya.sfa.util.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-03-12.
 */

public class ForgotPasswordDialog extends DialogFragment {

    Unbinder unbinder;
    @BindView(R.id.edtEmailAddress)
    EditText edtEmailAddress;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.forgot_password_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnClose, R.id.btnSendNow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                getDialog().dismiss();
                break;
            case R.id.btnSendNow:

                String email = edtEmailAddress.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    edtEmailAddress.setError(getResources().getString(R.string.errorMessageEmptyEmailAddress));
                } else if (!AppUtils.isValidEmailAddress(email)) {
                    edtEmailAddress.setError(getResources().getString(R.string.errorMessageEmailNotValid));
                } else {
                    getDialog().dismiss();
                }

                break;
        }
    }
}
