package com.phicomm.smarthome.ssp.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.consts.HomePageConst;
import com.phicomm.smarthome.ssp.server.dao.HomePageBannerMapper;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.dao.HomePageBannerDaoModel;
import com.phicomm.smarthome.ssp.server.model.request.MoveReqModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseHomePageBannerListModel;
import com.phicomm.smarthome.ssp.server.service.FtpOptService;
import com.phicomm.smarthome.ssp.server.service.HomePageBannerService;
import com.phicomm.smarthome.ssp.server.util.CommonUtils;
import com.phicomm.smarthome.ssp.server.util.MyListUtils;

/**
 * 描述：首页banner服务实现 Created by zhengwang.li on 2018/4/17.
 */
@Service
public class HomePageBannerServiceImpl implements HomePageBannerService {

    private static final Logger LOGGER = LogManager.getLogger(HomePageBannerServiceImpl.class);

    /** 没跳转的文件数量为2 */
    private static final int FILE_COUNT_BY_NO_SKIP = 2;

    /** 跳转到图片文件数量为4 */
    private static final int FILE_COUNT_BY_SKIP = 4;

    /** banner最多可以添加5张轮播图 */
    private static final int COUNT_BANNER = 5;

    @Value("${FTP_IMG_BASE_PATH}")
    private String domNameUrl; 
    
    @Autowired
    private FtpOptService ftpOptService;

    @Autowired
    private HomePageBannerMapper mapper;

    @Override
    public BaseResponseModel addBanner(MultipartFile[] files, int id, int action, 
            String pcBannerImgName, String pcGroundImgName,
            String mobileBannerImgName, String mobileGroundImgName,
            long lastOptUid,
            String lastOptUserName) {
        HomePageBannerDaoModel model = new HomePageBannerDaoModel();
        BaseResponseModel rspObj = new BaseResponseModel();
        try {
            int countBanner = mapper.countBanners();
            LOGGER.info("addBanner's method: banner's count[{}]", countBanner);
            if (countBanner >= COUNT_BANNER && id == 0) {
                rspObj.setRetCode(Const.ResponseStatus.STATUS_COUNT_BANNER_EXCEED_ERROR);
                rspObj.setRetMsg(Const.ResponseStatus.STATUS_COUNT_BANNER_EXCEED_ERROR_STR);
                return rspObj;
            }
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED_STR);
            return rspObj;
        }
        // 文件数量为2
        if (files.length >= FILE_COUNT_BY_NO_SKIP) {
            try {
                // 上传PC客户端图片
                rspObj = ftpOptService.uploadFileNoDomainName(files[0], HomePageConst.HomePageMgr.HOME_PAGE_BANNER_PC_IMG_BASE_PATH);
                if (rspObj.getRetCode() == Const.FtpFileStatus.STATUS_FILE_OK) {
                    model.setBackendPcBannerImgUrl(rspObj.getExtendMsg());
                    model.setPcBannerImgName(pcBannerImgName);
                } else {
                    rspObj.setRetCode(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR);
                    rspObj.setRetMsg(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR_STR);
                    return rspObj;
                }
                // 上传移动端图片
                rspObj = ftpOptService.uploadFileNoDomainName(files[1], HomePageConst.HomePageMgr.HOME_PAGE_BANNER_MOBILE_IMG_BASE_PATH);
                if (rspObj.getRetCode() == Const.FtpFileStatus.STATUS_FILE_OK) {
                    model.setBackendMobileBannerImgUrl(rspObj.getExtendMsg());
                    model.setMobileBannerImgName(mobileBannerImgName);
                } else {
                    rspObj.setRetCode(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR);
                    rspObj.setRetMsg(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR_STR);
                    return rspObj;
                }
            } catch (Exception e) {
                LOGGER.error(e);
                rspObj.setRetCode(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR);
                rspObj.setRetMsg(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR_STR);
                return rspObj;
            }
        }
        // 如果文件为四个，则为跳转到图片动作
        if (files.length == FILE_COUNT_BY_SKIP) {
            try {
                // 上传PC客户端落地图片
                rspObj = ftpOptService.uploadFileNoDomainName(files[2], HomePageConst.HomePageMgr.HOME_PAGE_BANNER_PC_IMG_BASE_PATH);
                if (rspObj.getRetCode() == Const.FtpFileStatus.STATUS_FILE_OK) {
                    model.setBackendPcGroundImgUrl(rspObj.getExtendMsg());
                    model.setPcGroundImgName(pcGroundImgName);
                } else {
                    rspObj.setRetCode(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR);
                    rspObj.setRetMsg(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR_STR);
                    return rspObj;
                }
                // 上传移动端落地图片
                rspObj = ftpOptService.uploadFileNoDomainName(files[3], HomePageConst.HomePageMgr.HOME_PAGE_BANNER_MOBILE_IMG_BASE_PATH);
                if (rspObj.getRetCode() == Const.FtpFileStatus.STATUS_FILE_OK) {
                    model.setBackendMobileGroundImgUrl(rspObj.getExtendMsg());
                    model.setMobileGroundImgName(mobileGroundImgName);
                } else {
                    rspObj.setRetCode(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR);
                    rspObj.setRetMsg(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR_STR);
                    return rspObj;
                }
            } catch (Exception e) {
                LOGGER.error(e);
                rspObj.setRetCode(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR);
                rspObj.setRetMsg(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR_STR);
                return rspObj;
            }
        } else {
            // 如果banner动作为无，则落地图片均为空字符串
            model.setBackendPcGroundImgUrl("");
            model.setBackendMobileGroundImgUrl("");
            model.setPcGroundImgName("");
            model.setMobileGroundImgName("");
        }
        model.setAction(action);
        model.setId(id);
        model.setLastOptUid(lastOptUid);
        model.setLastOptUserName(lastOptUserName);
        try {
            // 查询最大排序值
            Integer maxSequence = mapper.getMaxSequence();
            LOGGER.info("addBanner's method: maxSequence[{}]", maxSequence);
            model.setSequence(maxSequence == null ? 1 : maxSequence + 1);
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED_STR);
            return rspObj;
        }
        // 往数据库添加或者更新数据
        addBanner(model);
        rspObj.setRetCode(Const.ResponseStatus.STATUS_OK);
        rspObj.setRetMsg(Const.ResponseStatus.STATUS_OK_STR);
        rspObj.setExtendMsg(null);
        return rspObj;
    }

    /**
     * 添加或者编辑数据
     * @param model
     */
    private void addBanner(HomePageBannerDaoModel model) {
        model.setUpdateTime(System.currentTimeMillis() / 1000L);
        model.setCreateTime(System.currentTimeMillis() / 1000L);
        model.setStatus(HomePageConst.HomePageMgr.ADD_STATUS_VALUE);
        try {
            // 添加记录
            mapper.addBanner(model);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    /** 删除banner */
    @Override
    public BaseResponseModel deleteBanner(HomePageBannerDaoModel model) {
        BaseResponseModel rspObj = new BaseResponseModel();
        try {
            int status = mapper.getStatusById(model.getId());
            // 设置数据库status状态为-1
            status = HomePageConst.HomePageMgr.DELTE_PUBLIC_STATUS_VALUE;
            LOGGER.info("addBanner's method: maxSequence[{}]", status);
            model.setStatus(status);
            model.setUpdateTime(System.currentTimeMillis() / 1000L);
            mapper.deleteBanner(model);
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED_STR);
        }
        return rspObj;
    }

    /** 发布banner */
    @Override
    public BaseResponseModel publicBanner(HomePageBannerDaoModel model) {
        BaseResponseModel rspObj = new BaseResponseModel();
        // 查询状态为0,1,2的banner记录
        List<HomePageBannerDaoModel> list = new ArrayList<>();
        try {
            list = mapper.listBanners();
            if (MyListUtils.isEmpty(list)) {
                return rspObj;
            }
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED_STR);
            return rspObj;
        }
        // 更新banner图片状态及其数据
        updateListBannersStatus(list, model);
        return rspObj;
    }

    @Transactional
    /** 更新banner图片状态及其数据 */
    private void updateListBannersStatus(List<HomePageBannerDaoModel> list, HomePageBannerDaoModel model) {
        /**
         * 1.删除官网所有记录
         */
        mapper.deleteWebsiteBanner();
        for (HomePageBannerDaoModel daoModel : list) {
            daoModel.setLastOptUid(model.getLastOptUid());
            daoModel.setLastOptUserName(model.getLastOptUserName());
            model.setUpdateTime(System.currentTimeMillis() / 1000L);
            model.setCreateTime(System.currentTimeMillis() / 1000L);
        }
        /**
         * 添加后台的sw_home_page_banner表所有status=0的数据
         */
        try {
            mapper.addWebsiteBannerList(list);
        } catch (Exception e) {
            LOGGER.error(e);
        }
        // 存入数据库，发布时间及其操作人信息
        try {
            mapper.insertPublicLog(model);
        } catch (Exception e) {
            LOGGER.error(e);
        }
    }

    /** 更改banner记录的sequence值 */
    @Transactional
    @Override
    public BaseResponseModel moveBanner(MoveReqModel model) {
        BaseResponseModel rspObj = new BaseResponseModel();
        try {
            int count = mapper.updateSequenceById(model.getUpId(), model.getDownSequence(),
                    System.currentTimeMillis() / 1000L, model.getRealName(), model.getLastOptUid());
            if (count > 0) {
                mapper.updateSequenceById(model.getDownId(), model.getUpSequence(), System.currentTimeMillis() / 1000L,
                        model.getRealName(), model.getLastOptUid());
            }
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED_STR);
        }
        return rspObj;
    }

    /** 查询banner列表 */
    @Override
    public ResponseHomePageBannerListModel listBanners() {
        ResponseHomePageBannerListModel rspObj = new ResponseHomePageBannerListModel();
        try {
            List<HomePageBannerDaoModel> list = mapper.listBannersForBackend();
            LOGGER.info("listBanners's method: listBanners's count[{}]", list.size());
            for (HomePageBannerDaoModel model : list) {
                // 时间戳转成时间格式
                model.setUpdateTimeStr(CommonUtils.stampToDateTimeStr(model.getUpdateTime(), "yyyy/MM/dd HH:mm:ss"));
                model.setBackendPcBannerImgUrl(domNameUrl + model.getBackendPcBannerImgUrl());
                model.setBackendMobileBannerImgUrl(domNameUrl + model.getBackendMobileBannerImgUrl());
                if (model.getAction() == HomePageConst.HomePageMgr.SKIP_ACTION) {                    
                    model.setBackendPcGroundImgUrl(domNameUrl + model.getBackendPcGroundImgUrl());
                    model.setBackendMobileGroundImgUrl(domNameUrl + model.getBackendMobileGroundImgUrl());
                }
            }
            // 查询发布时间
            Long publicTime = mapper.getPublicTime();
            if (publicTime != null) {                
                rspObj.setPublicTime(CommonUtils.stampToDateTimeStr(publicTime, "yyyy/MM/dd HH:mm:ss"));
            } else {
                rspObj.setPublicTime("");
            }
            rspObj.setList(list);
            LOGGER.info("listBanners's method: publicTime[{}]", rspObj.getPublicTime());
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_QUERY_FAILED_STR);
        }
        return rspObj;
    }

    
    /**
     * 编辑banner信息
     */
    @Override
    public BaseResponseModel editBanner(HomePageBannerDaoModel model) {
        BaseResponseModel rspObj = new BaseResponseModel();
        model.setUpdateTime(System.currentTimeMillis() / 1000L);
        try {
            /**
             * 把url除去域名，
             * 例如：http://dev.home.phiwifi.com/file/images/home_page_banner/mobile/996e026afe834b959f9e4ea7acdad99c.jpg
             * 变成：/file/images/home_page_banner/mobile/996e026afe834b959f9e4ea7acdad99c.jpg
             */
//            model.setBackendPcBannerImgUrl(model.getBackendPcBannerImgUrl().substring(model.getBackendPcBannerImgUrl().indexOf("/images/sharedwifi-backend")));
            model.setBackendPcBannerImgUrl(model.getBackendPcBannerImgUrl().replace(domNameUrl, ""));
            model.setBackendMobileBannerImgUrl(model.getBackendMobileBannerImgUrl().replace(domNameUrl, ""));
            if (model.getAction() == HomePageConst.HomePageMgr.SKIP_ACTION) {
                model.setBackendPcGroundImgUrl(model.getBackendPcGroundImgUrl().replace(domNameUrl, ""));
                model.setBackendMobileGroundImgUrl(model.getBackendMobileGroundImgUrl().replace(domNameUrl, ""));
            } else {
                model.setBackendPcGroundImgUrl("");
                model.setBackendMobileGroundImgUrl("");
                model.setPcGroundImgName("");
                model.setMobileGroundImgName("");
            }
            mapper.editBannerById(model);
        } catch (Exception e) {
            LOGGER.error(e);
            rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED_STR);
        }
        return rspObj;
    }
}
