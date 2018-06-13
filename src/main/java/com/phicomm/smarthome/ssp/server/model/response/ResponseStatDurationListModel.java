package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;

@SuppressWarnings("serial")
public class ResponseStatDurationListModel extends BaseResponseModel {

    private List<ResponseStatDurationModel> durationList;

    private int totalCount;

    public List<ResponseStatDurationModel> getDurationList() {
        return durationList;
    }

    public void setDurationList(List<ResponseStatDurationModel> durationList) {
        this.durationList = durationList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
