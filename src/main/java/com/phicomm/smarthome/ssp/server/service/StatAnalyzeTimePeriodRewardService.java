package com.phicomm.smarthome.ssp.server.service;

import com.phicomm.smarthome.ssp.server.model.request.RequestStatTimePeriodModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatTimePeriodListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatTimePeriodModel;

import java.util.List;

public interface StatAnalyzeTimePeriodRewardService {

    /**
     * 获取任意时段内用户收益排名
     */
    ResponseStatTimePeriodListModel getAnalyzeStatTimePeriodRewardList(RequestStatTimePeriodModel model, boolean isPage);
}
