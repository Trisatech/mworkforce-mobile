package com.radya.sfa.view.contact.add;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.radya.sfa.Constant;
import com.radya.sfa.MyApplication;
import com.radya.sfa.R;
import com.radya.sfa.data.source.remote.ApiResponse;
import com.radya.sfa.data.source.remote.BaseModel;
import com.radya.sfa.util.ImageUtils;
import com.radya.sfa.util.LocationUtils;
import com.radya.sfa.util.NetworkUtils;
import com.radya.sfa.util.RequestBodyUtils;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.contact.ContactViewModel;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by aderifaldi on 2018-02-06.
 */

public class ContactAddFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.btnAdd)
    RelativeLayout btnAdd;
    @BindView(R.id.edtStoreName)
    EditText edtStoreName;
    @BindView(R.id.edtStoreAddress)
    EditText edtStoreAddress;
    @BindView(R.id.edtKelurahan)
    EditText edtKelurahan;
    @BindView(R.id.edtKecamatan)
    EditText edtKecamatan;
    @BindView(R.id.edtKabupaten)
    EditText edtKabupaten;
    @BindView(R.id.txtStoreStatus)
    TextView txtStoreStatus;
    @BindView(R.id.txtStoreType)
    TextView txtStoreType;
    @BindView(R.id.edtYearOfStore)
    EditText edtYearOfStore;
    @BindView(R.id.edtBannerStore)
    EditText edtBannerStore;
    @BindView(R.id.edtLebarJalan)
    EditText edtLebarJalan;
    @BindView(R.id.edtStoreNote)
    EditText edtStoreNote;
    @BindView(R.id.imgStorePhoto)
    ImageView imgStorePhoto;
    @BindView(R.id.addPhotoStore)
    ImageView addPhotoStore;
    @BindView(R.id.storePhoto)
    RelativeLayout storePhoto;
    @BindView(R.id.edtOwnerName)
    EditText edtOwnerName;
    @BindView(R.id.edtOwnerPhone)
    EditText edtOwnerPhone;
    @BindView(R.id.edtOwnerAddress)
    EditText edtOwnerAddress;
    @BindView(R.id.edtOwnerKelurahan)
    EditText edtOwnerKelurahan;
    @BindView(R.id.edtOwnerKecamatan)
    EditText edtOwnerKecamatan;
    @BindView(R.id.edtOwnerKabupaten)
    EditText edtOwnerKabupaten;
    @BindView(R.id.edtPenanggungJawabName)
    EditText edtPenanggungJawabName;
    @BindView(R.id.edtPenanggungJawabPhone)
    EditText edtPenanggungJawabPhone;
    @BindView(R.id.edtKTP)
    EditText edtKTP;
    @BindView(R.id.imgKTPPhoto)
    ImageView imgKTPPhoto;
    @BindView(R.id.addKTPPhoto)
    ImageView addKTPPhoto;
    @BindView(R.id.ktpPhoto)
    RelativeLayout ktpPhoto;
    @BindView(R.id.edtNPWP)
    EditText edtNPWP;
    @BindView(R.id.imgNPWPPhoto)
    ImageView imgNPWPPhoto;
    @BindView(R.id.addNPWPPhoto)
    ImageView addNPWPPhoto;
    @BindView(R.id.npwpPhoto)
    RelativeLayout npwpPhoto;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;
    @BindView(R.id.imgBannerStorePhoto)
    ImageView imgBannerStorePhoto;
    @BindView(R.id.addBannerPhotoStore)
    ImageView addBannerPhotoStore;
    @BindView(R.id.containerBody)
    RelativeLayout containerBody;
    @BindView(R.id.containerSuccess)
    FrameLayout containerSuccess;

    private File photoStore, photoKTP, photoNPWP, photoBannerStore;
    private Location currentLocation;

    private ContactViewModel viewModel;

    public ContactAddFragment() {
        // Requires empty public constructor
    }

    public static ContactAddFragment newInstance() {
        return new ContactAddFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_add_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initCurrentLocation();
        initViewModel();
        return view;
    }

    private void initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
    }

    private void initCurrentLocation() {
        LocationUtils locationUtils = new LocationUtils(getActivity());
        locationUtils.setCurrentLocation();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btnAdd, R.id.txtStoreStatus, R.id.txtStoreType, R.id.imgStorePhoto,
            R.id.addPhotoStore, R.id.imgKTPPhoto, R.id.addKTPPhoto, R.id.imgNPWPPhoto,
            R.id.addNPWPPhoto, R.id.imgBannerStorePhoto, R.id.addBannerPhotoStore,
            R.id.icStatus, R.id.icType})
    public void onViewClicked(View view) {

        StoreStatusDialog storeStatusDialog = new StoreStatusDialog();
        StoreTypeDialog storeTypeDialog = new StoreTypeDialog();

        switch (view.getId()) {
            case R.id.btnAdd:

                String storeName = edtStoreName.getText().toString();
                String storeAddress = edtStoreAddress.getText().toString();
                String storeKelurahan = edtKelurahan.getText().toString();
                String storeKecamatan = edtKecamatan.getText().toString();
                String storeKabupaten = edtKabupaten.getText().toString();
                String storeYear = edtYearOfStore.getText().toString();
                String storeBanner = edtBannerStore.getText().toString();
                String storeLebarJalan = edtLebarJalan.getText().toString();
                String storeNote = edtStoreNote.getText().toString();

                String ownerName = edtOwnerName.getText().toString();
                String ownerPhone = edtOwnerPhone.getText().toString();
                String ownerAddress = edtOwnerAddress.getText().toString();
                String ownerKelurahan = edtOwnerKelurahan.getText().toString();
                String ownerKecamatan = edtOwnerKecamatan.getText().toString();
                String ownerKabupaten = edtOwnerKabupaten.getText().toString();

                String picName = edtPenanggungJawabName.getText().toString();
                String picPhone = edtPenanggungJawabPhone.getText().toString();
                String picKTPNumber = edtKTP.getText().toString();
                String picNPWPNumber = edtNPWP.getText().toString();

                if (TextUtils.isEmpty(storeName)){
                    edtStoreName.setError(getString(R.string.alertMessageStoreNameEmpty));
                } else if (TextUtils.isEmpty(storeAddress)){
                    edtStoreAddress.setError(getString(R.string.alertMessageStoreAddressEmpty));
                } else if (TextUtils.isEmpty(storeKelurahan)){
                    edtKelurahan.setError(getString(R.string.alertMessageStoreKelurahanEmpty));
                } else if (TextUtils.isEmpty(storeKecamatan)){
                    edtKecamatan.setError(getString(R.string.alertMessageStoreKecamatanEmpty));
                } else if (TextUtils.isEmpty(storeKabupaten)){
                    edtKabupaten.setError(getString(R.string.alertMessageStoreKabupatenEmpty));
                } else if (TextUtils.isEmpty(txtStoreStatus.getText().toString())){
                    ToastUtils.showToast(getString(R.string.alertMessageStoreStatusEmpty));
                } else if (TextUtils.isEmpty(txtStoreType.getText().toString())){
                    ToastUtils.showToast(getString(R.string.alertMessageStoreTypeEmpty));
                } else if (TextUtils.isEmpty(storeYear)){
                    edtYearOfStore.setError(getString(R.string.alertMessageStoreYearEmpty));
                } else if (TextUtils.isEmpty(storeBanner)){
                    edtBannerStore.setError(getString(R.string.alertMessageBannerEmpty));
                } else if (photoStore == null){
                    ToastUtils.showToast(getString(R.string.alertMessageStorePhotoEmpty));
                } else if (photoBannerStore == null){
                    ToastUtils.showToast(getString(R.string.alertMessageBannerStorePhotoEmpty));
                } else if (photoKTP == null){
                    ToastUtils.showToast(getString(R.string.alertMessageKTPPhotoEmpty));
                } else if (photoNPWP == null){
                    ToastUtils.showToast(getString(R.string.alertMessageNPWPPhotoEmpty));
                }else {
                    RequestBody StoreName = RequestBodyUtils.getTextPlain(storeName);
                    RequestBody StoreAddress = RequestBodyUtils.getTextPlain(storeAddress);
                    RequestBody StoreCity = RequestBodyUtils.getTextPlain(storeKabupaten);
                    RequestBody StoreDistrict = RequestBodyUtils.getTextPlain(storeKecamatan);
                    RequestBody StoreVillage = RequestBodyUtils.getTextPlain(storeKelurahan);
                    RequestBody StoreStatus = RequestBodyUtils.getTextPlain(txtStoreStatus.getText().toString());
                    RequestBody StoreAge = RequestBodyUtils.getTextPlain(storeYear);
                    RequestBody StoreType = RequestBodyUtils.getTextPlain(txtStoreType.getText().toString());
                    RequestBody WidthRoad = RequestBodyUtils.getTextPlain(storeLebarJalan);
                    RequestBody BrandingName = RequestBodyUtils.getTextPlain(storeBanner);
                    RequestBody StoreLatitude = RequestBodyUtils.getTextPlain("" + MyApplication.getInstance().latitude());
                    RequestBody StoreLongitude = RequestBodyUtils.getTextPlain("" + MyApplication.getInstance().longitude());
                    RequestBody Note = RequestBodyUtils.getTextPlain(storeNote);
                    RequestBody OwnerName = RequestBodyUtils.getTextPlain(ownerName);
                    RequestBody OwnerAddress = RequestBodyUtils.getTextPlain(ownerAddress);
                    RequestBody OwnerCity = RequestBodyUtils.getTextPlain(ownerKabupaten);
                    RequestBody OwnerDistrict = RequestBodyUtils.getTextPlain(ownerKecamatan);
                    RequestBody OwnerVillage = RequestBodyUtils.getTextPlain(ownerKelurahan);
                    RequestBody OwnerPhoneNumber = RequestBodyUtils.getTextPlain(ownerPhone);
                    RequestBody PICName = RequestBodyUtils.getTextPlain(picName);
                    RequestBody PICPhoneNumber = RequestBodyUtils.getTextPlain(picPhone);

                    //STORE PHOTO
                    RequestBody requestFileStorePhoto;
                    MultipartBody.Part StorePhoto;

                    File storePhotoFile = photoStore;
                    if (storePhotoFile == null) {
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.null_image);
                        storePhotoFile = ImageUtils.persistImage(getActivity(), bm, "null_image");
                    }

                    requestFileStorePhoto = RequestBodyUtils.getImage(storePhotoFile);
                    StorePhoto = MultipartBody.Part.createFormData(Constant.Api.Params.STORE_PHOTO, storePhotoFile.getName(), requestFileStorePhoto);

                    //KTP PHOTO
                    RequestBody requestFileKTPPhoto;
                    MultipartBody.Part PhotoIdCard;

                    File ktpPhotoFile = photoKTP;
                    if (ktpPhotoFile == null) {
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.null_image);
                        ktpPhotoFile = ImageUtils.persistImage(getActivity(), bm, "null_image");
                    }

                    requestFileKTPPhoto = RequestBodyUtils.getImage(ktpPhotoFile);
                    PhotoIdCard = MultipartBody.Part.createFormData(Constant.Api.Params.PHOTO_ID_CARD, storePhotoFile.getName(), requestFileKTPPhoto);

                    //NPWP PHOTO
                    RequestBody requestFileNPWPPhoto;
                    MultipartBody.Part PhotoNPWP;

                    File npwpPhotoFile = photoNPWP;
                    if (npwpPhotoFile == null) {
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.null_image);
                        npwpPhotoFile = ImageUtils.persistImage(getActivity(), bm, "null_image");
                    }

                    requestFileNPWPPhoto = RequestBodyUtils.getImage(npwpPhotoFile);
                    PhotoNPWP = MultipartBody.Part.createFormData(Constant.Api.Params.PHOTO_NPWP, storePhotoFile.getName(), requestFileNPWPPhoto);

                    //BANNER PHOTO
                    RequestBody requestFileBannerPhoto;
                    MultipartBody.Part BrandingPhoto;

                    File brandingPhotoFile = photoBannerStore;
                    if (brandingPhotoFile == null) {
                        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.null_image);
                        brandingPhotoFile = ImageUtils.persistImage(getActivity(), bm, "null_image");
                    }

                    requestFileBannerPhoto = RequestBodyUtils.getImage(brandingPhotoFile);
                    BrandingPhoto = MultipartBody.Part.createFormData(Constant.Api.Params.PHOTO_BRANDING, storePhotoFile.getName(), requestFileBannerPhoto);

                    progressLoading.setVisibility(View.VISIBLE);
                    btnAdd.setEnabled(false);

                    viewModel.contactAdd(NetworkUtils.getConnectionManager(), StoreName, StoreAddress,
                            StoreCity, StoreDistrict, StoreVillage, StoreStatus, StoreAge, StoreType, WidthRoad, BrandingName,
                            StoreLatitude, StoreLongitude, Note, OwnerName, OwnerAddress, OwnerCity, OwnerDistrict,
                            OwnerVillage, OwnerPhoneNumber, PICName, PICPhoneNumber, PhotoIdCard, PhotoNPWP,
                            BrandingPhoto, StorePhoto);
                    viewModel.getContactAddResponse().observe(this, new Observer<ApiResponse>() {
                        @Override
                        public void onChanged(@Nullable ApiResponse apiResponse) {
                            progressLoading.setVisibility(View.GONE);
                            btnAdd.setEnabled(true);
                            BaseModel response = (BaseModel) apiResponse.getData();
                            if (response != null) {
                                ToastUtils.showToast(response.getAlert().getMessage());

                                containerBody.setVisibility(View.GONE);
                                containerSuccess.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }

                break;
            case R.id.txtStoreStatus:
                storeStatusDialog.show(getFragmentManager(), "");
                break;
            case R.id.txtStoreType:
                storeTypeDialog.show(getFragmentManager(), "");
                break;
            case R.id.icStatus:
                storeStatusDialog.show(getFragmentManager(), "");
                break;
            case R.id.icType:
                storeTypeDialog.show(getFragmentManager(), "");
                break;
            case R.id.imgStorePhoto:
                ((ContactAddActivity) getActivity()).launchCamera(0);
                break;
            case R.id.addPhotoStore:
                ((ContactAddActivity) getActivity()).launchCamera(0);
                break;
            case R.id.imgKTPPhoto:
                ((ContactAddActivity) getActivity()).launchCamera(1);
                break;
            case R.id.addKTPPhoto:
                ((ContactAddActivity) getActivity()).launchCamera(1);
                break;
            case R.id.imgNPWPPhoto:
                ((ContactAddActivity) getActivity()).launchCamera(2);
                break;
            case R.id.addNPWPPhoto:
                ((ContactAddActivity) getActivity()).launchCamera(2);
                break;
            case R.id.imgBannerStorePhoto:
                ((ContactAddActivity) getActivity()).launchCamera(3);
                break;
            case R.id.addBannerPhotoStore:
                ((ContactAddActivity) getActivity()).launchCamera(3);
                break;
        }
    }

    public void setStatus(String status) {
        txtStoreStatus.setText(status);
    }

    public void setType(String type) {
        txtStoreType.setText(type);
    }

    public void setImageCaptured(int photoFor, File imgFile, Bitmap imgBitmap) {
        if (photoFor == 0) {
            photoStore = imgFile;
            imgStorePhoto.setImageBitmap(imgBitmap);
            addPhotoStore.setVisibility(View.GONE);
        } else if (photoFor == 1) {
            photoKTP = imgFile;
            imgKTPPhoto.setImageBitmap(imgBitmap);
            addKTPPhoto.setVisibility(View.GONE);
        } else if (photoFor == 2) {
            photoNPWP = imgFile;
            imgNPWPPhoto.setImageBitmap(imgBitmap);
            addNPWPPhoto.setVisibility(View.GONE);
        } else if (photoFor == 3) {
            photoBannerStore = imgFile;
            imgBannerStorePhoto.setImageBitmap(imgBitmap);
            addBannerPhotoStore.setVisibility(View.GONE);
        }
    }
}
