package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;

@SuppressWarnings("serial")
public class ResponseUserInfoListModel extends BaseResponseModel {

    @JsonProperty("user_list")
    private List<ResponseUserInfoModel> userList;

    @JsonProperty("total_count")
    private int totalCount;

    public List<ResponseUserInfoModel> getUserList() {
        return userList;
    }

    public void setUserList(List<ResponseUserInfoModel> userList) {
        this.userList = userList;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
