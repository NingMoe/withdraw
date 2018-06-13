package com.phicomm.smarthome.ssp.server.controller.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.consts.Const.FtpFileStatus;
import com.phicomm.smarthome.ssp.server.controller.BaseController;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.UseHelpModel;
import com.phicomm.smarthome.ssp.server.model.request.MoveReqModel;
import com.phicomm.smarthome.ssp.server.model.response.UseHelpResponseModel;
import com.phicomm.smarthome.ssp.server.service.FtpOptService;
import com.phicomm.smarthome.ssp.server.service.UseHelpService;
import com.phicomm.smarthome.ssp.server.util.MyListUtils;
import com.phicomm.smarthome.util.StringUtil;

@ComponentScan
@RestController
@RequestMapping("config/use_help")
public class UseHelpController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(UseHelpController.class);

    /** 更新记录数为0 */
    private static final int UPDATE_RECORD_COUNT = 0;

    @Autowired
    private UseHelpService useHelpService;

    @Value("${FTP_IMG_BASE_PATH}")
    private String FtpImgBasePath;
    
    @Autowired
    private FtpOptService ftpOptService;
    
    @RequestMapping(value = "/upload_original_picture", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> uploadPicture(@RequestParam("file") MultipartFile file){

        BaseResponseModel rpsModel = new BaseResponseModel();
        try {
            
            /** 文件子目录 */
            String filePath = "/sharedwifi/use_help";
            rpsModel = ftpOptService.uploadFileNoDomainName(file, BaseController.IMG_BASE_PATH + filePath);
            // 文件上传成功后
            if (rpsModel.getRetCode() != Const.FtpFileStatus.STATUS_FILE_OK) {
                rpsModel.setExtendMsg(FtpFileStatus.STATUS_FILE_UPLOAD_ERROR_STR);
                rpsModel.setRetCode(FtpFileStatus.STATUS_FILE_UPLOAD_ERROR);
                return successResponse(rpsModel);
            }
            rpsModel.setRetCode(Const.ResponseStatus.STATUS_OK);
            rpsModel.setExtendMsg(FtpImgBasePath + rpsModel.getExtendMsg());
            return successResponse(rpsModel);
        } catch (Exception e) {
            LOGGER.error(e);
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED, Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }
    
    @RequestMapping(value = "/add_help", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> addUseHelp(@RequestBody UseHelpModel model) {
        BaseResponseModel rspObj = new BaseResponseModel();
        try {
            if (model == null || StringUtil.isNullOrEmpty(model.getTitle())
                    || StringUtil.isNullOrEmpty(model.getHtml()) || MyListUtils.isEmpty(model.getHelpContentList())) {
                return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                        Const.ResponseStatus.STATUS_COMMON_NULL_STR);
            }

            LOGGER.info("Title[{}], HelpContentList[{}]", model.getTitle(), model.getHelpContentList());
            LOGGER.info("UseHelpController beganing... ");
            int addCount = useHelpService.addUseHelp(model);
            if (addCount <= UPDATE_RECORD_COUNT) {
                rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED);
                rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED_STR);
            }
            LOGGER.info("UseHelpController end. ");
            return successResponse(rspObj);
        } catch (Exception e) {
            LOGGER.error(e);
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED, Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }

    @RequestMapping(value = "/delete_help", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> deleteUseHelp(@RequestBody UseHelpModel model) {
        BaseResponseModel rspObj = new BaseResponseModel();
        try {
            if (model == null || model.getId() == 0 || model.getLastOptUid() == 0 || StringUtil.isNullOrEmpty(model.getLastOptUserName())) {
                return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                        Const.ResponseStatus.STATUS_COMMON_NULL_STR);
            }
            LOGGER.info("Id[{}], LastOptUid[{}], LastOptUserName[{}]", model.getId(), model.getLastOptUid(), model.getLastOptUserName());
            LOGGER.info("UseHelpController beganing... ");
            int deleteCount = useHelpService.deleteUseHelp(model);
            if (deleteCount <= UPDATE_RECORD_COUNT) {
                rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED);
                rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED_STR);
            }
            LOGGER.info("UseHelpController end. ");
            return successResponse(rspObj);
        } catch (Exception e) {
            LOGGER.error(e);
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED, Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }

    @RequestMapping(value = "/move_help", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> moveUseHelpPosition(@RequestBody MoveReqModel model) {
        BaseResponseModel rspObj = new BaseResponseModel();
        try {
            if (model == null || model.getUpId() == 0 || model.getUpSequence() == 0
                    || model.getDownId() == 0  || model.getDownSequence() == 0
                    || model.getLastOptUid() == 0 || StringUtil.isNullOrEmpty(model.getRealName())) {
                return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                        Const.ResponseStatus.STATUS_COMMON_NULL_STR);
            }
            LOGGER.info("upId[{}], upSequence[{}], downIp[{}], downSequence[{}], LastOptUid[{}], RealName[{}]", model.getUpId(), model.getUpSequence(),
                    model.getDownId(), model.getDownSequence(), model.getLastOptUid(), model.getRealName());
            LOGGER.info("UseHelpController beganing... ");
            int moveCount = useHelpService.moveUseHelp(model);
            if (moveCount <= UPDATE_RECORD_COUNT) {
                rspObj.setRetCode(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED);
                rspObj.setRetMsg(Const.ResponseStatus.STATUS_COMMON_UPDATE_FAILED_STR);
            }
            LOGGER.info("UseHelpController end. ");
            return successResponse(rspObj);
        } catch (Exception e) {
            LOGGER.error(e);
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED, Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }

    @RequestMapping(value = "/query_use_help/{cur_page}/{page_size}", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> getUseHelpList(@PathVariable("cur_page") Integer curPage, @PathVariable("page_size") Integer pageSize, @RequestBody UseHelpModel model) {
        UseHelpResponseModel rspObj = new UseHelpResponseModel();
        try {
            if (curPage == null || curPage == 0 || pageSize == null || pageSize == 0
                    || StringUtil.isNullOrEmpty(model.getClient())) {
                return errorResponse(Const.ResponseStatus.STATUS_COMMON_NULL,
                        Const.ResponseStatus.STATUS_COMMON_NULL_STR);
            }
            LOGGER.info("UseHelpController beganing... ");
            rspObj.setUseHelpList(useHelpService.getUseHelpList(model.getClient(), (curPage - 1) * pageSize, pageSize));
            rspObj.setTotalCount(useHelpService.countUseHelp(model.getClient()));
            LOGGER.info("UseHelpController end. ");
            return successResponse(rspObj);
        } catch (Exception e) {
            LOGGER.error(e);
            return errorResponse(Const.ResponseStatus.STATUS_COMMON_FAILED, Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
    }

}
