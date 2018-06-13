package com.phicomm.smarthome.ssp.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.consts.HomePageConst;
import com.phicomm.smarthome.ssp.server.dao.HomePageCardMapper;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.dao.HomePageCardDaoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseHomePageCardListModel;
import com.phicomm.smarthome.ssp.server.service.FtpOptService;
import com.phicomm.smarthome.ssp.server.service.HomePageCardService;
import com.phicomm.smarthome.ssp.server.util.CommonUtils;
import com.phicomm.smarthome.ssp.server.util.MyListUtils;
import com.phicomm.smarthome.util.StringUtil;

/**
 * 描述：首页卡片服务实现
 * Created by zhengwang.li on 2018/4/17.
 */
@Service
public class HomePageCardServiceImpl implements HomePageCardService {

    private static final Logger LOGGER = LogManager.getLogger(HomePageCardServiceImpl.class);
    
    @Value("${FTP_IMG_BASE_PATH}")
    private String domNameUrl;
    
    @Autowired
    private FtpOptService ftpOptService;
    
    @Autowired
    private HomePageCardMapper mapper;
    
    @Override
    public BaseResponseModel addCard(int id, MultipartFile[] files, String content, 
            String pcBannerImgName, String pcGroundImgName,
            String mobileBannerImgName, String mobileGroundImgName, int type, long lastOptUid, String lastOptUserName) {
        BaseResponseModel rspObj = new BaseResponseModel();
        HomePageCardDaoModel model = new HomePageCardDaoModel();
        int count = mapper.countCard();
        if (count >= 2) {
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COUNT_HOME_PAGE_CAR_EXCEED_ERROR);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COUNT_HOME_PAGE_CAR_EXCEED_ERROR_STR);
            return rspObj;
        }
        model.setId(id);
        model.setContent(content);
        model.setPcBannerImgName(pcBannerImgName);
        model.setPcGroundImgName(pcGroundImgName);
        model.setMobileBannerImgName(mobileBannerImgName);
        model.setMobileGroundImgName(mobileGroundImgName);
        model.setType(type);
        model.setLastOptUid(lastOptUid);
        model.setLastOptUserName(lastOptUserName);
        model.setStatus(HomePageConst.HomePageMgr.ADD_STATUS_VALUE);
        model.setCreateTime(System.currentTimeMillis() / 1000L);
        model.setUpdateTime(System.currentTimeMillis() / 1000L);
        rspObj = addCard(model, files);
        rspObj.setExtendMsg(null);
        return rspObj;
    }

    private BaseResponseModel addCard(HomePageCardDaoModel model, MultipartFile[] files){
        BaseResponseModel rspObj = new BaseResponseModel();
        try {
            // 上传PC客户端图片
            rspObj = ftpOptService.uploadFileNoDomainName(files[0], HomePageConst.HomePageMgr.HOME_PAGE_CARD_PC_IMG_BASE_PATH);
            if (rspObj.getRetCode() == Const.FtpFileStatus.STATUS_FILE_OK) {
                model.setPcImgUrl(rspObj.getExtendMsg());
            }
            // 上传移动端图片
            rspObj = ftpOptService.uploadFileNoDomainName(files[1], HomePageConst.HomePageMgr.HOME_PAGE_CARD_MOBILE_IMG_BASE_PATH);
            if (rspObj.getRetCode() == Const.FtpFileStatus.STATUS_FILE_OK) {
                model.setMobileImgUrl(rspObj.getExtendMsg());
            }
            // 上传PC客户端落地图片
            rspObj = ftpOptService.uploadFileNoDomainName(files[2], HomePageConst.HomePageMgr.HOME_PAGE_CARD_PC_IMG_BASE_PATH);
            if (rspObj.getRetCode() == Const.FtpFileStatus.STATUS_FILE_OK) {
                model.setPcGroundImgUrl(rspObj.getExtendMsg());
            }
            // 上传移动端落地图片
            rspObj = ftpOptService.uploadFileNoDomainName(files[3], HomePageConst.HomePageMgr.HOME_PAGE_CARD_MOBILE_IMG_BASE_PATH);
            if (rspObj.getRetCode() == Const.FtpFileStatus.STATUS_FILE_OK) {
                model.setMobileGroundImgUrl(rspObj.getExtendMsg());
            }
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR);
            rspObj.setRetMsg(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR_STR);
        }
        try {                
            // 插入卡片记录
            mapper.insertCard(model);
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED_STR);
        }
        return rspObj;
    }
    
    @Override
    public ResponseHomePageCardListModel listCards() {
        ResponseHomePageCardListModel rspObj = new ResponseHomePageCardListModel();
        List<HomePageCardDaoModel> list = new ArrayList<>();
        rspObj.setLeftCardList(new ArrayList<>());
        rspObj.setRightCardList(new ArrayList<>());
        try {
            list = mapper.listCards();
            if (MyListUtils.isEmpty(list)) {
                return rspObj;
            } 
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED_STR);
        }
        try {
            for (HomePageCardDaoModel daoModel : list) {
                
                daoModel.setUpdateTimeStr(CommonUtils.stampToDateTimeStr(daoModel.getUpdateTime(), "yyyy/MM/dd HH:mm:ss"));
                if (StringUtil.isNullOrEmpty(daoModel.getPcImgUrl())) {
                    daoModel.setPcImgUrl("");
                } else {
                    daoModel.setPcImgUrl(domNameUrl + daoModel.getPcImgUrl());
                }
                if (StringUtil.isNullOrEmpty(daoModel.getPcGroundImgUrl())) {
                    daoModel.setPcGroundImgUrl("");
                } else {
                    daoModel.setPcGroundImgUrl(domNameUrl + daoModel.getPcGroundImgUrl());
                }
                if (StringUtil.isNullOrEmpty(daoModel.getMobileGroundImgUrl())) {
                    daoModel.setMobileGroundImgUrl("");
                } else {
                    daoModel.setMobileGroundImgUrl(domNameUrl + daoModel.getMobileGroundImgUrl());
                }
                if (StringUtil.isNullOrEmpty(daoModel.getMobileImgUrl())) {
                    daoModel.setMobileImgUrl("");
                } else {
                    daoModel.setMobileImgUrl(domNameUrl + daoModel.getMobileImgUrl());
                }
                
                if (daoModel.getType() == HomePageConst.HomePageMgr.LEFT_CARD) {
                    rspObj.getLeftCardList().add(daoModel);
                } else {
                    rspObj.getRightCardList().add(daoModel);                    
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return rspObj;
    }

    @Override
    public BaseResponseModel editCards(HomePageCardDaoModel model) {
        BaseResponseModel rspObj = new BaseResponseModel();
        model.setUpdateTime(System.currentTimeMillis() / 1000L);
        /**
         * 把url除去域名，
         * 例如：http://dev.home.phiwifi.com/file/images/home_page_banner/mobile/996e026afe834b959f9e4ea7acdad99c.jpg
         * 变成：/file/images/home_page_banner/mobile/996e026afe834b959f9e4ea7acdad99c.jpg
         */
        model.setPcImgUrl(model.getPcImgUrl().replace(domNameUrl, ""));
        model.setMobileImgUrl(model.getMobileImgUrl().replace(domNameUrl, ""));
        model.setPcGroundImgUrl(model.getPcGroundImgUrl().replace(domNameUrl, ""));
        model.setMobileGroundImgUrl(model.getMobileGroundImgUrl().replace(domNameUrl, ""));
        try {
            mapper.updateCard(model);
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED_STR);
        }
        return rspObj;
    }
}
