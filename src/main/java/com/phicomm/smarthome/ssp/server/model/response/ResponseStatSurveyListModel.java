package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.TblStatSurveyModel;

@SuppressWarnings("serial")
public class ResponseStatSurveyListModel extends BaseResponseModel {

    private List<TblStatSurveyModel> surveyList;

    private int totalCount;

    public List<TblStatSurveyModel> getSurveyList() {
        return surveyList;
    }

    public void setSurveyList(List<TblStatSurveyModel> surveyList) {
        this.surveyList = surveyList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
