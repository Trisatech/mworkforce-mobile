package com.radya.sfa.view.home;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.radya.sfa.BuildConfig;
import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.R;
import com.radya.sfa.data.source.preference.PrefManager;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.helper.image.LoadImage;
import com.radya.sfa.util.ActivityUtils;
import com.radya.sfa.util.AlertUtils;
import com.radya.sfa.util.AppUtils;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.ImageUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.LocationUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.BaseActivity;
import com.radya.sfa.view.agent.Agent;
import com.radya.sfa.view.assignment.AssignmentFragment;
import com.radya.sfa.view.assignment.gasoline.AssignmentGasolineInputActivity;
import com.radya.sfa.view.contact.add.ContactAddActivity;
import com.radya.sfa.view.contact.list.ContactListFragment;
import com.radya.sfa.view.dashboard.Attendance;
import com.radya.sfa.view.dashboard.CheckInAsDriverDialog;
import com.radya.sfa.view.dashboard.CheckInAsSPVDialog;
import com.radya.sfa.view.dashboard.CheckOutAsDriverDialog;
import com.radya.sfa.view.dashboard.DashboardFragment;
import com.radya.sfa.view.news.NewsFragment;
import com.radya.sfa.view.report.ReportListFragment;
import com.radya.sfa.view.settings.SettingsFragment;
import com.radya.sfa.view.sidemenu.SideMenu;
import com.radya.sfa.view.sidemenu.SideMenuAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

/**
 * Created by aderifaldi on 2018-03-24.
 */

public class HomeActivity extends BaseActivity {
    private static final String FILE_EXTENTION = ".jpg";
    private static final String FILE_NAME = "spedometer_photo";

    private static final int RESULT_LOAD_IMAGE_FROM_CAMERA = 100;

    @BindView(R.id.icNavLeft)
    ImageView icNavLeft;
    @BindView(R.id.listMenu)
    RecyclerView listMenu;
    @BindView(R.id.navigationView)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.appBarTitle)
    TextView appBarTitle;
    @BindView(R.id.addNew)
    RelativeLayout addNew;
    @BindView(R.id.navLeft)
    RelativeLayout navLeft;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private SideMenuAdapter sideMenuAdapter;
    private LinearLayoutManager linearLayoutManager;
    private FragmentManager fragmentManager;

    private boolean isSideMenuOpen;
    private String menuSelected;

    public AssignmentFragment assignmentFragment;
    private DashboardFragment dashboardFragment;
    private String filterDate;

    private CheckInAsSPVDialog attendanceAsSPVDialog;
    private CheckInAsDriverDialog attendanceAsDriverDialog;
    private CheckOutAsDriverDialog checkOutAsDriverDialog;

    private Agent agent;

    private HomeViewModel viewModel;

    private File file = null;
    private File fileTemp = null;
    private int status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);

        AppUtils.showAuthToken();

        initViewModel();
        initAttendanceDialog();
        initFragment();
        getLocation();
        setupSideMenu();
        initFragmentManager();
        checkAttendance();

    }

    private void checkAttendance() {
        progressLoading.setVisibility(View.VISIBLE);

        viewModel.checkAttendance(NetworkUtils.getConnectionManager());
        viewModel.getAttendanceCheckResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                progressLoading.setVisibility(View.GONE);

                Attendance response = (Attendance) apiResponse.getData();
                if (response != null) {
                    int status = response.getData();
                    if (status == 1) {
                        seeAssignment();
                    } else {
                        seeDashboard();
                    }
                }

            }
        });

    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    public void checkOutDriver(String lastKm, File file) {
        dashboardFragment.setLastKm(lastKm, file);
    }

    public void enableSideMenu() {
        navLeft.setEnabled(true);
    }

    public void disableSideMenu() {
        navLeft.setEnabled(false);
    }

    private void initAttendanceDialog() {
        attendanceAsSPVDialog = new CheckInAsSPVDialog();
        attendanceAsDriverDialog = new CheckInAsDriverDialog();
        checkOutAsDriverDialog = new CheckOutAsDriverDialog();
    }

    public void showAttendanceSPVDialog() {
        attendanceAsSPVDialog.show(getSupportFragmentManager(), "");
    }

    public void showAttendanceDriverDialog() {
        attendanceAsDriverDialog.show(getSupportFragmentManager(), "");
    }

    public void checkInAsSPV() {
        if (agent != null) {
            dashboardFragment.checkInSPV(agent.getUser_id());
        } else {
            dashboardFragment.checkInSPV("0");
        }
    }

    public void checkInDriver(String startKM, File file) {
        dashboardFragment.checkInDriver(startKM, file);
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
        attendanceAsSPVDialog.setAgent(agent.getUser_name());
        dashboardFragment.setSelectedAgent(agent);
    }

    public Agent getAgent() {
        return agent;
    }

    public String getFilterDate() {
        return filterDate;
    }

    public void setFilterDate(String filterDate) {
        this.filterDate = filterDate;
    }

    private void initFragment() {
        dashboardFragment = DashboardFragment.newInstance();
        assignmentFragment = AssignmentFragment.newInstance();
    }

    public void showSideMenu() {
        navLeft.setVisibility(View.VISIBLE);
    }

    public void hideSideMenu() {
        navLeft.setVisibility(View.GONE);
    }

    private void getLocation() {
        LocationUtils locationUtils = new LocationUtils(this);
        locationUtils.setCurrentLocation();
    }

    private void seeDashboard() {
        setFilterDate(DateUtils.getCurrentTime(Constant.DateFormat.SERVER));
        userName.setText(PrefManager.getString(Constant.Preference.User.USER_NAME));

        addNew.setVisibility(View.GONE);
        appBarTitle.setText(R.string.app_name);
        replaceFragment(dashboardFragment);
    }

    private void initFragmentManager() {
        fragmentManager = getSupportFragmentManager();
    }

    private void replaceFragment(Fragment fragment) {
        ActivityUtils.replaceFragment(fragmentManager, fragment, R.id.contentFrame, false);
    }

    private void closeSideMenu() {
        drawerLayout.closeDrawers();
        isSideMenuOpen = false;
    }

    private void openSideMenu() {
        isSideMenuOpen = true;
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void seeAssignment() {
        addNew.setVisibility(View.VISIBLE);
        menuSelected = sideMenuAdapter.getData().get(1).getMenuName();

        for (int i = 0; i < sideMenuAdapter.getData().size(); i++) {
            sideMenuAdapter.getData().get(i).setSelected(false);
        }

        sideMenuAdapter.getData().get(1).setSelected(true);
        sideMenuAdapter.notifyDataSetChanged();

        replaceFragment(assignmentFragment);
        appBarTitle.setText(DateUtils.getCurrentTime(Constant.DateFormat.MEDIUM_2));
    }

    private void setupSideMenu() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        sideMenuAdapter = new SideMenuAdapter();
        linearLayoutManager = new LinearLayoutManager(this);

        listMenu.setAdapter(sideMenuAdapter);
        listMenu.setLayoutManager(linearLayoutManager);

        sideMenuAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SideMenu menu = sideMenuAdapter.getData().get(position);
                menuSelected = menu.getMenuName();

                for (int i = 0; i < sideMenuAdapter.getData().size(); i++) {
                    sideMenuAdapter.getData().get(i).setSelected(false);
                }

                sideMenuAdapter.getData().get(position).setSelected(true);
                sideMenuAdapter.notifyDataSetChanged();

                if (menu.getId() == R.drawable.ic_dashboard_white) {
                    addNew.setVisibility(View.GONE);
                    replaceFragment(dashboardFragment);
                    appBarTitle.setText(R.string.app_name);
                } else if (menu.getId() == R.drawable.ic_appointment_white) {
                    addNew.setVisibility(View.VISIBLE);
                    replaceFragment(assignmentFragment);
                    appBarTitle.setText(DateUtils.getCurrentTime(Constant.DateFormat.MEDIUM_2));
                } else if (menu.getId() == R.drawable.ic_contact_white) {
                    addNew.setVisibility(View.GONE);
                    replaceFragment(ContactListFragment.newInstance());
                    appBarTitle.setText(Constant.Menu.CONTACT);
                } else if (menu.getId() == R.drawable.ic_settings_white) {
                    addNew.setVisibility(View.GONE);
                    replaceFragment(SettingsFragment.newInstance());
                    appBarTitle.setText(Constant.Menu.SETTINGS);
                } else if (menu.getId() == R.drawable.ic_news) {
                    addNew.setVisibility(View.GONE);
                    replaceFragment(NewsFragment.newInstance());
                    appBarTitle.setText(Constant.Menu.NEWS);
                } else if (menu.getId() == R.drawable.ic_report_white) {
                    addNew.setVisibility(View.GONE);
                    replaceFragment(ReportListFragment.newInstance());
                    appBarTitle.setText(Constant.Menu.REPORT);
                }

                closeSideMenu();

            }
        });

        initSideMenu();
    }

    private void initSideMenu() {
        List<SideMenu> menuList = new ArrayList<>();
        menuList.add(new SideMenu(R.drawable.ic_dashboard_white, Constant.Menu.DASHBOARD, true));
        menuList.add(new SideMenu(R.drawable.ic_appointment_white, Constant.Menu.APPOINTMENT, false));
        menuList.add(new SideMenu(R.drawable.ic_news, Constant.Menu.NEWS, false));
        if (MyApplication.getInstance().userRole().equals(Constant.Data.Agent.SPV)){
            menuList.add(new SideMenu(R.drawable.ic_report_white, Constant.Menu.REPORT, false));
        }
        menuList.add(new SideMenu(R.drawable.ic_contact_white, Constant.Menu.CONTACT, false));
        menuList.add(new SideMenu(R.drawable.ic_settings_white, Constant.Menu.SETTINGS, false));

        for (int i = 0; i < menuList.size(); i++) {
            sideMenuAdapter.getData().add(menuList.get(i));
            sideMenuAdapter.notifyItemInserted(sideMenuAdapter.getData().size() - 1);
        }
        sideMenuAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.navLeft)
    public void onViewClicked() {
        openSideMenu();
    }

    @OnClick(R.id.appBarTitle)
    public void showDatePicker() {
        if (menuSelected.equals(Constant.Menu.APPOINTMENT)) {
            showDatePickerDialog(this);
        }
    }

    @OnClick(R.id.addNew)
    public void addNew() {
        if (menuSelected.equals(Constant.Menu.APPOINTMENT)) {
            String userRole = MyApplication.getInstance().userRole();
            if (userRole.equals(Constant.Data.Agent.DRIVER)) {
                IntentUtils.intentTo(this, AssignmentGasolineInputActivity.class, false);
            }else if (userRole.equals(Constant.Data.Agent.SALES)
                    ||userRole.equals(Constant.Data.Agent.SPV)){
                IntentUtils.intentTo(this, ContactAddActivity.class, false);
            }
        }
    }

    private void showDatePickerDialog(Activity activity) {
        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DateFormat.MEDIUM_2);
                        SimpleDateFormat dateFormatFilter = new SimpleDateFormat(Constant.DateFormat.SERVER);

                        appBarTitle.setText(dateFormat.format(calendar.getTime()));

                        String dateFilter = dateFormatFilter.format(calendar.getTime());

                        setFilterDate(dateFilter);

                        assignmentFragment.getAssignment(dateFilter);

                    }
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());

        c.add(Calendar.MONTH, -1);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertUtils alertUtils = new AlertUtils(HomeActivity.this);
        alertUtils.showAlert(getString(R.string.alertMessageConfirmExit), new AlertUtils.negativeButton() {
            @Override
            public void onNo(DialogInterface dialogInterface) {
                dialogInterface.dismiss();
            }
        }, new AlertUtils.positiveButton() {
            @Override
            public void onYes(DialogInterface dialogInterface) {
                finish();
                IntentUtils.backAnimation(HomeActivity.this);
            }
        }, Constant.Data.NEGATIVE_BUTTON, Constant.Data.POSITIVE_BUTTON);
    }

    public void launchCamera(int status) {

        this.status = status;

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
        Uri imgUri = FileProvider.getUriForFile(HomeActivity.this,
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
                file = Compressor.getDefault(getApplicationContext()).compressToFile(fileTemp);
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

        Bitmap rotatedBitmap = ImageUtils.rotateBitmap(bitmap, orientation);
        if (status == 0){
            attendanceAsDriverDialog.setImageCaptured(file, rotatedBitmap);
        }else {
            checkOutAsDriverDialog.setImageCaptured(file, rotatedBitmap);
        }
    }

    public void showDriverCheckOutDialog() {
        checkOutAsDriverDialog = new CheckOutAsDriverDialog();
        checkOutAsDriverDialog.setCancelable(false);
        checkOutAsDriverDialog.show(getSupportFragmentManager(), "");
    }
}
