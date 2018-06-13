package com.phicomm.smarthome.ssp.server.model.response;

import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.WeekModel;

import java.util.List;

@SuppressWarnings("serial")
public class ResponseStatTimePeriodListModel extends BaseResponseModel {

    private List<ResponseStatTimePeriodModel> timePeriodModelList;

    private int totalCount;

    public List<ResponseStatTimePeriodModel> getTimePeriodModelList() {
        return timePeriodModelList;
    }

    public void setTimePeriodModelList(List<ResponseStatTimePeriodModel> timePeriodModelList) {
        this.timePeriodModelList = timePeriodModelList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
