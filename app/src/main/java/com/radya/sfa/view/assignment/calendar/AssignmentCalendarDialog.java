package com.radya.sfa.view.assignment.calendar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.view.assignment.Assignment;
import com.radya.sfa.view.assignment.AssignmentViewModel;
import com.radya.sfa.view.assignment.detail.AssignmentDetail;
import com.radya.sfa.view.assignment.detail.AssignmentDetailActivity;
import com.radya.sfa.view.assignment.list.AssignmentList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-03-12.
 */

public class AssignmentCalendarDialog extends DialogFragment {

    private static final String SEPARATOR = ": ";

    Unbinder unbinder;
    @BindView(R.id.txtMonth)
    TextView txtMonth;
    @BindView(R.id.compactCalendarView)
    CompactCalendarView compactCalendarView;
    @BindView(R.id.listTask)
    RecyclerView listTask;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.txtEmptyTask)
    TextView txtEmptyTask;
    @BindView(R.id.txtDay)
    TextView txtDay;

    private Bundle bundle;
    private AssignmentList assignmentList;

    private List<Event> eventList;
    private List<Assignment> assignmentDataList;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    private AssignmentViewModel viewModel;

    private AssignmentCalendarAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.assignment_calendar_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        viewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);

        bundle = getArguments();
        if (bundle != null) {
            setuList();
            setupCalendarView();
            assignmentList = (AssignmentList) bundle.getSerializable(Constant.SERIALIZABLE_NAME);
            if (assignmentList.getData().size() != 0) {
                assignmentDataList = new ArrayList<>();
                for (int i = 0; i < assignmentList.getData().size(); i++) {
                    assignmentDataList.add(assignmentList.getData().get(i));
                }
                storeDataToCalendarView();
                initView();
            }

        }

        return view;

    }

    private void setuList() {
        adapter = new AssignmentCalendarAdapter();
        linearLayoutManager = new LinearLayoutManager(getActivity());

        listTask.setAdapter(adapter);
        listTask.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Assignment assignment = adapter.getData().get(i);

                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.SERIALIZABLE_NAME, assignment);

                IntentUtils.intentTo(getActivity(), AssignmentDetailActivity.class, false, bundle);
            }
        });
    }

    private void initView() {
        Calendar calendar = Calendar.getInstance();

        String currentDate = DateUtils.convertDateToString(calendar.getTime(), Constant.DateFormat.SHORT_2);
        String dateView = DateUtils.convertDateToString(calendar.getTime(), Constant.DateFormat.MEDIUM_2);

        loadTaskByCalendar(dateView, currentDate);
    }

    private void loadTaskByCalendar(final String dateView, String date) {

        txtEmptyTask.setVisibility(View.GONE);
        progressLoading.setVisibility(View.VISIBLE);

        viewModel.getAssignmentByCalendar(NetworkUtils.getConnectionManager(), date, Constant.Api.LIMIT, 0);
        viewModel.getAssignmentCalendarResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                AssignmentList assignmentList = (AssignmentList) apiResponse.getData();
                if (assignmentList != null) {
                    if (assignmentList.getData().size() != 0) {
                        txtEmptyTask.setVisibility(View.GONE);

                        adapter.getData().clear();
                        adapter.notifyDataSetChanged();

                        for (int i = 0; i < assignmentList.getData().size(); i++) {
                            adapter.getData().add(assignmentList.getData().get(i));
                            adapter.notifyItemInserted(adapter.getData().size() - 1);
                        }
                        adapter.notifyDataSetChanged();

                    } else {
                        adapter.getData().clear();
                        adapter.notifyDataSetChanged();

                        txtEmptyTask.setVisibility(View.VISIBLE);
                    }

                    String selectedDate = dateView;
                    txtDay.setText(selectedDate);
                }
            }
        });

    }

    private void setupCalendarView() {

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

        txtMonth.setText(dateFormat.format(calendar.getTime()));

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                String selectedDate = DateUtils.convertDateToString(dateClicked, Constant.DateFormat.SHORT_2);
                String dateView = DateUtils.convertDateToString(dateClicked, Constant.DateFormat.MEDIUM_2);

                loadTaskByCalendar(dateView, selectedDate);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                txtMonth.setText(dateFormat.format(firstDayOfNewMonth));

            }
        });
    }

    private void storeDataToCalendarView() {

        eventList = new ArrayList<>();
        eventList.clear();

        for (int i = 0; i < assignmentDataList.size(); i++) {
            eventList.add(new Event(ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                    DateUtils.convertDateToLong(assignmentDataList.get(i).getAssignmentStartTime()),
                    assignmentDataList.get(i)));
        }

        compactCalendarView.addEvents(eventList);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnClose)
    public void onViewClicked() {
        getDialog().dismiss();
    }
}
