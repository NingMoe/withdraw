package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.dao.HomePageBannerDaoModel;

/**
 * 
 * @author zhengwang.li
 *
 */
public class ResponseHomePageBannerListModel extends BaseResponseModel{
    
    private static final long serialVersionUID = 121586018990094040L;

    @JsonProperty("public_time")
    private String publicTime; // 发布时间
    
    @JsonIgnore
    private String extendMsg;
    
    @JsonProperty("list")
    private List<HomePageBannerDaoModel> list;

    public String getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(String publicTime) {
        this.publicTime = publicTime;
    }

    public List<HomePageBannerDaoModel> getList() {
        return list;
    }

    public void setList(List<HomePageBannerDaoModel> list) {
        this.list = list;
    }
}
