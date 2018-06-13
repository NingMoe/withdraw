package com.phicomm.smarthome.ssp.server.service;

import org.springframework.web.multipart.MultipartFile;

import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.dao.HomePageCardDaoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseHomePageCardListModel;

/**
 * 描述:首页卡片服务接口
 * Created by zhengwang.li on 2018/4/17.
 */
public interface HomePageCardService {

    BaseResponseModel addCard(int id, MultipartFile[] files, String content, 
            String pcBannerImgName, String pcGroundImgName,
            String mobileBannerImgName, String mobileGroundImgName, int type, long lastOptUid, String lastOptUserName);
    ResponseHomePageCardListModel listCards();
    
    BaseResponseModel editCards(HomePageCardDaoModel model);
}
