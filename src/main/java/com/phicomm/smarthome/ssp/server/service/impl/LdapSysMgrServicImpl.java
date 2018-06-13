package com.phicomm.smarthome.ssp.server.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.phicomm.smarthome.consts.Const;
import com.phicomm.smarthome.consts.LDAPConst;
import com.phicomm.smarthome.model.ldapsys.common.BaseResponseModel;
import com.phicomm.smarthome.model.ldapsys.request.RequestSysAuthorityModel;
import com.phicomm.smarthome.model.ldapsys.request.RequestSysMenuModel;
import com.phicomm.smarthome.model.ldapsys.request.RequestSysRoleModel;
import com.phicomm.smarthome.model.ldapsys.request.RequestSysUserModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysAuthorityListModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysAuthorityNeedInfoListModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysDpRoleInfoModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysMenuListModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysRoleListModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysUserAuthModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseSysUserInfoListModel;
import com.phicomm.smarthome.model.ldapsys.response.ResponseTreeAuthListModel;
import com.phicomm.smarthome.model.ldapsys.response.SysUserResponseModel;
import com.phicomm.smarthome.ssp.server.service.LdapSysMgrService;
import com.phicomm.smarthome.util.StringUtil;

/**
 * @author fujiang.mao
 *
 */
@Service
public class LdapSysMgrServicImpl implements LdapSysMgrService {

    public static final Logger LOGGER = LogManager.getLogger(LdapSysMgrServicImpl.class);

    @Value("${SMARTHOME_LDAP_MGR_URL}")
    private String smarthomeLdapMgrUrl;

    private static final int HTMGR_MARK_PID = LDAPConst.ProductMark.SHARWDWIFI_HTMGR_MARK_PID;

    @Override
    public SysUserResponseModel requestLdapLoginPst(SysUserResponseModel model, String reqUrl) {
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapLoginPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        model.setRetCode(retCode);
                        model.setRetMsg(retMsg);
                        // 用户信息获取成功
                        if (retCode.intValue() == 200) {
                            model = new SysUserResponseModel(resultJson);
                        }
                    }
                } else {
                    model.setRetCode(errCode);
                    model.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapLoginPst error " + e.getMessage());
            model.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            model.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return model;
    }

    @Override
    public BaseResponseModel requestLdapRolePst(RequestSysRoleModel model, String reqUrl) {
        BaseResponseModel baseModel = new BaseResponseModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapRolePst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        baseModel.setRetCode(retCode);
                        baseModel.setRetMsg(retMsg);
                    }
                } else {
                    baseModel.setRetCode(errCode);
                    baseModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapRolePst error " + e.getMessage());
            baseModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            baseModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return baseModel;
    }

    @Override
    public ResponseTreeAuthListModel requestLdapAddRoleNeedAuthInfoPst(RequestSysRoleModel model, String reqUrl) {
        ResponseTreeAuthListModel rpsModel = new ResponseTreeAuthListModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapAddRoleNeedAuthInfoPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        rpsModel.setRetCode(retCode);
                        rpsModel.setRetMsg(retMsg);
                        if (retCode.intValue() == 200) {
                            rpsModel = new ResponseTreeAuthListModel(resultJson);
                        }
                    }
                } else {
                    rpsModel.setRetCode(errCode);
                    rpsModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapAddRoleNeedAuthInfoPst error " + e.getMessage());
            rpsModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            rpsModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return rpsModel;
    }

    @Override
    public ResponseSysDpRoleInfoModel requestLdapSysRolesPst(SysUserResponseModel model, String reqUrl) {
        ResponseSysDpRoleInfoModel rspModel = new ResponseSysDpRoleInfoModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapSysRolesPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        rspModel.setRetCode(retCode);
                        rspModel.setRetMsg(retMsg);
                        // 信息获取成功
                        if (retCode.intValue() == 200) {
                            rspModel = new ResponseSysDpRoleInfoModel(resultJson);
                        }
                    }
                } else {
                    rspModel.setRetCode(errCode);
                    rspModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapSysRolesPst error " + e.getMessage());
            rspModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            rspModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return rspModel;
    }

    @Override
    public ResponseSysAuthorityNeedInfoListModel requestLdapAuthorityNeedInfoPst(RequestSysAuthorityModel model,
            String reqUrl) {
        ResponseSysAuthorityNeedInfoListModel rpsModel = new ResponseSysAuthorityNeedInfoListModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapAuthorityNeedInfoPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        rpsModel.setRetCode(retCode);
                        rpsModel.setRetMsg(retMsg);
                        // 信息获取成功
                        if (retCode.intValue() == 200) {
                            rpsModel = new ResponseSysAuthorityNeedInfoListModel(resultJson);
                        }
                    }
                } else {
                    rpsModel.setRetCode(errCode);
                    rpsModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapAuthorityNeedInfoPst error " + e.getMessage());
            rpsModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            rpsModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return rpsModel;
    }

    @Override
    public BaseResponseModel requestLdapAuthorityPst(RequestSysAuthorityModel model, String reqUrl) {
        BaseResponseModel baseModel = new BaseResponseModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapAuthorityPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        baseModel.setRetCode(retCode);
                        baseModel.setRetMsg(retMsg);
                    }
                } else {
                    baseModel.setRetCode(errCode);
                    baseModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapAuthorityPst error " + e.getMessage());
            baseModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            baseModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return baseModel;
    }

    @Override
    public ResponseSysAuthorityListModel requestLdapSysAuthoritysPst(RequestSysUserModel model, String reqUrl) {
        ResponseSysAuthorityListModel rspModel = new ResponseSysAuthorityListModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapSysAuthoritysPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        rspModel.setRetCode(retCode);
                        rspModel.setRetMsg(retMsg);
                        // 信息获取成功
                        if (retCode.intValue() == 200) {
                            rspModel = new ResponseSysAuthorityListModel(resultJson);
                        }
                    }
                } else {
                    rspModel.setRetCode(errCode);
                    rspModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapSysAuthoritysPst error " + e.getMessage());
            rspModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            rspModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return rspModel;
    }

    @Override
    public ResponseSysMenuListModel requestLdapSysMenuPst(RequestSysMenuModel model, String reqUrl) {
        ResponseSysMenuListModel rspModel = new ResponseSysMenuListModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapSysMenuPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        rspModel.setRetCode(retCode);
                        rspModel.setRetMsg(retMsg);
                        // 信息获取成功
                        if (retCode.intValue() == 200) {
                            rspModel = new ResponseSysMenuListModel(resultJson);
                        }
                    }
                } else {
                    rspModel.setRetCode(errCode);
                    rspModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapSysMenuPst error " + e.getMessage());
            rspModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            rspModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return rspModel;
    }

    @Override
    public ResponseSysUserAuthModel requestLdapSysMenuPst(RequestSysAuthorityModel model, String reqUrl) {
        ResponseSysUserAuthModel rspModel = new ResponseSysUserAuthModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapSysMenuPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        rspModel.setRetCode(retCode);
                        rspModel.setRetMsg(retMsg);
                        // 信息获取成功
                        if (retCode.intValue() == 200) {
                            rspModel = new ResponseSysUserAuthModel(resultJson);
                        }
                    }
                } else {
                    rspModel.setRetCode(errCode);
                    rspModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapSysMenuPst error " + e.getMessage());
            rspModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            rspModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return rspModel;
    }

    @Override
    public ResponseSysDpRoleInfoModel requestLdapSysMenuPst(RequestSysUserModel model, String reqUrl) {
        ResponseSysDpRoleInfoModel rspModel = new ResponseSysDpRoleInfoModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapSysMenuPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        rspModel.setRetCode(retCode);
                        rspModel.setRetMsg(retMsg);
                        // 信息获取成功
                        if (retCode.intValue() == 200) {
                            rspModel = new ResponseSysDpRoleInfoModel(resultJson);
                        }
                    }
                } else {
                    rspModel.setRetCode(errCode);
                    rspModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapSysMenuPst error " + e.getMessage());
            rspModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            rspModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return rspModel;
    }

    @Override
    public ResponseSysUserInfoListModel requestLdapSysUserInfoPst(RequestSysUserModel model, String reqUrl) {
        ResponseSysUserInfoListModel rspModel = new ResponseSysUserInfoListModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapSysUserInfoPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        rspModel.setRetCode(retCode);
                        rspModel.setRetMsg(retMsg);
                        // 信息获取成功
                        if (retCode.intValue() == 200) {
                            rspModel = new ResponseSysUserInfoListModel(resultJson);
                        }
                    }
                } else {
                    rspModel.setRetCode(errCode);
                    rspModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapSysUserInfoPst error " + e.getMessage());
            rspModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            rspModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return rspModel;
    }

    @Override
    public ResponseSysRoleListModel requestLdapQuerySysRoleInfoListPst(RequestSysRoleModel model, String reqUrl) {
        ResponseSysRoleListModel rspModel = new ResponseSysRoleListModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapQuerySysRoleInfoListPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        rspModel.setRetCode(retCode);
                        rspModel.setRetMsg(retMsg);
                        // 信息获取成功
                        if (retCode.intValue() == 200) {
                            rspModel = new ResponseSysRoleListModel(resultJson);
                        }
                    }
                } else {
                    rspModel.setRetCode(errCode);
                    rspModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapQuerySysRoleInfoListPst error " + e.getMessage());
            rspModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            rspModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return rspModel;
    }

    @Override
    public BaseResponseModel requestLdapSysDelUserPst(RequestSysUserModel model, String reqUrl) {
        BaseResponseModel rspModel = new BaseResponseModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapSysDelUserPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        rspModel.setRetCode(retCode);
                        rspModel.setRetMsg(retMsg);
                    }
                } else {
                    rspModel.setRetCode(errCode);
                    rspModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapSysDelUserPst error " + e.getMessage());
            rspModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            rspModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return rspModel;
    }

    @Override
    public ResponseSysAuthorityListModel requestLdapAuthorityInfoListPst(RequestSysAuthorityModel model,
            String reqUrl) {
        ResponseSysAuthorityListModel rspModel = new ResponseSysAuthorityListModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapQuerySysRoleInfoListPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        rspModel.setRetCode(retCode);
                        rspModel.setRetMsg(retMsg);
                        // 信息获取成功
                        if (retCode.intValue() == 200) {
                            rspModel = new ResponseSysAuthorityListModel(resultJson);
                        }
                    }
                } else {
                    rspModel.setRetCode(errCode);
                    rspModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapQuerySysRoleInfoListPst error " + e.getMessage());
            rspModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            rspModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return rspModel;
    }

    @Override
    public ResponseSysUserAuthModel requestLdapCheckSysUserPageAuthPst(RequestSysAuthorityModel model, String reqUrl) {
        ResponseSysUserAuthModel rspModel = new ResponseSysUserAuthModel();
        String httpEntity = "";
        try {
            model.setPid(HTMGR_MARK_PID);
            String sendUrl = smarthomeLdapMgrUrl + reqUrl;
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(model);
            HttpEntity<String> formEntity = new HttpEntity<String>(jsonObject.toString(), headers);
            httpEntity = restTemplate.postForObject(sendUrl, formEntity, String.class);
            LOGGER.debug("requestLdapCheckSysUserPageAuthPst httpEntity---------->" + httpEntity);
            // 如果返回的Entity中不为空则将其转化为Json对象并取出响应的变量
            if (!StringUtil.isNullOrEmpty(httpEntity)) {
                JSONObject parseObject = JSON.parseObject(httpEntity);
                Integer errCode = (Integer) parseObject.get("errCode");
                String errMsg = (String) parseObject.get("errMsg");
                // LDAP服务器请求成功
                if (errCode.intValue() == 200) {
                    JSONObject resultJson = (JSONObject) parseObject.get("result");
                    if (resultJson != null) {
                        Integer retCode = (Integer) resultJson.get("retCode");
                        String retMsg = (String) resultJson.get("retMsg");
                        rspModel.setRetCode(retCode);
                        rspModel.setRetMsg(retMsg);
                        // 信息获取成功
                        if (retCode.intValue() == 200) {
                            rspModel = new ResponseSysUserAuthModel(resultJson);
                        }
                    }
                } else {
                    rspModel.setRetCode(errCode);
                    rspModel.setRetMsg(errMsg);
                }
            }
        } catch (Exception e) {
            LOGGER.error("requestLdapCheckSysUserPageAuthPst error " + e.getMessage());
            rspModel.setRetCode(Const.ResponseStatus.STATUS_COMMON_FAILED);
            rspModel.setRetMsg(Const.ResponseStatus.STATUS_COMMON_FAILED_STR);
        }
        return rspModel;
    }

}
