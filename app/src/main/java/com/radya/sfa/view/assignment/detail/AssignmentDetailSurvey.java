package com.radya.sfa.view.assignment.detail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@Data
public class AssignmentDetailSurvey implements Serializable{


    /**
     * survey_id : 4eb6cbc7-36b6-4cc8-8933-a443296f2566
     * name : Penggunaan SFA
     * link : https://docs.google.com/forms/d/e/1FAIpQLSdTUv03gnpbA0l2R8oL40C__VtrRyfdQ_TpOkeQphPkHwRHdA/viewform?usp=sf_link
     * start_date : 2018-07-01T17:00:00
     * end_date : 2018-07-30T17:00:00
     */

    @SerializedName("survey_id")
    private String surveyId;
    @SerializedName("name")
    private String name;
    @SerializedName("link")
    private String link;
    @SerializedName("start_date")
    private String startDate;
    @SerializedName("end_date")
    private String endDate;

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
