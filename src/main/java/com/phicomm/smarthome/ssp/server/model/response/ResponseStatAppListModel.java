package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.WeekModel;

@SuppressWarnings("serial")
public class ResponseStatAppListModel extends BaseResponseModel {

    private List<ResponseStatAppModel> appList;

    private int totalCount;
    
    private List<WeekModel> weekSplit;
    
    private List<String> months;

    public List<ResponseStatAppModel> getAppList() {
        return appList;
    }

    public void setAppList(List<ResponseStatAppModel> appList) {
        this.appList = appList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<WeekModel> getWeekSplit() {
        return weekSplit;
    }

    public void setWeekSplit(List<WeekModel> weekSplit) {
        this.weekSplit = weekSplit;
    }

    public List<String> getMonths() {
        return months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }

}
