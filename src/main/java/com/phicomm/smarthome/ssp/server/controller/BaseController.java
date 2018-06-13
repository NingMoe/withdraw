package com.phicomm.smarthome.ssp.server.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.phicomm.smarthome.cache.Cache;
import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.ssp.server.consts.Const.ResponseStatus;
import com.phicomm.smarthome.ssp.server.util.MyResponseutils;
import com.phicomm.smarthome.util.StringUtil;

/**
 * 基础controller
 * 
 * @author fujiang.mao
 *
 */
public abstract class BaseController {

    public static final String IMG_BASE_PATH = "/images/sharedwifi-backend";

    /**
     * 用户标识前缀SYS_USER_
     */
    private static final String SYS_USER_PRIFIX = "SYS_USER_";

    /**
     * 用户登录状态
     */
    private static final String USER_LOG_STATE = "USER_LOG_STATE";

    @Autowired
    private Cache cache;

    public void setUserLogInfo(String userName, String infoKey, String infoValue) {
        cache.putHashValue(SYS_USER_PRIFIX + userName, infoKey, infoValue);
    }

    /**
     * 用户是否登录成功
     * 
     * @param username
     * @return
     */
    public boolean userIsLogSuccess(String userName) {

        if (cache.getHashValue(SYS_USER_PRIFIX + userName, USER_LOG_STATE) != null
                && "1".equals(cache.getHashValue(SYS_USER_PRIFIX + userName, USER_LOG_STATE))) {
            // 每次校验到用户登录成功，更新key的过期时间，有效时间5~10分钟
            cache.putHashValue(SYS_USER_PRIFIX + userName, USER_LOG_STATE, "1");
            return true;
        }
        return false;
    }

    /**
     * 缓存设置用户登录成功
     * 
     * @param userName
     */
    public void setUserLogSuccess(String userName) {
        setUserLogInfo(userName, USER_LOG_STATE, "1");
    }

    /**
     * 缓存删除用户所有登录信息
     * 
     * @param userName
     */
    public void delUserAllLogInfo(String userName) {
        cache.deleteHash(SYS_USER_PRIFIX + userName);
    }

    /**
     * 删除用户登录信息
     * 
     * @param userName
     * @param infoKey
     * @param infoValue
     */
    public void delUserLogInfo(String userName, String infoKey) {
        cache.deleteHashValue(SYS_USER_PRIFIX + userName, infoKey);
    }

    /**
     * 获取用户登录状态
     * 
     * @param userName
     * @return 1.登陆成功 0.登陆失败或未登录
     */
    public String getUserLogState(String userName) {
        return userIsLogSuccess(userName) ? "1" : "0";
    }

    protected SmartHomeResponse<Object> errorResponse(int errCode) {
        String errMsg = MyResponseutils.parseMsg(errCode);
        return errorResponse(errCode, errMsg);
    }

    protected SmartHomeResponse<Object> errorResponse(int errCode, String errMsg) {
        SmartHomeResponse<Object> response = MyResponseutils.geResponse(null);
        response.setErrCode(errCode);
        if (StringUtil.isNullOrEmpty(errMsg)) {
            response.setErrMsg(MyResponseutils.parseMsg(errCode));
        } else {
            response.setErrMsg(errMsg);
        }
        return response;
    }

    protected SmartHomeResponse<Object> successResponse(Object obj) {
        SmartHomeResponse<Object> response = MyResponseutils.geResponse(obj);
        response.setErrCode(ResponseStatus.STATUS_OK);
        response.setErrMsg(MyResponseutils.parseMsg(ResponseStatus.STATUS_OK));
        return response;
    }
}
