package com.phicomm.smarthome.ssp.server.service;

import java.util.List;

import com.phicomm.smarthome.ssp.server.model.request.RequestIncomeRecordModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestSwUserModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseIncomeRedordModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseUserInfoListModel;

public interface ManagerUserIncomeService {

    ResponseUserInfoListModel getUserInfoList(RequestSwUserModel model);
    
    List<ResponseIncomeRedordModel> getIncomeRecordList(RequestIncomeRecordModel model);
    
    int getIncomeRecordListCount(RequestIncomeRecordModel model);
    
}
