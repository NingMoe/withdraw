package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.dao.HomePageIntroduceDaoModel;

/**
 * 
 * @author zhengwang.li
 *
 */
public class ResponseHomePageIntroduceListModel extends BaseResponseModel{

    private static final long serialVersionUID = 5107474834742162078L;

    @JsonIgnore
    private String extendMsg;
    
    @JsonProperty("list")
    private List<HomePageIntroduceDaoModel> list;

    public List<HomePageIntroduceDaoModel> getList() {
        return list;
    }

    public void setList(List<HomePageIntroduceDaoModel> list) {
        this.list = list;
    }
}
