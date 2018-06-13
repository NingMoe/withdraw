package com.phicomm.smarthome.ssp.server.controller.offcmgr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.consts.HomePageConst;
import com.phicomm.smarthome.ssp.server.controller.BaseController;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.dao.HomePageBannerDaoModel;
import com.phicomm.smarthome.ssp.server.model.request.MoveReqModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseHomePageBannerListModel;
import com.phicomm.smarthome.ssp.server.service.HomePageBannerService;
import com.phicomm.smarthome.util.StringUtil;

/**
 * Created by zhengwang.li on 2018/4/17.
 */
@RestController
@RequestMapping("/sw_mgr/home_page_banner")
public class HomePageBannerController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(HomePageBannerController.class);

    @Autowired
    private HomePageBannerService homePageBannerService;

    /**
     * 添加和编辑banner信息
     * 
     * @return
     */
    @RequestMapping(value = "/add_banner", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> addBanner(@RequestParam("files") MultipartFile[] files,
            @RequestParam(required = false, value = "id") Integer id, @RequestParam("action") int action,
            @RequestParam("pc_banner_img_name") String pcBannerImgName,
            @RequestParam(required = false, value = "pc_ground_img_name") String pcGroundImgName,
            @RequestParam("mobile_banner_img_name") String mobileBannerImgName,
            @RequestParam(required = false, value = "mobile_ground_img_name") String mobileGroundImgName,
            @RequestParam("lastOptUid") long lastOptUid, @RequestParam("real_name") String lastOptUserName) {
        BaseResponseModel rspObj = new BaseResponseModel();
        try {
            if ((action != HomePageConst.HomePageMgr.SKIP_NO_ACTION && action != HomePageConst.HomePageMgr.SKIP_ACTION)
                    || files == null || files.length < 2 || lastOptUid == 0 || StringUtil.isNullOrEmpty(lastOptUserName)
                    || StringUtil.isNullOrEmpty(pcBannerImgName) || StringUtil.isNullOrEmpty(mobileBannerImgName)) {
                return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                        Const.ResponseStatus.STATUS_COMMON_NULL_STR);
            }
            LOGGER.info("addBanner's method: file's count[{}], id[{}], action[{}], lastOptUid[{}], lastOptUserName[{}]",
                    files.length, id, action, lastOptUid, lastOptUserName);
            id = id == null ? 0 : id;
            rspObj = homePageBannerService.addBanner(files, id, action, pcBannerImgName, pcGroundImgName,
                    mobileBannerImgName, mobileGroundImgName, lastOptUid, lastOptUserName);
            return successResponse(rspObj);
        } catch (Exception e) {
            LOGGER.error(e);
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED,
                    Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }

    /**
     * 描述：删除banner
     * 
     * @return
     */
    @RequestMapping(value = "/delete_banner", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> deleteBanner(@RequestBody HomePageBannerDaoModel model) {
        BaseResponseModel rspObj = new BaseResponseModel();
        try {
            if (model.getId() == 0 || model.getLastOptUid() == 0
                    || StringUtil.isNullOrEmpty(model.getLastOptUserName())) {
                return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                        Const.ResponseStatus.STATUS_COMMON_NULL_STR);
            }
            LOGGER.info("addBanner's method: Id[{}], lastOptUid[{}], lastOptUserName[{}]", model.getId(),
                    model.getLastOptUid(), model.getLastOptUserName());
            rspObj = homePageBannerService.deleteBanner(model);
            return successResponse(rspObj);
        } catch (Exception e) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED,
                    Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }

    /**
     * 描述：banner发布
     * 
     * @return
     */
    @RequestMapping(value = "/public_banner", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> publicBanner(@RequestBody HomePageBannerDaoModel model) {
        if (model.getLastOptUid() == 0 || StringUtil.isNullOrEmpty(model.getLastOptUserName())) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL, Const.ResponseStatus.STATUS_COMMON_NULL_STR);
        }
        LOGGER.info("publicBanner's method: lastOptUid[{}], lastOptUserName[{}]", model.getLastOptUid(),
                model.getLastOptUserName());
        BaseResponseModel rspObj = new BaseResponseModel();
        try {
            rspObj = homePageBannerService.publicBanner(model);
            return successResponse(rspObj);
        } catch (Exception e) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED,
                    Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }

    /**
     * 描述:上下移动banner
     * 
     * @return
     */
    @RequestMapping(value = "/move_banner", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> moveBanner(@RequestBody MoveReqModel model) {
        if (model.getUpId() == 0 || model.getUpSequence() == 0 || model.getDownId() == 0 || model.getDownSequence() == 0
                || model.getLastOptUid() == 0 || StringUtil.isNullOrEmpty(model.getRealName())) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL, Const.ResponseStatus.STATUS_COMMON_NULL_STR);
        }
        LOGGER.info(
                "moveBanner's method: UpId[{}], UpSequence[{}], DownId[{}], DownSequence[{}], LastOptUid[{}], RealName[{}]",
                model.getUpId(), model.getUpSequence(), model.getDownId(), model.getDownSequence(),
                model.getLastOptUid(), model.getRealName());
        BaseResponseModel rspObj = new BaseResponseModel();
        try {
            rspObj = homePageBannerService.moveBanner(model);
            return successResponse(rspObj);
        } catch (Exception e) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED,
                    Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }

    /**
     * 描述:获取banner页信息
     * 
     * @return
     */
    @RequestMapping(value = "/list_banners", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> listBanners() {
        ResponseHomePageBannerListModel rspObj = new ResponseHomePageBannerListModel();
        try {
            LOGGER.info("listBanners method");
            rspObj = homePageBannerService.listBanners();
            return successResponse(rspObj);
        } catch (Exception e) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED,
                    Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }

    /**
     * 描述：编辑banner
     * 
     * @return
     */
    @RequestMapping(value = "/edit_banner", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> editBanner(@RequestBody HomePageBannerDaoModel model) {
        try {
            if (model.getId() == 0 || model.getLastOptUid() == 0
                    || StringUtil.isNullOrEmpty(model.getBackendPcBannerImgUrl())
                    || StringUtil.isNullOrEmpty(model.getBackendMobileBannerImgUrl()) || model.getAction() == 0
                    || StringUtil.isNullOrEmpty(model.getLastOptUserName())) {
                return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                        Const.ResponseStatus.STATUS_COMMON_NULL_STR);
            }
            LOGGER.info(
                    "edit's method: Id[{}], BackendPcBannerImgUrl[{}], getBackendMobileBannerImgUrl[{}], lastOptUid[{}], lastOptUserName[{}]",
                    model.getId(), model.getBackendPcBannerImgUrl(), model.getBackendMobileBannerImgUrl(),
                    model.getLastOptUid(), model.getLastOptUserName());
            BaseResponseModel rspObj = new BaseResponseModel();
            rspObj = homePageBannerService.editBanner(model);
            return successResponse(rspObj);
        } catch (Exception e) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED,
                    Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }
}
