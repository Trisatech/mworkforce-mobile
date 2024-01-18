package com.radya.sfa.view.report;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.NetworkUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class ReportListFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.txtCurrentDate)
    TextView txtCurrentDate;
    @BindView(R.id.listReport)
    RecyclerView listReport;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.txtCurrentDate2)
    TextView txtCurrentDate2;
    @BindView(R.id.containerEmptyInfo)
    LinearLayout containerEmptyInfo;

    private ReportViewModel viewModel;
    private LinearLayoutManager linearLayoutManager;

    private ReportListAdapter adapter;
    private String currentDate;

    public ReportListFragment() {
        // Requires empty public constructor
    }

    public static ReportListFragment newInstance() {
        return new ReportListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initViewModel();
        initView();
        initCurrentDate();
        return view;
    }

    private void initCurrentDate() {
        currentDate = DateUtils.getCurrentTime(Constant.DateFormat.SERVER);
        loadReport(currentDate);
    }

    private void loadReport(String currentDate) {
        txtCurrentDate.setText(DateUtils.convertStringDate(currentDate, Constant.DateFormat.MEDIUM_2));

        progressLoading.setVisibility(View.VISIBLE);

        adapter.getData().clear();
        adapter.notifyDataSetChanged();

        viewModel.getReportList(NetworkUtils.getConnectionManager(), currentDate);
        viewModel.getResponse().observe(getViewLifecycleOwner(), new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);

                ReportList response = (ReportList) apiResponse.getData();

                if (response != null) {
                    if (response.getData().size() != 0) {
                        containerEmptyInfo.setVisibility(View.GONE);
                        for (int i = 0; i < response.getData().size(); i++) {
                            adapter.getData().add(response.getData().get(i));
                            adapter.notifyItemInserted(adapter.getData().size() - 1);
                        }
                        adapter.notifyDataSetChanged();
                    }else {
                        containerEmptyInfo.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

    }

    private void initView() {
        adapter = new ReportListAdapter();
        linearLayoutManager = new LinearLayoutManager(getActivity());

        listReport.setLayoutManager(linearLayoutManager);
        listReport.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Report item = adapter.getData().get(i);
                String id = item.getUserId();

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putLong("tagihan", item.getTotalInvoice());
                bundle.putString("date", currentDate);

                IntentUtils.intentTo(getActivity(), ReportDetailActivity.class, false, bundle);

            }
        });
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.txtCurrentDate)
    public void onViewClicked() {
        showDatePicker(getActivity(), txtCurrentDate2, txtCurrentDate, Constant.DateFormat.SERVER);
    }

    private void showDatePicker(Activity activity, final TextView textView, final TextView textView2, final String format) {
        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

                        currentDate = dateFormat.format(calendar.getTime());
                        textView.setText(dateFormat.format(calendar.getTime()));

                        String showDate = convertStringDate(dateFormat.format(calendar.getTime()), Constant.DateFormat.MEDIUM_2);
                        textView2.setText(showDate);

                        loadReport(textView.getText().toString());


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

        c.add(Calendar.MONTH, -1);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    String convertStringDate(String date, String format) {

        SimpleDateFormat sdf;
        SimpleDateFormat dateFormat;
        Date convertedDate;

        sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7"));

        dateFormat = new SimpleDateFormat(Constant.DateFormat.SERVER);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            convertedDate = dateFormat.parse(date);
            return sdf.format(convertedDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        }

    }

}
