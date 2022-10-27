package com.radya.sfa.view.dashboard;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.R;
import com.radya.sfa.data.source.preference.PrefManager;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.util.AlertUtils;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.ImageUtils;
import com.radya.sfa.util.JsonUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.util.RequestBodyUtils;
import com.radya.sfa.util.WorkManagerUtils;
import com.radya.sfa.view.agent.Agent;
import com.radya.sfa.view.dashboard.news.DashboardNews;
import com.radya.sfa.view.dashboard.news.DashboardNewsAdapter;
import com.radya.sfa.view.home.HomeActivity;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class DashboardFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.txtInfo)
    TextView txtInfo;
    @BindView(R.id.txtCurrentDate)
    TextView txtCurrentDate;
    @BindView(R.id.btnCheckIn)
    RelativeLayout btnCheckIn;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.txtBtnCheckIn)
    TextView txtBtnCheckIn;
    @BindView(R.id.seeAllAssignment)
    RelativeLayout seeAllAssignment;
    @BindView(R.id.containerAssignmentReport)
    FrameLayout containerAssignmentReport;
    @BindView(R.id.txtCurrentDate2)
    TextView txtCurrentDate2;
    @BindView(R.id.viewNews)
    ViewPager viewNews;
    @BindView(R.id.containerNews)
    FrameLayout containerNews;
    @BindView(R.id.onBoardingIndicator)
    CircleIndicator onBoardingIndicator;
    @BindView(R.id.txtAssignmentTotal)
    TextView txtAssignmentTotal;
    @BindView(R.id.txtAssignmentSuccess)
    TextView txtAssignmentSuccess;
    @BindView(R.id.txtAssignmentFailed)
    TextView txtAssignmentFailed;
    @BindView(R.id.txtAssignmentOnGoing)
    TextView txtAssignmentOnGoing;
    @BindView(R.id.txtWorkTime)
    TextView txtWorkTime;
    @BindView(R.id.txtLostTime)
    TextView txtLostTime;
    @BindView(R.id.containerWorkTimeNotSPV)
    TableRow containerWorkTimeNotSPV;
    @BindView(R.id.txtAgentName)
    TextView txtAgentName;
    @BindView(R.id.txtWorkTimeAgent)
    TextView txtWorkTimeAgent;
    @BindView(R.id.txtLostTimeAgent)
    TextView txtLostTimeAgent;
    @BindView(R.id.containerWorkTimeAgent)
    LinearLayout containerWorkTimeAgent;

    private DashboardViewModel viewModel;
    private int status;
    private Agent selectedAgent;
    private boolean asDriver;
    private String lastKm;

    private DashboardNews news;
    private DashboardNewsAdapter newsAdapter;

    public DashboardFragment() {
        // Requires empty public constructor
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViewModel();
        initView();

        return view;
    }

    public void setLastKm(String lastKm, File file) {
        this.lastKm = lastKm;
        checkOut(file);
    }

    public void setSelectedAgent(Agent selectedAgent) {
        this.selectedAgent = selectedAgent;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkAttendance();
    }

    private void initView() {
        ((HomeActivity) getActivity()).disableSideMenu();

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        txtCurrentDate.setText(DateUtils.convertDateToString(currentDate, Constant.DateFormat.MEDIUM_2));
        txtCurrentDate2.setText(DateUtils.convertDateToString(currentDate, Constant.DateFormat.MEDIUM_2));

    }

    private void checkAttendance() {
        progressLoading.setVisibility(View.VISIBLE);
        btnCheckIn.setEnabled(false);

        viewModel.checkAttendance(NetworkUtils.getConnectionManager());
        viewModel.getAttendanceCheckResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                progressLoading.setVisibility(View.GONE);
                btnCheckIn.setEnabled(true);

                Attendance response = (Attendance) apiResponse.getData();
                if (response != null) {
                    status = response.getData();

                    if (status == 1) {
                        containerAssignmentReport.setVisibility(View.VISIBLE);
                        containerNews.setVisibility(View.GONE);

                        initDashboard();

                        txtBtnCheckIn.setText(R.string.labelStopWork);
                        btnCheckIn.setVisibility(View.VISIBLE);

                        ((HomeActivity) getActivity()).showSideMenu();
                        ((HomeActivity) getActivity()).enableSideMenu();
                        seeAllAssignment.setEnabled(true);

                    } else {
                        containerAssignmentReport.setVisibility(View.GONE);
                        containerNews.setVisibility(View.VISIBLE);

                        initNews();

                        if (status == 0) {
                            txtBtnCheckIn.setText(R.string.labelStartWork);
                            btnCheckIn.setVisibility(View.VISIBLE);

                            ((HomeActivity) getActivity()).hideSideMenu();
                            ((HomeActivity) getActivity()).showSideMenu();
                            ((HomeActivity) getActivity()).enableSideMenu();
                            seeAllAssignment.setEnabled(false);
                        } else {
                            btnCheckIn.setVisibility(View.GONE);

                            ((HomeActivity) getActivity()).showSideMenu();
                            ((HomeActivity) getActivity()).enableSideMenu();
                            seeAllAssignment.setEnabled(true);
                        }
                    }
                }
            }
        });

    }

    private void initDashboard() {
        progressLoading.setVisibility(View.VISIBLE);

        viewModel.getDashboard(NetworkUtils.getConnectionManager(), DateUtils.getCurrentTime(Constant.DateFormat.SERVER));
        viewModel.getDashboardResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                Dashboard response = (Dashboard) apiResponse.getData();
                if (response != null) {
                    txtAssignmentTotal.setText("" + response.getData().getTotalTask());
                    txtAssignmentSuccess.setText("" + response.getData().getSuccess());
                    txtAssignmentFailed.setText("" + response.getData().getFailed());
                    txtAssignmentOnGoing.setText("" + response.getData().getOn_going());

                    String agentStatus = MyApplication.getInstance().userRole();
                    txtWorkTime.setText("" + response.getData().getTotal_work_time() + " Jam");
                    txtLostTime.setText("" + response.getData().getTotal_loss_time() + " Jam");

//                    if (agentStatus.equals(Constant.Data.Agent.SPV)) {
//                        containerWorkTimeAgent.setVisibility(View.VISIBLE);
//
//                        if (response.getData().getAssignment_report().size() != 0){
//                            txtAgentName.setText("Laporan kerja agent: " + response.getData().getAssignment_report().get(0).getUser_name());
//                            txtWorkTimeAgent.setText("" + response.getData().getAssignment_report().get(0).getTotal_work_time() + " Jam");
//                            txtLostTimeAgent.setText("" + response.getData().getAssignment_report().get(0).getTotal_loss_time() + " Jam");
//                        } else {
//                            txtAgentName.setText("Laporan kerja agent: " + response.getData().getAssignment_report().get(0).getUser_name());
//                            txtWorkTimeAgent.setText("-");
//                            txtLostTimeAgent.setText("-");
//                        }
//
//                    } else {
//                        containerWorkTimeAgent.setVisibility(View.GONE);
//                    }

                }
            }
        });

    }

    private void initNews() {

        progressLoading.setVisibility(View.VISIBLE);

        viewModel.getNewsList(NetworkUtils.getConnectionManager(), 0);
        viewModel.getNewsListResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {

                progressLoading.setVisibility(View.GONE);

                DashboardNews response = (DashboardNews) apiResponse.getData();

                if (response != null) {
                    news = response;

                    newsAdapter = new DashboardNewsAdapter(getActivity(), news.getData());

                    viewNews.setAdapter(newsAdapter);
                    if (news.getData().size() != 0){
                        viewNews.setOffscreenPageLimit(news.getData().size());
                    }else {
                        viewNews.setOffscreenPageLimit(1);
                    }

                    onBoardingIndicator.setViewPager(viewNews);
                    onBoardingIndicator.getDataSetObserver().onInvalidated();

                }

            }
        });

    }

    private DashboardNews loadNewsMock() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        JsonObject object = JsonUtils.loadJSONFromAsset(getActivity(), "json/news_mock.json");

        return gson.fromJson(object, DashboardNews.class);
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.txtCurrentDate)
    public void txtCurrentDate() {

    }

    @OnClick(R.id.seeAllAssignment)
    public void seeAllAssignment() {
        ((HomeActivity) getActivity()).seeAssignment();
    }

    @OnClick(R.id.containerAssignmentTotal)
    public void containerAssignmentTotal() {

    }

    @OnClick(R.id.containerAssignmentSuccess)
    public void containerAssignmentSuccess() {

    }

    @OnClick(R.id.containerAssignmentFailed)
    public void containerAssignmentFailed() {

    }

    @OnClick(R.id.containerAssignmentOnGoing)
    public void containerAssignmentOnGoing() {
        ((HomeActivity) getActivity()).seeAssignment();
    }


    @OnClick(R.id.btnCheckIn)
    public void onViewClicked() {
        if (status == 0) {
            checkAgent();
        } else if (status == 1) {

            String agentStatus = MyApplication.getInstance().userRole();
            if (agentStatus.equals(Constant.Data.Agent.DRIVER)) {
                asDriver = true;

                ((HomeActivity) getActivity()).showDriverCheckOutDialog();

            } else {
                asDriver = false;

                AlertUtils alertUtils = new AlertUtils(getActivity());
                alertUtils.showAlert(getString(R.string.labelConfirmCheckOut), new AlertUtils.negativeButton() {
                    @Override
                    public void onNo(DialogInterface dialogInterface) {
                        dialogInterface.dismiss();
                    }
                }, new AlertUtils.positiveButton() {
                    @Override
                    public void onYes(DialogInterface dialogInterface) {
                        checkOut(null);
                    }
                }, Constant.Data.NEGATIVE_BUTTON, Constant.Data.POSITIVE_BUTTON);
            }

        }
    }

    private void checkAgent() {
        String agentStatus = MyApplication.getInstance().userRole();
        if (agentStatus.equals(Constant.Data.Agent.SPV)) {
            ((HomeActivity) getActivity()).showAttendanceSPVDialog();
        } else if (agentStatus.equals(Constant.Data.Agent.DRIVER)) {
            ((HomeActivity) getActivity()).showAttendanceDriverDialog();
        } else {
            checkIn();
        }
    }

    private void checkOut(File file) {

        progressLoading.setVisibility(View.VISIBLE);
        btnCheckIn.setEnabled(false);

        double latitude = MyApplication.getInstance().latitude();
        double longitude = MyApplication.getInstance().longitude();
        String userId = "123";
        int mode = 0;
        String endTime = DateUtils.getCurrentTime(Constant.DateFormat.SERVER);


        RequestBody requestFile;
        MultipartBody.Part Attachment;

        if (file != null){
            requestFile = RequestBodyUtils.getImage(file);
            Attachment = MultipartBody.Part.createFormData(Constant.Api.Params.ATTACHMENT, file.getName(), requestFile);
        }else {
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.null_image);
            File fileNull = ImageUtils.persistImage(getActivity(), bm, "null_image");

            requestFile = RequestBodyUtils.getImage(fileNull);
            Attachment = MultipartBody.Part.createFormData(Constant.Api.Params.ATTACHMENT, fileNull.getName(), requestFile);
        }

        RequestBody Latitude = RequestBody.create(MediaType.parse("text/plain"), "" + latitude);
        RequestBody Longitude = RequestBody.create(MediaType.parse("text/plain"), "" + longitude);
        RequestBody EndTime = RequestBody.create(MediaType.parse("text/plain"), endTime);
        RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody Mode = RequestBody.create(MediaType.parse("text/plain"), "" + mode);
        RequestBody Distance;

        if (asDriver) {
            Distance = RequestBody.create(MediaType.parse("text/plain"), lastKm);
        }else {
            Distance = RequestBody.create(MediaType.parse("text/plain"), "0");
        }

        viewModel.checkOut(NetworkUtils.getConnectionManager(), Latitude, Longitude, EndTime, UserId, Mode, Distance, Attachment);
        viewModel.getCheckOutResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                btnCheckIn.setEnabled(true);

                BaseModel response = (BaseModel) apiResponse.getData();
                if (response != null) {
                    stopLogLocationUpdate();
                    checkAttendance();
                }
            }
        });

    }

    private void stopLogLocationUpdate() {
        WorkManagerUtils.getWorkManager().cancelUniqueWork(Constant.Preference.User.WORK_LOCATION_UPDATE);
    }

    private void checkIn() {
        progressLoading.setVisibility(View.VISIBLE);
        btnCheckIn.setEnabled(false);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.null_image);
        File file = ImageUtils.persistImage(getActivity(), bm, "null_image");

        double latitude = MyApplication.getInstance().latitude();
        double longitude = MyApplication.getInstance().longitude();
        String userId = "123";
        int mode = 0;
        String startTime = DateUtils.getCurrentTime(Constant.DateFormat.SERVER);


        RequestBody requestFile;
        MultipartBody.Part Attachment;

        requestFile = RequestBodyUtils.getImage(file);
        Attachment = MultipartBody.Part.createFormData(Constant.Api.Params.ATTACHMENT, file.getName(), requestFile);

        RequestBody Latitude = RequestBody.create(MediaType.parse("text/plain"), "" + latitude);
        RequestBody Longitude = RequestBody.create(MediaType.parse("text/plain"), "" + longitude);
        RequestBody StartTime = RequestBody.create(MediaType.parse("text/plain"), startTime);
        RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody Mode = RequestBody.create(MediaType.parse("text/plain"), "" + mode);

        viewModel.checkIn(NetworkUtils.getConnectionManager(), Latitude, Longitude, StartTime, UserId, Mode, Attachment);
        viewModel.getCheckInResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                btnCheckIn.setEnabled(true);

                BaseModel response = (BaseModel) apiResponse.getData();
                if (response != null) {
//                    PrefManager.saveBoolean(Constant.Preference.ASSIGNMENT_COMPLETE, true);
//                    logLocationUpdate();
                    checkAttendance();
                }

            }
        });
    }

    private void logLocationUpdate() {
        PeriodicWorkRequest.Builder workRequestBuilder;
        PeriodicWorkRequest periodicWorkRequest;

        workRequestBuilder = new PeriodicWorkRequest.Builder(
                LocationUpdateWorker.class,
                PrefManager.getInt(Constant.Preference.Config.LOCATION_UPDATE_INTERVAL),
                TimeUnit.MILLISECONDS);

        periodicWorkRequest = workRequestBuilder.build();

        WorkManagerUtils.getWorkManager()
                .enqueueUniquePeriodicWork(Constant.Preference.User.WORK_LOCATION_UPDATE,
                        ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);
    }

    public void checkInSPV(String agentId) {
        progressLoading.setVisibility(View.VISIBLE);
        btnCheckIn.setEnabled(false);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.null_image);
        File file = ImageUtils.persistImage(getActivity(), bm, "null_image");

        double latitude = MyApplication.getInstance().latitude();
        double longitude = MyApplication.getInstance().longitude();
        String userId = agentId;
        int mode = 0;
        String startTime = DateUtils.getCurrentTime(Constant.DateFormat.SERVER);


        RequestBody requestFile;
        MultipartBody.Part Attachment;

        requestFile = RequestBodyUtils.getImage(file);
        Attachment = MultipartBody.Part.createFormData(Constant.Api.Params.ATTACHMENT, file.getName(), requestFile);

        RequestBody Latitude = RequestBody.create(MediaType.parse("text/plain"), "" + latitude);
        RequestBody Longitude = RequestBody.create(MediaType.parse("text/plain"), "" + longitude);
        RequestBody StartTime = RequestBody.create(MediaType.parse("text/plain"), startTime);
        RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody Mode = RequestBody.create(MediaType.parse("text/plain"), "" + mode);

        viewModel.checkIn(NetworkUtils.getConnectionManager(), Latitude, Longitude, StartTime, UserId, Mode, Attachment);
        viewModel.getCheckInResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                btnCheckIn.setEnabled(true);

                BaseModel response = (BaseModel) apiResponse.getData();
                if (response != null) {
//                    logLocationUpdate();
                    checkAttendance();
                }
            }
        });
    }

    public void checkInDriver(String startKM, File file) {
        progressLoading.setVisibility(View.VISIBLE);
        btnCheckIn.setEnabled(false);

        double latitude = MyApplication.getInstance().latitude();
        double longitude = MyApplication.getInstance().longitude();
        String userId = "123";
        int mode = 0;
        String distance = startKM;
        String startTime = DateUtils.getCurrentTime(Constant.DateFormat.SERVER);


        RequestBody requestFile;
        MultipartBody.Part Attachment;

        requestFile = RequestBodyUtils.getImage(file);
        Attachment = MultipartBody.Part.createFormData(Constant.Api.Params.ATTACHMENT, file.getName(), requestFile);

        RequestBody Latitude = RequestBody.create(MediaType.parse("text/plain"), "" + latitude);
        RequestBody Longitude = RequestBody.create(MediaType.parse("text/plain"), "" + longitude);
        RequestBody StartTime = RequestBody.create(MediaType.parse("text/plain"), startTime);
        RequestBody UserId = RequestBody.create(MediaType.parse("text/plain"), userId);
        RequestBody Mode = RequestBody.create(MediaType.parse("text/plain"), "" + mode);
        RequestBody Distance = RequestBody.create(MediaType.parse("text/plain"), "" + distance);

        viewModel.checkInDriver(NetworkUtils.getConnectionManager(), Latitude, Longitude, StartTime, UserId, Mode, Distance, Attachment);
        viewModel.getCheckInResponse().observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(@Nullable ApiResponse apiResponse) {
                progressLoading.setVisibility(View.GONE);
                btnCheckIn.setEnabled(true);

                BaseModel response = (BaseModel) apiResponse.getData();
                if (response != null) {
//                    logLocationUpdate();
                    checkAttendance();
                }
            }
        });
    }

}
