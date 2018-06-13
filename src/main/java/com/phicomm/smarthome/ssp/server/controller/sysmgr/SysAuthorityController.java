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
import com.phicomm.smarthome.model.ldapsys.request.RequestSysAuthorityModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysAuthorityListModel;
import com.phicomm.smarthome.ssp.server.consts.Const;
import com.phicomm.smarthome.ssp.server.service.LdapSysMgrService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 系统权限controller
 * 
 * @author fujiang.mao
 *
 */
@RestController
@ComponentScan
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class SysAuthorityController {

    private static final Logger LOGGER = LogManager.getLogger(SysAuthorityController.class);

    @Autowired
    private LdapSysMgrService ldapMgrService;

    /**
     * 添加权限所需信息
     * 
     * @param request
     * @param model
     * @return
     */
    /*@SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/add_authority_need_info", method = RequestMethod.POST, produces = {
            "application/json" })
    public SmartHomeResponse<Object> addAuthorityNeedInfo(HttpServletRequest request,
            @RequestBody RequestSysAuthorityModel model) {
        LOGGER.debug("addAuthorityNeedInfo begin");
        ResponseSysAuthorityNeedInfoListModel rpsObj = new ResponseSysAuthorityNeedInfoListModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        rpsObj = ldapMgrService.requestLdapAuthorityNeedInfoPst(model, LDAPConst.SysAuthUrl.ADD_AUTHORITY_NEED_INFO);
        LOGGER.debug("addAuthorityNeedInfo end");
        return new SmartHomeResponse(errCode, errMsg, rpsObj);
    }*/

    /**
     * 添加权限
     * 
     * @param request
     * @param model
     * @return
     */
    /*@SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/add_authority", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> addAuthority(HttpServletRequest request,
            @RequestBody RequestSysAuthorityModel model) {
        LOGGER.debug("addAuthority begin");
        BaseResponseModel baseModel = new BaseResponseModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || StringUtil.isNullOrEmpty(model.getUserName()) || StringUtil.isNullOrEmpty(model.getToken())
                || model.getClassify() == 0 || model.getOptType() == 0 || model.getDetail() == 0
                || model.getLastOptUid() == 0) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            baseModel.setRetCode(errCode);
            baseModel.setRetMsg(errMsg);
            return new SmartHomeResponse(errCode, errMsg, baseModel);
        }
        baseModel = ldapMgrService.requestLdapAuthorityPst(model, LDAPConst.SysAuthUrl.ADD_AUTHORITY);
        LOGGER.debug("addAuthority end");
        return new SmartHomeResponse(errCode, errMsg, baseModel);
    }*/

    /**
     * 查询权限列表
     * 
     * @param request
     * @param model
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/query_sys_authority_info_list", method = RequestMethod.POST, produces = {
            "application/json" })
    public SmartHomeResponse<Object> querySysAuthorityInfoList(HttpServletRequest request,
            @RequestBody RequestSysAuthorityModel model) {
        LOGGER.debug("querySysAuthorityInfoList begin");
        ResponseSysAuthorityListModel rpsModel = new ResponseSysAuthorityListModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || model.getPageSize() < 1 || model.getCurPage() < 1) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            rpsModel.setRetCode(errCode);
            rpsModel.setRetMsg(errMsg);
            return new SmartHomeResponse(errCode, errMsg, rpsModel);
        }
        rpsModel = ldapMgrService.requestLdapAuthorityInfoListPst(model,
                LDAPConst.SysAuthUrl.QUERY_SYS_AUTHORITY_INFO_LIST);
        LOGGER.debug("querySysAuthorityInfoList end");
        return new SmartHomeResponse(errCode, errMsg, rpsModel);
    }

    /**
     * 编辑权限
     * 
     * @param request
     * @param model
     * @return
     */
    /*@SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/edit_authority", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> editAuthority(HttpServletRequest request,
            @RequestBody RequestSysAuthorityModel model) {
        LOGGER.debug("editAuthority begin");
        BaseResponseModel baseModel = new BaseResponseModel();
        int errCode = Const.ResponseStatus.STATUS_OK;
        String errMsg = Const.ResponseStatus.STATUS_OK_STR;
        if (model == null || StringUtil.isNullOrEmpty(model.getUserName()) || StringUtil.isNullOrEmpty(model.getToken())
                || model.getId() == 0 || model.getClassify() == 0 || model.getOptType() == 0 || model.getDetail() == 0
                || model.getLastOptUid() == 0) {
            errCode = Const.ResponseStatus.STATUS_COMMON_NULL;
            errMsg = Const.ResponseStatus.STATUS_COMMON_NULL_STR;
            baseModel.setRetCode(errCode);
            baseModel.setRetMsg(errMsg);
            return new SmartHomeResponse(errCode, errMsg, baseModel);
        }
        baseModel = ldapMgrService.requestLdapAuthorityPst(model, LDAPConst.SysAuthUrl.EDIT_AUTHORITY);
        LOGGER.debug("editAuthority end");
        return new SmartHomeResponse(errCode, errMsg, baseModel);
    }*/

    /**
     * 删除权限
     * 
     * @param request
     * @param model
     * @return
     */
    /*@SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "/sys/del_authority", method = RequestMethod.POST, produces = { "application/json" })
    public SmartHomeResponse<Object> delAuthority(HttpServletRequest request,
            @RequestBody RequestSysAuthorityModel model) {
        LOGGER.debug("delAuthority begin");
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
        baseModel = ldapMgrService.requestLdapAuthorityPst(model, LDAPConst.SysAuthUrl.DEL_AUTHORITY);
        LOGGER.debug("delAuthority end");
        return new SmartHomeResponse(errCode, errMsg, baseModel);
    }*/

}
