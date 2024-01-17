package com.radya.sfa.view.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.radya.sfa.Constant;
import com.radya.sfa.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class CustomerFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.txtInfo)
    TextView txtInfo;

    public CustomerFragment() {
        // Requires empty public constructor
    }

    public static CustomerFragment newInstance() {
        return new CustomerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.null_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        txtInfo.setText(Constant.Menu.CONTACT);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
