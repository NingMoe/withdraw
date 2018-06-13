package com.phicomm.smarthome.ssp.server.service;

import javax.servlet.http.HttpServletResponse;

import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseQueryStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppListModel;

public interface StatAnalyzeAppUserService {

    /**
     * 获取APP用户数据明细
     * 
     * @param model
     * @param isPage
     *            是否分页
     * @return
     */
    ResponseStatAppListModel getAnalyzeStatAppList(RequestStatAppModel model, boolean isPage);

    ResponseStatAppListModel getAnalyzeStatAppNewUserList(RequestStatAppModel model, boolean isPage);

    ResponseStatAppListModel getAnalyzeStatAppPvUvList(ResponseStatAppListModel rpsModel, RequestStatAppModel model);

    ResponseQueryStatAppInfoModel queryStatAppUserInfo();

    ResponseStatAppInfoModel statAppUser(RequestStatAppModel model);

    ResponseStatAppInfoModel statAppPvUser(RequestStatAppModel model);

    ResponseStatAppInfoModel statAppUvUser(RequestStatAppModel model);

    ResponseStatAppInfoModel statAppNewUser(RequestStatAppModel model);

    void statAppUserListExportExcel(HttpServletResponse response, ResponseStatAppListModel listModel);

}
