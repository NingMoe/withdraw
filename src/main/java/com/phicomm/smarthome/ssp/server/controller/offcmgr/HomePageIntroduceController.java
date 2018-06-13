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
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.dao.HomePageIntroduceDaoModel;
import com.phicomm.smarthome.ssp.server.model.response.ResponseHomePageIntroduceListModel;
import com.phicomm.smarthome.ssp.server.service.HomePageIntroduceService;
import com.phicomm.smarthome.util.StringUtil;

/**
 * Created by zhengwang.li on 2018/4/17.
 */
@RestController
@RequestMapping("sw_mgr/home_page_introduce")
public class HomePageIntroduceController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(HomePageIntroduceController.class);
    
    @Autowired
    private HomePageIntroduceService service;
    
    /** 首页介绍图上传数量  */ 
    private static final int COUNT_HOME_PAGE_INTRODUCE_IMG = 2;
    
    /**
     *  添加和编辑首页介绍信息
     * @return
     */
    @RequestMapping(value = "/add_introduce", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> addIntroduce(@RequestParam(required = false,value="id") Integer id, 
            @RequestParam("files") MultipartFile[] files, 
            @RequestParam("pc_img_name") String pcImgName, @RequestParam("mobile_img_name") String mobileImgName,
            @RequestParam("lastOptUid") long lastOptUid,
            @RequestParam("real_name") String lastOptUserName) {
        BaseResponseModel rspObj = new BaseResponseModel();
        if (files.length < COUNT_HOME_PAGE_INTRODUCE_IMG) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                    Const.ResponseStatus.STATUS_COMMON_NULL_STR);
        }
        try {
            id = id == null? 0 : id;
            rspObj = service.addIntroduce(id, files, pcImgName, mobileImgName, lastOptUid, lastOptUserName);
            return successResponse(rspObj);
        } catch (Exception e) {
            LOGGER.error(e);
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED, Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }

    /**
     *  获取首页介绍信息
     * @return
     */
    @RequestMapping(value = "/get_introduce", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> getIntroduce() {
        ResponseHomePageIntroduceListModel rspObj = new ResponseHomePageIntroduceListModel();
        try {

            rspObj = service.getInstroduce();
            return successResponse(rspObj);
        } catch (Exception e) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED, Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }
    
    /**
     * 
     * @return
     */
   @RequestMapping(value = "/edit_introduce", method = RequestMethod.POST, produces = { "application/json" })
   public SmartHomeResponse<Object> editIntroduce(@RequestBody HomePageIntroduceDaoModel model) {
       BaseResponseModel rspObj = new BaseResponseModel();
       if (model.getId() == 0 || model.getLastOptUid() == 0 || StringUtil.isNullOrEmpty(model.getLastOptUserName())
               || StringUtil.isNullOrEmpty(model.getPcImgUrl()) || StringUtil.isNullOrEmpty(model.getPcImgName())
               || StringUtil.isNullOrEmpty(model.getMobileImgUrl()) || StringUtil.isNullOrEmpty(model.getMobileImgName())) {
           return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                   Const.ResponseStatus.STATUS_COMMON_NULL_STR);
       }
       try {
           rspObj = service.editInstroduce(model);
           return successResponse(rspObj);
       } catch (Exception e) {
           return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED, Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
       }
   }
}
