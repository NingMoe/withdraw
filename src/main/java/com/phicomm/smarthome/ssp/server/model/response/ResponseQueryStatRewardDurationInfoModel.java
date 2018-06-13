package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;

@SuppressWarnings("serial")
public class ResponseQueryStatRewardDurationInfoModel extends BaseResponseModel {

    private List<ResponseBaseModel> durations;

    public List<ResponseBaseModel> getDurations() {
        return durations;
    }

    public void setDurations(List<ResponseBaseModel> durations) {
        this.durations = durations;
    }

}
