package com.radya.sfa.view.report;

import com.google.gson.annotations.SerializedName;
import com.radya.sfa.data.source.remote.BaseModel;

import java.io.Serializable;
import java.util.List;

public class ReportDetail extends BaseModel implements Serializable{

    @SerializedName("data")
    private ReportDetailData data;

    public ReportDetailData getData() {
        return data;
    }

    public static class ReportDetailData implements Serializable{


        /**
         * user_id : string
         * user_code : string
         * role_name : string
         * user_name : string
         * name : string
         * user_email : string
         * user_phone : string
         * total_km : 0
         * start_latitude : 0
         * end_latitude : 0
         * start_longitude : 0
         * end_longitude : 0
         * start_time_utc : 2019-04-26T15:15:52.953Z
         * end_time_utc : 2019-04-26T15:15:52.953Z
         * start_time : 2019-04-26T15:15:52.953Z
         * end_time : 2019-04-26T15:15:52.953Z
         * total_task : 0
         * total_task_completed : 0
         * total_work_time : 0
         * lost_time : 0
         * total_loss_time : 0
         * total_time_at_store : 0
         * visit_history : [{"customer_name":"string","task_name":"string","started_time_utc":"2019-04-26T15:15:52.953Z","started_time":"2019-04-26T15:15:52.953Z","ended_time_utc":"2019-04-26T15:15:52.953Z","ended_time":"2019-04-26T15:15:52.953Z","status":"string","total_invoice":0,"invoice_amount":0,"is_verified":true,"loss_time":0,"lossTime":0,"sales_time":0,"salesTime":0}]
         * location_history : [{"label":"string","latitude":0,"longitude":0,"status":"string","is_active":true,"startTime":"2019-04-26T15:15:52.953Z","checkin_date":"string","googleTime":0,"salesTime":0,"google_time":0,"sales_time":0}]
         */

        @SerializedName("user_id")
        private String userId;
        @SerializedName("user_code")
        private String userCode;
        @SerializedName("role_name")
        private String roleName;
        @SerializedName("user_name")
        private String userName;
        @SerializedName("name")
        private String name;
        @SerializedName("user_email")
        private String userEmail;
        @SerializedName("user_phone")
        private String userPhone;
        @SerializedName("total_km")
        private int totalKm;
        @SerializedName("start_latitude")
        private double startLatitude;
        @SerializedName("end_latitude")
        private double endLatitude;
        @SerializedName("start_longitude")
        private double startLongitude;
        @SerializedName("end_longitude")
        private double endLongitude;
        @SerializedName("start_time_utc")
        private String startTimeUtc;
        @SerializedName("end_time_utc")
        private String endTimeUtc;
        @SerializedName("start_time")
        private String startTime;
        @SerializedName("end_time")
        private String endTime;
        @SerializedName("total_task")
        private int totalTask;
        @SerializedName("total_task_completed")
        private int totalTaskCompleted;
        @SerializedName("total_work_time")
        private double totalWorkTime;
        @SerializedName("lost_time")
        private double lostTime;
        @SerializedName("total_loss_time")
        private double totalLossTime;
        @SerializedName("total_time_at_store")
        private double totalTimeAtStore;
        @SerializedName("visit_history")
        private List<VisitHistory> visitHistory;
        @SerializedName("location_history")
        private List<LocationHistory> locationHistory;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public int getTotalKm() {
            return totalKm;
        }

        public void setTotalKm(int totalKm) {
            this.totalKm = totalKm;
        }

        public double getStartLatitude() {
            return startLatitude;
        }

        public void setStartLatitude(int startLatitude) {
            this.startLatitude = startLatitude;
        }

        public double getEndLatitude() {
            return endLatitude;
        }

        public void setEndLatitude(int endLatitude) {
            this.endLatitude = endLatitude;
        }

        public double getStartLongitude() {
            return startLongitude;
        }

        public void setStartLongitude(int startLongitude) {
            this.startLongitude = startLongitude;
        }

        public double getEndLongitude() {
            return endLongitude;
        }

        public void setEndLongitude(int endLongitude) {
            this.endLongitude = endLongitude;
        }

        public String getStartTimeUtc() {
            return startTimeUtc;
        }

        public void setStartTimeUtc(String startTimeUtc) {
            this.startTimeUtc = startTimeUtc;
        }

        public String getEndTimeUtc() {
            return endTimeUtc;
        }

        public void setEndTimeUtc(String endTimeUtc) {
            this.endTimeUtc = endTimeUtc;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getTotalTask() {
            return totalTask;
        }

        public void setTotalTask(int totalTask) {
            this.totalTask = totalTask;
        }

        public int getTotalTaskCompleted() {
            return totalTaskCompleted;
        }

        public void setTotalTaskCompleted(int totalTaskCompleted) {
            this.totalTaskCompleted = totalTaskCompleted;
        }

        public double getTotalWorkTime() {
            return totalWorkTime;
        }

        public void setTotalWorkTime(int totalWorkTime) {
            this.totalWorkTime = totalWorkTime;
        }

        public double getLostTime() {
            return lostTime;
        }

        public void setLostTime(int lostTime) {
            this.lostTime = lostTime;
        }

        public double getTotalLossTime() {
            return totalLossTime;
        }

        public void setTotalLossTime(int totalLossTime) {
            this.totalLossTime = totalLossTime;
        }

        public double getTotalTimeAtStore() {
            return totalTimeAtStore;
        }

        public void setTotalTimeAtStore(int totalTimeAtStore) {
            this.totalTimeAtStore = totalTimeAtStore;
        }

        public List<VisitHistory> getVisitHistory() {
            return visitHistory;
        }

        public void setVisitHistory(List<VisitHistory> visitHistory) {
            this.visitHistory = visitHistory;
        }

        public List<LocationHistory> getLocationHistory() {
            return locationHistory;
        }

        public void setLocationHistory(List<LocationHistory> locationHistory) {
            this.locationHistory = locationHistory;
        }

        public static class VisitHistory {
            /**
             * customer_name : string
             * task_name : string
             * started_time_utc : 2019-04-26T15:15:52.953Z
             * started_time : 2019-04-26T15:15:52.953Z
             * ended_time_utc : 2019-04-26T15:15:52.953Z
             * ended_time : 2019-04-26T15:15:52.953Z
             * status : string
             * total_invoice : 0
             * invoice_amount : 0
             * is_verified : true
             * loss_time : 0
             * lossTime : 0
             * sales_time : 0
             * salesTime : 0
             */

            @SerializedName("customer_name")
            private String customerName;
            @SerializedName("task_name")
            private String taskName;
            @SerializedName("started_time_utc")
            private String startedTimeUtc;
            @SerializedName("started_time")
            private String startedTime;
            @SerializedName("ended_time_utc")
            private String endedTimeUtc;
            @SerializedName("ended_time")
            private String endedTime;
            @SerializedName("status")
            private String status;
            @SerializedName("total_invoice")
            private long totalInvoice;
            @SerializedName("payment_amount")// invoice_amount
            private long invoiceAmount;
            @SerializedName("is_verified")
            private boolean isVerified;
            @SerializedName("loss_time")
            private double lossTime;
            @SerializedName("sales_time")
            private double salesTime;

            public String getCustomerName() {
                return customerName;
            }

            public void setCustomerName(String customerName) {
                this.customerName = customerName;
            }

            public String getTaskName() {
                return taskName;
            }

            public void setTaskName(String taskName) {
                this.taskName = taskName;
            }

            public String getStartedTimeUtc() {
                return startedTimeUtc;
            }

            public void setStartedTimeUtc(String startedTimeUtc) {
                this.startedTimeUtc = startedTimeUtc;
            }

            public String getStartedTime() {
                return startedTime;
            }

            public void setStartedTime(String startedTime) {
                this.startedTime = startedTime;
            }

            public String getEndedTimeUtc() {
                return endedTimeUtc;
            }

            public void setEndedTimeUtc(String endedTimeUtc) {
                this.endedTimeUtc = endedTimeUtc;
            }

            public String getEndedTime() {
                return endedTime;
            }

            public void setEndedTime(String endedTime) {
                this.endedTime = endedTime;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public long getTotalInvoice() {
                return totalInvoice;
            }

            public void setTotalInvoice(int totalInvoice) {
                this.totalInvoice = totalInvoice;
            }

            public long getInvoiceAmount() {
                return invoiceAmount;
            }

            public void setInvoiceAmount(int invoiceAmount) {
                this.invoiceAmount = invoiceAmount;
            }

            public boolean isIsVerified() {
                return isVerified;
            }

            public void setIsVerified(boolean isVerified) {
                this.isVerified = isVerified;
            }

            public double getLossTime() {
                return lossTime;
            }

            public void setLossTime(int lossTime) {
                this.lossTime = lossTime;
            }

            public double getSalesTime() {
                return salesTime;
            }

            public void setSalesTime(int salesTime) {
                this.salesTime = salesTime;
            }
        }

        public static class LocationHistory {
            /**
             * label : string
             * latitude : 0
             * longitude : 0
             * status : string
             * is_active : true
             * startTime : 2019-04-26T15:15:52.953Z
             * checkin_date : string
             * googleTime : 0
             * salesTime : 0
             * google_time : 0
             * sales_time : 0
             */

            @SerializedName("label")
            private String label;
            @SerializedName("latitude")
            private double latitude;
            @SerializedName("longitude")
            private double longitude;
            @SerializedName("status")
            private String status;
            @SerializedName("is_active")
            private boolean isActive;
            @SerializedName("startTime")
            private String startTime;
            @SerializedName("checkin_date")
            private String checkinDate;
            @SerializedName("google_time")
            private double googleTime;
            @SerializedName("sales_time")
            private double salesTime;

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(int latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(int longitude) {
                this.longitude = longitude;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public boolean isIsActive() {
                return isActive;
            }

            public void setIsActive(boolean isActive) {
                this.isActive = isActive;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getCheckinDate() {
                return checkinDate;
            }

            public void setCheckinDate(String checkinDate) {
                this.checkinDate = checkinDate;
            }

            public double getGoogleTime() {
                return googleTime;
            }

            public void setGoogleTime(int googleTime) {
                this.googleTime = googleTime;
            }

            public double getSalesTime() {
                return salesTime;
            }

            public void setSalesTime(int salesTime) {
                this.salesTime = salesTime;
            }
        }
    }

}
