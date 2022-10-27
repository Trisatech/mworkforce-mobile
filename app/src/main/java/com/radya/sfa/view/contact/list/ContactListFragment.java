package com.radya.sfa.view.contact.list;


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
import android.widget.ProgressBar;

import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.helper.EndlessRecyclerViewScrollListener;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.view.ListView;
import com.radya.sfa.view.contact.Contact;
import com.radya.sfa.view.contact.ContactViewModel;
import com.radya.sfa.view.contact.detail.ContactDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class ContactListFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.contactList)
    RecyclerView contactList;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private ContactViewModel viewModel;

    private ContactListAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private List<Contact> contactLists;

    private EndlessRecyclerViewScrollListener scrollListener;
    private int offset = 0;

    public ContactListFragment() {
        // Requires empty public constructor
    }

    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViewModel();
        setupList();

        return view;
    }

    private void initScrollListener() {
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int offset, int totalItemsCount, RecyclerView view) {
                loadData(Constant.Api.LIMIT, offset);
            }
        };

        contactList.addOnScrollListener(scrollListener);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void setupList() {
        adapter = new ContactListAdapter();
        linearLayoutManager = new LinearLayoutManager(getActivity());

        contactList.setAdapter(adapter);
        contactList.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact contact = adapter.getData().get(i);

                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.SERIALIZABLE_NAME, contact);

                IntentUtils.intentTo(getActivity(), ContactDetailActivity.class, false, bundle);

            }
        });

        loadData(Constant.Api.LIMIT, offset);

        initScrollListener();
    }


    public void loadData(int limit, int offset) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Constant.Api.Params.KEYWORD, "");

        viewModel.getContactList(NetworkUtils.getConnectionManager(), jsonObject, limit, offset);
        viewModel.getContactListResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                ContactList list = (ContactList) apiResponse.getData();
                if (list != null) {

                    if (list.getData().size() != 0) {
                        contactLists = new ArrayList<>();
                        for (int i = 0; i < list.getData().size(); i++) {
                            contactLists.add(list.getData().get(i));
                        }

                        storeDataToList();
                    }
                }
            }
        });
    }


    public void storeDataToList() {
        for (int i = 0; i < contactLists.size(); i++) {
            adapter.getData().add(contactLists.get(i));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }
        adapter.notifyDataSetChanged();
    }
}
