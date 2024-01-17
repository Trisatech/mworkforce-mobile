package com.radya.sfa.view.news;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.view.dashboard.DashboardViewModel;
import com.radya.sfa.view.dashboard.news.DashboardNews;
import com.radya.sfa.view.dashboard.news.DashboardNewsAdapter;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class NewsFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.txtCurrentDate2)
    TextView txtCurrentDate2;
    @BindView(R.id.viewNews)
    ViewPager viewNews;
    @BindView(R.id.onBoardingIndicator)
    CircleIndicator onBoardingIndicator;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private DashboardNews news;
    private DashboardNewsAdapter newsAdapter;

    private DashboardViewModel viewModel;

    public NewsFragment() {
        // Requires empty public constructor
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViewModel();
        initCurrentDate();
        return view;
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        initNews();
    }

    private void initCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        txtCurrentDate2.setText(DateUtils.convertDateToString(currentDate, Constant.DateFormat.MEDIUM_2));
    }

    private void initNews() {

        progressLoading.setVisibility(View.VISIBLE);

        viewModel.getNewsList(NetworkUtils.getConnectionManager(), 0);
        viewModel.getNewsListResponse().observe(getViewLifecycleOwner(), new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                progressLoading.setVisibility(View.GONE);

                DashboardNews response = (DashboardNews) apiResponse.getData();

                if (response != null) {
                    news = response;

                    newsAdapter = new DashboardNewsAdapter(getActivity(), news.getData());

                    viewNews.setAdapter(newsAdapter);
                    viewNews.setOffscreenPageLimit(news.getData().size());

                    onBoardingIndicator.setViewPager(viewNews);
                    onBoardingIndicator.getDataSetObserver().onInvalidated();

                }

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
