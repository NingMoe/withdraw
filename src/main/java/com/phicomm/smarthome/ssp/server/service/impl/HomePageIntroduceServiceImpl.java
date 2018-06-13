package com.phicomm.smarthome.ssp.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.consts.HomePageConst;
import com.phicomm.smarthome.ssp.server.dao.HomePageIntroduceMapper;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.dao.HomePageIntroduceDaoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseHomePageIntroduceListModel;
import com.phicomm.smarthome.ssp.server.service.FtpOptService;
import com.phicomm.smarthome.ssp.server.service.HomePageIntroduceService;
import com.phicomm.smarthome.ssp.server.util.CommonUtils;
import com.phicomm.smarthome.util.StringUtil;

/**
 * 描述：首页介绍服务实现
 * Created by zhengwang.li on 2018/4/17.
 */
@Service
public class HomePageIntroduceServiceImpl implements HomePageIntroduceService {
    
    private static final Logger LOGGER = LogManager.getLogger(HomePageIntroduceServiceImpl.class);

    @Value("${FTP_IMG_BASE_PATH}")
    private String domNameUrl;
    
    @Autowired
    private FtpOptService ftpOptService;
    
    @Autowired
    private HomePageIntroduceMapper mapper;
    
    @Override
    public BaseResponseModel addIntroduce(int id, MultipartFile[] files, String pcImgName, String mobileImgName, long lastOptUid, String lastOptUserName) {
        HomePageIntroduceDaoModel model = new HomePageIntroduceDaoModel();
        BaseResponseModel rspObj = new BaseResponseModel();
        try {
            int count = mapper.countIntroduce();
            if (id == 0 && count >= 1) {
                rspObj.setRetCode(Const.ResponseStatus.STATUS_COUNT_HOME_PAGE_INTRODUCE_EXCEED_ERROR);
                rspObj.setRetMsg(Const.ResponseStatus.STATUS_COUNT_HOME_PAGE_INTRODUCE_EXCEED_ERROR_STR);
                return rspObj;
            }
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED_STR);
            return rspObj;
        }
        // 上传PC客户端图片
        model.setId(id);
        model.setLastOptUid(lastOptUid);
        model.setLastOptUserName(lastOptUserName);
        model.setCreateTime(System.currentTimeMillis() / 1000L);
        model.setUpdateTime(System.currentTimeMillis() / 1000L);
        model.setStatus(HomePageConst.HomePageMgr.ADD_STATUS_VALUE);
        try {
            rspObj = ftpOptService.uploadFileNoDomainName(files[0], HomePageConst.HomePageMgr.HOME_PAGE_INTRODUCE_PC_IMG_BASE_PATH);
            if (rspObj.getRetCode() == Const.FtpFileStatus.STATUS_FILE_OK) {
                model.setPcImgUrl(rspObj.getExtendMsg());
                model.setPcImgName(pcImgName);
            }
            // 上传移动端图片
            rspObj = ftpOptService.uploadFileNoDomainName(files[1], HomePageConst.HomePageMgr.HOME_PAGE_INTRODUCE_MOBILE_IMG_BASE_PATH);
            if (rspObj.getRetCode() == Const.FtpFileStatus.STATUS_FILE_OK) {
                model.setMobileImgUrl(rspObj.getExtendMsg());
                model.setMobileImgName(mobileImgName);
            }
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR);
            rspObj.setRetMsg(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR_STR);
            return rspObj;
        }
        try {
            // 插入数据
            mapper.insertIntroduce(model);
            
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED_STR);
            return rspObj;
        }
        rspObj.setExtendMsg(null);
        return rspObj;
    }

    @Override
    public ResponseHomePageIntroduceListModel getInstroduce() {
        ResponseHomePageIntroduceListModel rspObj = new ResponseHomePageIntroduceListModel();
        List<HomePageIntroduceDaoModel> list = new ArrayList<>();
        try {
            list = mapper.getIntroduce();
            if (list == null) {
                rspObj.setList(new ArrayList<>());
                return rspObj;
            }
            for (HomePageIntroduceDaoModel model : list) {
                if (StringUtil.isNullOrEmpty(model.getPcImgUrl())) {
                    model.setPcImgUrl("");
                } else {
                    model.setPcImgUrl(domNameUrl + model.getPcImgUrl());
                }
                if (StringUtil.isNullOrEmpty(model.getMobileImgUrl())) {
                    model.setPcImgUrl("");
                } else {
                    model.setMobileImgUrl(domNameUrl + model.getMobileImgUrl());
                }
                model.setUpdateTimeStr(CommonUtils.stampToDateTimeStr(model.getUpdateTime(), "yyyy/MM/dd hh:mm:ss"));
            }
            rspObj.setList(list);
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED_STR);
        }
        return rspObj;
    }

    
    @Override
    public BaseResponseModel editInstroduce(HomePageIntroduceDaoModel model) {
        BaseResponseModel rspObj = new BaseResponseModel();
        model.setUpdateTime(System.currentTimeMillis() / 1000L);
        try {
            /**
             * 把url除去域名，
             * 例如：http://dev.home.phiwifi.com/file/images/home_page_banner/mobile/996e026afe834b959f9e4ea7acdad99c.jpg
             * 变成：/file/images/home_page_banner/mobile/996e026afe834b959f9e4ea7acdad99c.jpg
             */
            model.setPcImgUrl(model.getPcImgUrl().replace(domNameUrl, ""));
            model.setMobileImgUrl(model.getMobileImgUrl().replace(domNameUrl, ""));
            mapper.updateIntroduce(model);
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED_STR);
        }
        return rspObj;
    }
}
