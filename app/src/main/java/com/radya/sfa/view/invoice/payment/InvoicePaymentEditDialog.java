package com.radya.sfa.view.invoice.payment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.EditTextUtils;
import com.radya.sfa.util.StringUtils;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.invoice.InvoiceActivity;
import com.radya.sfa.view.invoice.InvoiceCash;
import com.radya.sfa.view.invoice.InvoiceTransfer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by aderifaldi on 2018-03-12.
 */

public class InvoicePaymentEditDialog extends DialogFragment {

    Unbinder unbinder;
    @BindView(R.id.inputType)
    TextView txtInputType;
    @BindView(R.id.edtInputPayment)
    EditText edtInputPayment;
    @BindView(R.id.edtInputNoGiro)
    EditText edtInputNoGiro;
    @BindView(R.id.imgGiro)
    ImageView imgGiro;
    @BindView(R.id.inputPhotoGiro)
    RelativeLayout inputPhotoGiro;
    @BindView(R.id.containerEditGiro)
    LinearLayout containerEditGiro;
    @BindView(R.id.edtInputDueDateGiro)
    TextView edtInputDueDateGiro;
    @BindView(R.id.edtInputDueDateGiroInvisible)
    TextView edtInputDueDateGiroInvisible;
    @BindView(R.id.btnUpdate)
    RelativeLayout btnUpdate;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private InvoicePayment invoicePayment;
    private Bundle bundle;
    private boolean isEditGiro;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        View view = inflater.inflate(R.layout.invoice_payment_edit_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        initData();

        return view;

    }

    private void initData() {
        bundle = getArguments();
        if (bundle != null) {
            invoicePayment = (InvoicePayment) bundle.getSerializable(Constant.SERIALIZABLE_NAME);

            txtInputType.setText(invoicePayment.getPayment_type());

            String amount = StringUtils.formatMoney("", invoicePayment.getAmount(), '.', "");
            edtInputPayment.setText(amount);

            if (invoicePayment.getPayment_type().equals(Constant.Data.PaymentMethod.GIRO)) {
                isEditGiro = true;
                containerEditGiro.setVisibility(View.VISIBLE);

                edtInputNoGiro.setText(invoicePayment.getNo_giro());

                edtInputDueDateGiroInvisible.setText(invoicePayment.getGiro_due_date());
                edtInputDueDateGiro.setText(DateUtils.convertStringDate(invoicePayment.getGiro_due_date(), Constant.DateFormat.SHORT));

                String filePath = invoicePayment.getGiro_photo().getPath();
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);

                imgGiro.setImageBitmap(bitmap);
            } else {
                isEditGiro = false;
                containerEditGiro.setVisibility(View.GONE);
            }

            EditTextUtils.setCurrency(edtInputPayment);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnClose, R.id.btnUpdate, R.id.imgGiro, R.id.edtInputDueDateGiro})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnClose:
                getDialog().dismiss();
                break;
            case R.id.edtInputDueDateGiro:
                DateUtils.showDatePicker(getActivity(), edtInputDueDateGiroInvisible, edtInputDueDateGiro, Constant.DateFormat.SERVER);
                break;
            case R.id.btnUpdate:

                String amount = edtInputPayment.getText().toString();

                if (TextUtils.isEmpty(amount) || amount.equals("0")) {
                    ToastUtils.showToast("Input nilai " + invoicePayment.getPayment_type() + " tidak boleh kosong");
                } else {
                    long newAmount = Long.parseLong(amount.replace(".", ""));

                    if (invoicePayment.getPayment_type().equals(Constant.Data.PaymentMethod.CASH)) {
                        InvoicePayment payment = new InvoicePayment(invoicePayment.getPayment_type(),
                                newAmount, "", null, "");

                        ((InvoiceActivity) getActivity()).updateInvoicePayment(payment);

                        InvoiceCash invoiceCash = new InvoiceCash(newAmount);
                        ((InvoiceActivity) getActivity()).setInvoiceCash(invoiceCash);
                    } else if (invoicePayment.getPayment_type().equals(Constant.Data.PaymentMethod.TRANSFER)) {
                        InvoicePayment payment = new InvoicePayment(invoicePayment.getPayment_type(),
                                newAmount, "", null, "");

                        ((InvoiceActivity) getActivity()).updateInvoicePayment(payment);

                        String bankName = ((InvoiceActivity) getActivity()).getBankNameSelected();
                        String bankCode = ((InvoiceActivity) getActivity()).getBankCodeSelected();

                        InvoiceTransfer invoiceTransfer = new InvoiceTransfer(bankName, bankCode, newAmount);
                        ((InvoiceActivity) getActivity()).setInvoiceTransfer(invoiceTransfer);
                    } else if (invoicePayment.getPayment_type().equals(Constant.Data.PaymentMethod.GIRO)) {

                        String giroNumber = edtInputNoGiro.getText().toString();
                        String giroDueDate = edtInputDueDateGiroInvisible.getText().toString();

                        if (TextUtils.isEmpty(giroNumber)){
                            edtInputNoGiro.setError(getString(R.string.alertMssageEmptyNoGiro));
                        }else {
                            InvoicePayment payment = new InvoicePayment(invoicePayment.getPayment_type(),
                                    newAmount, giroNumber, invoicePayment.getGiro_photo(), giroDueDate);

                            ((InvoiceActivity) getActivity()).updateInvoicePayment(payment);

                        }

                    }

                    getDialog().dismiss();
                }

                break;

            case R.id.imgGiro:
                break;
        }
    }
}
