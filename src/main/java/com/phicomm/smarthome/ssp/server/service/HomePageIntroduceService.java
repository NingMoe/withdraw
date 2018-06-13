package com.phicomm.smarthome.ssp.server.service;

import org.springframework.web.multipart.MultipartFile;

import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.dao.HomePageIntroduceDaoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseHomePageIntroduceListModel;

/**
 * 描述：首页介绍服务接口
 * Created by zhengwang.li on 2018/4/17.
 */
public interface HomePageIntroduceService {

    BaseResponseModel addIntroduce(int id, MultipartFile[] files, String pcImgName, String mobileImgName, long lastOptUid, String lastOptUserName);

    ResponseHomePageIntroduceListModel getInstroduce();

    BaseResponseModel editInstroduce(HomePageIntroduceDaoModel model);
}
