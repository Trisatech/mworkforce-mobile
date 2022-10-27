package com.radya.sfa.view.assignment.detail.failed;

import com.google.gson.annotations.SerializedName;
import com.radya.sfa.data.source.remote.BaseModel;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Data
public class AssignmentFailedReason extends BaseModel implements Serializable {

    @SerializedName("data")
    private List<AssignmentReason> data;

    public List<AssignmentReason> getData() {
        return data;
    }

    public void setData(List<AssignmentReason> data) {
        this.data = data;
    }

    @NoArgsConstructor
    @Data
    public static class AssignmentReason implements Serializable {

        @SerializedName("reason_code")
        private String reason_code;
        @SerializedName("reason_name")
        private String reason_name;

        public String getReason_code() {
            return reason_code;
        }

        public void setReason_code(String reason_code) {
            this.reason_code = reason_code;
        }

        public String getReason_name() {
            return reason_name;
        }

        public void setReason_name(String reason_name) {
            this.reason_name = reason_name;
        }

        public AssignmentReason(String reason_code, String reason_name) {
            this.reason_code = reason_code;
            this.reason_name = reason_name;
        }
    }

}
