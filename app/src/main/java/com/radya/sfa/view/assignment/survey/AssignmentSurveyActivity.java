package com.radya.sfa.view.assignment.survey;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.util.ActivityUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.view.BaseActivity;
import com.radya.sfa.view.assignment.detail.AssignmentDetailSurvey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssignmentSurveyActivity extends BaseActivity {

    @BindView(R.id.appBarTitle)
    TextView appBarTitle;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.icNavRight)
    ImageView icNavRight;
    @BindView(R.id.navRight)
    RelativeLayout navRight;

    private Bundle bundle;
    private AssignmentDetailSurvey survey;

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
            survey = (AssignmentDetailSurvey) bundle.getSerializable(Constant.SERIALIZABLE_NAME);

            initView();

        }
    }

    private void initView() {
        progressLoading.setVisibility(View.GONE);

        appBarTitle.setText(survey.getName());

        bundle = new Bundle();
        bundle.putSerializable(Constant.Bundle.SURVEY_URL, survey.getLink());

        Fragment fragment = AssignmentSurveyFragment.newInstance();
        fragment.setArguments(bundle);

        ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.contentFrame, false);

    }

    @OnClick(R.id.navLeft)
    public void onViewClicked() {
        onBackPressed();
        IntentUtils.backAnimation(this);
    }

    @OnClick(R.id.navRight)
    public void completeSurvei() {
        onBackPressed();
        IntentUtils.backAnimation(this);
    }

}
