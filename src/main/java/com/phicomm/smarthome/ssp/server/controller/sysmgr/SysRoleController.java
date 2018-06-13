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
import com.phicomm.smarthome.model.ldapsys.request.RequestSysRoleModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysDpRoleInfoModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysRoleListModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseTreeAuthListModel;
import com.phicomm.smarthome.model.ldapsys.response.SysUserResponseModel;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.service.LdapSysMgrService;
import com.phicomm.smarthome.util.StringUtil;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 系统角色controller
 * 
 * @author fujiang.mao
 *
 */
@RestController
@ComponentScan
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class SysRoleController {

    private static final Logger LOGGER = LogManager.getLogger(SysRoleController.class);

    @Autowired
    private LdapSysMgrService ldapMgrService;

    /**
     * 添加角色所需权限信息
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/query_role_need_auth_info", method = RequestMethod.POST, produces = {
            "application/json" })
    public SmartHomeResponse<Object> queryRoleNeedAuthInfo(HttpServletRequest request,
            @RequestBody RequestSysRoleModel model) {
        LOGGER.debug("queryRoleNeedAuthInfo begin");
        ResponseTreeAuthListModel rpsModel = new ResponseTreeAuthListModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        rpsModel = ldapMgrService.requestLdapAddRoleNeedAuthInfoPst(model,
                LDAPConst.SysRoleUrl.QUERY_ROLE_NEED_AUTH_INFO);
        LOGGER.debug("queryRoleNeedAuthInfo end");
        return new SmartHomeResponse(errCode, errMsg, rpsModel);
    }

    /**
     * 添加角色
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/add_role", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> addRole(HttpServletRequest request, @RequestBody RequestSysRoleModel model) {
        LOGGER.debug("addRole begin");
        BaseResponseModel baseModel = new BaseResponseModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || StringUtil.isNullOrEmpty(model.getUserName()) || StringUtil.isNullOrEmpty(model.getToken())
                || StringUtil.isNullOrEmpty(model.getName()) || StringUtil.isNullOrEmpty(model.getAuthIds())
                || model.getLastOptUid() == 0) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            baseModel.setRetCode(errCode);
            baseModel.setRetMsg(errMsg);
            return new SmartHomeResponse(errCode, errMsg, baseModel);
        }
        baseModel = ldapMgrService.requestLdapRolePst(model, LDAPConst.SysRoleUrl.ADD_ROLE);
        LOGGER.debug("addRole end");
        return new SmartHomeResponse(errCode, errMsg, baseModel);
    }

    /**
     * 查询角色列表
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/query_sys_role_info_list", method = RequestMethod.POST, produces = {
            "application/json" })
    public SmartHomeResponse<Object> querySysRoleInfoList(HttpServletRequest request,
            @RequestBody RequestSysRoleModel model) {
        LOGGER.debug("querySysRoleInfoList begin");
        ResponseSysRoleListModel rpsModel = new ResponseSysRoleListModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || model.getPageSize() < 1 || model.getCurPage() < 1 || model.getLastOptUid() == 0) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rpsModel.setRetCode(errCode);
            rpsModel.setRetMsg(errMsg);
            return new SmartHomeResponse(errCode, errMsg, rpsModel);
        }
        rpsModel = ldapMgrService.requestLdapQuerySysRoleInfoListPst(model,
                LDAPConst.SysRoleUrl.QUERY_SYS_ROLE_INFO_LIST);
        LOGGER.debug("querySysRoleInfoList end");
        return new SmartHomeResponse(errCode, errMsg, rpsModel);
    }

    /**
     * 编辑角色
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/edit_role", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> editRole(HttpServletRequest request, @RequestBody RequestSysRoleModel model) {
        LOGGER.debug("editRole begin");
        BaseResponseModel baseModel = new BaseResponseModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || StringUtil.isNullOrEmpty(model.getUserName()) || StringUtil.isNullOrEmpty(model.getToken())
                || model.getId() == 0 || StringUtil.isNullOrEmpty(model.getAuthIds()) || model.getLastOptUid() == 0) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            baseModel.setRetCode(errCode);
            baseModel.setRetMsg(errMsg);
            return new SmartHomeResponse(errCode, errMsg, baseModel);
        }
        baseModel = ldapMgrService.requestLdapRolePst(model, LDAPConst.SysRoleUrl.EDIT_ROLE);
        LOGGER.debug("editRole end");
        return new SmartHomeResponse(errCode, errMsg, baseModel);
    }

    /**
     * 删除角色
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/del_role", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> delRole(HttpServletRequest request, @RequestBody RequestSysRoleModel model) {
        LOGGER.debug("delRole begin");
        BaseResponseModel baseModel = new BaseResponseModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || StringUtil.isNullOrEmpty(model.getUserName()) || StringUtil.isNullOrEmpty(model.getToken())
                || model.getId() == 0 || model.getLastOptUid() == 0) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            baseModel.setRetCode(errCode);
            baseModel.setRetMsg(errMsg);
            return new SmartHomeResponse(errCode, errMsg, baseModel);
        }
        baseModel = ldapMgrService.requestLdapRolePst(model, LDAPConst.SysRoleUrl.DEL_ROLE);
        LOGGER.debug("delRole end");
        return new SmartHomeResponse(errCode, errMsg, baseModel);
    }

    /**
     * 查询系统所有角色信息
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/query_sys_role_info", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> querySysRoleInfo(HttpServletRequest request,
            @RequestBody SysUserResponseModel model) {
        LOGGER.debug("querySysRoleInfo begin");
        ResponseSysDpRoleInfoModel rpsModel = new ResponseSysDpRoleInfoModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        rpsModel = ldapMgrService.requestLdapSysRolesPst(model, LDAPConst.SysRoleUrl.QUERY_SYS_ROLE_INFO);
        LOGGER.debug("querySysRoleInfo end");
        return new SmartHomeResponse(errCode, errMsg, rpsModel);
    }

    /**
     * 更新用户的角色
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/update_user_roles", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> updateUserRoles(HttpServletRequest request,
            @RequestBody RequestSysRoleModel model) {
        LOGGER.debug("updateUserRoles begin");
        BaseResponseModel baseModel = new BaseResponseModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || StringUtil.isNullOrEmpty(model.getUserName()) || StringUtil.isNullOrEmpty(model.getToken())
                || StringUtil.isNullOrEmpty(model.getRoleIds()) || model.getLastOptUid() == 0) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            baseModel.setRetCode(errCode);
            baseModel.setRetMsg(errMsg);
            return new SmartHomeResponse(errCode, errMsg, baseModel);
        }
        baseModel = ldapMgrService.requestLdapRolePst(model, LDAPConst.SysRoleUrl.UPDATE_USER_ROLES);
        LOGGER.debug("updateUserRoles end");
        return new SmartHomeResponse(errCode, errMsg, baseModel);
    }

}
