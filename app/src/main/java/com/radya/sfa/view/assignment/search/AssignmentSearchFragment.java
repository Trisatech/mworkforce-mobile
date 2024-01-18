package com.radya.sfa.view.assignment.search;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.view.ListView;
import com.radya.sfa.view.assignment.Assignment;
import com.radya.sfa.view.assignment.detail.AssignmentDetail;
import com.radya.sfa.view.assignment.detail.AssignmentDetailActivity;
import com.radya.sfa.view.assignment.list.AssignmentList;
import com.radya.sfa.view.assignment.list.AssignmentListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class AssignmentSearchFragment extends Fragment implements ListView {

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
    private AssignmentList assignmentList;

    private Bundle bundle;

    public AssignmentSearchFragment() {
        // Requires empty public constructor
    }

    public static AssignmentSearchFragment newInstance() {
        return new AssignmentSearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.assignment_list_content_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();

        bundle = getArguments();
        if (bundle != null){
            assignmentList = (AssignmentList) bundle.getSerializable(Constant.SERIALIZABLE_NAME);
            if (assignmentList != null){
                setupList();
            }
        }

        return view;
    }

    private void initView() {
        progressLoading.setVisibility(View.GONE);
    }

    @Override
    public void loadData() {
        containerEmptyInfo.setVisibility(View.GONE);

        if (assignmentList.getData().size() != 0) {
            assignmentDataList = new ArrayList<>();
            for (int i = 0; i < assignmentList.getData().size(); i++) {
                assignmentDataList.add(assignmentList.getData().get(i));
            }

            storeDataToList();
        } else {
            containerEmptyInfo.setVisibility(View.VISIBLE);
            txtEmptyInfo.setText(R.string.labelEmptyAssisgnmentSearch);
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
