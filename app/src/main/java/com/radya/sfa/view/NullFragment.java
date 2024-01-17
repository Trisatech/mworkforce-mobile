package com.radya.sfa.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.radya.sfa.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class NullFragment extends Fragment {

    Unbinder unbinder;

    public NullFragment() {
        // Requires empty public constructor
    }

    public static NullFragment newInstance() {
        return new NullFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.null_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
