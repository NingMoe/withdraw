package com.phicomm.smarthome.ssp.server.service;

import javax.servlet.http.HttpServletResponse;

import com.phicomm.smarthome.ssp.server.model.TblGeneralDetailModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseQueryStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatSurveyListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatSurveyTotalInfoModel;

public interface StatAnalyzeSurveyService {

    ResponseQueryStatAppInfoModel queryStatSurveyInfo();

    ResponseStatAppInfoModel statSurvey(RequestStatAppModel model);

    ResponseStatSurveyTotalInfoModel statSurveyTotalInfo(ResponseStatSurveyTotalInfoModel model);

    TblGeneralDetailModel getSurveyIndexChildInfoById(int indexChild);

    void statSurveyListExportExcel(HttpServletResponse response, ResponseStatSurveyListModel listModel);

    public ResponseStatSurveyListModel statSurveyDataList(RequestStatAppModel model, boolean isPage);

}
