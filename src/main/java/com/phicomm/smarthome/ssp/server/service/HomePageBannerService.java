package com.phicomm.smarthome.ssp.server.service;

import org.springframework.web.multipart.MultipartFile;

import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.dao.HomePageBannerDaoModel;
import com.phicomm.smarthome.ssp.server.model.request.MoveReqModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseHomePageBannerListModel;

/**
 * 描述:首页banner服务接口
 * Created by zhengwang.li on 2018/4/17.
 */
public interface HomePageBannerService {
        
    BaseResponseModel addBanner(MultipartFile[] files, int id, int action, 
            String pcBannerImgName, String pcGroundImgName,
            String mobileBannerImgName, String mobileGroundImgName,
            long lastOptUid,
            String lastOptUserName);

    BaseResponseModel deleteBanner(HomePageBannerDaoModel model);

    BaseResponseModel publicBanner(HomePageBannerDaoModel model);

    BaseResponseModel moveBanner(MoveReqModel model);

    ResponseHomePageBannerListModel listBanners();

    BaseResponseModel editBanner(HomePageBannerDaoModel model);
}
