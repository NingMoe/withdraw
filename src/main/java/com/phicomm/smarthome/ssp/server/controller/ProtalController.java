package com.phicomm.smarthome.ssp.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.consts.Const.ResponseStatus;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.model.SwPortalPictureModel;
import com.phicomm.smarthome.ssp.server.service.FtpOptService;
import com.phicomm.smarthome.ssp.server.service.PortalService;
import com.phicomm.smarthome.util.OptDateUtil;
import com.phicomm.smarthome.util.StringUtil;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author fujiang.mao
 *
 */
@RestController
@ComponentScan
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class ProtalController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(ProtalController.class);

    @Autowired
    private FtpOptService ftpOptService;

    @Autowired
    private PortalService portalService;

    /**
     * portal页图片生效时间检测 每分钟执行一次
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void portalImgStatusCheck() {
        LOGGER.info("portalImgStatusCheck 每分钟执行一次，开始。。。。。。");
        portalService.portalImgStatusCheck();
        LOGGER.info("portalImgStatusCheck 每分钟执行一次，结束！");
    }

    /**
     * portal页上传图片
     * 
     * @param request
     * @param file
     *            上传文件
     * @param device_name
     *            设备名（如：pc、h5）
     * @param effective_date
     *            生效日期
     * @param old_img_url
     *            旧图片地址（非必须）
     * @return ret_code ret_msg
     */
    @RequestMapping(value = "/upload_picture", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> upload(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("file") MultipartFile file, @RequestParam("device_name") String deviceName,
            @RequestParam("effective_time") String effectiveTime,
            @RequestParam(value = "old_img_url", required = false) String oldImgUrl) {
        LOGGER.info("upload_picture device_name[{}] effective_time[{}] old_img_url[{}]", deviceName, effectiveTime,
                oldImgUrl);
        String imgPath = "/portal";
        if (!StringUtil.isNullOrEmpty(deviceName)) {
            imgPath += "/" + deviceName;
        }
        
        BaseResponseModel model = ftpOptService.uploadFile(file, BaseController.IMG_BASE_PATH + imgPath);
        
        // 图片上传成功后，数据库插入相关数据
        if (model.getRetCode() == Const.FtpFileStatus.STATUS_FILE_OK) {
            SwPortalPictureModel pictureModel = new SwPortalPictureModel();
            pictureModel.setDeviceName(deviceName);
            pictureModel.setImgUrl(model.getExtendMsg());
            pictureModel.setEffectiveTime(OptDateUtil.getLTimeByStr(effectiveTime));
            pictureModel.setCreateTime(OptDateUtil.getNowTimeLong());
            pictureModel.setUpdateTime(OptDateUtil.getNowTimeLong());
            int addRst = portalService.addImgInfo(pictureModel);
            // 数据库写入portal页图片信息失败
            if (addRst < 1) {
                model.setRetCode(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR);
                model.setRetMsg(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR_STR);
            } else {                
                model.setRetCode(Const.ResponseStatus.STATUS_OK);
                model.setRetMsg(Const.ResponseStatus.STATUS_OK_STR);
            }
        } else {
            // 服务器上传图片失败
            model.setRetCode(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR);
            model.setRetMsg(Const.FtpFileStatus.STATUS_FILE_UPLOAD_ERROR_STR);
        }
        
        return successResponse(model);
    }

    /**
     * 管理后台获取portal图片
     * 
     * @param requestParas
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/query_portal", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> queryPortal(@RequestBody SwPortalPictureModel requestParas) {
        BaseResponseModel baseResponseModel = new BaseResponseModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;

        if (requestParas == null) {
            errCode = ResponseStatus.STATUS_COMMON_NULL;
            errMsg = ResponseStatus.STATUS_COMMON_NULL_STR;
            baseResponseModel.setRetCode(errCode);
            baseResponseModel.setRetMsg(errMsg);
            return new SmartHomeResponse(errCode, errMsg, baseResponseModel);
        }
        if (StringUtil.isNullOrEmpty(requestParas.getDeviceName())) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            return new SmartHomeResponse(errCode, errMsg, null);
        }
        SwPortalPictureModel queryPortal = portalService.queryPortal(requestParas.getDeviceName());
        return new SmartHomeResponse(errCode, errMsg, queryPortal);
    }

}