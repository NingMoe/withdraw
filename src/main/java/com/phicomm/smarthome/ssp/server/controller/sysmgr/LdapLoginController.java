package com.phicomm.smarthome.ssp.server.controller.sysmgr;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.phicomm.smarthome.consts.LDAPConst;
import com.phicomm.smarthome.model.SmartHomeResponse;
import com.phicomm.smarthome.model.ldapsys.response.SysUserResponseModel;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.service.LdapSysMgrService;
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
public class LdapLoginController {

    private static final Logger LOGGER = LogManager.getLogger(LdapLoginController.class);

    @Autowired
    private LdapSysMgrService ldapSysMgrService;

    /**
     * 用户登录
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/user_login", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> userLogin(HttpServletRequest request, @RequestBody SysUserResponseModel model) {
        LOGGER.debug("userLogin begin");
        SysUserResponseModel rpsObj = new SysUserResponseModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || StringUtil.isNullOrEmpty(model.getAccount())
                || StringUtil.isNullOrEmpty(model.getPasswd())) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rpsObj.setPasswd(null);
            return new SmartHomeResponse(errCode, errMsg, rpsObj);
        }
        rpsObj = ldapSysMgrService.requestLdapLoginPst(model, LDAPConst.LoginUrl.LOGIN);
        rpsObj.setPasswd(null);
        LOGGER.debug("userLogin end");
        return new SmartHomeResponse(errCode, errMsg, rpsObj);
    }

    /**
     * 用户登出
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/user_logout", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> userLogout(HttpServletRequest request, @RequestBody SysUserResponseModel model) {
        LOGGER.debug("userLogout begin");
        SysUserResponseModel rpsObj = new SysUserResponseModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || StringUtil.isNullOrEmpty(model.getUserName())
                || StringUtil.isNullOrEmpty(model.getToken())) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            return new SmartHomeResponse(errCode, errMsg, rpsObj);
        }
        rpsObj = ldapSysMgrService.requestLdapLoginPst(model, LDAPConst.LoginUrl.LOGOUT);
        LOGGER.debug("userLogout end");
        return new SmartHomeResponse(errCode, errMsg, rpsObj);
    }

    /**
     * 检测用户登录状态
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/check_user_log_status", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> checkUserLogStatus(HttpServletRequest request,
            @RequestBody SysUserResponseModel model) {
        LOGGER.debug("checkUserLogStatus begin");
        SysUserResponseModel rpsObj = new SysUserResponseModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || StringUtil.isNullOrEmpty(model.getUserName())
                || StringUtil.isNullOrEmpty(model.getToken())) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            return new SmartHomeResponse(errCode, errMsg, rpsObj);
        }
        rpsObj = ldapSysMgrService.requestLdapLoginPst(model, LDAPConst.LoginUrl.CHECK_LOG_STATUS);
        LOGGER.debug("checkUserLogStatus end");
        return new SmartHomeResponse(errCode, errMsg, rpsObj);
    }

}