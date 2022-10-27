package com.radya.sfa.view.dashboard;

import com.google.gson.annotations.SerializedName;
import com.radya.sfa.data.source.remote.BaseModel;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
public class Dashboard extends BaseModel {

    @SerializedName("data")
    private DashboardData data;

    public DashboardData getData() {
        return data;
    }

    public void setData(DashboardData data) {
        this.data = data;
    }

    @NoArgsConstructor
    @Data
    public static class DashboardData {
        @SerializedName("totalTask")
        private int totalTask;
        @SerializedName("failed")
        private int failed;
        @SerializedName("success")
        private int success;
        @SerializedName("on_going")
        private int on_going;
        @SerializedName("assignment_report")
        private List<AssignmentReport> assignment_report;
        @SerializedName("total_work_time")
        private double total_work_time;
        @SerializedName("total_loss_time")
        private double total_loss_time;

        public int getTotalTask() {
            return totalTask;
        }

        public void setTotalTask(int totalTask) {
            this.totalTask = totalTask;
        }

        public int getFailed() {
            return failed;
        }

        public void setFailed(int failed) {
            this.failed = failed;
        }

        public int getSuccess() {
            return success;
        }

        public void setSuccess(int success) {
            this.success = success;
        }

        public int getOn_going() {
            return on_going;
        }

        public void setOn_going(int on_going) {
            this.on_going = on_going;
        }

        public List<AssignmentReport> getAssignment_report() {
            return assignment_report;
        }

        public void setAssignment_report(List<AssignmentReport> assignment_report) {
            this.assignment_report = assignment_report;
        }

        public double getTotal_work_time() {
            return total_work_time;
        }

        public void setTotal_work_time(double total_work_time) {
            this.total_work_time = total_work_time;
        }

        public double getTotal_loss_time() {
            return total_loss_time;
        }

        public void setTotal_loss_time(double total_loss_time) {
            this.total_loss_time = total_loss_time;
        }

        @NoArgsConstructor
        @Data
        public static class AssignmentReport {
            @SerializedName("total_work_time")
            private double total_work_time;
            @SerializedName("total_loss_time")
            private double total_loss_time;
            @SerializedName("user_name")
            private String user_name;

            public double getTotal_work_time() {
                return total_work_time;
            }

            public void setTotal_work_time(double total_work_time) {
                this.total_work_time = total_work_time;
            }

            public double getTotal_loss_time() {
                return total_loss_time;
            }

            public void setTotal_loss_time(double total_loss_time) {
                this.total_loss_time = total_loss_time;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }
        }

    }

}
