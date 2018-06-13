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
import com.phicomm.smarthome.ssp.server.controller.BaseController;
import com.phicomm.smarthome.ssp.server.model.dao.HomePageCardDaoModel;
import com.phicomm.smarthome.ssp.server.service.HomePageCardService;
import com.phicomm.smarthome.util.StringUtil;

/**
 * Created by zhengwang.li on 2018/4/17.
 */
@RestController
@RequestMapping("sw_mgr/home_page_card")
public class HomePageCardController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(HomePageCardController.class);

    @Autowired
    private HomePageCardService service;

    /**
     * 添加和编辑卡片信息
     * 
     * @return
     */
    @RequestMapping(value = "/add_card", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> addCard(@RequestParam(required = false, value = "id") Integer id,
            @RequestParam("files") MultipartFile[] files, @RequestParam("content") String content,
            @RequestParam("pc_banner_img_name") String pcBannerImgName, @RequestParam("pc_ground_img_name") String pcGroundImgName,
            @RequestParam("mobile_banner_img_name") String mobileBannerImgName, @RequestParam("mobile_ground_img_name") String mobileGroundImgName,
            @RequestParam("type") int type, @RequestParam("lastOptUid") long lastOptUid,
            @RequestParam("real_name") String lastOptUserName) {
        try {
            if (files.length != 4 || StringUtil.isNullOrEmpty(content) || type == 0
                    || StringUtil.isNullOrEmpty(pcBannerImgName) || StringUtil.isNullOrEmpty(pcGroundImgName)
                    || StringUtil.isNullOrEmpty(mobileBannerImgName) 
                    || StringUtil.isNullOrEmpty(mobileGroundImgName)) {
                return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                        Const.ResponseStatus.STATUS_COMMON_NULL_STR);
            }
            id = id == null ? 0 : id;
            return successResponse(service.addCard(id, files, content, 
                    pcBannerImgName, pcGroundImgName, mobileBannerImgName, mobileGroundImgName, type, lastOptUid, lastOptUserName));
        } catch (Exception e) {
            LOGGER.error(e);
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED,
                    Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }

    /**
     * 获取卡片信息
     * 
     * @return
     */
    @RequestMapping(value = "/list_cards", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> listCards() {
        try {
            return successResponse(service.listCards());
        } catch (Exception e) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED,
                    Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }

    /**
     * 获取卡片信息
     * 
     * @return
     */
    @RequestMapping(value = "/edit_card", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> editCards(@RequestBody HomePageCardDaoModel model) {
        if (model.getId() == 0 || StringUtil.isNullOrEmpty(model.getMobileGroundImgUrl())
                || StringUtil.isNullOrEmpty(model.getPcImgUrl()) || StringUtil.isNullOrEmpty(model.getPcGroundImgUrl())
                || StringUtil.isNullOrEmpty(model.getMobileImgUrl())
                || StringUtil.isNullOrEmpty(model.getPcBannerImgName())
                || StringUtil.isNullOrEmpty(model.getPcGroundImgName()) 
                || StringUtil.isNullOrEmpty(model.getMobileBannerImgName()) 
                || StringUtil.isNullOrEmpty(model.getMobileGroundImgName())
                || model.getType() == 0 || model.getLastOptUid() == 0 || StringUtil.isNullOrEmpty(model.getLastOptUserName())) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                    Const.ResponseStatus.STATUS_COMMON_NULL_STR);
        }
        try {
            if (StringUtil.isNullOrEmpty(model.getContent())) {
                model.setContent("");
            }
            return successResponse(service.editCards(model));
        } catch (Exception e) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED,
                    Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }
}
