package com.radya.sfa.view.assignment.list;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.view.ListView;
import com.radya.sfa.view.assignment.Assignment;
import com.radya.sfa.view.assignment.AssignmentViewModel;
import com.radya.sfa.view.assignment.detail.AssignmentDetail;
import com.radya.sfa.view.assignment.detail.AssignmentDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class AssignmentListOnProgressFragment extends Fragment implements ListView {

    Unbinder unbinder;
    @BindView(R.id.listTaskOnProgress)
    RecyclerView listTaskOnProgress;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.txtEmptyInfo)
    TextView txtEmptyInfo;
    @BindView(R.id.containerEmptyInfo)
    LinearLayout containerEmptyInfo;

    private AssignmentListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private List<Assignment> assignmentDataList;

    private Bundle bundle;
    private AssignmentAll data;

    private AssignmentViewModel viewModel;
    private String date;

    public AssignmentListOnProgressFragment() {
        // Requires empty public constructor
    }

    public static AssignmentListOnProgressFragment newInstance() {
        return new AssignmentListOnProgressFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assignment_list_content_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        bundle = getArguments();
        if (bundle != null){
            date = bundle.getString("date");
            setupList();
        }

        initViewModel();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        initViewModel();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);
//        getAssignment(DateUtils.getCurrentTime(Constant.DateFormat.SERVER));
        getAssignment(date);
    }

    private void getAssignment(String date) {
        getAssignmentOnProgress(date);
    }

    private void getAssignmentOnProgress(String date) {
        progressLoading.setVisibility(View.VISIBLE);
        viewModel.getAssignmentAllOnProgress(NetworkUtils.getConnectionManager(), date, date);
        viewModel.getAssignmentAllProgressResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                AssignmentAll response = (AssignmentAll) apiResponse.getData();
                if (response != null) {
                    data = response;
                    setupList();
                }
            }
        });
    }

    @Override
    public void loadData() {
        adapter.getData().clear();
        adapter.notifyDataSetChanged();

        if (data != null) {
            containerEmptyInfo.setVisibility(View.GONE);

            if (data.getData().size() != 0) {
                assignmentDataList = new ArrayList<>();
                for (int i = 0; i < data.getData().size(); i++) {
                    assignmentDataList.add(data.getData().get(i));
                }

                storeDataToList();
            }else {
                containerEmptyInfo.setVisibility(View.VISIBLE);
                txtEmptyInfo.setText(R.string.labelEmptyAssisgnmentOnProgress);
            }
        }
    }

    @Override
    public void storeDataToList() {
        for (int i = 0; i < assignmentDataList.size(); i++) {
            adapter.getData().add(assignmentDataList.get(i));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setupList() {
        adapter = new AssignmentListAdapter();
        linearLayoutManager = new LinearLayoutManager(getActivity());

        listTaskOnProgress.setAdapter(adapter);
        listTaskOnProgress.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Assignment assignment = adapter.getData().get(i);

                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.SERIALIZABLE_NAME, assignment);

                IntentUtils.intentTo(getActivity(), AssignmentDetailActivity.class, false, bundle);
            }
        });

        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
