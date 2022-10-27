package com.radya.sfa.view.invoice.payment;


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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.util.StringUtils;
import com.radya.sfa.view.ListView;
import com.radya.sfa.view.invoice.InvoiceActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class InvoicePaymentFragment extends Fragment implements ListView {

    Unbinder unbinder;
    @BindView(R.id.txtNotaPembayaran)
    TextView txtNotaPembayaran;
    @BindView(R.id.txtNotaKredit)
    TextView txtNotaKredit;
    @BindView(R.id.txtTotalTertagih)
    TextView txtTotalTertagih;
    @BindView(R.id.icAddNew)
    ImageView icAddNew;
    @BindView(R.id.btnAddPayment)
    RelativeLayout btnAddPayment;
    @BindView(R.id.listPayment)
    RecyclerView listPayment;
    @BindView(R.id.txtSisa)
    TextView txtSisa;

    private InvoicePaymentAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private InvoicePayment paymentSelected;
    private long paymentInputed;
    private long totalTertagih;

    public InvoicePaymentFragment() {
        // Requires empty public constructor
    }

    public static InvoicePaymentFragment newInstance() {
        return new InvoicePaymentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.invoice_payment_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        setupList();
        return view;
    }

    public void addPayment(InvoicePayment invoicePayment){
        adapter.getData().add(invoicePayment);
        adapter.notifyDataSetChanged();
    }

    private void initView() {

        txtTotalTertagih.setText("-");
        txtNotaPembayaran.setText("-");
        txtNotaKredit.setText("-");
        txtSisa.setText("-");

        long notaPembayaran = ((InvoiceActivity)getActivity()).getNotaPembayaran();
        long notaKredit = ((InvoiceActivity)getActivity()).getNotaKredit();

        txtNotaPembayaran.setText(StringUtils.getRpCurency(notaPembayaran));
        txtNotaKredit.setText(StringUtils.getRpCurency(notaKredit));

        totalTertagih = notaPembayaran + notaKredit;
        txtTotalTertagih.setText(StringUtils.getRpCurency(totalTertagih));

        ((InvoiceActivity)getActivity()).setSisa(totalTertagih);

    }

    public void setSisa(long sisa){
        txtSisa.setText(StringUtils.getRpCurency(sisa));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnAddPayment)
    public void onViewClicked() {
        ((InvoiceActivity)getActivity()).showInputAmountDialog();
    }

    @Override
    public void setupList() {

        adapter = new InvoicePaymentAdapter();
        linearLayoutManager = new LinearLayoutManager(getActivity());

        listPayment.setLayoutManager(linearLayoutManager);
        listPayment.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                paymentSelected = adapter.getData().get(i);

                Bundle bundle = new Bundle();
                bundle.putInt(Constant.Bundle.POSITION, i);
                bundle.putSerializable(Constant.SERIALIZABLE_NAME, paymentSelected);

                InvoicePaymentActionDialog paymentActionDialog = new InvoicePaymentActionDialog();

                paymentActionDialog.setCancelable(false);
                paymentActionDialog.setArguments(bundle);
                paymentActionDialog.show(getFragmentManager(), "");

            }
        });

        loadData();
    }

    public void removePayment(int position){
        adapter.getData().remove(position);
        adapter.notifyDataSetChanged();

        updateSisa();
    }

    public void updatePaymentSelected(InvoicePayment invoicePayment){
        paymentSelected.setAmount(invoicePayment.getAmount());
        paymentSelected.setGiro_due_date(invoicePayment.getGiro_due_date());
        adapter.notifyDataSetChanged();

        updateSisa();

    }

    private void updateSisa(){
        paymentInputed = 0;

        for (int i = 0; i < adapter.getData().size(); i++) {
            paymentInputed += adapter.getData().get(i).getAmount();
        }

        ((InvoiceActivity) getActivity()).setPaymentInputed(paymentInputed);
        ((InvoiceActivity) getActivity()).updateSisa(paymentInputed);
    }

    public long getPaymentAmount(){
        long paymentAmount = 0;

        for (int i = 0; i < adapter.getData().size(); i++) {
            paymentAmount += adapter.getData().get(i).getAmount();
        }

        return paymentAmount;
    }

    public List<InvoicePayment> getGiroPayment(){

        List<InvoicePayment> giroPayment = new ArrayList<>();

        for (int i = 0; i < adapter.getData().size(); i++) {
            if (adapter.getData().get(i).getPayment_type().equals(Constant.Data.PaymentMethod.GIRO)){
                giroPayment.add(adapter.getData().get(i));
            }
        }

        return giroPayment;
    }

    @Override
    public void loadData() {
        storeDataToList();
    }

    @Override
    public void storeDataToList() {

    }
}
