package com.radya.sfa.view.assignment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.view.assignment.list.AssignmentAll;
import com.radya.sfa.view.assignment.list.AssignmentList;
import com.radya.sfa.view.assignment.list.AssignmentListOnCompleteFragment;
import com.radya.sfa.view.assignment.list.AssignmentListOnProgressFragment;
import com.radya.sfa.view.assignment.list.AssignmentListPagerAdapter;
import com.radya.sfa.view.assignment.search.AssignmentSearchActivity;
import com.radya.sfa.view.home.HomeActivity;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class AssignmentFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.txtOngoingInvestment)
    TextView txtOngoingInvestment;
    @BindView(R.id.txtDoneInvestment)
    TextView txtDoneInvestment;
    @BindView(R.id.viewDashboard)
    ViewPager viewVisiting;
    @BindView(R.id.tabPortofolio)
    TabLayout tabPortofolio;
    @BindView(R.id.edtSearchAssignment)
    EditText edtSearchAssignment;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private AssignmentViewModel viewModel;
    private AssignmentAll assignmentOnProgressData, assignmentCompleteData;

    public AssignmentListOnProgressFragment assignmentOnProgress;
    public AssignmentListOnCompleteFragment assignmentComplete;

    private AssignmentListPagerAdapter pagerAdapter;
    private CharSequence pagerTitle[] = {"", ""};
    private int numbOfTabs;

    public AssignmentFragment() {
        // Requires empty public constructor
    }

    public static AssignmentFragment newInstance() {
        return new AssignmentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assignment_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

//        initViewModel();
        getAssignment(DateUtils.getCurrentTime(Constant.DateFormat.SERVER));

        edtSearchAssignment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtSearchAssignment.getWindowToken(), 0);

                    String q = edtSearchAssignment.getText().toString();
                    if (!q.equals("")) {
                        edtSearchAssignment.setText("");

                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.Bundle.KEYWORD, q);

                        IntentUtils.intentTo(getActivity(), AssignmentSearchActivity.class, false, bundle);
                    }

                    return true;
                }
                return false;
            }
        });

        viewVisiting.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    txtOngoingInvestment.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                    txtDoneInvestment.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGrey));
                } else {
                    txtOngoingInvestment.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorGrey));
                    txtDoneInvestment.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);
        getAssignment(DateUtils.getCurrentTime(Constant.DateFormat.SERVER));
    }

    public void getAssignment(String date) {
//        getAssignmentOnProgress(date);
//        getAssignmentComplete(date);

        initFragment(date);
    }

    private void getAssignmentOnProgress(final String date) {

        progressLoading.setVisibility(View.VISIBLE);
        viewModel.getAssignmentAllOnProgress(NetworkUtils.getConnectionManager(), date, date);
        viewModel.getAssignmentAllProgressResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                AssignmentAll response = (AssignmentAll) apiResponse.getData();
                if (response != null) {
                    assignmentOnProgressData = response;
                }
            }
        });

    }

    private void getAssignmentComplete(final String date) {

        viewModel.getAssignmentAllOnComplete(NetworkUtils.getConnectionManager(), date, date);
        viewModel.getAssignmentAllCompleteResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                AssignmentAll response = (AssignmentAll) apiResponse.getData();
                if (response != null) {
                    assignmentCompleteData = response;
                }

//                initFragment(date);
            }
        });

    }


    private void initFragment(String date) {
        assignmentOnProgress = AssignmentListOnProgressFragment.newInstance();
        assignmentComplete = AssignmentListOnCompleteFragment.newInstance();

        numbOfTabs = 2;
        pagerAdapter = new AssignmentListPagerAdapter(getFragmentManager(),
                assignmentOnProgress, assignmentComplete, pagerTitle, numbOfTabs, date); //,assignmentOnProgressData, assignmentCompleteData

        viewVisiting.setAdapter(pagerAdapter);
        viewVisiting.setOffscreenPageLimit(numbOfTabs);
        tabPortofolio.setupWithViewPager(viewVisiting);
        viewVisiting.setCurrentItem(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        Handler handler = new Handler();
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                getAssignment(DateUtils.getCurrentTime(Constant.DateFormat.SERVER));
            }
        });
//
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnCalendarView)
    public void onViewClicked() {

//        AssignmentList assignmentList = MyApplication.getInstance().getAssignmentList();
//
//        if (assignmentList != null) {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(Constant.SERIALIZABLE_NAME, assignmentList);
//
//            AssignmentCalendarDialog taskCalendarDialog = new AssignmentCalendarDialog();
//            taskCalendarDialog.setCancelable(false);
//            taskCalendarDialog.setArguments(bundle);
//
//            assert getFragmentManager() != null;
//            taskCalendarDialog.show(getFragmentManager(), "");
//        }

    }
}
