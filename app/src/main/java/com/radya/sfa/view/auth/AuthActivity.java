package com.radya.sfa.view.auth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.util.ActivityUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.view.BaseActivity;

public class AuthActivity extends BaseActivity {

    private Bundle bundle;
    private int page;
    private Fragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);

        bundle = getIntent().getExtras();
        if (bundle != null){
            page = bundle.getInt(Constant.Bundle.PAGE);

            if (page == Constant.Page.LOGIN){
                fragment = LoginFragment.newInstance();
            }

            ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.contentFrame, false);

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        IntentUtils.backAnimation(this);
    }
}
