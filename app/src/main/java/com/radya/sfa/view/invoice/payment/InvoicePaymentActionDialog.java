package com.radya.sfa.view.invoice.payment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.util.EditTextUtils;
import com.radya.sfa.util.StringUtils;
import com.radya.sfa.view.invoice.InvoiceActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-03-12.
 */

public class InvoicePaymentActionDialog extends DialogFragment {

    Unbinder unbinder;

    private InvoicePayment invoicePayment;
    private Bundle bundle;
    private int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.invoice_payment_action_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        initData();

        return view;

    }

    private void initData() {
        bundle = getArguments();
        if (bundle != null){
            position = bundle.getInt(Constant.Bundle.POSITION);
            invoicePayment = (InvoicePayment) bundle.getSerializable(Constant.SERIALIZABLE_NAME);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnClose, R.id.txtEdit, R.id.txtDelete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                break;
            case R.id.txtEdit:
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.SERIALIZABLE_NAME, invoicePayment);

                InvoicePaymentEditDialog invoicePaymentEditDialog = new InvoicePaymentEditDialog();

                invoicePaymentEditDialog.setCancelable(false);
                invoicePaymentEditDialog.setArguments(bundle);
                invoicePaymentEditDialog.show(getFragmentManager(), "");
                break;
            case R.id.txtDelete:
                if (invoicePayment.getPayment_type().equals(Constant.Data.PaymentMethod.CASH)){
                    ((InvoiceActivity) getActivity()).setCashInputed(false);
                }else if (invoicePayment.getPayment_type().equals(Constant.Data.PaymentMethod.TRANSFER)){
                    ((InvoiceActivity) getActivity()).setTransferInputed(false);
                }

                ((InvoiceActivity) getActivity()).removePayment(position);
                break;
        }

        getDialog().dismiss();
    }
}
