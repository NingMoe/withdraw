package com.phicomm.smarthome.ssp.server.controller.offcmgr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.phicomm.smarthome.ssp.server.service.FtpOptService;
import com.phicomm.smarthome.util.StringUtil;

@RestController
@RequestMapping("sw_mgr/home_page")
public class HomePageUploadFileController extends BaseController {
    
    private static final Logger LOGGER = LogManager.getLogger(HomePageUploadFileController.class);
    
    @Autowired
    private FtpOptService ftpOptService;
    
    @Value("${FTP_IMG_BASE_PATH}")
    private String domNameUrl;
    
    /**
     * 上传文件
     * @param client
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload_picture", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> addIntroduce(@RequestParam("client") String client, 
            @RequestParam("file") MultipartFile file) {
        BaseResponseModel rspObj = new BaseResponseModel();
        if (file.isEmpty() || StringUtil.isNullOrEmpty(client)) {
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                    Const.ResponseStatus.STATUS_COMMON_NULL_STR);
        }
        try {
            switch (client) {
            case "PC_BANNER":
                rspObj = ftpOptService.uploadFileNoDomainName(file, HomePageConst.HomePageMgr.HOME_PAGE_BANNER_PC_IMG_BASE_PATH);
                break;
            case "MOBILE_BANNER":
                rspObj = ftpOptService.uploadFileNoDomainName(file, HomePageConst.HomePageMgr.HOME_PAGE_BANNER_MOBILE_IMG_BASE_PATH);
                break;
            case "PC_CARD":
                rspObj = ftpOptService.uploadFileNoDomainName(file, HomePageConst.HomePageMgr.HOME_PAGE_CARD_PC_IMG_BASE_PATH);
                break;
            case "MOBILE_CARD":
                rspObj = ftpOptService.uploadFileNoDomainName(file, HomePageConst.HomePageMgr.HOME_PAGE_CARD_MOBILE_IMG_BASE_PATH);
                break;
            case "PC_INTRODUCE":
                rspObj = ftpOptService.uploadFileNoDomainName(file, HomePageConst.HomePageMgr.HOME_PAGE_INTRODUCE_PC_IMG_BASE_PATH);
                break;
            case "MOBILE_INTRODUCE":
                rspObj = ftpOptService.uploadFileNoDomainName(file, HomePageConst.HomePageMgr.HOME_PAGE_INTRODUCE_MOBILE_IMG_BASE_PATH);
                break;
            default:
                break;
            }
            if (rspObj.getRetCode() != Const.FtpFileStatus.STATUS_FILE_OK) {
                throw new RuntimeException("upload picture error!");
            }
            rspObj.setRetCode(Const.ResponseStatus.STATUS_OK);
            rspObj.setRetMsg(Const.ResponseStatus.STATUS_OK_STR);
            rspObj.setExtendMsg(domNameUrl + rspObj.getExtendMsg());
            return successResponse(rspObj);
        } catch (Exception e) {
            LOGGER.error(e);
            return errorResponse(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR, Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR_STR);
        }
    }
}
