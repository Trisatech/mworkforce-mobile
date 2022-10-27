package com.radya.sfa.view.report;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.util.ActivityUtils;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportDetailActivity extends BaseActivity {

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
    private String id;

    private ReportViewModel viewModel;
    private long totalInvoice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        ButterKnife.bind(this);
        appBarTitle.setText("Detail Laporan");
        initViewModel();
        initData();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
    }

    private void initData() {
        bundle = getIntent().getExtras();

        if (bundle != null){
            id = bundle.getString("id");
            String date = bundle.getString("date");
            totalInvoice = bundle.getLong("tagihan");

            loadReportDetail(id, date);
        }
    }

    public long getTotalInvoice(){
        return totalInvoice;
    }

    private void loadReportDetail(final String id, final String date) {
        progressLoading.setVisibility(View.VISIBLE);

        viewModel.getReportDetail(NetworkUtils.getConnectionManager(), id, date);
        viewModel.getResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                ReportDetail response = (ReportDetail) apiResponse.getData();
                if (response != null){
                    bundle = new Bundle();
                    bundle.putSerializable(Constant.SERIALIZABLE_NAME, response);

                    Fragment fragment = ReportDetailFragmentDev.newInstance();
                    fragment.setArguments(bundle);

                    ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.contentFrame, false);
                }
            }
        });
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
