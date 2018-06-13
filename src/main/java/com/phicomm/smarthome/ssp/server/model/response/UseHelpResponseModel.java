package com.phicomm.smarthome.ssp.server.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.UseHelpModel;

import java.util.List;

public class UseHelpResponseModel extends BaseResponseModel {

    @JsonProperty("total_count")
    private Integer totalCount; // 记录总数量

    @JsonProperty("use_help_list")
    private List<UseHelpModel> useHelpList;

    public List<UseHelpModel> getUseHelpList() {
        return useHelpList;
    }

    public void setUseHelpList(List<UseHelpModel> useHelpList) {
        this.useHelpList = useHelpList;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
