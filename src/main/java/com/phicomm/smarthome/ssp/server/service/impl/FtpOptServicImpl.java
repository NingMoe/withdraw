package com.phicomm.smarthome.ssp.server.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.model.BaseResponseModel;
import com.phicomm.smarthome.ssp.server.service.FtpOptService;
import com.phicomm.smarthome.ssp.server.util.FtpUtil;
import com.phicomm.smarthome.ssp.server.util.UUIDUtils;

/**
 * 图片上传服务
 * <p>
 * Title: PictureServicImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @version 1.0
 */
@Service
public class FtpOptServicImpl implements FtpOptService {

    @Value("${FTP_ADDRESS}")
    private String ftpAddress;

    @Value("${FTP_PORT}")
    private Integer ftpPort;

    @Value("${FTP_USERNAME}")
    private String ftpUsername;

    @Value("${FTP_PASSWORD}")
    private String ftpPasswd;

    @Value("${FTP_BASE_PATH}")
    private String ftpBasePath;

    @Value("${FTP_IMG_BASE_PATH}")
    private String ftpImgBasePath;

    /***
     * 文件上传功能
     * <p>
     * Title: uploadFile
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param uploadFile
     * @return
     */
    @Override
    public BaseResponseModel uploadFile(MultipartFile uploadFile, String imgPath) {
        BaseResponseModel model = new BaseResponseModel();
        try {
            // 获取源文件扩展名
            String oldName = uploadFile.getOriginalFilename();

            // 形成新的文件扩展名
            String newName = UUIDUtils.create() + oldName.substring(oldName.lastIndexOf("."));

            // 上传
            boolean result = FtpUtil.uploadFile(ftpAddress, ftpPort, ftpUsername, ftpPasswd, ftpBasePath,
                    imgPath, newName, uploadFile.getInputStream());
            if (!result) {
                model.setRetCode(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED);
                model.setRetMsg(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED_STR);
                return model;
            }
            model.setRetCode(Const.FtpFileStatus.STATUS_FILE_OK);
            model.setRetMsg(Const.FtpFileStatus.STATUS_FILE_OK_STR);
            model.setExtendMsg(ftpImgBasePath + imgPath + "/" + newName);
            return model;

        } catch (Exception e) {
            model.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            model.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
            return model;
        }
    }

    /***
     * 文件上传功能，返回的下载地址不带域名
     * <p>
     * Title: uploadFile
     * </p>
     * <p>
     * Description:
     * </p>
     *
     * @param uploadFile
     * @return
     */
    @Override
    public BaseResponseModel uploadFileNoDomainName(MultipartFile uploadFile, String imgPath) {
        BaseResponseModel model = new BaseResponseModel();
        try {
            // 获取源文件扩展名
            String oldName = uploadFile.getOriginalFilename();

            // 形成新的文件扩展名
            String newName = UUIDUtils.create() + oldName.substring(oldName.lastIndexOf("."));

            // 上传
            boolean result = FtpUtil.uploadFile(ftpAddress, ftpPort, ftpUsername, ftpPasswd, ftpBasePath,
                    imgPath, newName, uploadFile.getInputStream());
            if (!result) {
                model.setRetCode(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED);
                model.setRetMsg(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED_STR);
                return model;
            }
            model.setRetCode(Const.FtpFileStatus.STATUS_FILE_OK);
            model.setRetMsg(Const.FtpFileStatus.STATUS_FILE_OK_STR);
            model.setExtendMsg(imgPath + "/" + newName);
            return model;

        } catch (Exception e) {
            model.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            model.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
            return model;
        }
    }

    /***
     * 文件删除功能
     * <p>
     * Title: uploadFile
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param uploadFile
     * @return
     */
    @Override
    public BaseResponseModel removeFile(String pathname) {
        BaseResponseModel model = new BaseResponseModel();
        try {
            boolean result = FtpUtil.removeFile(ftpAddress, ftpPort, ftpUsername, ftpPasswd, pathname);
            if (!result) {
                model.setRetCode(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED);
                model.setRetMsg(Const.ResponseStatus.STATUS_COMMON_INSERT_FAILED_STR);
                return model;
            }
            model.setRetCode(Const.FtpFileStatus.STATUS_FILE_OK);
            model.setRetMsg(Const.FtpFileStatus.STATUS_FILE_OK_STR);
            return model;

        } catch (Exception e) {
            model.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            model.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
            return model;
        }
    }

}
