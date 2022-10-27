package com.radya.sfa.view.invoice.payment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.EditTextUtils;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.invoice.InvoiceActivity;
import com.radya.sfa.view.invoice.InvoiceCash;
import com.radya.sfa.view.invoice.InvoiceGiro;
import com.radya.sfa.view.invoice.InvoiceTransfer;
import com.radya.sfa.view.invoice.master.InvoiceBank;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-03-12.
 */

public class InvoicePaymentInputDialog extends DialogFragment {

    Unbinder unbinder;
    @BindView(R.id.btnClose)
    ImageView btnClose;
    @BindView(R.id.txtPaymentMethod)
    TextView txtPaymentMethod;
    @BindView(R.id.edtNoGiro)
    EditText edtNoGiro;
    @BindView(R.id.inputNoGiro)
    RelativeLayout inputNoGiro;
    @BindView(R.id.edtInputAmount)
    EditText edtInputAmount;
    @BindView(R.id.inputAmount)
    RelativeLayout inputAmount;
    @BindView(R.id.imgGiro)
    ImageView imgGiro;
    @BindView(R.id.inputPhotoGiro)
    RelativeLayout inputPhotoGiro;
    @BindView(R.id.btnAdd)
    RelativeLayout btnAdd;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.containerInput)
    LinearLayout containerInput;
    @BindView(R.id.txtBank)
    TextView txtBank;
    @BindView(R.id.containerBank)
    RelativeLayout containerBank;
    @BindView(R.id.addPhotoStore)
    ImageView addPhotoGiro;
    @BindView(R.id.txtGiroDueDate)
    TextView txtGiroDueDate;
    @BindView(R.id.inputGiroDueDate)
    RelativeLayout inputGiroDueDate;
    @BindView(R.id.txtGiroDueDateInvisible)
    TextView txtGiroDueDateInvisible;

    private String emptyMessage;
    private String paymentMethod;

    private InvoiceBank bank;
    private File photoGiro;

    private boolean isPaymentMethodSelected;
    private String giroDueDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.invoice_payment_input_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();

        return view;

    }

    public void setGiroDueDate(String giroDueDate) {
        this.giroDueDate = giroDueDate;
    }

    public File getPhotoGiro() {
        return photoGiro;
    }

    public void setPhotoGiro(File photoGiro) {
        this.photoGiro = photoGiro;
    }

    private void initView() {
        containerInput.setVisibility(View.GONE);
        containerBank.setVisibility(View.GONE);

        EditTextUtils.setCurrency(edtInputAmount);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnClose)
    public void onViewClicked() {
        getDialog().dismiss();
    }

    public void setPaymentMethod(String paymentMethod) {
        isPaymentMethodSelected = true;
        this.paymentMethod = paymentMethod;

        txtPaymentMethod.setText(paymentMethod);
        containerInput.setVisibility(View.VISIBLE);

        if (paymentMethod.equals(Constant.Data.PaymentMethod.CASH)) {
            emptyMessage = getString(R.string.errorMessageEmptyCash);
            edtInputAmount.setHint(R.string.hintCash);

            inputAmount.setVisibility(View.VISIBLE);
            containerBank.setVisibility(View.GONE);
            inputNoGiro.setVisibility(View.GONE);
            inputPhotoGiro.setVisibility(View.GONE);
            inputGiroDueDate.setVisibility(View.GONE);
        } else if (paymentMethod.equals(Constant.Data.PaymentMethod.TRANSFER)) {
            emptyMessage = getString(R.string.errorMessageEmptyTransfer);
            edtInputAmount.setHint(R.string.hintInputTransfer);

            containerBank.setVisibility(View.VISIBLE);
            inputAmount.setVisibility(View.VISIBLE);
            inputNoGiro.setVisibility(View.GONE);
            inputPhotoGiro.setVisibility(View.GONE);
            inputGiroDueDate.setVisibility(View.GONE);
        } else {
            emptyMessage = getString(R.string.emptyMessageEmptyGiro);
            edtInputAmount.setHint(R.string.hintGiro);
            edtNoGiro.setHint(R.string.hintNoGiro);

            containerBank.setVisibility(View.GONE);
            inputAmount.setVisibility(View.VISIBLE);
            inputNoGiro.setVisibility(View.VISIBLE);
            inputPhotoGiro.setVisibility(View.VISIBLE);
            inputGiroDueDate.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.btnClose, R.id.containerPaymentMethod, R.id.btnAdd,
            R.id.containerBank, R.id.addPhotoStore, R.id.imgGiro, R.id.txtGiroDueDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                getDialog().dismiss();
                break;
            case R.id.containerPaymentMethod:
                ((InvoiceActivity) getActivity()).showPaymentMethodDilaog();
                break;
            case R.id.containerBank:
                ((InvoiceActivity) getActivity()).showBankDilaog();
                break;
            case R.id.addPhotoStore:
                ((InvoiceActivity) getActivity()).launchCamera();
                break;
            case R.id.imgGiro:
                ((InvoiceActivity) getActivity()).launchCamera();
                break;
            case R.id.txtGiroDueDate:
                DateUtils.showDatePicker(getActivity(), txtGiroDueDateInvisible, txtGiroDueDate, Constant.DateFormat.SERVER);
                break;
            case R.id.btnAdd:

                if (isPaymentMethodSelected) {
                    String amount = edtInputAmount.getText().toString();
                    long amountLong = 0;
                    if (!TextUtils.isEmpty(amount)) {
                        amountLong = Long.parseLong(amount.replace(".", ""));
                    }

                    if (paymentMethod.equals(Constant.Data.PaymentMethod.CASH)) {

                        if (TextUtils.isEmpty(amount)) {
                            edtInputAmount.setError(emptyMessage);
                        } else {
                            InvoicePayment invoicePayment = new InvoicePayment(paymentMethod,
                                    amountLong, "", null, "");

                            InvoiceCash invoiceCash = new InvoiceCash(amountLong);
                            ((InvoiceActivity) getActivity()).setInvoiceCash(invoiceCash);

                            ((InvoiceActivity) getActivity()).addPayment(invoicePayment);
                            getDialog().dismiss();
                        }

                    } else if (paymentMethod.equals(Constant.Data.PaymentMethod.TRANSFER)) {

                        if (bank == null) {
                            ToastUtils.showToast(getString(R.string.alertMessageEmptyBank));
                        } else if (TextUtils.isEmpty(amount)) {
                            edtInputAmount.setError(emptyMessage);
                        } else {
                            InvoicePayment invoicePayment = new InvoicePayment(paymentMethod,
                                    amountLong, "", null, "");

                            InvoiceTransfer invoiceTransfer = new InvoiceTransfer(bank.getBank_name(), bank.getBank_code(), amountLong);

                            ((InvoiceActivity) getActivity()).setBank(bank.getBank_name(), bank.getBank_code());
                            ((InvoiceActivity) getActivity()).setInvoiceTransfer(invoiceTransfer);

                            ((InvoiceActivity) getActivity()).addPayment(invoicePayment);
                            getDialog().dismiss();
                        }

                    } else {
                        String noGiro = edtNoGiro.getText().toString();
                        String giroDueDate = txtGiroDueDateInvisible.getText().toString();
                        String giroDueDateShow = txtGiroDueDate.getText().toString();

                        if (giroDueDateShow.equals(getString(R.string.hintGiroDueDate))) {
                            ToastUtils.showToast(getString(R.string.alertMessageGiroDueDateEmpty));
                        } else if (TextUtils.isEmpty(noGiro)) {
                            edtNoGiro.setError(getString(R.string.alertMssageEmptyNoGiro));
                        } else if (TextUtils.isEmpty(amount)) {
                            edtInputAmount.setError(emptyMessage);
                        } else if (getPhotoGiro() == null) {
                            ToastUtils.showToast(getString(R.string.alertMessageEmptyGiroPhoto));
                        } else {

                            InvoicePayment invoicePayment = new InvoicePayment(paymentMethod,
                                    amountLong, noGiro, getPhotoGiro(), giroDueDate);

                            InvoiceGiro invoiceGiro = new InvoiceGiro(noGiro, amountLong, getPhotoGiro());
                            ((InvoiceActivity) getActivity()).setInvoiceGiro(invoiceGiro);

                            ((InvoiceActivity) getActivity()).addPayment(invoicePayment);
                            getDialog().dismiss();
                        }

                    }
                }

                break;
        }
    }

    public void setBankAccount(InvoiceBank bank) {
        this.bank = bank;
        txtBank.setText(bank.getBank_name());
    }

    public void setImageCaptured(File file, Bitmap rotatedBitmap) {

        addPhotoGiro.setVisibility(View.GONE);

        setPhotoGiro(file);
        imgGiro.setImageBitmap(rotatedBitmap);

    }
}
