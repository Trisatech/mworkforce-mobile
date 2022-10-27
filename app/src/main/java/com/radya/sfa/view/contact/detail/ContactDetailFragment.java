package com.radya.sfa.view.contact.detail;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.databinding.ContactDetailFragmentBinding;
import com.radya.sfa.view.contact.Contact;
import com.radya.sfa.view.contact.list.ContactList;
import com.radya.sfa.view.contact.list.ContactListDataBinding;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class ContactDetailFragment extends Fragment {

    Unbinder unbinder;

    private ContactDetailFragmentBinding fragmentBinding;
    private ContactListDataBinding dataBinding;

    private Bundle bundle;
    private Contact data;

    public ContactDetailFragment() {
        // Requires empty public constructor
    }

    public static ContactDetailFragment newInstance() {
        return new ContactDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.contact_detail_fragment, container, false);
        unbinder = ButterKnife.bind(this, fragmentBinding.getRoot());

        bundle = getArguments();
        if (bundle != null){
            data = (Contact) bundle.getSerializable(Constant.SERIALIZABLE_NAME);
            dataBinding = new ContactListDataBinding(data);
            fragmentBinding.setContactDetail(dataBinding);
        }

        return fragmentBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
