package com.radya.sfa.data.source.remote;

import com.google.gson.JsonObject;
import com.radya.sfa.Constant;
import com.radya.sfa.view.agent.AgentList;
import com.radya.sfa.view.assignment.detail.AssignmentDetail;
import com.radya.sfa.view.assignment.detail.failed.AssignmentFailedReason;
import com.radya.sfa.view.assignment.list.AssignmentAll;
import com.radya.sfa.view.assignment.list.AssignmentList;
import com.radya.sfa.view.assignment.product.AssignmentProduct;
import com.radya.sfa.view.auth.Login;
import com.radya.sfa.view.contact.list.ContactList;
import com.radya.sfa.view.dashboard.Attendance;
import com.radya.sfa.view.dashboard.Dashboard;
import com.radya.sfa.view.dashboard.news.DashboardNews;
import com.radya.sfa.view.invoice.verification.InvoiceVerification;
import com.radya.sfa.view.report.ReportDetail;
import com.radya.sfa.view.report.ReportList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by aderifaldi on 08/08/2016.
 */
public interface ApiService {

    //ASSIGNMENT
    @GET(Constant.Api.EndPoint.Assignment.LIST)
    @Headers("Content-Type: application/json")
    Call<AssignmentList> GetAssignmentList(@Query(Constant.Api.Params.STATUS) int status,
                                           @Query(Constant.Api.Params.FROM) String form,
                                           @Query(Constant.Api.Params.TO) String to,
                                           @Query(Constant.Api.Params.LIMIT) int limit,
                                           @Query(Constant.Api.Params.OFFSET) int offset);

    @GET(Constant.Api.EndPoint.Assignment.CALENDAR)
    @Headers("Content-Type: application/json")
    Call<AssignmentList> GetAssignmentByCalendar(@Path(Constant.Api.Params.FROM) String date,
                                                 @Path(Constant.Api.Params.TO) String to,
                                                 @Query(Constant.Api.Params.LIMIT) int limit,
                                                 @Query(Constant.Api.Params.OFFSET) int offset);

    @GET(Constant.Api.EndPoint.Assignment.DETAIL)
    @Headers("Content-Type: application/json")
    Call<AssignmentDetail> GetAssignmentDetail(@Path(Constant.Api.Params.ID) String id);

    @GET(Constant.Api.EndPoint.Assignment.REASON)
    @Headers("Content-Type: application/json")
    Call<AssignmentFailedReason> GetAssignmentReason();

    @GET(Constant.Api.EndPoint.Assignment.ALL)
    @Headers("Content-Type: application/json")
    Call<AssignmentAll> GetAssignmentAll(@Query("from") String from,
                                         @Query("to") String to,
                                         @Query("status") int status);

    @GET(Constant.Api.EndPoint.User.Agent.LIST)
    @Headers("Content-Type: application/json")
    Call<AgentList> GetAgentList(@Query(Constant.Api.Params.ROLE) String role);

    @POST(Constant.Api.EndPoint.Assignment.SEARCH)
    @Headers("Content-Type: application/json")
    Call<AssignmentList> SearchAssignment(@Body JsonObject jsonObject,
                                          @Query(Constant.Api.Params.STATUS) int status,
                                          @Query(Constant.Api.Params.LIMIT) int limit,
                                          @Query(Constant.Api.Params.OFFSET) int offset);

    @POST(Constant.Api.EndPoint.Assignment.START)
    @Headers("Content-Type: application/json")
    Call<BaseModel> AssignmentStart(@Path(Constant.Api.Params.ASSIGNMENT_ID) String id, @Body JsonObject jsonObject, @Query("is_online") boolean is_online);

    @Multipart
    @POST(Constant.Api.EndPoint.Assignment.COMPLETE)
    Call<BaseModel> AssignmentComplete(@Path(Constant.Api.Params.ASSIGNMENT_ID) String assignment_id,
                                       @Part(Constant.Api.Params.LATITUDE_2) RequestBody Latitude,
                                       @Part(Constant.Api.Params.LONGITUDE_2) RequestBody Longitude,
                                       @Part(Constant.Api.Params.PROSESSED_TIME) RequestBody ProcessedTime,
                                       @Part(Constant.Api.Params.ASSIGNMENT_STATUS) RequestBody AssignmentStatus,
                                       @Query("is_online") boolean is_online);

    @Multipart
    @POST(Constant.Api.EndPoint.Assignment.COMPLETE)
    Call<BaseModel> AssignmentFailed(@Path(Constant.Api.Params.ASSIGNMENT_ID) String assignment_id,
                                     @Part(Constant.Api.Params.LATITUDE_2) RequestBody Latitude,
                                     @Part(Constant.Api.Params.LONGITUDE_2) RequestBody Longitude,
                                     @Part(Constant.Api.Params.ASSIGNMENT_STATUS) RequestBody AssignmentStatus,
                                     @Part(Constant.Api.Params.REASON_CODE) RequestBody ReasonCode,
                                     @Part MultipartBody.Part file,
                                     @Part(Constant.Api.Params.REMARKS_2) RequestBody ReasonPlus);

    @Multipart
    @POST(Constant.Api.EndPoint.Assignment.GASOLINE)
    Call<BaseModel> AssignmentGasoline(@Query(Constant.Api.Params.TYPE) int type,
                                       @Part(Constant.Api.Params.ADDRESS) RequestBody Address,
                                       @Part(Constant.Api.Params.REMARKS_2) RequestBody Remarks,
                                       @Part(Constant.Api.Params.LITER) RequestBody Liter,
                                       @Part(Constant.Api.Params.LATITUDE_2) RequestBody Latitude,
                                       @Part(Constant.Api.Params.LONGITUDE_2) RequestBody Longitude,
                                       @Part(Constant.Api.Params.EVENTDATE) RequestBody EventDate,
                                       @Part MultipartBody.Part Attachment);

    //AUTH
    @POST(Constant.Api.EndPoint.Auth.LOGIN)
    @Headers("Content-Type: application/json")
    Call<Login> Login(@Body JsonObject jsonPost);

    @POST(Constant.Api.EndPoint.Auth.LOGOUT)
    @Headers("Content-Type: application/json")
    Call<BaseModel> Logout();

    //CONTACT
    @POST(Constant.Api.EndPoint.Contact.LIST)
    @Headers("Content-Type: application/json")
    Call<ContactList> GetContactList(@Body JsonObject jsonObject,
                                     @Query(Constant.Api.Params.LIMIT) int limit,
                                     @Query(Constant.Api.Params.OFFSET) int offset);

    @Multipart
    @POST(Constant.Api.EndPoint.Contact.ADD)
    Call<BaseModel> ContactAdd(@Part(Constant.Api.Params.STORE_NAME) RequestBody StoreName,
                               @Part(Constant.Api.Params.STORE_ADDRESS) RequestBody StoreAddress,
                               @Part(Constant.Api.Params.STORE_CITY) RequestBody StoreCity,
                               @Part(Constant.Api.Params.STORE_DISTRICT) RequestBody StoreDistrict,
                               @Part(Constant.Api.Params.STORE_VILLAGE) RequestBody StoreVillage,
                               @Part(Constant.Api.Params.STORE_STATUS) RequestBody StoreStatus,
                               @Part(Constant.Api.Params.STORE_AGE) RequestBody StoreAge,
                               @Part(Constant.Api.Params.STORE_TYPE) RequestBody StoreType,
                               @Part(Constant.Api.Params.WIDTH_ROAD) RequestBody WidthRoad,
                               @Part(Constant.Api.Params.BRANDING_NAME) RequestBody BrandingName,
                               @Part(Constant.Api.Params.STORE_LATITUDE) RequestBody StoreLatitude,
                               @Part(Constant.Api.Params.STORE_LONGITUDE) RequestBody StoreLongitude,
                               @Part(Constant.Api.Params.NOTE) RequestBody Note,
                               @Part(Constant.Api.Params.OWNER_NAME) RequestBody OwnerName,
                               @Part(Constant.Api.Params.OWNER_ADDRESS) RequestBody OwnerAddress,
                               @Part(Constant.Api.Params.OWNER_CITY) RequestBody OwnerCity,
                               @Part(Constant.Api.Params.OWNER_DISTRICT) RequestBody OwnerDistrict,
                               @Part(Constant.Api.Params.OWNER_VILLAGE) RequestBody OwnerVillage,
                               @Part(Constant.Api.Params.OWNER_PHONE) RequestBody OwnerPhoneNumber,
                               @Part(Constant.Api.Params.PIC_NAME) RequestBody PICName,
                               @Part(Constant.Api.Params.PIC_PHONE) RequestBody PICPhoneNumber,
                               @Part MultipartBody.Part PhotoIdCard,
                               @Part MultipartBody.Part PhotoNPWP,
                               @Part MultipartBody.Part BrandingPhoto,
                               @Part MultipartBody.Part StorePhoto);

    //ATTENDANCE
    @POST(Constant.Api.EndPoint.Attendance.CHECK)
    @Headers("Content-Type: application/json")
    Call<Attendance> CheckAttendance(@Query(Constant.Api.Params.STATUS) int status);

    @Multipart
    @POST(Constant.Api.EndPoint.Attendance.CHECK_IN)
    Call<BaseModel> CheckIn(@Part("Latitude") RequestBody Latitude,
                            @Part("Longitude") RequestBody Longitude,
                            @Part("StartTime") RequestBody StartTime,
                            @Part("UserId") RequestBody UserId,
                            @Part("Mode") RequestBody Mode,
                            @Part MultipartBody.Part Attachment);

    @Multipart
    @POST(Constant.Api.EndPoint.Attendance.CHECK_IN)
    Call<BaseModel> CheckIn2(@Part("Latitude") RequestBody Latitude,
                             @Part("Longitude") RequestBody Longitude,
                             @Part("StartTime") RequestBody StartTime,
                             @Part("UserId") RequestBody UserId,
                             @Part("Mode") RequestBody Mode,
                             @Part("Distance") RequestBody Distance,
                             @Part MultipartBody.Part Attachment);

    @Multipart
    @POST(Constant.Api.EndPoint.Attendance.CHECK_OUT)
    Call<BaseModel> CheckOut(@Part("Latitude") RequestBody Latitude,
                             @Part("Longitude") RequestBody Longitude,
                             @Part("EndTime") RequestBody EndTime,
                             @Part("UserId") RequestBody UserId,
                             @Part("Mode") RequestBody Mode,
                             @Part("Distance") RequestBody Distance,
                             @Part MultipartBody.Part Attachment);

    //INVOICE
    @POST(Constant.Api.EndPoint.Invoice.OTP)
    @Headers("Content-Type: application/json")
    Call<InvoiceVerification> RequestOTP(@Path(Constant.Api.Params.ASSIGNMENT_ID) String assignment_id,
                                         @Body JsonObject jsonObject);

    @Multipart
    @POST(Constant.Api.EndPoint.Invoice.PAYMENT)
    Call<BaseModel> InvoicePayment(@Part(Constant.Api.Params.ASSIGNMENT_ID_2) RequestBody AssignmentId,
                                   @Part(Constant.Api.Params.INVOICE_CODE) RequestBody InvoiceCode,
                                   @Part(Constant.Api.Params.INVOICE_AMOUNT) RequestBody InvoiceAmount,
                                   @Part(Constant.Api.Params.PAYMENT_AMOUNT) RequestBody PaymentAmount,
                                   @Part(Constant.Api.Params.PAYMENT_DEBT) RequestBody PaymentDebt,
                                   @Part(Constant.Api.Params.PAYMENT_CHANNEL) RequestBody PaymentChannel,
                                   @Part(Constant.Api.Params.TRANSFER_AMOUNT) RequestBody TransferAmount,
                                   @Part(Constant.Api.Params.GIRO_AMOUNT) RequestBody GiroAmount,
                                   @Part(Constant.Api.Params.CASH_AMOUNT) RequestBody CashAmount,
                                   @Part(Constant.Api.Params.OTP) RequestBody Otp,
                                   @Part(Constant.Api.Params.TRANSFER_BANK) RequestBody TransferBank,
                                   @Part MultipartBody.Part GiroAttachment,
                                   @Part(Constant.Api.Params.GIRO_PHOTO) RequestBody GiroPhoto,
                                   @Part(Constant.Api.Params.GIRO_NUMBER) RequestBody GiroNumber,
                                   @Part(Constant.Api.Params.GIRO_DUE_DATE) RequestBody GiroDueDate,
                                   @Part(Constant.Api.Params.GIRO_NUMBER1) RequestBody GiroNumber1,
                                   @Part(Constant.Api.Params.GIRO_PHOTO1) RequestBody GiroPhoto1,
                                   @Part(Constant.Api.Params.GIRO_AMOUNT1) RequestBody GiroAmount1,
                                   @Part(Constant.Api.Params.GIRO_DUE_DATE1) RequestBody GiroDueDate1,
                                   @Part(Constant.Api.Params.GIRO_NUMBER2) RequestBody GiroNumber2,
                                   @Part(Constant.Api.Params.GIRO_PHOTO2) RequestBody GiroPhoto2,
                                   @Part(Constant.Api.Params.GIRO_AMOUNT2) RequestBody GiroAmount2,
                                   @Part(Constant.Api.Params.GIRO_DUE_DATE2) RequestBody GiroDueDate2,
                                   @Part(Constant.Api.Params.GIRO_NUMBER3) RequestBody GiroNumber3,
                                   @Part(Constant.Api.Params.GIRO_PHOTO3) RequestBody GiroPhoto3,
                                   @Part(Constant.Api.Params.GIRO_AMOUNT3) RequestBody GiroAmount3,
                                   @Part(Constant.Api.Params.GIRO_DUE_DATE3) RequestBody GiroDueDate3,
                                   @Part(Constant.Api.Params.GIRO_NUMBER4) RequestBody GiroNumber4,
                                   @Part(Constant.Api.Params.GIRO_PHOTO4) RequestBody GiroPhoto4,
                                   @Part(Constant.Api.Params.GIRO_AMOUNT4) RequestBody GiroAmount4,
                                   @Part(Constant.Api.Params.GIRO_DUE_DATE4) RequestBody GiroDueDate4,
                                   @Part MultipartBody.Part GiroAttachment1,
                                   @Part MultipartBody.Part GiroAttachment2,
                                   @Part MultipartBody.Part GiroAttachment3,
                                   @Part MultipartBody.Part GiroAttachment4

    );

    //PRODUCT
    @GET(Constant.Api.EndPoint.Product.LIST)
    @Headers("Content-Type: application/json")
    Call<AssignmentProduct> GetProductList();

    @POST(Constant.Api.EndPoint.Product.ORDER)
    @Headers("Content-Type: application/json")
    Call<BaseModel> ProductOrder(@Body JsonObject jsonObject);

    //NEWS
    @GET(Constant.Api.EndPoint.News.LIST)
    @Headers("Content-Type: application/json")
    Call<DashboardNews> GetNewsList(@Query(Constant.Api.Params.LIMIT) int limit,
                                    @Query(Constant.Api.Params.OFFSET) int offset);

    //DASHBOARD
    @GET(Constant.Api.EndPoint.Dashboard.DASHBOARD)
    @Headers("Content-Type: application/json")
    Call<Dashboard> Dashboard(@Query(Constant.Api.Params.DATE) String date);

    //ACTIVITY
    @POST(Constant.Api.EndPoint.User.ACTIVITY)
    @Headers("Content-Type: application/json")
    Call<BaseModel> UpdateLocation(@Body JsonObject jsonObject);

    //REPORT
    @GET(Constant.Api.EndPoint.Report.LIST)
    @Headers("Content-Type: application/json")
    Call<ReportList> ReportList(@Query(Constant.Api.Params.DATE) String date);

    @GET(Constant.Api.EndPoint.Report.DETAIL)
    @Headers("Content-Type: application/json")
    Call<ReportDetail> ReportDetail(@Path(Constant.Api.Params.ID) String id, @Query(Constant.Api.Params.DATE) String date);

}
