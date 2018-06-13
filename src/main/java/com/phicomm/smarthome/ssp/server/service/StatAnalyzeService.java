package com.phicomm.smarthome.ssp.server.service;

import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatTimePeriodModel;

public interface StatAnalyzeService {

    /**
     * 规范日期范围
     * 
     * @param model
     * @return
     */
    RequestStatAppModel standardDateScope(RequestStatAppModel model);

    RequestStatTimePeriodModel standardDateScope(RequestStatTimePeriodModel model);

    String getIndexChildNameById(int indexChild);
    
    String getDatesByDate(String startDate, String endDate);

}
