package com.radya.sfa.view.auth;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.data.source.preference.PrefManager;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.helper.Sha256;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.home.HomeActivity;

import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class LoginFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.edtEmailAddress)
    EditText edtEmailAddress;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnLogin)
    RelativeLayout btnLogin;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private AuthViewModel viewModel;

    public LoginFragment() {
        // Requires empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        viewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnForgotPassword, R.id.btnLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnForgotPassword:
                ForgotPasswordDialog forgotPasswordDialog = new ForgotPasswordDialog();
                forgotPasswordDialog.setCancelable(false);
                forgotPasswordDialog.show(getFragmentManager(), "");
                break;
            case R.id.btnLogin:

                String emailAddress = edtEmailAddress.getText().toString();
                String password = edtPassword.getText().toString();

                if (TextUtils.isEmpty(emailAddress)) {
                    edtEmailAddress.setError(getString(R.string.errorMessageEmptyEmailAddress));
                } else if (TextUtils.isEmpty(password)) {
                    edtPassword.setError(getString(R.string.errorMessageEmptyPassword));
                }

//                else if (!AppUtils.isValidEmailAddress(emailAddress)) {
//                    edtEmailAddress.setError(getString(R.string.errorMessageEmailNotValid));
//                }

                else if (password.length() < Constant.MIN_PASSWORD) {
                    edtPassword.setError(getString(R.string.errorMessageMinimumPassword));
                } else {
                    progressLoading.setVisibility(View.VISIBLE);
                    btnLogin.setEnabled(false);
                    try {
                        String encriptPassword = Sha256.hexString(password);

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty(Constant.Api.Params.USERNAME, emailAddress);
                        jsonObject.addProperty(Constant.Api.Params.PASSWORD, encriptPassword);

                        viewModel.login(NetworkUtils.getConnectionManager(), jsonObject);
                        viewModel.getUserLoginResponse().observe(this, new Observer<ApiResponse>() {
                            @Override
                            public void onChanged(@Nullable ApiResponse apiResponse) {

//                                PrefManager.saveBoolean(Constant.Preference.ASSIGNMENT_COMPLETE, true);

                                clearFormInput();

                                progressLoading.setVisibility(View.GONE);
                                btnLogin.setEnabled(true);

                                Login response = (Login) apiResponse.getData();
                                if (response != null){
                                    ToastUtils.showToast(response.getAlert().getMessage());
                                    if (response.getData() != null)
                                        login(response.getData().getLogin_token(), response.getData().getProfile());
                                    else
                                        ToastUtils.showToast(response.getAlert().getMessage());
                                }
                            }
                        });

                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }

                }

                break;
        }
    }

    private void login(String authToken, Profile profile) {
        PrefManager.saveBoolean(Constant.Preference.User.IS_LOGIN, true);
        PrefManager.saveString(Constant.Preference.User.AUTH_TOKEN, authToken);

        PrefManager.saveString(Constant.Preference.User.USER_NAME, profile.getUser_name());
        PrefManager.saveString(Constant.Preference.User.USER_CODE, profile.getUser_code());
        PrefManager.saveString(Constant.Preference.User.USER_ROLE_CODE, profile.getRole_code());
        PrefManager.saveString(Constant.Preference.User.USER_ROLE_NAME, profile.getRole_name());

        IntentUtils.intentTo(getActivity(), HomeActivity.class, true);
    }

    private void clearFormInput() {
        edtEmailAddress.setText("");
        edtPassword.setText("");
    }
}
