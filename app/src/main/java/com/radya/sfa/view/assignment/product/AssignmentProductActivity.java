package com.radya.sfa.view.assignment.product;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

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

public class AssignmentProductActivity extends BaseActivity {

    @BindView(R.id.appBarTitle)
    TextView appBarTitle;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private Bundle bundle;
    private Assignment assignmentDetail;

    private AssignmentProductFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        ButterKnife.bind(this);
        initData();
    }

    public void updateOrderQty(int position, int qty){
        fragment.updateOrderQty(position, qty);
    }

    private void initData() {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            assignmentDetail = (Assignment) bundle.getSerializable(Constant.SERIALIZABLE_NAME);
            initView();
        }
    }

    private void initView() {
        progressLoading.setVisibility(View.GONE);
        appBarTitle.setText(R.string.labelTitleOrderProduct);

        bundle = new Bundle();
        bundle.putSerializable(Constant.SERIALIZABLE_NAME, assignmentDetail);

        fragment = AssignmentProductFragment.newInstance();
        fragment.setArguments(bundle);

        ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.contentFrame, false);

    }

    public void back(){
        onBackPressed();
        IntentUtils.backAnimation(this);
    }

    @OnClick(R.id.navLeft)
    public void onViewClicked() {
        back();
    }
}
