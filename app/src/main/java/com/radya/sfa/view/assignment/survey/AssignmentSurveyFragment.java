package com.radya.sfa.view.assignment.survey;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.util.IntentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class AssignmentSurveyFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.webView)
    WebView webView;

    private Bundle bundle;
    private String surveyLink;

    public AssignmentSurveyFragment() {
        // Requires empty public constructor
    }

    public static AssignmentSurveyFragment newInstance() {
        return new AssignmentSurveyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assignment_survey_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        bundle = getArguments();
        if (bundle != null) {
            surveyLink = bundle.getString(Constant.Bundle.SURVEY_URL);

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

//            webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSeszrgYaRvGTI9r4OccZ0O-ArMU8QYHga4UT2lr1WAjxYN4eA/viewform");
            webView.loadUrl(surveyLink);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnDone)
    public void onViewClicked() {
        getActivity().onBackPressed();
        IntentUtils.backAnimation(getActivity());
    }
}
