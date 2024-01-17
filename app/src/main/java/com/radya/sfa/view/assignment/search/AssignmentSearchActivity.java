package com.radya.sfa.view.assignment.search;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.util.ActivityUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.view.BaseActivity;
import com.radya.sfa.view.assignment.AssignmentViewModel;
import com.radya.sfa.view.assignment.list.AssignmentList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssignmentSearchActivity extends BaseActivity {

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
    private AssignmentViewModel viewModel;
    private Bundle bundle;
    private String keyword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        ButterKnife.bind(this);
        initViewModel();
        initView();

        bundle = getIntent().getExtras();
        if (bundle != null) {
            keyword = bundle.getString(Constant.Bundle.KEYWORD);
            searchAssignment(keyword);
        }

    }

    private void initView() {
        appBarTitle.setText(R.string.labelTItleAssignmentSearch);
    }

    private void searchAssignment(String keyword) {

        progressLoading.setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constant.Api.Params.KEYWORD, keyword);

        viewModel.searchAssignment(NetworkUtils.getConnectionManager(), jsonObject, 0, Constant.Api.LIMIT, 0);
        viewModel.getAssignmentSearchResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                AssignmentList response = (AssignmentList) apiResponse.getData();
                if (response != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.SERIALIZABLE_NAME, response);

                    Fragment fragment = AssignmentSearchFragment.newInstance();
                    fragment.setArguments(bundle);

                    ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.contentFrame, false);

                }
            }
        });

    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);
    }

    @OnClick(R.id.navLeft)
    public void onViewClicked() {
        onBackPressed();
    }
}
