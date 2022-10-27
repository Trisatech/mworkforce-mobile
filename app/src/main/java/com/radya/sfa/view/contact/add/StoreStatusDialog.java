package com.radya.sfa.view.contact.add;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.radya.sfa.R;
import com.radya.sfa.view.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-03-12.
 */

public class StoreStatusDialog extends DialogFragment implements ListView{

    Unbinder unbinder;
    @BindView(R.id.btnClose)
    ImageView btnClose;
    @BindView(R.id.listPaymentMethod)
    RecyclerView listPaymentMethod;

    private LinearLayoutManager linearLayoutManager;
    private StoreStatusAdapter adapter;

    private List<StoreStatus> storeStatuses;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.invoice_payment_method_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();

        return view;

    }

    private void initView() {
        setupList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                getDialog().dismiss();
                break;
        }
    }

    @Override
    public void setupList() {

        adapter = new StoreStatusAdapter();
        linearLayoutManager = new LinearLayoutManager(getActivity());

        listPaymentMethod.setAdapter(adapter);
        listPaymentMethod.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StoreStatus item = adapter.getData().get(i);

                ((ContactAddActivity) getActivity()).setStatus(item.getStatus_name());

                getDialog().dismiss();
            }
        });

        loadData();
    }

    @Override
    public void loadData() {
        storeStatuses = new ArrayList<>();
        storeStatuses.add(new StoreStatus("", "Sewa"));
        storeStatuses.add(new StoreStatus("", "Milik sendiri"));

        storeDataToList();
    }

    @Override
    public void storeDataToList() {

        for (int i = 0; i < storeStatuses.size(); i++) {
            adapter.getData().add(storeStatuses.get(i));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }
        adapter.notifyDataSetChanged();

    }
}
