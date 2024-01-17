package com.radya.sfa.view.assignment.detail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.radya.sfa.BuildConfig;
import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.helper.image.LoadImage;
import com.radya.sfa.util.ActivityUtils;
import com.radya.sfa.util.AppUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.LocationUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.view.BaseActivity;
import com.radya.sfa.view.assignment.Assignment;
import com.radya.sfa.view.assignment.AssignmentViewModel;
import com.radya.sfa.view.assignment.detail.failed.AssignmentFailedReason;
import com.radya.sfa.view.assignment.detail.failed.AssignmentFailedReasonDialog;

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

public class AssignmentDetailActivity extends BaseActivity {

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
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private AssignmentViewModel viewModel;

    private String taskId;

    private Bundle bundle;

    private Assignment assignmentDetail;

    private File file = null;
    private File fileTemp = null;

    private AssignmentDetailFragment taskDetailFragment;
    private AssignmentFailedReasonDialog failedReasonDialog;
    private AssignmentFailedReason.AssignmentReason assignmentReason;
    private AddOTPDialog addOTPDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        ButterKnife.bind(this);

        progressLoading.setVisibility(View.GONE);

        assignmentReason = null;

        appBarTitle.setText(R.string.labelTitlePageAssignmentDetail);

        initViewModel();
        initCurrentLocation();

        bundle = getIntent().getExtras();
        if (bundle != null){
            assignmentDetail = (Assignment) bundle.getSerializable(Constant.SERIALIZABLE_NAME);
            taskId = assignmentDetail.getAssignmentId();

            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.SERIALIZABLE_NAME, assignmentDetail);

            taskDetailFragment = AssignmentDetailFragment.newInstance();

            ActivityUtils.replaceFragment(getSupportFragmentManager(), taskDetailFragment, R.id.contentFrame, bundle, false);
        }


    }

    public void completeAssignment(){
        addOTPDialog.dismiss();
        taskDetailFragment.completeAssignment();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        loadAssignmentDetail(taskId);
    }

    private void initCurrentLocation() {
        LocationUtils locationUtils = new LocationUtils(this);
        locationUtils.setCurrentLocation();
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AssignmentViewModel.class);
    }

    private void loadAssignmentDetail(String taskId) {
        viewModel.getAssignmentDetail(NetworkUtils.getConnectionManager(), taskId);
        viewModel.getAssignmentDetailResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                AssignmentDetail data = (AssignmentDetail) apiResponse.getData();
                if (data != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.SERIALIZABLE_NAME, data);

                    taskDetailFragment = AssignmentDetailFragment.newInstance();

                    ActivityUtils.replaceFragment(getSupportFragmentManager(), taskDetailFragment, R.id.contentFrame, bundle, false);

                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        IntentUtils.backAnimation(this);
    }

    @OnClick({R.id.navLeft, R.id.navRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.navLeft:
                onBackPressed();
                break;
            case R.id.navRight:
                break;
        }
    }

    public void launchCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constant.Permission.CAMERA);
            }else {
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
            if (resultCode == RESULT_OK){
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
        Uri imgUri = FileProvider.getUriForFile(AssignmentDetailActivity.this,
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
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
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
                file = fileTemp;
                rotateImage(file, bitmap);
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

        Bitmap rotatedBitmap = rotateBitmap(bitmap, orientation);


        failedReasonDialog.setImageCaptured(file, rotatedBitmap);
//        taskDetailFragment.setImageCaptured(file, rotatedBitmap);
    }

    private Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public void showAssignmentFailedResponse() {
        failedReasonDialog = new AssignmentFailedReasonDialog();
        failedReasonDialog.setCancelable(false);
        failedReasonDialog.show(getSupportFragmentManager(), "");
    }

    public AssignmentFailedReason.AssignmentReason getAssignmentReason() {
        return assignmentReason;
    }

    public void setAssignmentReason(AssignmentFailedReason.AssignmentReason assignmentReason) {
        this.assignmentReason = assignmentReason;
        failedReasonDialog.setAssignmentReason(assignmentReason);
    }

    public String getAssgnmentId() {
        return taskId;
    }

    public void showAddOTPDialog() {
        addOTPDialog = new AddOTPDialog();
        addOTPDialog.setCancelable(false);

        addOTPDialog.show(getSupportFragmentManager(), "");
    }
}
