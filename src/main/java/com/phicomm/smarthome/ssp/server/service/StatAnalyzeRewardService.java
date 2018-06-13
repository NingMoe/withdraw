package com.phicomm.smarthome.ssp.server.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.phicomm.smarthome.ssp.server.model.TblStatDurationUserDetailModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatAppModel;
import com.phicomm.smarthome.ssp.server.model.request.RequestStatRewardDurationModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseQueryStatRewardDurationInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseQueryStatRewardUserInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppInfoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatAppListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatDurationListModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseStatDurationModel;

public interface StatAnalyzeRewardService {

    /**
     * 获取打赏用户折线图数据明细
     * 
     * @param model
     * @param isPage
     *            是否分页
     * @return
     */
    ResponseStatAppListModel getAnalyzeStatRewardList(RequestStatAppModel model, boolean isPage);

    List<TblStatDurationUserDetailModel> getAnalyzeStatRewardSwUserDetailList(RequestStatAppModel model);

    List<TblStatDurationUserDetailModel> geAllTblStatDurationUserDetails(RequestStatAppModel model);

    int getAnalyzeStatRewardSwUserDetailListCount(RequestStatAppModel model);

    ResponseQueryStatRewardUserInfoModel queryStatRewardUserInfo();

    ResponseQueryStatRewardDurationInfoModel queryStatRewardDurationInfo();

    ResponseStatAppInfoModel statRewardUser(RequestStatAppModel model);

    ResponseStatAppInfoModel statRewardDuration(RequestStatRewardDurationModel model);

    ResponseStatDurationListModel statRewardDurationList(RequestStatRewardDurationModel model);

    String getIndexChildNameById(int indexChild);

    void statRewardUserListExportExcel(HttpServletResponse response, ResponseStatAppListModel listModel);

    void statRewardDurationListExportExcel(HttpServletResponse response, List<ResponseStatDurationModel> list);

    void statRewardSwUserDetailListExportExcel(HttpServletResponse response, List<TblStatDurationUserDetailModel> list);

}
