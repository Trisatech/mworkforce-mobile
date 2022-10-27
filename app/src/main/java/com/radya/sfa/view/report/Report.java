package com.radya.sfa.view.report;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Report {


    /**
     * user_id : string
     * user_code : string
     * user_name : string
     * name : string
     * start_time_utc : 2019-04-22T22:21:10.682Z
     * start_time : 2019-04-22T22:21:10.682Z
     * end_time_utc : 2019-04-22T22:21:10.682Z
     * end_time : 2019-04-22T22:21:10.682Z
     * total_task_completed : 0
     * total_task_in_progress : 0
     * total_task_failed : 0
     * total_task : 0
     * total_invoice : 0
     * total_work_time : 0
     * lost_time : 0
     * total_loss_time : 0
     * total_time_at_store : 0
     * listVisit : [{"assignmentId":"string","invoiceAmount":0,"assignmentDate":"2019-04-22T22:21:10.682Z","startTime":"2019-04-22T22:21:10.682Z","endTime":"2019-04-22T22:21:10.682Z"}]
     */

    @SerializedName("user_id")
    private String userId;
    @SerializedName("user_code")
    private String userCode;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("name")
    private String name;
    @SerializedName("start_time_utc")
    private String startTimeUtc;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time_utc")
    private String endTimeUtc;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("total_task_completed")
    private int totalTaskCompleted;
    @SerializedName("total_task_in_progress")
    private int totalTaskInProgress;
    @SerializedName("total_task_failed")
    private int totalTaskFailed;
    @SerializedName("total_task")
    private int totalTask;
    @SerializedName("total_payment")// total_invoice
    private long totalInvoice;
    @SerializedName("total_work_time")
    private double totalWorkTime;
    @SerializedName("lost_time")
    private double lostTime;
    @SerializedName("total_loss_time")
    private double totalLossTime;
    @SerializedName("total_time_at_store")
    private double totalTimeAtStore;
    @SerializedName("listVisit")
    private List<ListVisit> listVisit;

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

    public String getStartTimeUtc() {
        return startTimeUtc;
    }

    public void setStartTimeUtc(String startTimeUtc) {
        this.startTimeUtc = startTimeUtc;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTimeUtc() {
        return endTimeUtc;
    }

    public void setEndTimeUtc(String endTimeUtc) {
        this.endTimeUtc = endTimeUtc;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getTotalTaskCompleted() {
        return totalTaskCompleted;
    }

    public void setTotalTaskCompleted(int totalTaskCompleted) {
        this.totalTaskCompleted = totalTaskCompleted;
    }

    public int getTotalTaskInProgress() {
        return totalTaskInProgress;
    }

    public void setTotalTaskInProgress(int totalTaskInProgress) {
        this.totalTaskInProgress = totalTaskInProgress;
    }

    public int getTotalTaskFailed() {
        return totalTaskFailed;
    }

    public void setTotalTaskFailed(int totalTaskFailed) {
        this.totalTaskFailed = totalTaskFailed;
    }

    public int getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(int totalTask) {
        this.totalTask = totalTask;
    }

    public long getTotalInvoice() {
        return totalInvoice;
    }

    public void setTotalInvoice(int totalInvoice) {
        this.totalInvoice = totalInvoice;
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

    public List<ListVisit> getListVisit() {
        return listVisit;
    }

    public void setListVisit(List<ListVisit> listVisit) {
        this.listVisit = listVisit;
    }

    public static class ListVisit {
        /**
         * assignmentId : string
         * invoiceAmount : 0
         * assignmentDate : 2019-04-22T22:21:10.682Z
         * startTime : 2019-04-22T22:21:10.682Z
         * endTime : 2019-04-22T22:21:10.682Z
         */

        @SerializedName("assignmentId")
        private String assignmentId;
        @SerializedName("invoiceAmount")
        private long invoiceAmount;
        @SerializedName("assignmentDate")
        private String assignmentDate;
        @SerializedName("startTime")
        private String startTime;
        @SerializedName("endTime")
        private String endTime;

        public String getAssignmentId() {
            return assignmentId;
        }

        public void setAssignmentId(String assignmentId) {
            this.assignmentId = assignmentId;
        }

        public long getInvoiceAmount() {
            return invoiceAmount;
        }

        public void setInvoiceAmount(int invoiceAmount) {
            this.invoiceAmount = invoiceAmount;
        }

        public String getAssignmentDate() {
            return assignmentDate;
        }

        public void setAssignmentDate(String assignmentDate) {
            this.assignmentDate = assignmentDate;
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
    }
}
