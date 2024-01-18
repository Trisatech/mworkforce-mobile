package com.radya.sfa.view.report;


import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiServiceDirection;
import com.radya.sfa.data.source.remote.ServiceGeneratorGoogleDirection;
import com.radya.sfa.helper.maps.Distance;
import com.radya.sfa.helper.maps.Duration;
import com.radya.sfa.helper.maps.Route;
import com.radya.sfa.util.DateUtils;
import com.radya.sfa.util.StringUtils;
import com.radya.sfa.util.ToastUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class ReportDetailFragment extends Fragment implements OnMapReadyCallback {

    Unbinder unbinder;

    @BindView(R.id.txtAgentName)
    TextView txtAgentName;
    @BindView(R.id.txtTotalKunjungan)
    TextView txtTotalKunjungan;
    @BindView(R.id.txtStartTime)
    TextView txtStartTime;
    @BindView(R.id.txtTotalWorkTime)
    TextView txtTotalWorkTime;
    @BindView(R.id.txtEndTime)
    TextView txtEndTime;
    @BindView(R.id.txtTotalLossTime)
    TextView txtTotalLossTime;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.listVisit)
    RecyclerView listVisit;

    @BindView(R.id.txtWaktuKerja)
    TextView txtWaktuKerja;
    @BindView(R.id.txtTagihan)
    TextView txtTagihan;

    private Bundle bundle;
    private ReportDetail reportDetail;

    private SupportMapFragment map;

    private GoogleMap mMap;
    private ArrayList<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();

    private List<Address> addresses = null;
    private List<Address> destiny = null;
    private Geocoder geocoder;
    private String addres, dest;
    private List<Route> routes;

    public ReportDetailFragment() {
        // Requires empty public constructor
    }

    public static ReportDetailFragment newInstance() {
        return new ReportDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_detail_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        map = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);
    }

    private void initData() {
        bundle = getArguments();
        if (bundle != null) {

            reportDetail = (ReportDetail) bundle.getSerializable(Constant.SERIALIZABLE_NAME);

            if (reportDetail.getData() != null){
                if (reportDetail.getData().getName() != null){
                    txtAgentName.setText(reportDetail.getData().getName());
                }

//                if (reportDetail.getData().getVisitHistory().size() != 0) {
//                    txtTotalKunjungan.setText("" + reportDetail.getData().getTotalTaskCompleted() + "/" + ""
//                            +reportDetail.getData().getTotalTask());
//                } else {
//                    txtTotalKunjungan.setText("0");
//                }

                txtTotalKunjungan.setText("" + reportDetail.getData().getTotalTaskCompleted() + "/" + ""
                        +reportDetail.getData().getTotalTask());

                txtWaktuKerja.setText("" + String.format("%.2f",reportDetail.getData().getTotalWorkTime()) + " Jam");

                long totalInvoice = ((ReportDetailActivity) getActivity()).getTotalInvoice();
                txtTagihan.setText(StringUtils.getRpCurency(totalInvoice));

                if (reportDetail.getData().getStartTime() != null) {
                    txtStartTime.setText(DateUtils.convertStringDate(reportDetail.getData().getStartTime(),
                            Constant.DateFormat.XSHORT));
                } else {
                    txtStartTime.setText("-");
                }

                if (reportDetail.getData().getTotalWorkTime() != 0){
                    txtTotalWorkTime.setText("" + String.format("%.2f",reportDetail.getData().getTotalTimeAtStore()) + " Jam");
                }

                if (reportDetail.getData().getTotalLossTime() != 0){
                    txtTotalLossTime.setText("" + String.format("%.2f",reportDetail.getData().getTotalLossTime()) + " Jam");
                }

                if (reportDetail.getData().getEndTime() != null){
                    txtEndTime.setText(DateUtils.convertStringDate(reportDetail.getData().getEndTime(), Constant.DateFormat.XSHORT));
                }

                if (reportDetail.getData().getVisitHistory().size() != 0){
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    ReportDetailVisitHistoryAdapter adapter = new ReportDetailVisitHistoryAdapter();

                    listVisit.setLayoutManager(linearLayoutManager);
                    listVisit.setAdapter(adapter);

                    adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        }
                    });

                    for (int i = 0; i < reportDetail.getData().getVisitHistory().size(); i++) {
                        adapter.getData().add(reportDetail.getData().getVisitHistory().get(i));
                        adapter.notifyItemInserted(adapter.getData().size() - 1);
                    }

                    adapter.notifyDataSetChanged();
                }

                initMaps(reportDetail);
            }else {
                ToastUtils.showToast("Data tidak ditemukan");
            }

        }
    }

    private void initMaps(ReportDetail reportDetail) {

        if (reportDetail.getData().getStartLatitude() != 0.0 && reportDetail.getData().getStartLongitude() != 0.0
                && reportDetail.getData().getEndLatitude() != 0.0 && reportDetail.getData().getEndLongitude() != 0.0){
            geocoder = new Geocoder(getActivity(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(reportDetail.getData().getStartLatitude(),
                        reportDetail.getData().getStartLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                destiny = geocoder.getFromLocation(reportDetail.getData().getEndLatitude(),
                        reportDetail.getData().getEndLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses != null){
                addres = addresses.get(0).getAddressLine(0);
                dest = destiny.get(0).getAddressLine(0);

                GetDirection();
            }
        }

    }

    private void drawDirection() {
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 14));

            originMarkers.add(mMap.addMarker(new MarkerOptions()
//                    .icon(getMarkerIcon("#0000FF"))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_ktb))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(ContextCompat.getColor(getActivity(), R.color.colorPrimary)).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }

    private void GetDirection() {
        progressLoading.setVisibility(View.VISIBLE);

        String origin = "";
        String destiny = "";

        try {
            origin = URLEncoder.encode(addres, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            destiny = URLEncoder.encode(dest, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ApiServiceDirection apiServiceDirection = ServiceGeneratorGoogleDirection.createService(ApiServiceDirection.class, getActivity());

        Call<ModelGoogleDirection> call = apiServiceDirection.getDirection(origin, destiny, "AIzaSyDvLObwEeXxfdSaRO-E3-7oeWoDLFs-2Gc");
        call.enqueue(new Callback<ModelGoogleDirection>() {
            @Override
            public void onResponse(Call<ModelGoogleDirection> call, Response<ModelGoogleDirection> response) {
                progressLoading.setVisibility(View.GONE);

                if (response.body().getStatus().equals("OK")) {

                    routes = new ArrayList<>();

                    for (int i = 0; i < response.body().getRoutes().size(); i++) {
                        Route route = new Route();

                        route.distance = new Distance(response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText(),
                                response.body().getRoutes().get(i).getLegs().get(i).getDistance().getValue());
                        route.duration = new Duration(response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText(),
                                response.body().getRoutes().get(i).getLegs().get(i).getDuration().getValue());
                        route.endAddress = response.body().getRoutes().get(i).getLegs().get(i).getEnd_address();
                        route.startAddress = response.body().getRoutes().get(i).getLegs().get(i).getStart_address();
                        route.startLocation = new LatLng(response.body().getRoutes().get(i).getLegs().get(i).getStart_location().getLat(),
                                response.body().getRoutes().get(i).getLegs().get(i).getStart_location().getLng());
                        route.endLocation = new LatLng(response.body().getRoutes().get(i).getLegs().get(i).getEnd_location().getLat(),
                                response.body().getRoutes().get(i).getLegs().get(i).getEnd_location().getLng());
                        route.points = decodePolyLine(response.body().getRoutes().get(i).getOverview_polyline().getPoints());

                        routes.add(route);

                    }

                    try {
                        drawDirection();
                    } catch (Exception e) {

                    }

                }

            }

            @Override
            public void onFailure(Call<ModelGoogleDirection> call, Throwable t) {

            }
        });

    }

    private BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (reportDetail.getData() != null){
            for (int i = 0; i < reportDetail.getData().getLocationHistory().size(); i++) {
                double latitude = reportDetail.getData().getLocationHistory().get(i).getLatitude();
                double longitude = reportDetail.getData().getLocationHistory().get(i).getLongitude();

                LatLng location = new LatLng(latitude, longitude);
                googleMap.addMarker(new MarkerOptions().position(location));
            }
        }

    }
}
