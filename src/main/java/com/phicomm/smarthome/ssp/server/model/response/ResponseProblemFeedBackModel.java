package com.phicomm.smarthome.ssp.server.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.SwProblemFeedbackModel;

import java.util.List;

/**
 * Created by zhengwang.li on 2018/5/4.
 */
public class ResponseProblemFeedBackModel extends BaseResponseModel{

    @JsonProperty("list")
    private List<SwProblemFeedbackModel> list;

    @JsonProperty("total_count")
    private int totalCount;
    
    public List<SwProblemFeedbackModel> getList() {
        return list;
    }

    public void setList(List<SwProblemFeedbackModel> list) {
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
