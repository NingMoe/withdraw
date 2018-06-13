package com.phicomm.smarthome.ssp.server.consts;

public interface FtpConst {
    /**
     * FTP操作自定义状态
     * 
     * @author fujiang.mao
     *
     */
    public interface FtpFileStatus {

        int STATUS_FILE_OK = 0;
        String STATUS_FILE_OK_STR = "文件操作成功";

        int STATUS_FILE_UPLOAD_ERROR = 100001;
        String STATUS_FILE_UPLOAD_ERROR_STR = "文件上传失败";

        int STATUS_FILE_SIZE_EXCEED_ERROR = 100002;
        String STATUS_FILE_SIZE_EXCEED_ERROR_STR = "图片大小超出限制，仅支持大小不超过200K的图片";

        int STATUS_FILE_MD5_ERROR = 100003;
        String STATUS_FILE_MD5_ERROR_STR = "文件MD5值不匹配";
        
        int STATUS_FILE_FORMAT_ERROR = 100004;
        String STATUS_FILE_FORMAT_ERROR_STR = "图片格式错误，仅支持PNG、JPG、JPEG格式的图片";
        
        int STATUS_FILE_IMG_SIZE_ERROR = 100005;
        String STATUS_FILE_IMG_SIZE_ERROR_STR = "图片尺寸错误，仅支持1080*608尺寸的图片";
        
        int STATUS_FILE_IMG_MEANWHILE_EFFECT_ERROR = 100006;
        String STATUS_FILE_IMG_MEANWHILE_EFFECT_ERROR_STR = "同时生效图片已达上限";
        
        int STATUS_TIME_SCOPE_ERROR = 100007;
        String STATUS_TIME_SCOPE_ERROR_STR = "请选择正确的日期和时间";
        
        int STATUS_OTA_FILE_SIZE_EXCEED_ERROR = 100008;
        String STATUS_OTA_FILE_SIZE_EXCEED_ERROR_STR = "固件大小超出限制，仅支持大小不超过30M的固件";
        
        int STATUS_SKIP_IMG_FILE_SIZE_EXCEED_ERROR = 100009;
        String STATUS_SKIP_IMG_FILE_SIZE_EXCEED_ERROR_STR = "图片大小超出限制，仅支持大小不超过200K的图片";
        
        int STATUS_VOID_DMDS_FILE_IMG_SIZE_ERROR = 100011;
        String STATUS_VOID_DMDS_FILE_IMG_SIZE_ERROR_STR = "图片尺寸错误，仅支持200*200尺寸的图片";

        int STATUS_IMG_FILE_SIZE_EXCEED_ERROR = 100012;
        String STATUS_IMG_FILE_SIZE_EXCEED_ERROR_STR = "图片尺寸不正确，请检查一下";
    }
}
