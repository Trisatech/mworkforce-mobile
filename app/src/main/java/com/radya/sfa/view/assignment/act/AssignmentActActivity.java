package com.radya.sfa.view.assignment.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.util.ActivityUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.view.BaseActivity;
import com.radya.sfa.view.assignment.Assignment;
import com.radya.sfa.view.assignment.detail.AssignmentDetail;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssignmentActActivity extends BaseActivity {

    @BindView(R.id.icNavLeft)
    ImageView icNavLeft;
    @BindView(R.id.navLeft)
    RelativeLayout navLeft;
    @BindView(R.id.appBarTitle)
    TextView appBarTitle;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private Bundle bundle;
    private Assignment assignmentDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            assignmentDetail = (Assignment) bundle.getSerializable(Constant.SERIALIZABLE_NAME);

            initView();

        }
    }

    private void initView() {
        appBarTitle.setText(R.string.labelTitleAssignmentActivity);
        progressLoading.setVisibility(View.GONE);

        bundle = new Bundle();
        bundle.putSerializable(Constant.SERIALIZABLE_NAME, assignmentDetail);

        Fragment fragment = AssignmentActFragment.newInstance();
        fragment.setArguments(bundle);

        ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.contentFrame, false);
    }

    @OnClick(R.id.navLeft)
    public void onViewClicked() {
        onBackPressed();
        IntentUtils.backAnimation(this);
    }
}
