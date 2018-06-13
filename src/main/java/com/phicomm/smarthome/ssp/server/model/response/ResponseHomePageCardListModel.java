package com.phicomm.smarthome.ssp.server.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.dao.HomePageCardDaoModel;

/**
 * 
 * @author zhengwang.li
 *
 */
public class ResponseHomePageCardListModel extends BaseResponseModel{

    private static final long serialVersionUID = 2939790762633033225L;
    
    @JsonIgnore
    private String extendMsg; 
    
    @JsonProperty("left_card")
    private List<HomePageCardDaoModel> leftCardList;

    @JsonProperty("right_card")
    private List<HomePageCardDaoModel> rightCardList;

    public List<HomePageCardDaoModel> getLeftCardList() {
        return leftCardList;
    }

    public void setLeftCardList(List<HomePageCardDaoModel> leftCardList) {
        this.leftCardList = leftCardList;
    }

    public List<HomePageCardDaoModel> getRightCardList() {
        return rightCardList;
    }

    public void setRightCardList(List<HomePageCardDaoModel> rightCardList) {
        this.rightCardList = rightCardList;
    }
}
