package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.TblStatDurationUserDetailModel;

@SuppressWarnings("serial")
public class ResponseStatSwUserDetailListModel extends BaseResponseModel {

    private List<TblStatDurationUserDetailModel> detailList;

    private int totalCount;

    public List<TblStatDurationUserDetailModel> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<TblStatDurationUserDetailModel> detailList) {
        this.detailList = detailList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
