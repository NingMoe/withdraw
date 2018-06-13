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
import com.phicomm.smarthome.model.ldapsys.common.BaseResponseModel;
import com.phicomm.smarthome.model.ldapsys.request.RequestSysAuthorityModel;
import com.phicomm.smarthome.model.ldapsys.request.RequestSysMenuModel;
import com.phicomm.smarthome.model.ldapsys.request.RequestSysUserModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysDpRoleInfoModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysMenuListModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysUserAuthModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysUserInfoListModel;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.service.LdapSysMgrService;
import com.phicomm.smarthome.util.StringUtil;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 系统用户controller
 * 
 * @author fujiang.mao
 *
 */
@RestController
@ComponentScan
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class SysUserController {

    private static final Logger LOGGER = LogManager.getLogger(SysUserController.class);

    @Autowired
    private LdapSysMgrService ldapMgrService;

    /**
     * 查询系统菜单
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/query_sys_menu", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> querySysMenu(HttpServletRequest request, @RequestBody RequestSysMenuModel model) {
        LOGGER.debug("querySysMenu begin");
        ResponseSysMenuListModel rpsModel = new ResponseSysMenuListModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        rpsModel = ldapMgrService.requestLdapSysMenuPst(model, LDAPConst.SysUserInfoUrl.QUERY_SYS_MENU);
        LOGGER.debug("querySysMenu end");
        return new SmartHomeResponse(errCode, errMsg, rpsModel);
    }

    /**
     * 查询用户菜单
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/query_user_menus", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> queryUserMenus(HttpServletRequest request,
            @RequestBody RequestSysMenuModel model) {
        LOGGER.debug("queryUserMenus begin");
        ResponseSysMenuListModel rpsModel = new ResponseSysMenuListModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || model.getLastOptUid() == 0) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rpsModel.setRetCode(errCode);
            rpsModel.setRetMsg(errMsg);
            return new SmartHomeResponse(errCode, errMsg, rpsModel);
        }
        rpsModel = ldapMgrService.requestLdapSysMenuPst(model, LDAPConst.SysUserInfoUrl.QUERY_USER_MENUS);
        LOGGER.debug("queryUserMenus end");
        return new SmartHomeResponse(errCode, errMsg, rpsModel);
    }

    /**
     * 账户详情搜索条件信息
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/query_sys_dprole_info", method = RequestMethod.POST, produces = {
            "application/json" })
    public SmartHomeResponse<Object> querySysDproleInfo(HttpServletRequest request,
            @RequestBody RequestSysUserModel model) {
        LOGGER.debug("querySysDproleInfo begin");
        ResponseSysDpRoleInfoModel rpsModel = new ResponseSysDpRoleInfoModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        rpsModel = ldapMgrService.requestLdapSysMenuPst(model, LDAPConst.SysUserInfoUrl.QUERY_SYS_DPROLE_INFO);
        LOGGER.debug("querySysDproleInfo end");
        return new SmartHomeResponse(errCode, errMsg, rpsModel);
    }

    /**
     * 查询系统用户信息
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/query_sys_user_info", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> querySysUserInfo(HttpServletRequest request,
            @RequestBody RequestSysUserModel model) {
        LOGGER.debug("querySysUserInfo begin");
        ResponseSysUserInfoListModel rpsModel = new ResponseSysUserInfoListModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || model.getPageSize() < 1 || model.getCurPage() < 1 || model.getLastOptUid() == 0) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            return new SmartHomeResponse(errCode, errMsg, rpsModel);
        }
        rpsModel = ldapMgrService.requestLdapSysUserInfoPst(model, LDAPConst.SysUserInfoUrl.QUERY_SYS_USER_INFO);
        LOGGER.debug("querySysUserInfo end");
        return new SmartHomeResponse(errCode, errMsg, rpsModel);
    }

    /**
     * 删除用户
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/del_user", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> delUser(HttpServletRequest request, @RequestBody RequestSysUserModel model) {
        LOGGER.debug("delUser begin");
        BaseResponseModel rpsModel = new BaseResponseModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        rpsModel = ldapMgrService.requestLdapSysDelUserPst(model, LDAPConst.SysUserInfoUrl.DEL_USER);
        LOGGER.debug("delUser end");
        return new SmartHomeResponse(errCode, errMsg, rpsModel);
    }

    /**
     * 校验用户页面权限
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/check_sys_user_page_auth", method = RequestMethod.POST, produces = {
            "application/json" })
    public SmartHomeResponse<Object> checkSysUserPageAuth(HttpServletRequest request,
            @RequestBody RequestSysAuthorityModel model) {
        LOGGER.debug("checkSysUserPageAuth begin");
        ResponseSysUserAuthModel rpsModel = new ResponseSysUserAuthModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || StringUtil.isNullOrEmpty(model.getUserName()) || StringUtil.isNullOrEmpty(model.getToken())
                || StringUtil.isNullOrEmpty(model.getPageUrl()) || model.getLastOptUid() == 0) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rpsModel.setRetCode(errCode);
            rpsModel.setRetMsg(errMsg);
            return new SmartHomeResponse(errCode, errMsg, rpsModel);
        }
        rpsModel = ldapMgrService.requestLdapCheckSysUserPageAuthPst(model,
                LDAPConst.SysUserInfoUrl.CHECK_SYS_USER_PAGE_AUTH);
        LOGGER.debug("checkSysUserPageAuth end");
        return new SmartHomeResponse(errCode, errMsg, rpsModel);
    }

}
