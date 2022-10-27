package com.radya.sfa.view.invoice;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.radya.sfa.BuildConfig;
import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.helper.image.LoadImage;
import com.radya.sfa.util.ActivityUtils;
import com.radya.sfa.util.AppUtils;
import com.radya.sfa.util.ImageUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.view.BaseActivity;
import com.radya.sfa.view.assignment.Assignment;
import com.radya.sfa.view.assignment.detail.AssignmentDetail;
import com.radya.sfa.view.invoice.faktur.InvoiceFakturFragment;
import com.radya.sfa.view.invoice.master.InvoiceBank;
import com.radya.sfa.view.invoice.master.InvoiceBankDialog;
import com.radya.sfa.view.invoice.master.InvoicePaymentMethodDialog;
import com.radya.sfa.view.invoice.payment.InvoicePayment;
import com.radya.sfa.view.invoice.payment.InvoicePaymentFragment;
import com.radya.sfa.view.invoice.payment.InvoicePaymentInputDialog;
import com.radya.sfa.view.invoice.verification.InvoiceVerificationFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

public class InvoiceActivity extends BaseActivity {

    private static final String FILE_EXTENTION = ".jpg";
    private static final String FILE_NAME = "assignment_photo";

    private static final int RESULT_LOAD_IMAGE_FROM_CAMERA = 100;
    private static final int RESULT_LOAD_IMAGE_FROM_GALLERY = 101;


    @BindView(R.id.icNavLeft)
    ImageView icNavLeft;
    @BindView(R.id.navLeft)
    RelativeLayout navLeft;
    @BindView(R.id.icNavRight)
    ImageView icNavRight;
    @BindView(R.id.navRight)
    RelativeLayout navRight;
    @BindView(R.id.appBarTitle)
    TextView appBarTitle;
    @BindView(R.id.indicatorStep1)
    RelativeLayout indicatorStep1;
    @BindView(R.id.indicatorStep2)
    RelativeLayout indicatorStep2;
    @BindView(R.id.indicatorStep3)
    RelativeLayout indicatorStep3;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.btnPrev)
    RelativeLayout btnPrev;
    @BindView(R.id.txtBtnStart)
    TextView txtBtnStart;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoadingOnButton;
    @BindView(R.id.btnNext)
    RelativeLayout btnNext;
    @BindView(R.id.progressLoading2)
    ProgressBar progressLoading;
    @BindView(R.id.containerButton)
    FrameLayout containerButton;
    @BindView(R.id.containerIndicator)
    RelativeLayout containerIndicator;
    @BindView(R.id.btnPrevText)
    TextView btnPrevText;

    private int activeStep;
    private long notaPembayaran, notaKredit, sisa;

    private Assignment assignmentDetail;
    private Bundle bundle;

    private InvoicePaymentInputDialog invoicePaymentInputDialog;
    private InvoicePaymentMethodDialog invoicePaymentMethodDialog;
    private InvoicePaymentFragment invoicePaymentFragment;
    private InvoiceVerificationFragment verificationFragment;

    private InvoiceCash invoiceCash;
    private InvoiceTransfer invoiceTransfer;
    private InvoiceGiro invoiceGiro;

    private File invoiceGiroPhoto = null;
    private File fileTemp = null;

    private String OTPCode;

    private boolean cashInputed;
    private boolean transferInputed;
    private int giroInputed;

    private String bankNameSelected, bankCodeSelected;
    private long paymentInputed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_activity);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    public List<InvoicePayment> getGiroPayment() {
        return invoicePaymentFragment.getGiroPayment();
    }

    public long getPaymentAmount() {
        return invoicePaymentFragment.getPaymentAmount();
    }

    public long getPaymentInputed() {
        return paymentInputed;
    }

    public void setPaymentInputed(long paymentInputed) {
        this.paymentInputed = paymentInputed;
    }

    public void setBank(String bankName, String bankCode) {
        bankNameSelected = bankName;
        bankCodeSelected = bankCode;
    }

    public String getBankNameSelected() {
        return bankNameSelected;
    }

    public String getBankCodeSelected() {
        return bankCodeSelected;
    }

    public void updateInvoicePayment(InvoicePayment payment) {
        invoicePaymentFragment.updatePaymentSelected(payment);
    }

    public void removePayment(int position) {
        invoicePaymentFragment.removePayment(position);
    }

    public boolean isCashInputed() {
        return cashInputed;
    }

    public boolean isTransferInputed() {
        return transferInputed;
    }

    public void setCashInputed(boolean cashInputed) {
        this.cashInputed = cashInputed;
    }

    public void setTransferInputed(boolean transferInputed) {
        this.transferInputed = transferInputed;
    }

    public int getGiroInputed() {
        return giroInputed;
    }

    private void initData() {
        InvoiceCash invoiceCash = new InvoiceCash(0);
        InvoiceTransfer invoiceTransfer = new InvoiceTransfer("", "", 0);
        InvoiceGiro invoiceGiro = new InvoiceGiro("", 0, null);

        setInvoiceCash(invoiceCash);
        setInvoiceTransfer(invoiceTransfer);
        setInvoiceGiro(invoiceGiro);
    }


    public void hideIndicator() {
        containerIndicator.setVisibility(View.INVISIBLE);
    }

    public InvoiceViewModel obtainViewModel() {
        return ViewModelProviders.of(this).get(InvoiceViewModel.class);
    }

    public long getNotaPembayaran() {
        return notaPembayaran;
    }

    public void setNotaPembayaran(long notaPembayaran) {
        this.notaPembayaran = notaPembayaran;
    }

    public long getNotaKredit() {
        return notaKredit;
    }

    public void setNotaKredit(long notaKredit) {
        this.notaKredit = notaKredit;
    }

    public long getSisa() {
        return sisa;
    }

    public void setSisa(long sisa) {
        this.sisa = sisa;
        invoicePaymentFragment.setSisa(sisa);
    }

    public void updateSisa(long payment) {
        long totalTertagih = notaPembayaran + notaKredit;
        setSisa(Math.abs(totalTertagih) - payment);
    }

    public Assignment getAssignmentDetail() {
        return assignmentDetail;
    }

    private void initView() {

        Bundle data = getIntent().getExtras();

        if (data != null) {
            setTitle(getString(R.string.labelTitleInvoiceChooseFaktur));
            setActiveStep(1);

            assignmentDetail = (Assignment) data.getSerializable(Constant.SERIALIZABLE_NAME);

            bundle = new Bundle();
            bundle.putSerializable(Constant.SERIALIZABLE_NAME, assignmentDetail);

            Fragment fragment = InvoiceFakturFragment.newInstance();
            fragment.setArguments(bundle);

            ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.contentFrame, false);
        }
    }

    public void showLoading() {
        progressLoading.setVisibility(View.VISIBLE);
    }

    public void dismissLoading() {
        progressLoading.setVisibility(View.GONE);
    }

    public void setTitle(String title) {
        appBarTitle.setText(title);
    }

    public int getActiveStep() {
        return activeStep;
    }

    public void setActiveStep(int activeStep) {
        this.activeStep = activeStep;
    }

    @OnClick({R.id.navLeft, R.id.btnPrev, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.navLeft:
                onBackPressed();
                IntentUtils.backAnimation(this);
                break;
            case R.id.btnPrev:
                if (getActiveStep() == 1) {
                    onBackPressed();
                } else if (getActiveStep() == 2) {
                    setTitle(getString(R.string.labelTitleInvoiceChooseFaktur));
                    setActiveStep(1);

                    indicatorStep2.setBackgroundResource(R.drawable.bg_bullet_grey);
                    line1.setBackgroundResource(R.color.colorNull);

                    Fragment fragment = InvoiceFakturFragment.newInstance();
                    fragment.setArguments(bundle);

                    ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.contentFrame, false);
                }
                break;
            case R.id.btnNext:
                if (getActiveStep() == 1) {
                    setActiveStep(2);
                    setTitle(getString(R.string.labelTitleInvoicePayment));

                    indicatorStep2.setBackgroundResource(R.drawable.bg_bullet_green);
                    line1.setBackgroundResource(R.color.colorGreen);

                    invoicePaymentFragment = InvoicePaymentFragment.newInstance();

                    ActivityUtils.replaceFragment(getSupportFragmentManager(), invoicePaymentFragment, R.id.contentFrame, false);
                } else if (getActiveStep() == 2) {
                    containerButton.setVisibility(View.INVISIBLE);

                    setActiveStep(3);
                    setTitle(getString(R.string.labelInvoiceTitleVerification));

                    indicatorStep3.setBackgroundResource(R.drawable.bg_bullet_green);
                    line2.setBackgroundResource(R.color.colorGreen);

                    ActivityUtils.replaceFragment(getSupportFragmentManager(), InvoiceVerificationFragment.newInstance(), R.id.contentFrame, false);

                }
                break;
        }
    }

    public void showInputAmountDialog() {
        invoicePaymentInputDialog = new InvoicePaymentInputDialog();
        invoicePaymentInputDialog.show(getSupportFragmentManager(), "");
    }

    public void setPaymentMethod(String paymentMethod) {
        invoicePaymentInputDialog.setPaymentMethod(paymentMethod);
    }

    public void showPaymentMethodDilaog() {
        invoicePaymentMethodDialog = new InvoicePaymentMethodDialog();
        invoicePaymentMethodDialog.show(getSupportFragmentManager(), "");
    }

    public void addPayment(InvoicePayment payment) {

        if (payment.getPayment_type().equals(Constant.Data.PaymentMethod.CASH)) {
            cashInputed = true;
        } else if (payment.getPayment_type().equals(Constant.Data.PaymentMethod.TRANSFER)) {
            transferInputed = true;
        } else {
            giroInputed = +1;
        }

        invoicePaymentFragment.addPayment(payment);
        setSisa(Math.abs(getSisa()) - payment.getAmount());
    }

    public void setBankAccount(InvoiceBank bank) {
        invoicePaymentInputDialog.setBankAccount(bank);
    }

    public InvoiceCash getInvoiceCash() {
        return invoiceCash;
    }

    public void setInvoiceCash(InvoiceCash invoiceCash) {
        this.invoiceCash = invoiceCash;
    }

    public InvoiceTransfer getInvoiceTransfer() {
        return invoiceTransfer;
    }

    public void setInvoiceTransfer(InvoiceTransfer invoiceTransfer) {
        this.invoiceTransfer = invoiceTransfer;
    }

    public InvoiceGiro getInvoiceGiro() {
        return invoiceGiro;
    }

    public void setInvoiceGiro(InvoiceGiro invoiceGiro) {
        this.invoiceGiro = invoiceGiro;
    }

    public void showBankDilaog() {
        InvoiceBankDialog invoiceBankDialog = new InvoiceBankDialog();
        invoiceBankDialog.show(getSupportFragmentManager(), "");
    }

    public File getInvoiceGiroPhoto() {
        return invoiceGiroPhoto;
    }

    public void launchCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constant.Permission.CAMERA);
            } else {
                openCamera();
            }
        } else {
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constant.Permission.CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }

//        else if (requestCode == Constant.Permission.GALLERY){
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE_FROM_GALLERY);
//            }
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE_FROM_CAMERA) {
            if (resultCode == RESULT_OK) {
                processImageFromCamera(data);
            }
        }

//        else if (requestCode == RESULT_LOAD_IMAGE_FROM_GALLERY) {
//            if (resultCode == RESULT_OK){
//                if (data != null){
//                    processImageFromGallery(data);
//                }
//            }
//        }
    }

    private void openCamera() {
        fileTemp = getCameraCaptureImageProfile();

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imgUri = FileProvider.getUriForFile(InvoiceActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                fileTemp);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(cameraIntent, RESULT_LOAD_IMAGE_FROM_CAMERA);

    }

    private File getCameraCaptureImageProfile() {
        File outputDir = getExternalCacheDir();
        File file = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
            String timeStamp = simpleDateFormat.format(new Date());
            file = new File(outputDir, FILE_NAME + "_" + timeStamp + FILE_EXTENTION);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private void processImageFromCamera(Intent data) {
        if (fileTemp != null && fileTemp.exists()) {
            loadImage();
        } else {
            if (data != null && data.getExtras() != null && data.getExtras().containsKey("data") && data.getExtras().get("data") instanceof Bitmap) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                try {
                    fileTemp = getCameraCaptureImageProfile();
                    FileOutputStream fos = new FileOutputStream(fileTemp);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.close();
                    loadImage();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bitmap.recycle();
            }
        }
    }

    private void loadImage() {
        int width = AppUtils.getScreenSize(getApplicationContext()).x;
        LoadImage loadImage = new LoadImage(fileTemp, getApplicationContext(), new LoadImage.ImageFinishLoad() {
            @Override
            public void onImageFinishLoad(Bitmap bitmap) {
                invoiceGiroPhoto = Compressor.getDefault(getApplicationContext()).compressToFile(fileTemp);
                rotateImage(invoiceGiroPhoto, bitmap);
            }
        }, width, true);
        loadImage.execute();
    }

    private void rotateImage(File file, Bitmap bitmap) {
        ExifInterface exif = null;
        int orientation;

        try {
            Uri selectedImageUri = Uri.fromFile(file);
            exif = new ExifInterface(selectedImageUri.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = ImageUtils.rotateBitmap(bitmap, orientation);
        invoicePaymentInputDialog.setImageCaptured(file, rotatedBitmap);
    }

    public String getOTPCode() {
        return OTPCode;
    }

    public void setOTPCode(String OTPCode) {
        this.OTPCode = OTPCode;
    }
}
