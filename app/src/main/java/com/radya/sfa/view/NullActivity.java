package com.radya.sfa.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.radya.sfa.R;
import com.radya.sfa.util.IntentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NullActivity extends BaseActivity {

    @BindView(R.id.icNavLeft)
    ImageView icNavLeft;
    @BindView(R.id.navLeft)
    RelativeLayout navLeft;
    @BindView(R.id.icNavRight)
    ImageView icNavRight;
    @BindView(R.id.navRight)
    RelativeLayout navRight;
    @BindView(R.id.appBarTitle)
    TextView appBarTitle;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        if (bundle != null){

        }
    }

    @OnClick({R.id.navLeft, R.id.navRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.navLeft:
                onBackPressed();
                IntentUtils.backAnimation(this);
                break;
            case R.id.navRight:
                break;
        }
    }

}
