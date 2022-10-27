package com.radya.sfa.view.assignment;

import com.google.gson.annotations.SerializedName;
import com.radya.sfa.view.assignment.detail.AssignmentDetailOrder;
import com.radya.sfa.view.assignment.detail.AssignmentDetailPayment;
import com.radya.sfa.view.assignment.detail.AssignmentDetailSurvey;
import com.radya.sfa.view.invoice.Invoice;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
public class Assignment implements Serializable {

    @SerializedName("assignment_id")
    private String assignmentId;
    @SerializedName("assignment_code")
    private String assignmentCode;
    @SerializedName("assignment_name")
    private String assignmentName;
    @SerializedName("assignment_date")
    private String assignmentDate;
    @SerializedName("assignment_address")
    private String assignmentAddress;
    @SerializedName("assignment_status_code")
    private String assignmentStatusCode;
    @SerializedName("assignment_longitude")
    private double assignmentLongitude;
    @SerializedName("assignment_latitude")
    private double assignmentLatitude;
    @SerializedName("assignment_start_time")
    private String assignmentStartTime;
    @SerializedName("assignment_end_time")
    private String assignmentEndTime;
    @SerializedName("lost_time")
    private int lostTime;
    @SerializedName("remarks")
    private String remarks;
    @SerializedName("assignment_type")
    private int assignmentType;
    @SerializedName("status")
    private String status;
    private Contact contact;
    @SerializedName("type")
    private int type;
    @SerializedName("invoices")
    private List<Invoice> invoices;
    @SerializedName("payments")
    private List<AssignmentDetailPayment> payments;
    @SerializedName("survey")
    private List<AssignmentDetailSurvey> survey;
    @SerializedName("orders")
    private List<AssignmentDetailOrder> orders;

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentCode() {
        return assignmentCode;
    }

    public void setAssignmentCode(String assignmentCode) {
        this.assignmentCode = assignmentCode;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(String assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    public String getAssignmentAddress() {
        return assignmentAddress;
    }

    public void setAssignmentAddress(String assignmentAddress) {
        this.assignmentAddress = assignmentAddress;
    }

    public String getAssignmentStatusCode() {
        return assignmentStatusCode;
    }

    public void setAssignmentStatusCode(String assignmentStatusCode) {
        this.assignmentStatusCode = assignmentStatusCode;
    }

    public double getAssignmentLongitude() {
        return assignmentLongitude;
    }

    public void setAssignmentLongitude(double assignmentLongitude) {
        this.assignmentLongitude = assignmentLongitude;
    }

    public double getAssignmentLatitude() {
        return assignmentLatitude;
    }

    public void setAssignmentLatitude(double assignmentLatitude) {
        this.assignmentLatitude = assignmentLatitude;
    }

    public String getAssignmentStartTime() {
        return assignmentStartTime;
    }

    public void setAssignmentStartTime(String assignmentStartTime) {
        this.assignmentStartTime = assignmentStartTime;
    }

    public String getAssignmentEndTime() {
        return assignmentEndTime;
    }

    public void setAssignmentEndTime(String assignmentEndTime) {
        this.assignmentEndTime = assignmentEndTime;
    }

    public int getLostTime() {
        return lostTime;
    }

    public void setLostTime(int lostTime) {
        this.lostTime = lostTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(int assignmentType) {
        this.assignmentType = assignmentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<AssignmentDetailPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<AssignmentDetailPayment> payments) {
        this.payments = payments;
    }

    public List<AssignmentDetailSurvey> getSurvey() {
        return survey;
    }

    public void setSurvey(List<AssignmentDetailSurvey> survey) {
        this.survey = survey;
    }

    public List<AssignmentDetailOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<AssignmentDetailOrder> orders) {
        this.orders = orders;
    }

    @NoArgsConstructor
    @Data
    public static class Contact implements Serializable {
        /**
         * contact_name : Toko Jajang
         * contact_number : 6282129290963
         * remarks : null
         */
        @SerializedName("contact_id")
        private String contact_id;
        @SerializedName("contact_name")
        private String contact_name;
        @SerializedName("contact_number")
        private String contact_number;
        @SerializedName("remarks")
        private String remarks;

        public String getContact_id() {
            return contact_id;
        }

        public void setContact_id(String contact_id) {
            this.contact_id = contact_id;
        }

        public String getContact_name() {
            return contact_name;
        }

        public void setContact_name(String contact_name) {
            this.contact_name = contact_name;
        }

        public String getContact_number() {
            return contact_number;
        }

        public void setContact_number(String contact_number) {
            this.contact_number = contact_number;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }

}
