package com.radya.sfa.view.contact.add;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.radya.sfa.BuildConfig;
import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.helper.image.LoadImage;
import com.radya.sfa.util.ActivityUtils;
import com.radya.sfa.util.AppUtils;
import com.radya.sfa.util.ImageUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.view.BaseActivity;
import com.radya.sfa.view.invoice.InvoiceActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

public class ContactAddActivity extends BaseActivity {

    private static final String FILE_EXTENTION = ".jpg";
    private static final String FILE_NAME = "customer_photo";

    private static final int RESULT_LOAD_IMAGE_FROM_CAMERA = 100;

    private File invoiceGiroPhoto = null;
    private File fileTemp = null;

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
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private Bundle bundle;

    private ContactAddFragment fragment;
    private int photoFor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        ButterKnife.bind(this);
        initFragment();
        initData();
        initView();
    }

    public void setStatus(String status){
        fragment.setStatus(status);
    }

    public void setType(String type){
        fragment.setType(type);
    }

    private void initFragment() {
        fragment = ContactAddFragment.newInstance();
    }

    private void initView() {
        progressLoading.setVisibility(View.GONE);
        appBarTitle.setText("Tambah Pelanggan");
        ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.contentFrame, false);
    }

    private void initData() {
        if (bundle != null){

        }
    }

    @OnClick({R.id.navLeft, R.id.navRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.navLeft:
                onBackPressed();
                IntentUtils.backAnimation(this);
                break;
            case R.id.navRight:
                break;
        }
    }

    public void launchCamera(int photoFor) {

        this.photoFor = photoFor;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.Permission.CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE_FROM_CAMERA) {
            if (resultCode == RESULT_OK) {
                processImageFromCamera(data);
            }
        }
    }

    private void openCamera() {
        fileTemp = getCameraCaptureImageProfile();

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imgUri = FileProvider.getUriForFile(ContactAddActivity.this,
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
        LoadImage loadImage = new LoadImage(fileTemp, getApplicationContext(), bitmap -> {
            invoiceGiroPhoto = fileTemp;
            rotateImage(invoiceGiroPhoto, bitmap);
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
        fragment.setImageCaptured(photoFor, file, rotatedBitmap);
    }

}
