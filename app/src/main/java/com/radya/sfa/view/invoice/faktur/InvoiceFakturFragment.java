package com.radya.sfa.view.invoice.faktur;


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

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.view.ListView;
import com.radya.sfa.view.assignment.Assignment;
import com.radya.sfa.view.assignment.detail.AssignmentDetail;
import com.radya.sfa.view.invoice.Invoice;
import com.radya.sfa.view.invoice.InvoiceActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class InvoiceFakturFragment extends Fragment implements ListView {

    Unbinder unbinder;
    @BindView(R.id.listInvoiceType)
    RecyclerView listInvoiceType;

    private Assignment assignmentDetail;
    private InvoiceFakturAdapter adapter;

    public InvoiceFakturFragment() {
        // Requires empty public constructor
    }

    public static InvoiceFakturFragment newInstance() {
        return new InvoiceFakturFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invoice_faktur_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if (bundle != null){
            assignmentDetail = (Assignment) bundle.getSerializable(Constant.SERIALIZABLE_NAME);
            setupList();
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setupList() {
        adapter = new InvoiceFakturAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        listInvoiceType.setAdapter(adapter);
        listInvoiceType.setLayoutManager(linearLayoutManager);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Invoice invoice = adapter.getData().get(i);
                long notaPembayaran = ((InvoiceActivity)getActivity()).getNotaPembayaran();
                long notaKredit = ((InvoiceActivity)getActivity()).getNotaKredit();

                if (!invoice.isSelected()){
                    adapter.getData().get(i).setSelected(true);
                    if (invoice.getType() == Constant.Data.InvoiceType.INVOICE){
                        ((InvoiceActivity)getActivity()).setNotaPembayaran(notaPembayaran + invoice.getAmount());
                    }else {
                        ((InvoiceActivity)getActivity()).setNotaKredit(notaKredit + invoice.getAmount());
                    }
                }else {
                    adapter.getData().get(i).setSelected(false);
                    if (invoice.getType() == Constant.Data.InvoiceType.INVOICE){
                        ((InvoiceActivity)getActivity()).setNotaPembayaran(notaPembayaran - invoice.getAmount());
                    }else {
                        ((InvoiceActivity)getActivity()).setNotaKredit(notaKredit - invoice.getAmount());
                    }
                }

                adapter.notifyDataSetChanged();
            }
        });

        loadData();
    }

    @Override
    public void loadData() {
        if (assignmentDetail != null){
            if (assignmentDetail.getInvoices().size() != 0){
                storeDataToList();
            }
        }
    }

    @Override
    public void storeDataToList() {
        List<Invoice> invoices;
        invoices = assignmentDetail.getInvoices();

        for (int i = 0; i < invoices.size(); i++) {
            adapter.getData().add(invoices.get(i));
            adapter.notifyItemInserted(adapter.getData().size() - 1);
        }
        adapter.notifyDataSetChanged();

    }
}
